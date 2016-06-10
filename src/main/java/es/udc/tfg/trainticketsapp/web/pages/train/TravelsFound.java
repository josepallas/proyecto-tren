package es.udc.tfg.trainticketsapp.web.pages.train;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.model.trainService.TravelInfo;
import es.udc.tfg.trainticketsapp.web.pages.purchase.TicketPassengers;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;
import es.udc.tfg.trainticketsapp.web.util.TravelSession;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class TravelsFound {

	private String origin;


	private String destination;
	@Persist
	private Long originId;
	@Persist
	private Long destinationId;
	@Inject
	private ComponentResources componentResources;
	private List<TravelInfo> travels;
	@Inject
	private Locale locale;
	@SessionState(create = false)
	private TravelSession travelSession;
	@Inject
	private TrainService trainService;
	@Property
	private TravelInfo travel;
	@Persist
	@Property
	private Date zoneDate;
	@InjectComponent
	private Zone travelsZone;
	@InjectComponent
	private Zone formZone;
	private int numberPassengers;
	@InjectPage
	private TicketPassengers ticketPassengers;
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	@Environmental
	private JavaScriptSupport javaScriptSupport;
	   
	public int getNumberPassengers() {
		return numberPassengers;
	}

	public void setNumberPassengers(int numberPassengers) {
		this.numberPassengers = numberPassengers;
	}

	public String getOrigin() {
		return origin.toUpperCase();
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination.toUpperCase();
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<TravelInfo> getTravels() {
		return travels;
	}

	public DateFormat getDateFormat() {
		return DateFormat.getTimeInstance(DateFormat.SHORT, locale);
	}
	public boolean isTrainFull(int emptySeats){
		return numberPassengers<emptySeats;
		
	}
	
	public String getDuration(Long arrivalTime,Long departTime){
		Long duration=arrivalTime-departTime;
		int minutes = (int) ((duration / (1000*60)) % 60);
		int hours   = (int) ((duration / (1000*60*60)) % 24);
		return String.format("%02d hours, %02d min",hours,minutes );
	}



	void setupRender() {
		javaScriptSupport
				.importJavaScriptLibrary("/traintickets-app/js/datechange.js");
		this.zoneDate = travelSession.getDeparture().getTime();
	}

	void onPrepareForRender() {
		travels = trainService.findTravels(travelSession.getDeparture(), origin, destination);
	}

	Object onSuccess() {
		Calendar cal=Calendar.getInstance();
		cal.setTime(zoneDate);
		if (travelSession.getArrival() != null && this.originId != null )
		travels = trainService.findTravels(cal, destination,origin);
		else
		travels = trainService.findTravels(cal, origin, destination);
			
		return travelsZone.getBody();
	}

	Object onAction(Long orig, Long dest) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(zoneDate);
		if (travelSession.getArrival() == null) {
			travelSession.setOriginStation(origin);
			travelSession.setDestinationStation(destination);
			travelSession.setDeparture(cal);
			ticketPassengers.setOrigin(orig);
			ticketPassengers.setDestination(dest);
			return ticketPassengers;
		} else {
			if (this.originId != null && this.destinationId != null) {
				travelSession.setOriginStation(origin);
				travelSession.setDestinationStation(destination);
				travelSession.setArrival(cal);
				ticketPassengers.setOrigin(originId);
				ticketPassengers.setDestination(destinationId);
				ticketPassengers.setOriginReturn(orig);
				ticketPassengers.setDestinationReturn(dest);
				componentResources.discardPersistentFieldChanges();
				return ticketPassengers;
			} else {
				this.originId = orig;
				this.destinationId = dest;
				travels = trainService.findTravels(cal,
						destination, origin);
				this.zoneDate=travelSession.getArrival().getTime();
				travelSession.setDeparture(cal);
	            ajaxResponseRenderer.addRender(formZone).addRender(travelsZone);
				return null;
			}
		}
	}

	void onActivate(String origin, String destination) {
		this.origin = origin;
		this.destination = destination;
	}

	Object[] onPassivate() {
		return new Object[] { origin, destination};
	}

}
