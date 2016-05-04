package es.udc.tfg.trainticketsapp.web.pages.train;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.tfg.trainticketsapp.model.stop.Stop;
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
	

	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date=date;
	}
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
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
	
	
	void onActivate(String origin, String destination,String date) {
		
		this.origin = origin;
		this.destination=destination;
		this.date=date;
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal  = Calendar.getInstance();
		try {
			cal.setTime(df.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		travels=trainService.findTravels2(cal, origin, destination);
		
	}
	Object[] onPassivate() {
		 return new Object[] {origin, destination,date};
	}
	
	
}
