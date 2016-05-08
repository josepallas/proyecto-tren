package es.udc.tfg.trainticketsapp.model.purchaseService;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.car.CarDao;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.fare.FareDao;
import es.udc.tfg.trainticketsapp.model.passenger.Passenger;
import es.udc.tfg.trainticketsapp.model.passenger.PassengerDao;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase;
import es.udc.tfg.trainticketsapp.model.purchase.PurchaseDao;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase.PaymentMethod;
import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.route.RouteDao;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.stop.StopDao;
import es.udc.tfg.trainticketsapp.model.ticket.Ticket;
import es.udc.tfg.trainticketsapp.model.ticket.TicketDao;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfileDao;
import es.udc.tfg.trainticketsapp.model.util.exceptions.TimeoutTicketException;


@Service("purchaseService")
@Transactional
public class PurchaseServiceImpl implements PurchaseService{

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
    public Purchase buyTickets(PaymentMethod paymentMethod, Long userId, Calendar
    		ticketsDate,Long origin, Long destination, List<TicketDetails> tickets) throws InstanceNotFoundException{
    	UserProfile user=userProfileDao.find(userId);
    	Stop stopOrigin=stopDao.find(origin);
    	Stop destinationOrigin=stopDao.find(destination);
    	Purchase purchase=new Purchase(Calendar.getInstance(), paymentMethod,user); 
    	purchaseDao.save(purchase);
   		for (TicketDetails d:tickets) {
   			Passenger passenger=new Passenger(0, d.getFirstName(),d.getLastName(),d.getEmail(),d.getDni());
   			passengerDao.save(passenger);
   			setSeat(ticketsDate,d.getCarType(),stopOrigin.getRoute().getRouteId(),d);
   			Ticket ticket=new Ticket(new Float(0), d.getSeat(), ticketsDate,
   				d.getCar(),passenger,destinationOrigin,stopOrigin);
   			ticket.setFares(d.getFare());
   			purchase.addTicket(ticket);
			ticketDao.save(ticket);
		}
    	return purchase;
    }
    
	@Transactional(readOnly = true)
    public List<Ticket> showUserTickets(Long userId) {
    	return ticketDao.findTicketsUser(userId);
    }
	
	@Transactional(readOnly = true)
	public List<Purchase> findPurchaseByUserId(Long userId, int startIndex, int count) throws InstanceNotFoundException {
		userProfileDao.find(userId);
		return purchaseDao.findPurchasesByUser(userId, startIndex, count);
	}
	@Transactional(readOnly = true)
	public int getNumberOfPurchases(Long userId) throws InstanceNotFoundException  {
		userProfileDao.find(userId);
		return purchaseDao.getNumberOfPurchases(userId);
	}

	private int getNumber(List<Integer> exclude,int n) {
		int number=0;
		for (int i=1;i<=n;i++) {
			if (exclude.contains(i)) {		
			}
			else {
			number=i;
			return number;
			}
		}
		return number;
	}
	
	private void setSeat(Calendar ticketDate,CarType carType,Long routeId,TicketDetails ticketDetails) throws InstanceNotFoundException {
		int num=0;
		Route route=routeDao.find(routeId);
		List<Car>cars=carDao.findCarsByTrain(carType, route.getTrain().getTrainId());
		for (Car c:cars) {
		num=getNumber(ticketDao.findOccupedSeats(ticketDate, carType, routeId),c.getCapacity());
		if (num!=0)
			ticketDetails.setCar(c);
			ticketDetails.setSeat(num);
			return;
		}
	}
    
    public void cancelTicket(Long ticketId) throws InstanceNotFoundException, TimeoutTicketException {
    	Ticket ticket=ticketDao.find(ticketId);
    	Purchase purchase=ticket.getPurchase();
    	Calendar ticketDate=ticket.getTicketDate();
    	Calendar hora=Calendar.getInstance();
    	hora.setTimeInMillis(ticket.getOrigin().getDepartTime());
    	ticketDate.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
    	ticketDate.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
    	ticketDate.add(Calendar.HOUR_OF_DAY, 2);
    	if (ticketDate.after(Calendar.getInstance())){
    		throw new TimeoutTicketException(ticketId,ticketDate);
    	}
    	else { 
        if (purchase != null) {
            purchase.removeTicket(ticket);
        }
        ticketDao.remove(ticket.getTicketId());
    	}
    }
}
