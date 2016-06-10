package es.udc.tfg.trainticketsapp.web.pages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile.TypeUser;
import es.udc.tfg.trainticketsapp.web.pages.train.TravelsFound;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;
import es.udc.tfg.trainticketsapp.web.util.TravelSession;
import es.udc.tfg.trainticketsapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class Index {

	@Property
	private String origin;

	@Property
	private String destination;
	@InjectPage
	private TravelsFound travelsFound;
	@Property
	private Date dateOut;
	@Property
	private Date dateReturn;
	@Property
	private List<String> stations;
	@Inject
	private TrainService trainService;
	@Component
	private Form findForm;
	@Inject
	private Messages messages;
	@Property
	private int numberPassengers;
	@Property
	private String radioSelectedValue;
	@SessionState(create = false)
	private TravelSession travelSession;
	@SessionState(create = false)
	private UserSession userSession;

	@Environmental
	private JavaScriptSupport javaScriptSupport;

	void setupRender() {
		javaScriptSupport
				.importJavaScriptLibrary("/traintickets-app/js/datepicker.js");
		radioSelectedValue = "I";
	}
	public boolean getAdministrator() {
		if (userSession!=null){
		return (userSession.getTypeUser() == TypeUser.ADMINISTRATOR);
		}
		else return false;
	}

	List<String> onProvideCompletions(String partial) {
		stations = trainService.findNameStations();
		List<String> matches = new ArrayList<String>();
		partial = partial.toUpperCase();

		for (String station : stations) {
			if (station.contains(partial)) {
				matches.add(station);
			}
		}

		return matches;
	}

	void onValidateFromFindForm() {

		if (!findForm.isValid()) {
			return;
		}
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Date today = formatter.parse(formatter.format(new Date()));
			if (dateOut.before(today)) {
				findForm.recordError(messages.format("error-incorrectDate"));
			}
		} catch (ParseException e) {

		}

	}

	Object onSuccess() {
		travelsFound.setDestination(destination);
		travelsFound.setOrigin(origin);
		travelSession = new TravelSession();
		travelSession.setNumPassengers(numberPassengers);
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(dateOut);
		travelSession.setDeparture(calendar);
		if (dateReturn != null) {
			Calendar cal=Calendar.getInstance();
			cal.setTime(dateReturn);
			travelSession.setArrival(cal);
		}
		return travelsFound;
	}
}
