package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.text.DateFormat;
import java.util.Locale;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.ticket.Ticket;

public class TicketDetails {
	@Inject
	private PurchaseService purchaseService;
	@Property	
	private Ticket ticket;
	
	private Long ticId;
	@Property
	private Fare fare;
	@Inject
	private Locale locale;
	
	
	public Long getTicId() {
		return ticId;
	}
	public void setTicId(Long ticId) {
		this.ticId = ticId;
	}
	Long onPassivate() {
		return ticId;
		
	}
	public DateFormat getDateFormat() {
		return DateFormat.getDateInstance( DateFormat.SHORT,locale);
	}	
	public DateFormat getTimeFormat() {
		return DateFormat.getTimeInstance(DateFormat.SHORT,locale);
	}

	void onActivate(Long ticId) {
		this.ticId=ticId;
		try {
			this.ticket=purchaseService.findTicket(ticId);
		} catch (InstanceNotFoundException e) {
			
		}
	}
}
