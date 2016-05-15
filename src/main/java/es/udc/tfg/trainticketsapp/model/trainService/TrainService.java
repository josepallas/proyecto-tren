package es.udc.tfg.trainticketsapp.model.trainService;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.route.Route.WeekDay;
import es.udc.tfg.trainticketsapp.model.station.Station;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.train.Train;
import es.udc.tfg.trainticketsapp.model.train.Train.TrainType;

public interface TrainService{

	public Train findTrain(Long id) throws InstanceNotFoundException;
	public List<Stop> findTravels(Calendar travelDay,String origin, String destination);
	public List<Train> findTrains() ;
	public Station findStation(Long id) throws InstanceNotFoundException;
	public List<Station> findStations() ;
	public Route createRoute(String routeName, String routeDescription,Long trainId,List<Stop> stops,List<WeekDay> days)
			throws DuplicateInstanceException, InstanceNotFoundException;
	public Route findRoute(Long id) throws InstanceNotFoundException;
	public Route findRouteByName(String routeName) throws InstanceNotFoundException;
	public List<CarType> findClassTypesByTrain(Long trainId);
	public List<TravelInfo> findTravels2(Calendar day,String origin, String destination);
	public Station createStation (String stationName, String city, String address) throws DuplicateInstanceException;
	public Train createTrain(String trainName, TrainType trainType,List<Car> cars) throws DuplicateInstanceException, InstanceNotFoundException;
	public Train findTrainByName(String trainName) throws InstanceNotFoundException;
	public List<String> findNameStations() ;

}