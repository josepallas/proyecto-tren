package es.udc.tfg.trainticketsapp.model.passenger;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("PassengerDao")
public class PassengerDaoHibernate extends GenericDaoHibernate<Passenger, Long> implements PassengerDao {

}
