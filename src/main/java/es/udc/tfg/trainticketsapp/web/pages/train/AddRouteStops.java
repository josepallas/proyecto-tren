package es.udc.tfg.trainticketsapp.web.pages.train;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.route.Route.WeekDay;
import es.udc.tfg.trainticketsapp.model.station.Station;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.web.pages.Index;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRATOR_USERS)
public class AddRouteStops {
	@Property
	private SelectModel stationModel;
	@Inject
	private SelectModelFactory selectModelFactory;
	@Property
	private Station station;
	@Inject
	private TrainService trainService;
	@Property
	private String arrivalTime;
	@Property
	private String departTime;	
	@Component
	private Form stopForm;
	@Component
	private Form acceptForm;
	@Inject
	private Messages messages;
	@Component(id="departtime")
	private TextField departTimeField;
	@Component(id="arrivaltime")
	private TextField arrivalTimeField;
	@Property @Persist
	private List<Stop> stops;
	private Long arrival;
	private Long depart;
	private Long trainId;
	private String routeDescription;
	private String routeName;
	private Float price;
	@Persist
	private List<WeekDay> days;

	
	public List<WeekDay> getDays() {
		return days;
	}

	public void setDays(List<WeekDay> days) {
		this.days = days;
	}

	public Long getTrainId() {
		return trainId;
	}
	
	public String getRouteDescription() {
		return routeDescription;
	}


	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}


	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getRouteName() {
		return routeName;
	}


	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}


	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}


	void onActivate(Long trainId,String routeName, String routeDescription,Float price) {
		this.trainId = trainId;
		this.routeName=routeName;
		this.routeDescription=routeDescription;	
		this.price=price;
	}
	Object[] onPassivate() {
		 return new Object[] {trainId,routeName,routeDescription,price};
	}
	
	void onPrepareForRender() {
		List<Station> stations = trainService.findStations();
		stationModel = selectModelFactory.create(stations,"stationName");

	}
	public ValueEncoder<Station> getStationEncoder() {	 
	    return new ValueEncoder<Station>() {
	        @Override
	        public String toClient(Station value) {
	            return String.valueOf(value.getStationId()); 
	        }
	        @Override
	        public Station toValue(String id) { 
	            try {
					return trainService.findStation(Long.parseLong(id));
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
					return null;
				} 
	        }
	    }; 
	}
	
	private Long validateDate(TextField textField, String timeAsString) {
		SimpleDateFormat curFormater = new SimpleDateFormat("HH:mm"); 
		curFormater.setLenient(false);
		Long milis=new Long(0);
		try {
			milis=curFormater.parse(timeAsString).getTime();
		} catch (ParseException e) {
			stopForm.recordError(textField,
					messages.format("error-invaliddate", timeAsString));
		}			
		return milis;
	}
	void onValidateFromStopForm() {
		if (arrivalTime!=null)
		arrival=validateDate(arrivalTimeField, arrivalTime);
		if (departTime!=null)
		depart=validateDate(departTimeField, departTime);

	}
	public void onValidateFromAcceptForm(){
		if (!acceptForm.isValid()) {
			return;
		}
		if (stops==null) {
			acceptForm.recordError(messages.format("error-nostops"));
		} else {
			try {
				trainService.createRoute(routeName, routeDescription, trainId, price,stops,days);
			} catch (InstanceNotFoundException | DuplicateInstanceException e) {
				acceptForm.recordError(messages.format("error-invalidname"));
				stops=null;
			}
			stops=null;
		}
	}
	
	public void onSuccessFromStopForm (){
		if (stops==null)
		stops=new ArrayList<Stop>();
		stops.add(new Stop(depart,arrival,station));
	}
	public Object onSuccessFromAcceptForm (){
		return Index.class;
	}
}
