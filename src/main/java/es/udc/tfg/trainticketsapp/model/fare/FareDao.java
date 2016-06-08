package es.udc.tfg.trainticketsapp.model.fare;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface FareDao extends GenericDao<Fare, Long> {
	public List<Fare> findAllFares();

	public List<Fare> findByType(String name);

	public Fare findByName(String name) throws InstanceNotFoundException;

}
