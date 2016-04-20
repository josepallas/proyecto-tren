package es.udc.tfg.trainticketsapp.model.trainService;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.train.Train;

public interface TrainService{

	public Train findTrain(Long id) throws InstanceNotFoundException;
	public List<Stop> findTravels(Calendar travelDay,String origin, String destination);
	public List<Train> findTrains() ;
	public Route createRoute(String routeName, String routeDescription,Long trainId)
			throws DuplicateInstanceException, InstanceNotFoundException;
}