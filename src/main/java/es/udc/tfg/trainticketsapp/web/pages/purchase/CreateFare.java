package es.udc.tfg.trainticketsapp.web.pages.purchase;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.web.pages.Index;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRATOR_USERS)
public class CreateFare {
	@Property
	private Fare fare;
	@Inject
	private PurchaseService purchaseService;
	@Property
	private String fareName;
	@Property
	private String fareType;
	@Property
	private String fareDescription;
	@Property
	private int discount;
	@Component
	private Form fareForm;
	@Inject
	private Messages messages;

	void onValidateFromFareForm() {

		if (!fareForm.isValid()) {
			return;
		}
		try {
			purchaseService.createFare(fareName, fareDescription, fareType,
					discount);
		} catch (DuplicateInstanceException e) {
			fareForm.recordError(messages.get("error-fareNameAlreadyExists"));
		}
	}

	Object onSuccess() {
		return Index.class;
	}

}
