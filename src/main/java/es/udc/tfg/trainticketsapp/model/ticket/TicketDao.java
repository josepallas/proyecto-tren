package es.udc.tfg.trainticketsapp.model.ticket;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface TicketDao extends GenericDao<Ticket, Long> {
	public List<Integer> findOccupiedSeats(Calendar ticketDate, Long carId,
			Long routeId);

	public List<Ticket> findTicketsUser(Long userId, int startIndex, int count);
	
	public int getNumberOccupiedSeats(Calendar ticketDate, Long routeId);

}
