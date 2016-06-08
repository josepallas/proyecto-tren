package es.udc.tfg.trainticketsapp.web.components;

import java.text.DateFormat;
import java.util.Locale;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.tfg.trainticketsapp.web.util.TravelSession;

public class Panel {
	@Property
	@SessionState(create = false)
	private TravelSession travelSession;

	@Inject
	private Locale locale;

	public DateFormat getDateFormat() {
		return DateFormat.getDateInstance(DateFormat.SHORT, locale);
	}
}
