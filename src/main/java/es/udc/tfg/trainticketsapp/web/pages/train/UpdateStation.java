package es.udc.tfg.trainticketsapp.web.pages.train;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.station.Station;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;

public class UpdateStation {


	@Property
	private String address;

	@Property
	private String city;

	@Property
	private String stationName;

	@Inject
	private TrainService trainService;
	
	@Property
	private Station station;

	@Component
	private Form stationForm;
	
	@Component
	private Zone stationZone;
	
	@Property
	private SelectModel stationModel;
	@Inject
	private SelectModelFactory selectModelFactory;

	@Component(id = "stationName")
	private TextField stationNameField;

	@Inject
	private Messages messages;
	
	@Inject
	private Request request;
	
	void onPrepareForRender() {
		List<Station> stations = trainService.findStations();
		stationModel = selectModelFactory.create(stations, "stationName");
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
	
	public Object onValueChanged(Station s) {
		stationName=s.getStationName();
		address = s.getAddress();
		city = s.getCity();
		return request.isXHR() ? stationZone.getBody() : null;
	}

	void onSuccess() {

		try {
			trainService.updateStation(station.getStationId(), city, address);
		} catch (InstanceNotFoundException e) {
			stationForm.recordError(stationNameField,
					messages.get("error-stationNameAlreadyExists"));
		}
	}
}
