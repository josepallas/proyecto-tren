package es.udc.tfg.trainticketsapp.web.pages.train;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;


public class FindRoute {
	@Property
	private Long routeId;

	@InjectPage
	private UpdateRoute updateRoute;

	Object onSuccess() {
		updateRoute.setRouteId(routeId);
		return updateRoute;
	}

}
