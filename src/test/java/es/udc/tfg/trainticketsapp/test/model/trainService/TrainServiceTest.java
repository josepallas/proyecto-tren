package es.udc.tfg.trainticketsapp.test.model.trainService;

import static es.udc.tfg.trainticketsapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.tfg.trainticketsapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
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
import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.route.Route.WeekDay;
import es.udc.tfg.trainticketsapp.model.route.RouteDao;
import es.udc.tfg.trainticketsapp.model.station.Station;
import es.udc.tfg.trainticketsapp.model.station.StationDao;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.stop.StopDao;
import es.udc.tfg.trainticketsapp.model.train.Train;
import es.udc.tfg.trainticketsapp.model.train.Train.TrainType;
import es.udc.tfg.trainticketsapp.model.train.TrainDao;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.model.trainService.TravelInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class TrainServiceTest {

	private final String STATION_NAME = "Madrid";
	private final String ADDRESS = "Calle mayor";
	private final String CITY = "MADRID";
	private final String TRAIN_NAME = "A23";
	private final TrainType TRAIN_TYPE = TrainType.ALVIA;
	private final CarType CAR_TYPE = CarType.TURISTA;
	private final int CAPACITY_TRAIN = 10;
	private final Float ROUTE_PRICE = new Float(10);

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
	private TrainService trainService;

	private List<WeekDay> getListDays() {
		List<WeekDay> days=new ArrayList<WeekDay>();
		days.add(WeekDay.VIERNES);
		days.add(WeekDay.SABADO);
		days.add(WeekDay.LUNES);
		days.add(WeekDay.MARTES);
		days.add(WeekDay.MIERCOLES);
		days.add(WeekDay.JUEVES);
		days.add(WeekDay.DOMINGO);
		return days;
	}
	
	
	@Test
	public void createStationTest() throws DuplicateInstanceException,
			InstanceNotFoundException {

		Station station = trainService.createStation(STATION_NAME, ADDRESS,
				CITY);
		Station station2 = trainService.findStation(station.getStationId());

		assertEquals(station, station2);

	}
	@Test
	public void updateStationTest() throws
			InstanceNotFoundException, DuplicateInstanceException {
		Station station = trainService.createStation(STATION_NAME, ADDRESS,
				CITY);
		trainService.updateStation(station.getStationId(), "una", "otra");

		assertEquals("una", station.getCity());
		assertEquals("otra", station.getAddress());


	}

	@Test(expected = DuplicateInstanceException.class)
	public void createDuplicateStationTest() throws DuplicateInstanceException,
			InstanceNotFoundException {

		trainService.createStation(STATION_NAME, ADDRESS, CITY);
		trainService.createStation(STATION_NAME, ADDRESS, CITY);

	}

	@Test
	public void createRouteTest() throws DuplicateInstanceException,
			InstanceNotFoundException {
		Train train = new Train("A25", TrainType.AVE);
		Long hora = new Long(123445);
		Station station = new Station("estacionA", "Calle de españa", "Coruna");
		stationDao.save(station);
		Station station2 = new Station("estacionB", "Calle mayor", "Coruna");
		stationDao.save(station2);
		trainDao.save(train);
		Stop s1 = new Stop(hora, hora, station);
		Stop s2 = new Stop(hora, hora, station2);
		List<Stop> stops = new ArrayList<Stop>();
		stops.add(s1);
		stops.add(s2);
		Route result = trainService.createRoute("M-Coruña", "sin paradas",
				train.getTrainId(), ROUTE_PRICE, stops, null);
		assertEquals(result.getRouteName(), "M-Coruña");
	}
	@Test
	public void updateRouteTest() throws DuplicateInstanceException,
			InstanceNotFoundException {
		Train train = new Train("A25", TrainType.AVE);
		trainDao.save(train);
		Station station = new Station("estacionA", "Calle de españa", "Coruna");
		stationDao.save(station);
		Stop s1 = new Stop(new Long(0), new Long(10), station);
		List<Stop> stops = new ArrayList<Stop>();
		stops.add(s1);
		Route route = trainService.createRoute(
				"M-Coruña", "sin paradas",
				train.getTrainId(), ROUTE_PRICE, stops, null);
		trainService.updateRoute(route.getRouteId(), "Madird", "ninguna", train, new Float(12), null);
		assertEquals("ninguna", route.getRouteDescription());
		assertEquals(new Float(12), route.getPrice());

		
	}
	
	@Test
	public void createTrainTest() throws DuplicateInstanceException,
			InstanceNotFoundException {

		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(CAPACITY_TRAIN, CAR_TYPE, 1));
		cars.add(new Car(CAPACITY_TRAIN, CAR_TYPE, 2));
		Train train = trainService.createTrain(TRAIN_NAME, TRAIN_TYPE, cars);
		Train train2 = trainService.findTrainByName(TRAIN_NAME);
		assertEquals(train, train2);
	}
	@Test
	public void updateTrainTest() throws DuplicateInstanceException,
			InstanceNotFoundException {
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(CAPACITY_TRAIN, CAR_TYPE, 1));
		cars.add(new Car(CAPACITY_TRAIN, CAR_TYPE, 2));
		Train train = trainService.createTrain(TRAIN_NAME, TRAIN_TYPE, cars);

		trainService.updateTrain(train.getTrainId(), TrainType.AVANT, cars);
		assertEquals(TrainType.AVANT, train.getTrainType());
	}

	@Test(expected = DuplicateInstanceException.class)
	public void createDuplicateTrainTest() throws DuplicateInstanceException,
			InstanceNotFoundException {
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(CAPACITY_TRAIN, CAR_TYPE, 1));
		trainService.createTrain(TRAIN_NAME, TRAIN_TYPE, cars);
		trainService.createTrain(TRAIN_NAME, TRAIN_TYPE, cars);
	}
	@Test	
	public void findTrainByIdTest() throws InstanceNotFoundException {
		Train train=new Train(TRAIN_NAME, TRAIN_TYPE);
		trainDao.save(train);
		Train train2=trainService.findTrain(train.getTrainId());
		assertEquals(train, train2);
	}
	@Test
	public void findStationByIdTest() throws InstanceNotFoundException {
		Station station = new Station("estacionA", "Calle de españa", "Coruna");
		stationDao.save(station);
		Station station2=trainService.findStation(station.getStationId());
		assertEquals(station, station2);
	}
	@Test	
	public void findRouteByIdTest() throws InstanceNotFoundException {
		Train train=new Train(TRAIN_NAME, TRAIN_TYPE);
		trainDao.save(train);
		Route route=new Route("Madrid-Coruna","Viaje con paradas", null,train,ROUTE_PRICE);
		routeDao.save(route);
		Route route2=trainService.findRoute(route.getRouteId());
		assertEquals(route,route2);
	}	
	@Test	
	public void findTravelsTest() throws InstanceNotFoundException, DuplicateInstanceException {
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(CAPACITY_TRAIN, CAR_TYPE, 1));
		Train train =trainService.createTrain(TRAIN_NAME, TRAIN_TYPE, cars);
		Long hora = new Long(123445);
		Station station = new Station("estacionA", "Calle de españa", "Coruna");
		stationDao.save(station);
		Station station2 = new Station("estacionB", "Calle mayor", "Coruna");
		stationDao.save(station2);
		Stop s1 = new Stop(null, hora, station);
		Stop s2 = new Stop(new Long(923445), new Long(923446), station2);
		List<Stop> stops = new ArrayList<Stop>();
		stops.add(s1);
		stops.add(s2);
		trainService.createRoute("M-Coruña", "sin paradas",
				train.getTrainId(), ROUTE_PRICE, stops, getListDays());
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 2);
		List<TravelInfo> travels=trainService.findTravels(calendar, "estacionA", "estacionB");
		assertEquals(1, travels.size());
		assertEquals(train, travels.get(0).getTrain());
		assertEquals(s1, travels.get(0).getOrigin());
		assertEquals(s2, travels.get(0).getDestination());
	}	
}
