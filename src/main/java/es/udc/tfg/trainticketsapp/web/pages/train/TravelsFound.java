package es.udc.tfg.trainticketsapp.web.pages.train;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.model.trainService.TravelInfo;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class TravelsFound {

	private String origin;
	
	private String date;
	
	private String destination;
	
	private List<TravelInfo> travels;
	@Inject
	private Locale locale;
	
	@Inject
	private TrainService trainService;
	@Property
	private TravelInfo travel;
	@Property 
	private Date zoneDate;
    @InjectComponent
    private Zone travelsZone;
    private int numberPassengers;

	public int getNumberPassengers() {
		return numberPassengers;
	}

	public void setNumberPassengers(int numberPassengers) {
		this.numberPassengers = numberPassengers;
	}

	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date=date;
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
		return DateFormat.getTimeInstance(DateFormat.SHORT,locale);
	}
	Object onSuccess() {
		Calendar cal  = Calendar.getInstance();
		cal.setTime(zoneDate);
		travels=trainService.findTravels2(cal, origin, destination);
        return travelsZone.getBody();
	}
	
	void onActivate(String origin, String destination,String date,int numberPassengers) {
		this.origin = origin;
		this.destination=destination;
		this.date=date;
		this.numberPassengers=numberPassengers;
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal  = Calendar.getInstance();
		try {
			cal.setTime(df.parse(date));
			this.zoneDate=cal.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		travels=trainService.findTravels2(cal, origin, destination);
		
	}
	Object[] onPassivate() {
		 return new Object[] {origin, destination,date,numberPassengers};
	}
	
	
}
