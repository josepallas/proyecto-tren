package es.udc.tfg.trainticketsapp.web.pages.train;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.train.Train.TrainType;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRATOR_USERS)
public class CreateTrain {

	@Property
	private String trainName;

	@Property
	private TrainType trainType;
	
	@Inject
	private TrainService trainService;	
	
    @InjectPage
    private AddCarTrain addCarTrain;
    
    @Component
    private Form trainForm;
    
	@Inject
	private Messages messages;	     
    
    void onValidateFromTrainForm() {
        if (!trainForm.isValid()) {
            return;
        }
        
    	try {
			trainService.findTrainByName(trainName);
			trainForm.recordError(messages.format("error-invalidname", trainName));
		} catch (InstanceNotFoundException e) {

		}
        
    }

    
    Object onSuccess() {
	    addCarTrain.setTrainName(trainName);
	    addCarTrain.setTrainType(trainType);
	    return addCarTrain;
    }
}
