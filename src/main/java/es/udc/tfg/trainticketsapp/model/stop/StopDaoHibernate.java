package es.udc.tfg.trainticketsapp.model.stop;

import org.springframework.stereotype.Repository;
import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("stopDao")
public class StopDaoHibernate extends GenericDaoHibernate<Stop, Long> implements StopDao {

}
