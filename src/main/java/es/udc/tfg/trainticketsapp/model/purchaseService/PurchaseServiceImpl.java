package es.udc.tfg.trainticketsapp.model.purchaseService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.car.CarDao;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.fare.FareDao;
import es.udc.tfg.trainticketsapp.model.passenger.Passenger;
import es.udc.tfg.trainticketsapp.model.passenger.PassengerDao;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase.PaymentMethod;
import es.udc.tfg.trainticketsapp.model.purchase.PurchaseDao;
import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.route.RouteDao;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.stop.StopDao;
import es.udc.tfg.trainticketsapp.model.ticket.Ticket;
import es.udc.tfg.trainticketsapp.model.ticket.TicketDao;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfileDao;
import es.udc.tfg.trainticketsapp.model.util.exceptions.NotEmpySeatsException;
import es.udc.tfg.trainticketsapp.model.util.exceptions.TimeoutTicketException;
import es.udc.tfg.trainticketsapp.model.util.MailService;

@Service("purchaseService")
@Transactional(rollbackFor = { NotEmpySeatsException.class })
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseDao purchaseDao;
	@Autowired
	private PassengerDao passengerDao;
	@Autowired
	private FareDao fareDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private UserProfileDao userProfileDao;
	@Autowired
	private StopDao stopDao;
	@Autowired
	private CarDao carDao;
	@Autowired
	private RouteDao routeDao;

	public Ticket findTicket(Long ticketId) throws InstanceNotFoundException {
		return ticketDao.find(ticketId);
	}

	public Purchase findPurchase(Long purchaseId)
			throws InstanceNotFoundException {
		return purchaseDao.find(purchaseId);
	}

	@Transactional(readOnly = true)
	public Fare findFare(Long id) throws InstanceNotFoundException {
		return fareDao.find(id);
	}

	@Transactional(readOnly = true)
	public List<Fare> findFares() {
		return fareDao.findAllFares();
	}

	@Transactional(readOnly = true)
	public List<Fare> findFareBytype(String type) {
		return fareDao.findByType(type);
	}

	public Fare createFare(String fareName, String fareDescription,
			String fareType, int discount) throws DuplicateInstanceException {
		try {
			fareDao.findByName(fareName);
			throw new DuplicateInstanceException(fareName, Fare.class.getName());
		} catch (InstanceNotFoundException e) {
			Fare fare = new Fare(fareName, fareDescription, discount, fareType);
			fareDao.save(fare);
			return fare;
		}
	}

	public void updateFare(Long fareId, String fareName,
			String fareDescription, String fareType, int discount)
			throws InstanceNotFoundException {
		Fare fare = fareDao.find(fareId);
		fare.setDescription(fareDescription);
		fare.setDiscount(discount);
		fare.setFareName(fareName);
		fare.setTypeFare(fareType);
	}

	public Purchase buyTickets(PaymentMethod paymentMethod, Long userId,
			Calendar ticketsDate, Calendar ticketsDateReturn, Long origin,
			Long destination, Long originReturn, Long destinationReturn,
			List<TicketDetails> tickets, CarType carType, CarType carTypeReturn)
			throws InstanceNotFoundException, NotEmpySeatsException {
		boolean outreturn = (ticketsDateReturn != null);
		UserProfile user = userProfileDao.find(userId);
		Stop stopOrigin = stopDao.find(origin);
		Stop stopDestination = stopDao.find(destination);
		Stop stopOriginReturn = null;
		Stop stopDestinationReturn = null;
		if (outreturn) {
			stopOriginReturn = stopDao.find(originReturn);
			stopDestinationReturn = stopDao.find(destinationReturn);
		}

		Purchase purchase = new Purchase(Calendar.getInstance(), paymentMethod,
				user);
		purchaseDao.save(purchase);
		for (TicketDetails d : tickets) {
			Passenger passenger = new Passenger(0, d.getFirstName(),
					d.getLastName(), d.getEmail(), d.getDni());
			passengerDao.save(passenger);
			if (outreturn) {
				setSeat(ticketsDateReturn, carTypeReturn, stopOriginReturn
						.getRoute().getRouteId(), d, true);
				Ticket ticketReturn = new Ticket(new Float(0),
						d.getSeatReturn(), ticketsDateReturn, d.getCarReturn(),
						passenger, stopDestinationReturn, stopOriginReturn);
				ticketReturn.addFare(d.getFareFamily());
				purchase.addTicket(ticketReturn);
				ticketDao.save(ticketReturn);
			}
			setSeat(ticketsDate, carType, stopOrigin.getRoute().getRouteId(),
					d, false);
			Ticket ticket = new Ticket(new Float(0), d.getSeat(), ticketsDate,
					d.getCar(), passenger, stopDestination, stopOrigin);
			ticket.addFare(d.getFareFamily());
			purchase.addTicket(ticket);
			ticketDao.save(ticket);
		}
		return purchase;
	}

	private int getNumber(List<Integer> exclude, int n) {
		int number = -1;
		for (int i = 0; i < n; i++) {
			if (exclude.contains(i)) {
			} else {
				number = i;
				return number;
			}
		}
		return number;
	}

	private void setSeat(Calendar ticketDate, CarType carType, Long routeId,
			TicketDetails ticketDetails, boolean outreturn)
			throws InstanceNotFoundException, NotEmpySeatsException {
		int num = -1;
		if (ticketDetails.getCar() != null)
			return;
		Route route = routeDao.find(routeId);
		List<Car> cars = carDao.findCarsByTrain(carType, route.getTrain()
				.getTrainId());
		for (Car c : cars) {
			num = getNumber(ticketDao.findOccupiedSeats(ticketDate,
					c.getCarId(), routeId), c.getCapacity());
			if (num == -1) {
				throw new NotEmpySeatsException(routeId, ticketDate);

			}
			if (outreturn) {
				ticketDetails.setCarReturn(c);
				ticketDetails.setSeatReturn(num);
			} else {
				ticketDetails.setCar(c);
				ticketDetails.setSeat(num);
			}
		}
	}

	@Transactional(readOnly = true)
	public TicketBlock showUserTickets(Long userId, int startIndex, int count) {
		/*
		 * Find count+1 tickets to determine if there exist more tickets above
		 * the specified range.
		 */
		List<Ticket> tickets = ticketDao.findTicketsUser(userId, startIndex,
				count + 1);
		boolean existMoreTickets = tickets.size() == (count + 1);
		/*
		 * Remove the last account from the returned list if there exist more
		 * accounts above the specified range.
		 */
		if (existMoreTickets) {
			tickets.remove(tickets.size() - 1);
		}

		return new TicketBlock(tickets, existMoreTickets);
	}

	@Transactional(readOnly = true)
	public List<Purchase> findPurchaseByUserId(Long userId, int startIndex,
			int count) throws InstanceNotFoundException {
		userProfileDao.find(userId);
		return purchaseDao.findPurchasesByUser(userId, startIndex, count);
	}

	@Transactional(readOnly = true)
	public int getNumberOfPurchases(Long userId)
			throws InstanceNotFoundException {
		userProfileDao.find(userId);
		return purchaseDao.getNumberOfPurchases(userId);
	}

	public List<CarInfo> findCars(Calendar ticketDate, CarType carType,
			Long stopId) throws InstanceNotFoundException {

		List<CarInfo> carlist = new ArrayList<CarInfo>();
		Route route = stopDao.find(stopId).getRoute();
		List<Car> cars = carDao.findCarsByTrain(carType, route.getTrain()
				.getTrainId());
		List<Integer> list;
		for (Car c : cars) {
			List<Integer> seats = new ArrayList<Integer>();
			list = ticketDao.findOccupiedSeats(ticketDate, c.getCarId(),
					route.getRouteId());
			for (int i = 0; i < c.getCapacity(); i++) {
				if (list.contains(i))
					seats.add(1);
				else
					seats.add(0);
			}

			carlist.add(new CarInfo(c, seats));
		}
		return carlist;
	}

	public float calculatePayment(Long id, Long idReturn,
			List<TicketDetails> ticketDetails, CarType carType,
			CarType carTypeReturn) throws InstanceNotFoundException {
		float total = 0;
		float amount = 0;
		Float price = stopDao.find(id).getRoute().getPrice();
		Fare classTravel = fareDao.findByName(carType.toString());
		price = price + classTravel.getDiscount() * price / 100;
		if (idReturn != null) {
			float price2 = stopDao.find(idReturn).getRoute().getPrice();
			Fare classTravelReturn = fareDao.findByName(carTypeReturn
					.toString());
			price = price + classTravelReturn.getDiscount() * price / 100;
			price = price + price2;
		}
		for (TicketDetails t : ticketDetails) {
			amount = price;
			if (t.getFareFamily() != null)
				amount = (amount + (t.getFareFamily().getDiscount() * amount) / 100);
			total = total + amount;
		}
		return total;
	}

	public void cancelTicket(Long ticketId) throws InstanceNotFoundException,
			TimeoutTicketException {
		Ticket ticket = ticketDao.find(ticketId);
		Purchase purchase = ticket.getPurchase();
		Calendar ticketDate = ticket.getTicketDate();
		Calendar hora = Calendar.getInstance();
		hora.setTimeInMillis(ticket.getOrigin().getDepartTime());
		ticketDate.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
		ticketDate.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
		ticketDate.add(Calendar.HOUR_OF_DAY, 2);
		if (ticketDate.before(Calendar.getInstance())) {
			throw new TimeoutTicketException(ticketId, ticketDate);
		} else {
			if (purchase != null) {
				purchase.removeTicket(ticket);
			}
			ticketDao.remove(ticket.getTicketId());
			if (purchase != null) {
				if (purchase.getTickets().isEmpty())
					purchaseDao.remove(purchase.getPurchaseId());
			}
		}
	}

	public void sendTickets(Long purchaseId) throws InstanceNotFoundException {
		Purchase purchase = purchaseDao.find(purchaseId);
		List<Ticket> tickets = purchase.getTickets();
		Iterator<Ticket> it1 = tickets.iterator();
		boolean roundTrip = false;
		if (tickets.size() > 1) {
			roundTrip = tickets.get(0).getPassenger()
					.equals(tickets.get(1).getPassenger());
		}
		while (it1.hasNext()) {
			Ticket ticket = it1.next();
			String srtmessage = formatTicket(ticket.getTicketId(),
					ticket.getTicketDate(), ticket.getOrigin().getDepartTime(),
					ticket.getDestination().getArrivalTime(), ticket.getSeat(),
					ticket.getCar().getTrain().getTrainName(), ticket.getCar()
							.getCarNum());
			if (roundTrip) {
				ticket = it1.next();
				srtmessage = srtmessage
						+ formatTicket(ticket.getTicketId(),
								ticket.getTicketDate(), ticket.getOrigin()
										.getDepartTime(), ticket
										.getDestination().getArrivalTime(),
								ticket.getSeat(), ticket.getCar().getTrain()
										.getTrainName(), ticket.getCar()
										.getCarNum());

			}
			MailService.sendMessage(ticket.getPassenger().getEmail(),
					srtmessage);
		}
	}

	private String formatTicket(Long id, Calendar ticketDate, Long departTime,
			Long arrivalTime, int seat, String trainName, int carNum) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat dateformat = DateFormat.getTimeInstance(DateFormat.SHORT);
		return "<table bgcolor=\"#D3D3D3\"><tr><th>TicketId</th><td>" + id
				+ "</td></tr>" + "<tr><th>Date</th><td>"
				+ sdf.format(ticketDate.getTime()) + "</td></tr>"
				+ "<tr><th>Departure</th><td>" + dateformat.format(departTime)
				+ "</td></tr>" + "<tr><th>Arrival</th><td>"
				+ dateformat.format(arrivalTime) + "</td></tr>"
				+ "<tr><th>Seat</th><td>" + seat + "</td></tr>"
				+ "<tr><th>Train</th><td>" + trainName + "</td></tr>"
				+ "<tr><th>Car</th><td>" + carNum
				+ "</td></tr></table><br><br>";
	}
}
