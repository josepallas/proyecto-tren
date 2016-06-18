package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;
import es.udc.tfg.trainticketsapp.web.util.PaypalManager;
import es.udc.tfg.trainticketsapp.web.util.TravelSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class TicketPayment {
	@Property
	@SessionState(create = false)
	private TravelSession travelSession;
	@Property
	private float price;


	void onPrepareForRender() {
		this.price = travelSession.getPrice();
	}
	URL onAction() {
		try {
			return new URL(PaypalManager.startPurchase(travelSession.getPrice()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	
	
}
