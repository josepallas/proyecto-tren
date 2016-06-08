package es.udc.tfg.trainticketsapp.model.purchaseService;

import java.util.List;

import es.udc.tfg.trainticketsapp.model.ticket.Ticket;

public class TicketBlock {
	private List<Ticket> tickets;
	private boolean existMoreTickets;

	public TicketBlock(List<Ticket> tickets, boolean existMoreTickets) {

		this.tickets = tickets;
		this.existMoreTickets = existMoreTickets;

	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public boolean getExistMoreTickets() {
		return existMoreTickets;
	}

}
