package es.udc.tfg.trainticketsapp.model.fare;

import org.springframework.stereotype.Repository;
import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("fareDao")
public class FareDaoHibernate extends GenericDaoHibernate<Fare, Long> implements FareDao {

}
