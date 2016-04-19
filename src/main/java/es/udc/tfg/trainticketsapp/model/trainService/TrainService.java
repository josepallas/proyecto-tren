package es.udc.tfg.trainticketsapp.model.trainService;

import java.util.Calendar;
import java.util.List;

import es.udc.tfg.trainticketsapp.model.stop.Stop;

public interface TrainService{

	public List<Stop> findTravels(Calendar travelDay,String origin, String destination);
}
