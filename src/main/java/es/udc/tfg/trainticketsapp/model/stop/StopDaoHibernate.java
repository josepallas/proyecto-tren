package es.udc.tfg.trainticketsapp.model.stop;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("stopDao")
public class StopDaoHibernate extends GenericDaoHibernate<Stop, Long> implements
		StopDao {

	@SuppressWarnings("unchecked")
	public List<Stop> findTravels(String origin, String destination) {
		/*
		 * select stop.route from stop a, stop b where a.route=b.route and
		 * a.station=:destination and b.station=:origin
		 */
		List<Stop> result = getSession()
				.createQuery(
						"SELECT s FROM Stop s  WHERE s.station.stationName =:origin"
								+ " AND exists (select 1 FROM Stop b WHERE s.route=b.route AND b.station.stationName=:destination AND b.arrivalTime>s.arrivalTime)")
				.setString("origin", origin)
				.setString("destination", destination).list();
		return result;
	}

	public List<Long> findRouteByStops(String origin, String destination) {
		@SuppressWarnings("unchecked")
		List<Long> result = getSession()
				.createQuery(
						"SELECT s.route.routeId FROM Stop s  WHERE s.station.stationName =:origin"
								+ " AND exists (select 1 FROM Stop b WHERE s.route=b.route AND b.station.stationName=:destination AND b.arrivalTime>coalesce(s.arrivalTime,0))")
				.setString("origin", origin)
				.setString("destination", destination).list();
		return result;
	}
}
