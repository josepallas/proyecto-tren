package es.udc.tfg.trainticketsapp.model.ticket;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.train.Train.TrainType;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile;

@Repository("ticketDao")
public class TicketDaoHibernate extends GenericDaoHibernate<Ticket, Long> implements TicketDao {
	
	@SuppressWarnings("unchecked")
	public List<Ticket> findTicketsUser(Long userId) {
		return getSession().createQuery("SELECT t FROM Ticket t WHERE t.purchase.userProfile.userId= :user AND t.ticketDate> actualDate" +
	            "ORDER BY t.ticketDate").setParameter("userId", userId).setParameter("actualDate", Calendar.getInstance()).list();	
	}
	@SuppressWarnings("unchecked")
	public List<Integer> findOccupedSeats(Calendar ticketDate,CarType carType,Long carId) {
		return getSession().createQuery("SELECT t.seat FROM Ticket t WHERE t.car.carId= :carId AND t.car.carType= :carType")
				.setParameter("carId", carId).setParameter("carType", carType).list();	
	}

}
