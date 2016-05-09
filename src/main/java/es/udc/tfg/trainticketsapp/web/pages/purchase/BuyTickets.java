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
    @Property
    private String date;
    private Calendar ticketDate;
    private List<CarType> trainClasses;
    
    
    
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
	void onActivate(Long origin, Long destination,String date) {
		
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
		
	}
	Object[] onPassivate() {
		 return new Object[] {origin, destination,date};
	}
	
	void onPrepareForRender() {
		trainClasses = trainService.findClassTypesByTrain(new Long(1));
		
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
    	fares.add(fareFamily);
    	TicketDetails ticketDetails=new TicketDetails(firstName, lastName, dni,email,carType, fares);
    	List<TicketDetails> tickets=new ArrayList<TicketDetails>();
    	tickets.add(ticketDetails);
    	try {
			purchaseService.buyTickets(PaymentMethod.EFECTIVO, userSession.getUserProfileId(), ticketDate, origin, destination, tickets);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return Index.class;
    }
	
}


