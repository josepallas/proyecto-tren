package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.util.Calendar;
import java.util.List;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.purchaseService.CarInfo;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;
import es.udc.tfg.trainticketsapp.web.util.TravelSession;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class SelectSeat {

	@Property
	private int seats;
	@SessionState(create = false)
	private TravelSession travelSession;
	@Inject
	private PurchaseService purchaseService;
	@Inject
	private TrainService trainService;
	private Calendar ticketDate;
	private Long originId;
	private Long originReturnId;
	@Property
	private List<CarInfo> cars;
	@Property
	private CarInfo carInfo;
	@Property
	private int index;
	@Property
	private int seat;
	private CarType carType;

	@Property
	private int num;
	private int passengers;
	@InjectPage
	private ConfirmPurchase confirmPurchase;
	@Persist
	private boolean setReturn;

	public int getPassengers() {
		return passengers;
	}


	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public Long getOriginId() {
		return originId;
	}

	public void setOriginId(Long originId) {
		this.originId = originId;
	}

	public Long getOriginReturnId() {
		return originReturnId;
	}

	public void setOriginReturnId(Long originReturnId) {
		this.originReturnId = originReturnId;
	}
	

	Object[] onPassivate() {
		return new Object[] { originId,originReturnId,num };
	}
	

	Object onAction(int index, Long carId) {
		Car car;
		try {
			car = trainService.findCar(carId);
			if(setReturn)
				confirmPurchase.setSeatsReturn(index, car, num);
			else
				confirmPurchase.setSeats(index, car, num);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		num++;
		if (num < passengers) {
			return null;
		} else
			if (setReturn||travelSession.getCarTypeReturn()==null) {
				setReturn=false;
				return TicketPayment.class;
			}
			else {
				setReturn=true;
				num=0;
				return null;
			}
			
	}

	void onActivate(Long originId,Long originReturnId ,int num) {
		this.num = num;
		this.passengers = travelSession.getNumPassengers();
		this.ticketDate = travelSession.getDeparture();
		this.originId = originId;
		this.originReturnId = originReturnId;
		this.carType=travelSession.getCarType();

		try {
			if(!setReturn) {
				cars = purchaseService.findCars(ticketDate,carType,originId);
				
			} else
			cars = purchaseService.findCars(travelSession.getArrival(),travelSession.getCarTypeReturn(),originReturnId);

		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
