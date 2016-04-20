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
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.train.Train;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.web.pages.Index;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRATOR_USERS)
public class CreateRoute {

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
    @Component
    private Form routeForm;
    @Inject
    private Messages messages;
    @Component(id = "routeName")
    private TextField routeNameField;
    @InjectPage
    private AddRouteStops addRouteStops;
    private Long routeId;
    
	void onPrepareForRender() {
		List<Train> trains = trainService.findTrains();
		trainModel = selectModelFactory.create(trains,"trainName");

	}
	public ValueEncoder<Train> getTrainEncoder() {	 
	    return new ValueEncoder<Train>() {
	        @Override
	        public String toClient(Train  value) {
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
	
    void onValidateFromRouteForm() {

        if (!routeForm.isValid()) {
            return;
        }

            try {
                Long routeId=trainService.createRoute(routeName, routeDescription, train.getTrainId()).getRouteId();
            } catch (DuplicateInstanceException e) {
                routeForm.recordError(routeNameField, messages
                        .get("error-loginNameAlreadyExists"));
            } catch (InstanceNotFoundException e) {
            	routeForm.recordError(messages.get("error-trainNotexists"));
			}

    }
    Object onSuccess() {
    	addRouteStops.setRouteId(routeId);
    	return addRouteStops;
    }
}
