package es.udc.tfg.trainticketsapp.model.ticket;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("ticketDao")
public class TicketDaoHibernate extends GenericDaoHibernate<Ticket, Long>
		implements TicketDao {

	@SuppressWarnings("unchecked")
	public List<Ticket> findTicketsUser(Long userId, int startIndex, int count) {
		return getSession()
				.createQuery(
						"SELECT t FROM Ticket t WHERE t.purchase.userProfile.userProfileId= :userId "
								+ "AND t.ticketDate> :actualDate OR (t.ticketDate= :actualDate AND t.origin.departTime> :dTime) "
								+ "ORDER BY t.ticketDate")
				.setParameter("userId", userId)
				.setParameter("actualDate", Calendar.getInstance())
				.setParameter("dTime", Calendar.getInstance().getTimeInMillis())
				.setFirstResult(startIndex).setMaxResults(count).list();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> findOccupiedSeats(Calendar ticketDate, Long carId,
			Long routeId) {
		return getSession()
				.createQuery(
						"SELECT t.seat FROM Ticket t WHERE  t.ticketDate= :ticketDate AND t.origin.route.routeId= :routeId AND t.car.carId= :carId")
				.setParameter("routeId", routeId).setParameter("carId", carId)
				.setParameter("ticketDate", ticketDate).list();
	}
	public int getNumberOccupiedSeats(Calendar ticketDate, Long routeId) {
		long occupied =(Long)getSession()
				.createQuery(
						"SELECT COUNT(t) FROM Ticket t WHERE  t.ticketDate= :ticketDate AND t.origin.route.routeId= :routeId")
				.setParameter("routeId", routeId)
				.setParameter("ticketDate", ticketDate).uniqueResult();
		return (int)occupied;
	}

}
