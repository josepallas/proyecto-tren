package es.udc.tfg.trainticketsapp.model.stop;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface StopDao extends GenericDao<Stop, Long> {

	public List<Stop> findTravels(String origin, String destination);

	public List<Long> findRouteByStops(String origin, String destination);
}
