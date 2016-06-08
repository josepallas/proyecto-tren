package es.udc.tfg.trainticketsapp.web.pages.purchase;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.util.exceptions.TimeoutTicketException;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class CancelTicket {
	private Long ticketId;
	@Property
	private boolean cancel;
	@Inject
	private PurchaseService purchaseService;
	@Inject
	private AlertManager alertManager;
	@Inject
	private Messages messages;

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	Long onPassivate() {
		return ticketId;
	}

	void onActivate(Long ticketId) {
		this.ticketId = ticketId;
		try {
			purchaseService.cancelTicket(ticketId);
			this.alertManager.success(messages.get("cancel-label"));
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutTicketException e) {
			this.alertManager.error(messages.get("cancelerror-label"));

		}
	}
}
