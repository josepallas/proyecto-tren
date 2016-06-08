package es.udc.tfg.trainticketsapp.model.station;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Repository("statioDao")
public class StationDaoHibernate extends GenericDaoHibernate<Station, Long>
		implements StationDao {

	@SuppressWarnings("unchecked")
	public List<Station> findAllStations() {
		return getSession().createQuery(
				"SELECT t FROM Station t " + "ORDER BY t.stationName").list();
	}

	@SuppressWarnings("unchecked")
	public List<String> findAllNameStations() {
		return getSession().createQuery(
				"SELECT t.stationName FROM Station t "
						+ "ORDER BY t.stationName").list();
	}

	public Station findByName(String stationName)
			throws InstanceNotFoundException {

		Station station = (Station) getSession()
				.createQuery(
						"SELECT s FROM Station s WHERE s.stationName = :stationName")
				.setParameter("stationName", stationName).uniqueResult();
		if (station == null) {
			throw new InstanceNotFoundException(stationName,
					Station.class.getName());
		} else {
			return station;
		}

	}

}
