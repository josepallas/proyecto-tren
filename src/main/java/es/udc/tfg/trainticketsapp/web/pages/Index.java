package es.udc.tfg.trainticketsapp.web.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;

import es.udc.tfg.trainticketsapp.web.pages.train.TravelsFound;

public class Index {
	
	@Property
	private String origin;
	
	@Property
	private String destination;
	@InjectPage
	private TravelsFound travelsFound;
	
    Object onSuccess() {
    	travelsFound.setDestination(destination);
    	travelsFound.setOrigin(origin);
    	return travelsFound;
    }
}
