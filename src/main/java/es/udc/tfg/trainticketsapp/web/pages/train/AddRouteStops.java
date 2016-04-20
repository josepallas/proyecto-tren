package es.udc.tfg.trainticketsapp.web.pages.train;

import org.apache.tapestry5.annotations.Property;

public class AddRouteStops {

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	private Long routeId;
}
