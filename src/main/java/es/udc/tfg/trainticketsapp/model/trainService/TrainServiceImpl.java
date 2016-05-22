package es.udc.tfg.trainticketsapp.model.trainService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service("trainService")
@Transactional
public class TrainServiceImpl implements TrainService  {


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
    
	@Transactional(readOnly = true)
	public List<Stop> findTravels(Calendar travelDay,String origin, String destination){
		return stopDao.findTravels(origin, destination);
	}
	@Transactional(readOnly = true)
	public Train findTrain(Long id) throws InstanceNotFoundException {
		return trainDao.find(id);
	}
	@Transactional(readOnly = true)
	public List<Train> findTrains() {
		return trainDao.findAllTrains();
	}
	@Transactional(readOnly = true)
	public Station findStation(Long id) throws InstanceNotFoundException {
		return stationDao.find(id);
	}
	@Transactional(readOnly = true)
	public List<Station> findStations() {
		return stationDao.findAllStations();
	}
	@Transactional(readOnly = true)
	public List<String> findNameStations() {
		return stationDao.findAllNameStations();
	}
	@Transactional(readOnly = true)
	public Route findRoute(Long id) throws InstanceNotFoundException {
		return routeDao.find(id);
	}
	@Transactional(readOnly = true)
	public Route findRouteByName(String routeName) throws InstanceNotFoundException {
		return routeDao.findByName(routeName);
	}
	@Transactional(readOnly = true)
	public Train findTrainByName(String trainName) throws InstanceNotFoundException {
		return trainDao.findByName(trainName);
	}
	@Transactional(readOnly = true)
	public List<CarType> findClassTypesByTrain(Long trainId) {
		return carDao.findClassByTrain(trainId);
	}
	public List<TravelInfo> findTravels2(Calendar day,String origin, String destination) {
		List<Long> routesId=stopDao.findRouteByStops(origin, destination);
		if (routesId.size()==0)
			return null;
		List<Route> routes=routeDao.findRoutesByDay(getWeekDay(day), routesId);
		List<TravelInfo> travels=new ArrayList<TravelInfo>();
		for(Route r:routes) {
			TravelInfo travelInfo=new TravelInfo(r.getRouteId(),r.getRouteName(),r.getRouteDescription(),r.getTrain());
			for(Stop s:r.getStops()) {
				if (s.getStation().getStationName().equals(origin))
					travelInfo.setOrigin(s);
				if (s.getStation().getStationName().equals(destination))
					travelInfo.setDestination(s);		
			}
			travels.add(travelInfo);
		}
		return travels;
	}
	private WeekDay getWeekDay(Calendar day) {
		WeekDay weekDay=Route.WeekDay.LUNES;
		switch(day.get(Calendar.DAY_OF_WEEK)) {
	    case 1:
	        weekDay=Route.WeekDay.DOMINGO;
	        break;
	    case 2:
	        weekDay=Route.WeekDay.LUNES;
	        break;
	    case 3:
	        weekDay=Route.WeekDay.MARTES;
	        break;
	    case 4:
	        weekDay=Route.WeekDay.MIERCOLES;
	        break;
	    case 5:
	        weekDay=Route.WeekDay.JUEVES;
	        break;
	    case 6:
	        weekDay=Route.WeekDay.VIERNES;
	        break;
	    case 7:
	        weekDay=Route.WeekDay.SABADO;
	        break;

		}
		return weekDay;
	}
	

	public Route createRoute(String routeName, String routeDescription,Long trainId, Float price, List<Stop> stops,List<WeekDay> days) throws DuplicateInstanceException, InstanceNotFoundException {
        try {
            routeDao.findByName(routeName);
            throw new DuplicateInstanceException(routeName,
                    Route.class.getName());
        } catch (InstanceNotFoundException e) {
        	Train train=trainDao.find(trainId);
            Route route=new Route(routeName,routeDescription,days,train,price);
            routeDao.save(route);
    		for (Stop a:stops) {
    			route.addStop(a);
    			stopDao.save(a);
			}
            return route;
        }
	}
	
	public Station createStation (String stationName, String city, String address) throws DuplicateInstanceException {
		try {
			stationDao.findByName(stationName);
	        throw new DuplicateInstanceException(stationName,Station.class.getName());
		} catch (InstanceNotFoundException e) {
			Station station=new Station(stationName,city,address);
			stationDao.save(station);
			return station;
		}
	}

	public Train createTrain(String trainName, TrainType trainType,List<Car> cars) throws DuplicateInstanceException, InstanceNotFoundException {
        try {
            trainDao.findByName(trainName);
            throw new DuplicateInstanceException(trainName,
                    Train.class.getName());
        } catch (InstanceNotFoundException e) {
            Train train=new Train( trainName,  trainType);
            trainDao.save(train);
    		for (Car a:cars) {
    			train.addCar(a);
    			carDao.save(a);
			}
            return train;
        }
	}
}
