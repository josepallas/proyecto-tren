package es.udc.tfg.trainticketsapp.model.train;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("trainDao")
public class TrainDaoHibernate extends GenericDaoHibernate<Train, Long> implements TrainDao{

}
