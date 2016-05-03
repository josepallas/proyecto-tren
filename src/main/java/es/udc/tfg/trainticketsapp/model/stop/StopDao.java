package es.udc.tfg.trainticketsapp.model.stop;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.tfg.trainticketsapp.model.route.Route;

public interface StopDao extends GenericDao<Stop, Long> {

	public List<Stop> findTravels(String origin, String destination);
	public List<Long> findRouteByStops(String origin, String destination);
}
