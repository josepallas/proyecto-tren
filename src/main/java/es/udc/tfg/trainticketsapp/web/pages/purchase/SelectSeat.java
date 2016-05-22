package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.purchaseService.CarInfo;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;

public class SelectSeat {

	@Property
	private int seats;
	@Inject
	private PurchaseService purchaseService;
	private String ticketDate;
	private Long routeId;
	@Property
	private List<CarInfo> cars;
	@Property
	private CarInfo carInfo;
	@Property
	private int index;
	@Property
	private int seat;
	
	public String getTicketDate() {
		return ticketDate;
	}
	public void setTicketDate(String ticketDate) {
		this.ticketDate = ticketDate;
	}
	public Long getRouteId() {
		return routeId;
	}
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	void onActivate(){
	}
	Object[] onPassivate() {
		return new Object[] { ticketDate,routeId};
	}
	
	void onActivate(String ticketDate, Long routeId) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Calendar calendar  = Calendar.getInstance();
		try {
			calendar.setTime(df.parse(ticketDate));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block			
		}
		try {
			cars=purchaseService.findCars(calendar, CarType.PREFERENTE, routeId);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
