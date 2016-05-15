package es.udc.tfg.trainticketsapp.web.pages.train;

import java.text.DateFormat;
import java.util.Locale;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.trainService.TrainService;

public class RouteDetails {
	@Inject
	private TrainService trainService;
	@Property	
	private Route route;
	@Property
	private Stop stop;
	
	private Long routeId;
	@Inject
	private Locale locale;
	
	public Long getRouteId() {
		return routeId;
	}
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	Long onPassivate() {
		return routeId;
		
	}
	
	public DateFormat getDateFormat() {
		return DateFormat.getDateInstance( DateFormat.SHORT,locale);
	}	
	public DateFormat getTimeFormat() {
		return DateFormat.getTimeInstance(DateFormat.SHORT,locale);
	}

	void onActivate(Long routeId) {
		this.routeId=routeId;
		try {
			this.route=trainService.findRoute(routeId);
		} catch (InstanceNotFoundException e) {
			
		}
	}
}
