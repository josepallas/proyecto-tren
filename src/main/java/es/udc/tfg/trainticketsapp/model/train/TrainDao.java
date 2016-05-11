package es.udc.tfg.trainticketsapp.model.train;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public interface TrainDao extends GenericDao<Train, Long> {

	public List<Train> findAllTrains();
	public Train findByName(String name) throws InstanceNotFoundException;
}
