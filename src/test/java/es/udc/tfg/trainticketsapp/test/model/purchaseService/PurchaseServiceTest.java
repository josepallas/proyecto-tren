package es.udc.tfg.trainticketsapp.test.model.purchaseService;

import static es.udc.tfg.trainticketsapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.tfg.trainticketsapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.purchaseService.TicketDetails;
import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.route.RouteDao;
import es.udc.tfg.trainticketsapp.model.station.Station;
import es.udc.tfg.trainticketsapp.model.station.StationDao;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.stop.StopDao;
import es.udc.tfg.trainticketsapp.model.ticket.Ticket;
import es.udc.tfg.trainticketsapp.model.ticket.TicketDao;
import es.udc.tfg.trainticketsapp.model.train.Train;
import es.udc.tfg.trainticketsapp.model.train.Train.TrainType;
import es.udc.tfg.trainticketsapp.model.train.TrainDao;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile.TypeUser;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfileDao;
import es.udc.tfg.trainticketsapp.model.util.exceptions.NotEmpySeatsException;
import es.udc.tfg.trainticketsapp.model.util.exceptions.TimeoutTicketException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class PurchaseServiceTest {

	private final String STATION_NAME = "Madird-ATocha";
	private final String STATION_NAME2 = "Coruña";
	private final String ADDRESS = "Calle mayor";
	private final String CITY = "MADRID";
	private final String TRAIN_NAME = "A25";
	private final TrainType TRAIN_TYPE = TrainType.AVE;
	private final int CAR_NUM = 1;
	private final int CAPACITY = 5;
	private final CarType CAR_TYPE = CarType.TURISTA;
	private final String ROUTE_NAME = "Madrid-Coruña";
	private final String ROUTE_DESCRIPTION = "Viaje Con pasajeros";
	private final String FIRST_NAME = "Jose";
	private final String LAST_NAME = "Pallas";
	private final String USER_DNI = "12345124V";
	private final String USER_EMAIL = "mytrainexpress@gmail.com";
	private final String USER_LOGIN = "jose1234";
	private final String USER_PASSWORD = "contraseña";
	private final Calendar USER_BIRTHDATE = new GregorianCalendar(1992, 0, 1);
	private final TypeUser TYPE_USER = TypeUser.CLIENT;
	private final String FARE_NAME = "Normal fare";
	private final String FARE_DESCRIPTION = "Fare all publics";
	private final String FARE_TYPE = "normal";
	private final int FARE_DISCOUNT = 10;
	private final Float ROUTE_PRICE = new Float(10);

	@Autowired
	private FareDao fareDao;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private TrainDao trainDao;
	@Autowired
	private CarDao carDao;
	@Autowired
	private RouteDao routeDao;
	@Autowired
	private StationDao stationDao;
	@Autowired
	private StopDao stopDao;
	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private UserProfileDao userProfileDao;
	@Autowired
	private PurchaseDao purchaseDao;
	@Autowired
	private PassengerDao passengerDao;

	private Stop initialStop;
	private Stop finalStop;
	private Car car;
	private Passenger passenger;

	private void initialiceDatabase() {
		passenger = new Passenger(30, "Jose", "Pallas", "jose@udc", "473123G");
		passengerDao.save(passenger);
		Station station = new Station(STATION_NAME, ADDRESS, CITY);
		stationDao.save(station);
		Station station2 = new Station(STATION_NAME2, ADDRESS, CITY);
		stationDao.save(station2);
		Train train = new Train(TRAIN_NAME, TRAIN_TYPE);
		trainDao.save(train);
		car = new Car(CAPACITY, CAR_TYPE, CAR_NUM);
		train.addCar(car);
		carDao.save(car);
		Route route = new Route(ROUTE_NAME, ROUTE_DESCRIPTION, null, train,
				ROUTE_PRICE);
		routeDao.save(route);
		Long hora = Calendar.getInstance().getTimeInMillis();
		Long hora_salida = hora - 3600000 * 4;
		Long hora_llegada = hora;
		initialStop = new Stop(hora_salida, null, station);
		finalStop = new Stop(null, hora_llegada, station2);
		route.addStop(initialStop);
		route.addStop(finalStop);
		stopDao.save(initialStop);
		stopDao.save(finalStop);
	}

	@Test
	public void findFareByType() {
		Fare f = new Fare("adulto", "traifa de adulto", 10, "tipoA");
		fareDao.save(f);
		List<Fare> result = purchaseService.findFareBytype("tipoA");
		assertEquals(result.size(), 1);

	}

	@Test
	public void buyTicketsTest() throws InstanceNotFoundException, NotEmpySeatsException {
		initialiceDatabase();
		UserProfile userProfile = new UserProfile(USER_LOGIN, USER_PASSWORD,
				FIRST_NAME, LAST_NAME, USER_EMAIL, USER_DNI, USER_BIRTHDATE,
				TYPE_USER);
		userProfileDao.save(userProfile);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 2);
		List<TicketDetails> tickets = new ArrayList<TicketDetails>();
		TicketDetails t = new TicketDetails(FIRST_NAME, LAST_NAME, USER_DNI,
				USER_EMAIL, CAR_TYPE, null);
		tickets.add(t);
		Purchase purchase = purchaseService.buyTickets(PaymentMethod.PAYPAL,
				userProfile.getUserProfileId(), cal, null,
				initialStop.getStopId(), finalStop.getStopId(), null, null,
				tickets,CAR_TYPE,CAR_TYPE);
		assertEquals(purchaseDao.find(purchase.getPurchaseId()), purchase);
		assertEquals(purchase.getTickets().size(), 1);
	}
	
	/*
	@Test(expected = NotEmpySeatsException.class)
	public void buyTicketsFullTest() throws InstanceNotFoundException, NotEmpySeatsException {
		initialiceDatabase();
		UserProfile userProfile = new UserProfile(USER_LOGIN, USER_PASSWORD,
				FIRST_NAME, LAST_NAME, USER_EMAIL, USER_DNI, USER_BIRTHDATE,
				TYPE_USER);
		userProfileDao.save(userProfile);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 2);
		List<TicketDetails> tickets = new ArrayList<TicketDetails>();
		TicketDetails t = new TicketDetails(FIRST_NAME, LAST_NAME, USER_DNI,
				USER_EMAIL, CAR_TYPE, null);
		TicketDetails t1 = new TicketDetails(FIRST_NAME, LAST_NAME, USER_DNI,
				USER_EMAIL, CAR_TYPE, null);
		TicketDetails t2 = new TicketDetails(FIRST_NAME, LAST_NAME, USER_DNI,
				USER_EMAIL, CAR_TYPE, null);
		tickets.add(t);
		tickets.add(t1);
		tickets.add(t2);
		Purchase purchase =purchaseService.buyTickets(PaymentMethod.PAYPAL,
				userProfile.getUserProfileId(), cal, null,
				initialStop.getStopId(), finalStop.getStopId(), null, null,
				tickets,CAR_TYPE,CAR_TYPE);
		
	}
*/
	@Test
	public void cancelTicketTest() throws InstanceNotFoundException,
			TimeoutTicketException {
		initialiceDatabase();
		UserProfile userProfile = new UserProfile(USER_LOGIN, USER_PASSWORD,
				FIRST_NAME, LAST_NAME, USER_EMAIL, USER_DNI, USER_BIRTHDATE,
				TYPE_USER);
		userProfileDao.save(userProfile);
		Purchase p = new Purchase(Calendar.getInstance(), PaymentMethod.PAYPAL,
				userProfile);
		purchaseDao.save(p);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Ticket ticket = new Ticket(new Float(3), 1, cal, car, passenger,
				finalStop, initialStop);
		p.addTicket(ticket);
		ticket.setPurchase(p);
		ticketDao.save(ticket);
		purchaseService.cancelTicket(ticket.getTicketId());
		assertEquals(p.getTickets().size(), 0);

	}

	@Test(expected = TimeoutTicketException.class)
	public void cancelTimeoutTicketTest() throws InstanceNotFoundException,
			TimeoutTicketException {
		initialiceDatabase();
		UserProfile userProfile = new UserProfile(USER_LOGIN, USER_PASSWORD,
				FIRST_NAME, LAST_NAME, USER_EMAIL, USER_DNI, USER_BIRTHDATE,
				TYPE_USER);
		userProfileDao.save(userProfile);
		Purchase p = new Purchase(Calendar.getInstance(), PaymentMethod.PAYPAL,
				userProfile);
		purchaseDao.save(p);
		initialStop.setDepartTime(Calendar.getInstance().getTimeInMillis());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Ticket ticket = new Ticket(new Float(3), 1, cal, car, passenger,
				finalStop, initialStop);
		p.addTicket(ticket);
		ticket.setPurchase(p);
		ticketDao.save(ticket);
		purchaseService.cancelTicket(ticket.getTicketId());

	}

	@Test
	public void testCreateFareAndFindFare() throws DuplicateInstanceException,
			InstanceNotFoundException {

		/* Register user and find. */
		Fare fare = purchaseService.createFare(FARE_NAME, FARE_DESCRIPTION,
				FARE_TYPE, FARE_DISCOUNT);

		Fare fare2 = purchaseService.findFare(fare.getFareId());

		/* Check data. */
		assertEquals(fare, fare2);

	}

	@Test(expected = DuplicateInstanceException.class)
	public void testCreateDuplicatedFare() throws DuplicateInstanceException,
			InstanceNotFoundException {

		purchaseService.createFare(FARE_NAME, FARE_DESCRIPTION, FARE_TYPE,
				FARE_DISCOUNT);
		purchaseService.createFare(FARE_NAME, FARE_DESCRIPTION, FARE_TYPE,
				FARE_DISCOUNT);
	}

}
