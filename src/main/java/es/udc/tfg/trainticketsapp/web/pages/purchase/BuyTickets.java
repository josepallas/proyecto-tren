package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase.PaymentMethod;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.purchaseService.TicketDetails;
import es.udc.tfg.trainticketsapp.web.pages.Index;
import es.udc.tfg.trainticketsapp.web.util.UserSession;

public class BuyTickets {

    @SessionState(create=false)
    private UserSession userSession;
	@Property
	private Fare fare;
	@Property
	private Fare fareFamily;
	@Inject
	private PurchaseService purchaseService;
	@Property
	private SelectModel fareModel;
	@Property
	private SelectModel fareFamilyModel;
	@Inject
	private SelectModelFactory selectModelFactory;
    @Component
    private Form ticketForm;
    @Property
    private String firstName;
    @Property
    private String lastName;
    @Property 
    private String dni;
    @Property
    private String email;
    private Long origin;
    private Long destination;
    
    
    
	public Long getOrigin() {
		return origin;
	}
	public void setOrigin(Long origin) {
		this.origin = origin;
	}
	public Long getDestination() {
		return destination;
	}
	public void setDestination(Long destination) {
		this.destination = destination;
	}
	void onActivate(Long origin, Long destination) {
		
		this.origin = origin;
		this.destination=destination;
		
	}
	Object[] onPassivate() {
		 return new Object[] {origin, destination};
	}
	
	void onPrepareForRender() {
		List<Fare> fares = purchaseService.findFareBytype("clase");
		fareModel = selectModelFactory.create(fares,"fareName");
		List<Fare> faresFamily = purchaseService.findFareBytype("familia");
		fareFamilyModel = selectModelFactory.create(faresFamily,"fareName");
	}
	public ValueEncoder<Fare> getFareEncoder() {	 
	    return new ValueEncoder<Fare>() {
	        @Override
	        public String toClient(Fare  value) {
	            return String.valueOf(value.getFareId()); 
	        }
	        @Override
	        public Fare toValue(String id) { 
	            try {
					return purchaseService.findFare(Long.parseLong(id));
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
					return null;
				} 
	        }
	    }; 
	}
    Object onSuccess() {
    	List<Fare> fares=new ArrayList<Fare>();
    	fares.add(fare);
    	fares.add(fareFamily);
    	TicketDetails ticketDetails=new TicketDetails(firstName, lastName, dni,email, null,"a", fares);
    	List<TicketDetails> tickets=new ArrayList<TicketDetails>();
    	tickets.add(ticketDetails);
    	try {
			purchaseService.buyTickets(PaymentMethod.EFECTIVO, userSession.getUserProfileId(), Calendar.getInstance(), origin, destination, tickets);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return Index.class;
    }
	
}


