package es.udc.tfg.trainticketsapp.model.car;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.train.Train;


@Repository("carDao")
public class CarDaoHibernate extends GenericDaoHibernate<Car, Long> implements CarDao{

	public List<Car> findCarsByTrain(CarType carType,Long trainId){
		@SuppressWarnings("unchecked")
		List<Car> result= getSession().createQuery("SELECT c FROM Car c  WHERE c.train.trainId= :trainId"
				+ " AND c.carType= :carType").
				setParameter("carType", carType).setParameter("trainId", trainId).list();
		return result;
		}
	@SuppressWarnings("unchecked")
	public List<CarType> findClassByTrain(Long trainId){
		return getSession().createQuery("SELECT distinct c.carType FROM Car c " +
	            "WHERE c.train.trainId= :trainId").setParameter("trainId", trainId).list();	
	}
}
