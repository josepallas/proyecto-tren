package es.udc.tfg.trainticketsapp.web.pages.train;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.train.Train.TrainType;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.web.pages.Index;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRATOR_USERS)
public class AddCarTrain {

	@Property
	private int capacity;

	@Property
	@Persist
	private List<Car> cars;

	@Component
	private Form carForm;
	@Component
	private Form acceptForm;

	@Inject
	private Messages messages;

	@Property
	private CarType carType;

	private String trainName;
	private TrainType trainType;

	@Inject
	private TrainService trainService;

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public TrainType getTrainType() {
		return trainType;
	}

	public void setTrainType(TrainType trainType) {
		this.trainType = trainType;
	}

	void onActivate(String trainName, TrainType trainType) {
		this.trainName = trainName;
		this.trainType = trainType;
	}

	Object[] onPassivate() {
		return new Object[] { trainName, trainType };
	}

	public void onValidateFromAcceptForm() {
		if (!acceptForm.isValid()) {
			return;
		}
		if (cars == null) {
			acceptForm.recordError(messages.format("error-nocars"));
		} else {
			try {
				trainService.createTrain(trainName, trainType, cars);
			} catch (InstanceNotFoundException | DuplicateInstanceException e) {
				acceptForm.recordError(messages.format("error-invalidname"));
				cars = null;
			}
			cars = null;
		}

	}

	public void onSuccessFromCarForm() {
		if (cars == null)
			cars = new ArrayList<Car>();
		cars.add(new Car(capacity, carType, cars.size() + 1));
	}

	public Object onSuccessFromAcceptForm() {
		return Index.class;
	}

}
