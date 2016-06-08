package es.udc.tfg.trainticketsapp.test.model.trainService;

import static es.udc.tfg.trainticketsapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.tfg.trainticketsapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
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
import es.udc.tfg.trainticketsapp.model.route.RouteDao;
import es.udc.tfg.trainticketsapp.model.station.Station;
import es.udc.tfg.trainticketsapp.model.station.StationDao;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.stop.StopDao;
import es.udc.tfg.trainticketsapp.model.train.Train;
import es.udc.tfg.trainticketsapp.model.train.Train.TrainType;
import es.udc.tfg.trainticketsapp.model.train.TrainDao;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;

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

	@Test
	public void createStationTest() throws DuplicateInstanceException,
			InstanceNotFoundException {

		Station station = trainService.createStation(STATION_NAME, ADDRESS,
				CITY);
		Station station2 = trainService.findStation(station.getStationId());

		assertEquals(station, station2);

	}

	@Test(expected = DuplicateInstanceException.class)
	public void createDuplicateStationTest() throws DuplicateInstanceException,
			InstanceNotFoundException {

		trainService.createStation(STATION_NAME, ADDRESS, CITY);
		trainService.createStation(STATION_NAME, ADDRESS, CITY);

	}

	@Test
	public void findTravelTest() {/*
								 * Long hora=new Long(123445); Station
								 * station=new Station("Madrid-ATocha",
								 * "Calle de españa","Coruna");
								 * stationDao.save(station); Station
								 * station2=new Station("Coruña",
								 * "Calle mayor","Coruna");
								 * stationDao.save(station2); Train train=new
								 * Train("A25", TrainType.AVE);
								 * trainDao.save(train); List<WeekDay> days=new
								 * ArrayList<WeekDay>();
								 * days.add(WeekDay.DOMINGO);
								 * days.add(WeekDay.JUEVES);
								 * days.add(WeekDay.LUNES);
								 * days.add(WeekDay.MARTES);
								 * days.add(WeekDay.MIERCOLES);
								 * days.add(WeekDay.SABADO);
								 * days.add(WeekDay.VIERNES);
								 * 
								 * Route route=new Route("Madrid-Coruna",
								 * "Viaje con paradas", days,train);
								 * routeDao.save(route); Stop s1=new Stop(hora,
								 * hora, station); Stop s2=new Stop(new
								 * Long(123446), new Long(123446), station2);
								 * route.addStop(s1); route.addStop(s2);
								 * stopDao.save(s1); stopDao.save(s2);
								 * List<TravelInfo>
								 * result=trainService.findTravels2
								 * (Calendar.getInstance(), "Madrid-ATocha",
								 * "Coruña"); assertEquals(result.size(),1);
								 * result
								 * .get(0).getDestination().getArrivalTime();
								 * result.get(0).getOrigin().getDepartTime();
								 */

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
	public void createTrainTest() throws DuplicateInstanceException,
			InstanceNotFoundException {

		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(CAPACITY_TRAIN, CAR_TYPE, 1));
		cars.add(new Car(CAPACITY_TRAIN, CAR_TYPE, 2));
		Train train = trainService.createTrain(TRAIN_NAME, TRAIN_TYPE, cars);
		Train train2 = trainService.findTrainByName(TRAIN_NAME);
		assertEquals(train, train2);
	}

	@Test(expected = DuplicateInstanceException.class)
	public void createDuplicateTrainTest() throws DuplicateInstanceException,
			InstanceNotFoundException {
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car(CAPACITY_TRAIN, CAR_TYPE, 1));
		trainService.createTrain(TRAIN_NAME, TRAIN_TYPE, cars);
		trainService.createTrain(TRAIN_NAME, TRAIN_TYPE, cars);
	}

}
