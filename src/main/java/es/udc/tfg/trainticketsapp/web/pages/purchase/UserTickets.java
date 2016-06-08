package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.purchaseService.TicketBlock;
import es.udc.tfg.trainticketsapp.model.ticket.Ticket;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;
import es.udc.tfg.trainticketsapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class UserTickets {
	private final static int TICKETS_PER_PAGE = 10;

	private int startIndex = 0;
	private TicketBlock ticketBlock;
	private Ticket ticket;

	@Inject
	private PurchaseService purchaseService;

	@Inject
	private Locale locale;
	@SessionState(create = false)
	private UserSession userSession;

	public List<Ticket> getTickets() {
		return ticketBlock.getTickets();
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public DateFormat getDateFormat() {
		return DateFormat.getDateInstance(DateFormat.SHORT, locale);
	}

	public DateFormat getTimeFormat() {
		return DateFormat.getTimeInstance(DateFormat.SHORT, locale);
	}

	public Format getFormat() {
		return NumberFormat.getInstance(locale);
	}

	public Object[] getPreviousLinkContext() {

		if (startIndex - TICKETS_PER_PAGE >= 0) {
			return new Object[] { startIndex - TICKETS_PER_PAGE };
		} else {
			return null;
		}

	}

	public Object[] getNextLinkContext() {

		if (ticketBlock.getExistMoreTickets()) {
			return new Object[] { startIndex + TICKETS_PER_PAGE };
		} else {
			return null;
		}

	}

	Object[] onPassivate() {
		return new Object[] { startIndex };
	}

	void onActivate(int startIndex) {
		this.startIndex = startIndex;
		ticketBlock = purchaseService.showUserTickets(
				userSession.getUserProfileId(), startIndex, TICKETS_PER_PAGE);
	}

}
