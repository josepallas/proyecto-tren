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
import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.station.Station;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.web.pages.Index;

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
	private Route route;
	private String routeDescription;
	private String routeName;

	public Long getTrainId() {
		return trainId;
	}
	
	public String getRouteDescription() {
		return routeDescription;
	}


	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
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


	void onActivate(Long trainId,String routeName, String routeDescription) {
		this.trainId = trainId;
		this.routeName=routeName;
		this.routeDescription=routeDescription;	
	}
	Object[] onPassivate() {
		 return new Object[] {trainId,routeName,routeDescription};
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
		arrival=validateDate(arrivalTimeField, arrivalTime);
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
				trainService.createRoute(routeName, routeDescription, trainId, stops);
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
		stops.add(new Stop(depart,arrival,route,station));
	}
	public Object onSuccessFromAcceptForm (){
		return Index.class;
	}
}