package es.udc.tfg.trainticketsapp.model.fare;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Repository("fareDao")
public class FareDaoHibernate extends GenericDaoHibernate<Fare, Long> implements
		FareDao {
	@SuppressWarnings("unchecked")
	public List<Fare> findAllFares() {
		return getSession().createQuery(
				"SELECT f FROM Fare f " + "ORDER BY f.fareName").list();
	}

	@SuppressWarnings("unchecked")
	public List<Fare> findByType(String name) {
		return getSession()
				.createQuery("SELECT f FROM Fare f WHERE f.typeFare = :name")
				.setParameter("name", name).list();

	}

	public Fare findByName(String name) throws InstanceNotFoundException {

		Fare fare = (Fare) getSession()
				.createQuery("SELECT f FROM Fare f WHERE f.fareName = :name")
				.setParameter("name", name).uniqueResult();
		if (fare == null) {
			throw new InstanceNotFoundException(name, Fare.class.getName());
		} else {
			return fare;
		}

	}
}
