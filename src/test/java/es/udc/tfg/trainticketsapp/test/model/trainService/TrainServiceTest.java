package es.udc.tfg.trainticketsapp.test.model.trainService;

import static es.udc.tfg.trainticketsapp.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.tfg.trainticketsapp.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile;
import es.udc.tfg.trainticketsapp.model.userservice.UserProfileDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class TrainServiceTest {

	private final String STATION_NAME="Madrid";
	private final String ADDRESS="Calle mayor";
	private final String CITY="MADRID";
	
	
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
    public void createStationTest () throws DuplicateInstanceException, InstanceNotFoundException{

        Station station = trainService.createStation(STATION_NAME, ADDRESS, CITY);
        Station station2 = trainService.findStation(station.getStationId());

        assertEquals(station, station2);
    	
    }
    @Test(expected = DuplicateInstanceException.class)
    public void createDuplicateStationTest () throws DuplicateInstanceException, InstanceNotFoundException{

        trainService.createStation(STATION_NAME, ADDRESS, CITY);
        trainService.createStation(STATION_NAME, ADDRESS, CITY);
    	
    }
	@Test
	public void findTravelTest() {/*
		Long hora=new Long(123445);
		Station station=new Station("Madrid-ATocha", "Calle de españa","Coruna");
		stationDao.save(station);
		Station station2=new Station("Coruña", "Calle mayor","Coruna");
		stationDao.save(station2);
		Train train=new Train("A25", TrainType.AVE);
		trainDao.save(train);
		List<WeekDay> days=new ArrayList<WeekDay>();
		days.add(WeekDay.DOMINGO);
		days.add(WeekDay.JUEVES);
		days.add(WeekDay.LUNES);
		days.add(WeekDay.MARTES);
		days.add(WeekDay.MIERCOLES);
		days.add(WeekDay.SABADO);
		days.add(WeekDay.VIERNES);

		Route route=new Route("Madrid-Coruna", "Viaje con paradas", days,train);
		routeDao.save(route);
		Stop s1=new Stop(hora, hora, station);
		Stop s2=new Stop(new Long(123446), new Long(123446), station2);
		route.addStop(s1);
		route.addStop(s2);
		stopDao.save(s1);
		stopDao.save(s2);
		List<TravelInfo> result=trainService.findTravels2(Calendar.getInstance(), "Madrid-ATocha", "Coruña");
		assertEquals(result.size(),1);
		result.get(0).getDestination().getArrivalTime();
		result.get(0).getOrigin().getDepartTime();

*/
		
	}
	
	@Test
	public void createRouteTest() throws DuplicateInstanceException, InstanceNotFoundException{
		Train train=new Train("A25", TrainType.AVE);
		Long hora=new Long(123445);
		Station station=new Station("estacionA", "Calle de españa","Coruna");
		stationDao.save(station);
		Station station2=new Station("estacionB", "Calle mayor","Coruna");
		stationDao.save(station2);
		trainDao.save(train);
		Stop s1=new Stop(hora, hora, station);
		Stop s2=new Stop(hora, hora, station2);
		List<Stop> stops=new ArrayList<Stop>();
		stops.add(s1);
		stops.add(s2);
        Route result=trainService.createRoute("M-Coruña", "sin paradas", train.getTrainId(),stops,null);
        assertEquals(result.getRouteName(),"M-Coruña");
	}
}
