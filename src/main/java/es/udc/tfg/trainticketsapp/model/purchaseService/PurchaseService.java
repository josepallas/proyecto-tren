package es.udc.tfg.trainticketsapp.model.purchaseService;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase.PaymentMethod;
import es.udc.tfg.trainticketsapp.model.ticket.Ticket;
import es.udc.tfg.trainticketsapp.model.util.exceptions.TimeoutTicketException;

public interface PurchaseService {
	public Fare findFare(Long id) throws InstanceNotFoundException;
	public List<Fare> findFares();
	public List<Fare> findFareBytype(String type);
    public Purchase buyTickets(PaymentMethod paymentMethod, Long userId, Calendar
    		ticketsDate,Long origin, Long destination, List<TicketDetails> tickets)throws InstanceNotFoundException;
    public void cancelTicket(Long ticketId) throws InstanceNotFoundException, TimeoutTicketException ;
    public List<Ticket> showUserTickets(Long userId) ;
    public Ticket findTicket(Long ticketId) throws InstanceNotFoundException;
    public List<Purchase> findPurchaseByUserId(Long userId, int startIndex, int count) throws InstanceNotFoundException;
	public int getNumberOfPurchases(Long userId) throws InstanceNotFoundException;
}
