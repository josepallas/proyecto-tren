package es.udc.tfg.trainticketsapp.model.train;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("trainDao")
public class TrainDaoHibernate extends GenericDaoHibernate<Train, Long> implements TrainDao{

	@SuppressWarnings("unchecked")
	public List<Train> findAllTrains(){
		return getSession().createQuery("SELECT t FROM Train t " +
	            "ORDER BY t.trainName").list();	
	}
}
