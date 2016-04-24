package es.udc.tfg.trainticketsapp.model.fare;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.tfg.trainticketsapp.model.route.Route;

public interface FareDao extends GenericDao<Fare, Long> {
	public List<Fare> findAllFares();
	public List<Fare> findByType(String name);

}
