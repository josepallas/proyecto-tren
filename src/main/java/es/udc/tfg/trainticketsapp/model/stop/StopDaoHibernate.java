package es.udc.tfg.trainticketsapp.model.stop;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.tfg.trainticketsapp.model.route.Route;

@Repository("stopDao")
public class StopDaoHibernate extends GenericDaoHibernate<Stop, Long> implements StopDao {
	
	@SuppressWarnings("unchecked")
	public List<Stop> findTravels(Calendar travelDay,String origin, String destination){
		/* select stop.route from stop a, stop b where a.route=b.route and a.station=:destination and b.station=:origin */
		List<Stop> result= getSession().createQuery("SELECT s FROM Stop s  WHERE s.station.stationName =:origin"
				+ " AND exists (select 1 FROM Stop b WHERE s.route=b.route AND b.station.stationName=:destination)").
				setString("origin", origin).setString("destination", destination).list();
		return result;
	}
}
