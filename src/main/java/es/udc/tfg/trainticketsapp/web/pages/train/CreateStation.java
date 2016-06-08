package es.udc.tfg.trainticketsapp.web.pages.train;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRATOR_USERS)
public class CreateStation {

	@Property
	private String address;

	@Property
	private String city;

	@Property
	private String stationName;

	@Inject
	private TrainService trainService;

	@Component
	private Form stationForm;

	@Component(id = "stationName")
	private TextField stationNameField;

	@Inject
	private Messages messages;

	void onValidateFromStationForm() {
		if (!stationForm.isValid()) {
			return;
		}
		try {
			trainService
					.createStation(stationName.toUpperCase(), city, address);
		} catch (DuplicateInstanceException e) {
			stationForm.recordError(stationNameField,
					messages.get("error-stationNameAlreadyExists"));
		}
	}
}
