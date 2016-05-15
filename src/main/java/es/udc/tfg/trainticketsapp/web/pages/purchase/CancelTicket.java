package es.udc.tfg.trainticketsapp.web.pages.purchase;

import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.util.exceptions.TimeoutTicketException;

public class CancelTicket {
	private Long ticketId;
	@Inject
	private PurchaseService purchaseService;
	
	public Long getTicketId() {
		return ticketId;
		}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
		}
	Long onPassivate() {
		return ticketId;
		}
	void onActivate(Long ticketId) {
		this.ticketId = ticketId;
		try {
			purchaseService.cancelTicket(ticketId);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutTicketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
}
