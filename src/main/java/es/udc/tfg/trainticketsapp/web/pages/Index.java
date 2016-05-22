package es.udc.tfg.trainticketsapp.web.pages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.web.pages.train.TravelsFound;

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
	@Persist
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

    @Environmental
    private JavaScriptSupport javaScriptSupport;        

	void setupRender() {
        javaScriptSupport.importJavaScriptLibrary("/traintickets-app/js/datepicker.js");

		stations=trainService.findNameStations();
	}

    List<String> onProvideCompletions(String partial) {
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
			Date today =formatter.parse(formatter.format(new Date()));
			if (dateOut.before(today)) {
				findForm.recordError(
					messages.format("error-incorrectDate"));
			}
		} catch (ParseException e) {

		}

	}
    Object onSuccess() {
    	travelsFound.setDestination(destination);
    	travelsFound.setOrigin(origin);
    	travelsFound.setNumberPassengers(numberPassengers);
    	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = DATE_FORMAT.format(dateOut);
    	travelsFound.setDate(strDate);
    	return travelsFound;
    }
}
