package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.passenger.Passenger;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase.PaymentMethod;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.purchaseService.TicketDetails;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.web.pages.Index;
import es.udc.tfg.trainticketsapp.web.util.UserSession;

public class BuyTickets {

    @SessionState(create=false)
    private UserSession userSession;
	@Property
	private CarType carType;
	@Property
	private Fare fareFamily;
	@Inject
	private PurchaseService purchaseService;
	@Inject
	private TrainService trainService;
	@Property
	private SelectModel fareFamilyModel;
	@Inject
	private SelectModelFactory selectModelFactory;
    @Component
    private Form ticketForm;
    private Long origin;
    private Long destination;
    private int numberPassengers=0;
    @Property
    private String date;
    private Calendar ticketDate;
    @Property
    private int index;
    private List<TicketDetails> ticketsDetails;
    private TicketDetails ticketDetails;
    
    
	public TicketDetails getTicketDetails() {
		return ticketDetails;
	}
	public void setTicketDetails(TicketDetails ticketDetails) {
		this.ticketDetails = ticketDetails;
	}
	public int getNumberPassengers() {
		return numberPassengers;
	}
	public void setNumberPassengers(int numberPassengers) {
		this.numberPassengers = numberPassengers;
	}
	public List<TicketDetails> getTicketsDetails() {
		return ticketsDetails;
	}
	public void setTicketsDetails(List<TicketDetails> ticketsDetails) {
		this.ticketsDetails = ticketsDetails;
	}
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
	public List<CarType> getClasses(){
		return this.getClasses();
	}

	void onActivate(Long origin, Long destination,String date,int numberPassengers) {
		this.ticketsDetails=new ArrayList<TicketDetails>();
		this.numberPassengers=numberPassengers;
		this.origin = origin;
		this.destination=destination;
		this.date=date;
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		ticketDate= Calendar.getInstance();
		try {
			ticketDate.setTime(df.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (ticketsDetails.size()<numberPassengers)
		ticketsDetails.add(new TicketDetails());
		
	}
	Object[] onPassivate() {
		 return new Object[] {origin, destination,date,numberPassengers};
	}

	void onPrepareForRender() {		
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
    	try {
			purchaseService.buyTickets(PaymentMethod.EFECTIVO, userSession.getUserProfileId(), ticketDate, origin, destination, ticketsDetails);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch bl
			e.printStackTrace();
		}
    	return Index.class;
    }
	
}


