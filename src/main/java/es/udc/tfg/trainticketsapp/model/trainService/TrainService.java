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

public interface TrainService {

	/**
	 * Método que devuelve todos los trenes contenidos en el sistema
	 */
	public List<Train> findTrains();

	/**
	 * Método que devuelve todas las estaciones existentes en el sistema
	 */
	public List<Station> findStations();

	/**
	 * Método que crea un itinerario a partir de un nombre, descripción, id de un tren, precio, una lista de paradas y
	 * una lista de los días de la semana que estará dosponible
	 * @throws DuplicateInstanceException
	 * En caso de que se intente crear un itinerario con un nombre ya existente
	 */
	public Route createRoute(String routeName, String routeDescription,
			Long trainId, Float price, List<Stop> stops, List<WeekDay> days)
			throws DuplicateInstanceException, InstanceNotFoundException;
	
	/**
	 * Método que actualiza un itinerario
	 * @throws InstanceNotFoundException
	 */
	public void updateRoute(Long routeId,String routeName, String routeDescription,
			Train train, Float price, List<WeekDay> days)
			throws InstanceNotFoundException;

	/**
	 * Método que busca el itinerario que coincida con el nombre buscado
	 * @param routeName
	 * Nombre del itinerario
	 */
	public Route findRouteByName(String routeName)
			throws InstanceNotFoundException;

	/**
	 * Método que devuelve los tipos de vagones a partir del id de una parada
	 */
	public List<CarType> findClassTypes(Long stopId) throws InstanceNotFoundException;
	/**
	 * Método que busca los itinerarios disponibles en una determinada fecha, a partir de una
	 * lugar de origen y un destino
	 * @return
	 * Devuelve una lista de elemntos que incluye la información del itinerario y las paradas de origen
	 * y destino
	 */
	public List<TravelInfo> findTravels(Calendar day, String origin,
			String destination);

	/**
	 * Método crea una estación a partir de un nombre, ciudad y dirección
	 * @throws DuplicateInstanceException
	 * En caso de que se intente crear una estación con un nombre ya existente
	 */
	public Station createStation(String stationName, String city, String address)
			throws DuplicateInstanceException;
	/**
	 * Método modifica una estación
	 */
	public void updateStation(Long id,String city, String address) throws InstanceNotFoundException;

	/**
	 * Método que crea un tren a partire de un nombre, un tipo y una lista de vagones
	 * @throws DuplicateInstanceException
	 * En caso de que se intente crear un tren con un nombre ya existente
	 * @throws InstanceNotFoundException
	 */
	public Train createTrain(String trainName, TrainType trainType,
			List<Car> cars) throws DuplicateInstanceException,
			InstanceNotFoundException;
	
	/**
	 * Método que crea actualiza un tren con sus vagones
	 */	
	public void updateTrain(Long id, TrainType trainType,
			List<Car> cars) throws InstanceNotFoundException;

	/**
	 * Método que busca un tren a partir de su nombre
	 */
	public Train findTrainByName(String trainName)
			throws InstanceNotFoundException;

	/**
	 * Método que devuelve una lista con todos los nombres de todas estaciones
	 */
	public List<String> findNameStations();
	
	public Train findTrain(Long id) throws InstanceNotFoundException;

	public Car findCar(Long carId) throws InstanceNotFoundException;

	public Station findStation(Long id) throws InstanceNotFoundException;

	public Route findRoute(Long id) throws InstanceNotFoundException;

}