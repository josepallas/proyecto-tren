package es.udc.tfg.trainticketsapp.web.pages.purchase;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.ticket.Ticket;
import es.udc.tfg.trainticketsapp.model.userservice.UserService;
import es.udc.tfg.trainticketsapp.web.util.UserSession;

public class PurchaseDetails {
	private Long purchaseId;
	@Inject
	private PurchaseService purchaseService;
	@Inject
	private UserService userService;
	@Property
	private Purchase purchase;
	@Property
	private Ticket ticket;
	@Property
	private int index;
	@Property
	private float price;
	@SessionState(create = false)
	private UserSession userSession;
	@Inject
	private AlertManager alertManager;
	@Inject
	private Messages messages;

	public Long getpurchaseId() {
		return purchaseId;
	}

	public void setpurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	Long onPassivate() {
		return purchaseId;
	}

	void onActivate(Long purchaseId) {
		this.purchaseId = purchaseId;
		try {
			this.purchase = purchaseService.findPurchase(purchaseId);
			this.price=new Float(0);

		} catch (InstanceNotFoundException e) {
		}
	}


}
