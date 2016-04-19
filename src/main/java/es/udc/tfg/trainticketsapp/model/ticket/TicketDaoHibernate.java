package es.udc.tfg.trainticketsapp.model.ticket;

import org.springframework.stereotype.Repository;
import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("ticketDao")
public class TicketDaoHibernate extends GenericDaoHibernate<Ticket, Long> implements TicketDao {

}
