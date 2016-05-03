package es.udc.tfg.trainticketsapp.web.pages.train;

import java.text.DateFormat;
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
	
	private String destination;
	
	private List<TravelInfo> travels;
	@Inject
	private Locale locale;
	
	@Inject
	private TrainService trainService;
	@Property
	private TravelInfo travel;
	

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
	
	
	void onActivate(String origin, String destination) {
		
		this.origin = origin;
		this.destination=destination;
		travels=trainService.findTravels2(null, origin, destination);
		
	}
	Object[] onPassivate() {
		 return new Object[] {origin, destination};
	}
	
	
}
