package es.udc.tfg.trainticketsapp.model.car;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;

public interface CarDao extends GenericDao<Car, Long> {

	public List<Car> findCarsByTrain(CarType carType, Long trainId);

	public List<CarType> findClassByTrain(Long trainId);
	
	public int getCapacityByTrain(Long trainId);
}
