package es.udc.tfg.trainticketsapp.web.pages.purchase;

import java.util.List;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.SelectModelFactory;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.fare.Fare;
import es.udc.tfg.trainticketsapp.model.purchaseService.PurchaseService;
import es.udc.tfg.trainticketsapp.web.pages.Index;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicy;
import es.udc.tfg.trainticketsapp.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ADMINISTRATOR_USERS)
public class UpdateFare {
	@Property
	private Fare fare;
	@Inject
	private PurchaseService purchaseService;
	@Property
	private SelectModel fareModel;
	@Inject
	private SelectModelFactory selectModelFactory;
	@Inject
	private Request request;
	@Property
	private String fareName;
	@Property
	private String fareType;
	@Property
	private String fareDescription;
	@Property
	private int discount;
	@Component
	private Zone fareZone;

	void onPrepareForRender() {
		List<Fare> fares = purchaseService.findFares();
		fareModel = selectModelFactory.create(fares, "fareName");
	}

	public ValueEncoder<Fare> getFareEncoder() {
		return new ValueEncoder<Fare>() {
			@Override
			public String toClient(Fare value) {
				return String.valueOf(value.getFareId());
			}

			@Override
			public Fare toValue(String id) {
				try {
					return purchaseService.findFare(Long.parseLong(id));
				} catch (InstanceNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
	}

	public Object onValueChanged(Fare f) {
		fareName = f.getFareName();
		fareDescription = f.getDescription();
		fareType = f.getTypeFare();
		discount = f.getDiscount();
		return request.isXHR() ? fareZone.getBody() : null;
	}

	Object onSuccess() {
		try {
			purchaseService.updateFare(fare.getFareId(), fareName,
					fareDescription, fareType, discount);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Index.class;
	}
}
