package es.udc.tfg.trainticketsapp.model.station;

import org.springframework.stereotype.Repository;
import es.udc.pojo.modelutil.dao.GenericDaoHibernate;


@Repository("statioDao")
public class StationDaoHibernate extends GenericDaoHibernate<Station, Long> implements StationDao {

}
