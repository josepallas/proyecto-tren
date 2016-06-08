package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.purchaseService.TicketDetails;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;
import es.udc.tfg.trainticketsapp.web.util.TravelSession;
import es.udc.tfg.trainticketsapp.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class TicketPassengers {

	@SessionState(create = false)
	private UserSession userSession;
	@Inject
	private ComponentResources componentResources;
	@Property
	private Fare fareFamily;
	@Inject
	private PurchaseService purchaseService;
	@Property
	private Boolean autoSeat;
	@Inject
	private TrainService trainService;
	@Property
	private SelectModel fareFamilyModel;
	private List<CarType> carTypes;
	private List<CarType> carTypesReturn;
	@Inject
	private SelectModelFactory selectModelFactory;
	@Component
	private Form ticketForm;
	@Property
	private int index;
	@Persist
	private List<TicketDetails> ticketsDetails;
	@Property
	private CarType carType;
	@Property
	private CarType carTypeReturn;
	@Persist
	private TicketDetails ticketDetails;
	@InjectPage
	private SelectSeat selectSeat;
	@InjectPage
	private TicketPayment ticketPayment;
	@SessionState(create = false)
	private TravelSession travelSession;
	private Long origin;
	private Long destination;
	private Long originReturn;
	private Long destinationReturn;

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

	public TicketDetails getTicketDetails() {
		return ticketDetails;
	}

	public void setTicketDetails(TicketDetails ticketDetails) {
		this.ticketDetails = ticketDetails;
	}

	public List<TicketDetails> getTicketsDetails() {
		return ticketsDetails;
	}

	public void setTicketsDetails(List<TicketDetails> ticketsDetails) {
		this.ticketsDetails = ticketsDetails;
	}

	public List<CarType> getCarTypes (){
		return carTypes;
	}

	public List<CarType> getCarTypesReturn (){
		return carTypesReturn;
	}
	
	void onActivate(Long origin, Long destination, Long originReturn,
			Long destinationReturn) {
		this.origin = origin;
		this.originReturn = originReturn;
		this.destination = destination;
		this.destinationReturn = destinationReturn;
		this.ticketsDetails = new ArrayList<TicketDetails>();
		while (ticketsDetails.size() < travelSession.getNumPassengers())
			ticketsDetails.add(new TicketDetails());

	}

	Object[] onPassivate() {
		return new Object[] { origin, destination, originReturn,
				destinationReturn };
	}

	void onPrepareForRender() throws InstanceNotFoundException {
		List<Fare> faresFamily = purchaseService.findFareBytype("familia");
		fareFamilyModel = selectModelFactory.create(faresFamily, "fareName");
		carTypes=trainService.findClassTypes(origin);
		if (travelSession.getArrival()!=null) {
			carTypesReturn=trainService.findClassTypes(originReturn);
		}
	}

	public ValueEncoder<Fare> getFareEncoder() {
		return new ValueEncoder<Fare>() {
			@Override
			public String toClient(Fare value) {
				return String.valueOf(value.getFareId());
			}

			@Override
			public Fare toValue(String id) {
				try {
					return purchaseService.findFare(Long.parseLong(id));
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
	}

	Object onSuccess() {
		float price = 0;
		try {
			price = purchaseService.calculatePayment(origin, originReturn,
					ticketsDetails,carType,carTypeReturn);

		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		travelSession.setPrice(price);
		travelSession.setCarType(carType);
		travelSession.setCarTypeReturn(carTypeReturn);
		ticketPayment.setTicketsDetails(ticketsDetails);
		ticketPayment.setOrigin(origin);
		ticketPayment.setDestination(destination);
		ticketPayment.setOriginReturn(originReturn);
		ticketPayment.setDestinationReturn(destinationReturn);
		if (autoSeat) {
			return ticketPayment;
		} else {
			selectSeat.setOriginId(origin);
			selectSeat.setOriginReturnId(originReturn);
			return selectSeat;

		}
	}

}
