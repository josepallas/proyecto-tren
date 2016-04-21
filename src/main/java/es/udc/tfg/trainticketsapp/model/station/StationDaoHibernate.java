package es.udc.tfg.trainticketsapp.model.station;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.tfg.trainticketsapp.model.train.Train;


@Repository("statioDao")
public class StationDaoHibernate extends GenericDaoHibernate<Station, Long> implements StationDao {
	
	@SuppressWarnings("unchecked")
	public List<Station> findAllStations(){
		return getSession().createQuery("SELECT t FROM Station t " +
	            "ORDER BY t.stationName").list();	
	}

}
