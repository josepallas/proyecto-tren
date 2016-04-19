package es.udc.tfg.trainticketsapp.model.car;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;


@Repository("carDao")
public class CarDaoHibernate extends GenericDaoHibernate<Car, Long> implements CarDao{

}
