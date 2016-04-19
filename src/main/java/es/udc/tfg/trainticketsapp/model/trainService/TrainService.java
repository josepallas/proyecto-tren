package es.udc.tfg.trainticketsapp.model.trainService;

import java.util.Calendar;
import java.util.List;

import es.udc.tfg.trainticketsapp.model.route.Route;

public interface TrainService{

	public List<Route> findTravel(Calendar travelDay,String origin, String destination);
}
