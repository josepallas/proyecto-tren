package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import es.udc.tfg.trainticketsapp.model.purchaseService.TicketDetails;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.purchase.Purchase.PaymentMethod;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.util.exceptions.NotEmpySeatsException;
import es.udc.tfg.trainticketsapp.web.pages.Index;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;
import es.udc.tfg.trainticketsapp.web.util.TravelSession;
import es.udc.tfg.trainticketsapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class ConfirmPurchase {
	@Property
	@SessionState(create = false)
	private TravelSession travelSession;
	@SessionState(create = false)
	private UserSession userSession;
	@InjectPage
	private SendTickets sendTickets;
	@Property
	private boolean confirm;
	@Persist
	private List<TicketDetails> ticketsDetails;
	@Inject
	private ComponentResources componentResources;
	@Inject
	private PurchaseService purchaseService;
	@Persist
	private Long origin;
	@Persist
	private Long destination;
	@Persist
	private Long originReturn;
	@Persist
	private Long destinationReturn;
	@Property
	private float price;

	public Long getOrigin() {
		return origin;
	}

	public void setOrigin(Long origin) {
		this.origin = origin;
	}

	public Long getDestination() {
		return destination;
	}

	public void setDestination(Long destination) {
		this.destination = destination;
	}

	public Long getOriginReturn() {
		return originReturn;
	}

	public void setOriginReturn(Long originReturn) {
		this.originReturn = originReturn;
	}

	public Long getDestinationReturn() {
		return destinationReturn;
	}

	public void setDestinationReturn(Long destinationReturn) {
		this.destinationReturn = destinationReturn;
	}

	public List<TicketDetails> getTicketsDetails() {
		return ticketsDetails;
	}

	public void setTicketsDetails(List<TicketDetails> ticketsDetails) {
		this.ticketsDetails = ticketsDetails;
	}

	public void setSeats(int seat, Car car, int num) {
		this.ticketsDetails.get(num).setSeat(seat);
		this.ticketsDetails.get(num).setCar(car);
	}
	public void setSeatsReturn(int seat, Car car, int num) {
		this.ticketsDetails.get(num).setSeatReturn(seat);
		this.ticketsDetails.get(num).setCarReturn(car);
	}

	void onPrepareForRender() {
		this.price = travelSession.getPrice();
	}

	Object onActionFromPurchase() {
		Long purchaseId = null;
		try {
			purchaseId = purchaseService.buyTickets(PaymentMethod.PAYPAL,
					userSession.getUserProfileId(),
					travelSession.getDeparture(), travelSession.getArrival(),
					origin, destination, originReturn, destinationReturn,
					ticketsDetails,travelSession.getCarType(),travelSession.getCarTypeReturn()).getPurchaseId();
		} catch (InstanceNotFoundException e) {
			return null;
			
		} catch (NotEmpySeatsException e) {
			return null;
		}
		sendTickets.setpurchaseId(purchaseId);
		return sendTickets;

	}
}
