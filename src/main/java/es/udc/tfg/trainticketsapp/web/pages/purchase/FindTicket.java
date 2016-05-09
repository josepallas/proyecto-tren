package es.udc.tfg.trainticketsapp.web.pages.purchase;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;

public class FindTicket {
	
	@Property
	private Long ticketId;
	
	
	@InjectPage
	private TicketDetails ticketDetails; 


	Object onSuccess(){
		ticketDetails.setTicId(ticketId);
		return ticketDetails;
	}
}
