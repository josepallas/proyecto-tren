package es.udc.tfg.trainticketsapp.model.util.exceptions;

import java.util.Calendar;

@SuppressWarnings("serial")
public class TimeoutTicketException extends Exception {
	private Long ticketId;
	private Calendar date;
	public TimeoutTicketException (Long ticketId,Calendar date) {
		super("The ticket " + ticketId +
				" expired. Ticket expired at"+ date);
		this.ticketId = ticketId;
		this.date=date;	}

	public Long getTicketId() {
		return ticketId;
	}
	public Calendar getDate() {
		return date;
	}
}
