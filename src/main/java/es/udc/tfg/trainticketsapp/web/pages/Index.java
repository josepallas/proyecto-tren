package es.udc.tfg.trainticketsapp.web.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;

import es.udc.tfg.trainticketsapp.web.pages.train.TravelsFound;

public class Index {
	
	@Property
	private String origin;
	
	@Property
	private String destination;
	@InjectPage
	private TravelsFound travelsFound;
	@Property
	private Date date;


    Object onSuccess() {
    	travelsFound.setDestination(destination);
    	travelsFound.setOrigin(origin);
    	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = DATE_FORMAT.format(date);
    	travelsFound.setDate(strDate);
    	return travelsFound;
    }
}
