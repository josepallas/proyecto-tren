package es.udc.tfg.trainticketsapp.model.train;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface TrainDao extends GenericDao<Train, Long> {

	public List<Train> findAllTrains();
}
