package es.udc.tfg.trainticketsapp.model.train;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Repository("trainDao")
public class TrainDaoHibernate extends GenericDaoHibernate<Train, Long> implements TrainDao{

	@SuppressWarnings("unchecked")
	public List<Train> findAllTrains(){
		return getSession().createQuery("SELECT t FROM Train t " +
	            "ORDER BY t.trainName").list();	
	}
	
	public Train findByName(String name) throws InstanceNotFoundException {

    	Train train = (Train) getSession().createQuery(
    			"SELECT t FROM Train t WHERE t.trainName = :name")
    			.setParameter("name", name)
    			.uniqueResult();
    	if (train == null) {
   			throw new InstanceNotFoundException(name, Train.class.getName());
    	} else {
    		return train;
    	}

	}
}
