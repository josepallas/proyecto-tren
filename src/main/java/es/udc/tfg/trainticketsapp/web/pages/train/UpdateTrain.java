package es.udc.tfg.trainticketsapp.web.pages.train;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.train.Train;
import es.udc.tfg.trainticketsapp.model.train.Train.TrainType;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;

public class UpdateTrain {

	@Property
	private String trainName;

	@Property
	private TrainType trainType;
	
	@Property Train train;

	@Inject
	private TrainService trainService;

	@InjectPage
	private AddCarTrain addCarTrain;
	
	@Property
	private SelectModel trainModel;
	@Inject
	private SelectModelFactory selectModelFactory;

	@Component
	private Form trainForm;

	@Inject
	private Messages messages;
	
	@Property
	private Car car;
	
	@Component
	private Zone trainZone;
	
	@Inject
	private Request request;
	
	void onPrepareForRender() {
		List<Train> trains = trainService.findTrains();
		trainModel = selectModelFactory.create(trains, "trainName");
	}

	public ValueEncoder<Train> getTrainEncoder() {
		return new ValueEncoder<Train>() {
			@Override
			public String toClient(Train value) {
				return String.valueOf(value.getTrainId());
			}

			@Override
			public Train toValue(String id) {
				try {
					return trainService.findTrain(Long.parseLong(id));
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
	}
	
	public Object onValueChanged(Train t) {
		train=t;
		trainName=t.getTrainName();
		return request.isXHR() ? trainZone.getBody() : null;
	}



	Object onSuccess() throws InstanceNotFoundException {

		trainService.updateTrain(train.getTrainId(), train.getTrainType(), train.getCars());
		return null;
	}
}
