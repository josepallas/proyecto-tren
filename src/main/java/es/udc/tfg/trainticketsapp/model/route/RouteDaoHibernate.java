package es.udc.tfg.trainticketsapp.model.route;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;


@Repository("routeDao")
public class RouteDaoHibernate extends GenericDaoHibernate<Route, Long> implements RouteDao{

	public Route findByName(String name) throws InstanceNotFoundException {

    	Route route = (Route) getSession().createQuery(
    			"SELECT r FROM Route r WHERE r.routeName = :name")
    			.setParameter("name", name)
    			.uniqueResult();
    	if (route == null) {
   			throw new InstanceNotFoundException(name, Route.class.getName());
    	} else {
    		return route;
    	}

	}
}
