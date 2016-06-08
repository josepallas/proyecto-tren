package es.udc.tfg.trainticketsapp.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class PurchaseState {
	@Property
	@Parameter(required = true)
	private String actualPage;

	public String getState(String str) {
		if (actualPage.equalsIgnoreCase(str))
			return "label1";
		else
			return "label2";
	}
}
