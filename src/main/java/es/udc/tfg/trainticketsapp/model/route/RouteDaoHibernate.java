package es.udc.tfg.trainticketsapp.model.route;

import org.springframework.stereotype.Repository;
import es.udc.pojo.modelutil.dao.GenericDaoHibernate;


@Repository("routeDao")
public class RouteDaoHibernate extends GenericDaoHibernate<Route, Long> implements RouteDao{

}
