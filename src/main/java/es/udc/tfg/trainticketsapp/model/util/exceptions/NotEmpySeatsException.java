package es.udc.tfg.trainticketsapp.model.util.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class NotEmpySeatsException extends Exception { 
	private Long routeId;
	private Calendar date;

	public NotEmpySeatsException(Long routeId, Calendar date) {
		super("The Route " + routeId + " at "+date+" is full.Not empty seats");
		this.routeId = routeId;
		this.date = date;
	}

	public Long getRouteId() {
		return routeId;
	}

	public Calendar getDate() {
		return date;
	}
}
