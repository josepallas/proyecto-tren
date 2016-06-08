package es.udc.tfg.trainticketsapp.web.pages.train;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.SelectModelFactory;
import org.apache.tapestry5.util.EnumSelectModel;
import org.apache.tapestry5.util.EnumValueEncoder;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.route.Route.WeekDay;
import es.udc.tfg.trainticketsapp.model.train.Train;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;

public class UpdateRoute {
	@Property
	private Train train;
	@Inject
	private TrainService trainService;
	@Property
	private SelectModel trainModel;
	@Inject
	private SelectModelFactory selectModelFactory;
	@Property
	private String routeName;
	@Property
	private String routeDescription;
	@Property
	private Float price;
	@Component
	private Form routeForm;
	@Inject
	private Messages messages;
	@Component(id = "routeName")
	private TextField routeNameField;
	@InjectPage
	private AddRouteStops addRouteStops;
	@Property
	private List<WeekDay> days;
	private Long routeId;
	@Property
	private Route route;
	@Inject
	private TypeCoercer typeCoercer;
	@Property
	private final ValueEncoder<WeekDay> encoder = new EnumValueEncoder<WeekDay>(
			typeCoercer, WeekDay.class);

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	@Property
	private final SelectModel model = new EnumSelectModel(WeekDay.class,
			messages);

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

	Long onPassivate() {
		return routeId;

	}

	void onSuccess() {
		try {
			trainService.updateRoute(routeId, routeName, routeDescription, train, price, days);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	void onActivate(Long routeId) {
		this.routeId=routeId;
		try {
			route=trainService.findRoute(routeId);
			days=route.getDays();
			price=route.getPrice();
			routeDescription=route.getRouteDescription();
			routeName=route.getRouteName();
			train=route.getTrain();
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
