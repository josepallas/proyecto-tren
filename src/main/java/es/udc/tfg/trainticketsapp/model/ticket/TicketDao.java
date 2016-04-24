package es.udc.tfg.trainticketsapp.model.ticket;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface TicketDao extends GenericDao<Ticket, Long> {
	public List<Ticket> findTicketsUser(Long userId);

}
