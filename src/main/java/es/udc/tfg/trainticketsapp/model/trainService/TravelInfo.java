package es.udc.tfg.trainticketsapp.model.trainService;

import java.util.List;

import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.train.Train;

public class TravelInfo {
	private Stop origin;
	private Stop destination;
	private String routeName;
	private String routeDescription;
	private Train train;
	private List<Stop> stops;
	public TravelInfo(){
		
	}
	public TravelInfo( String routeName,String routeDescription, Train train) {
		this.routeName = routeName;
		this.routeDescription = routeDescription;
		this.train = train;
	}
	public Stop getOrigin() {
		return origin;
	}
	public Stop getDestination() {
		return destination;
	}
	public String getRouteName() {
		return routeName;
	}
	public String getRouteDescription() {
		return routeDescription;
	}
	public Train getTrain() {
		return train;
	}
	public List<Stop> getStops() {
		return stops;
	}
	public void setOrigin(Stop origin) {
		this.origin = origin;
	}
	public void setDestination(Stop destination) {
		this.destination = destination;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}
	public void setTrain(Train train) {
		this.train = train;
	}
	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}
	
	

}
