package es.udc.tfg.trainticketsapp.model.trainService;

import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.train.Train;

public class TravelInfo {
	private Long routeId;
	private Stop origin;
	private Stop destination;
	private String routeName;
	private String routeDescription;
	private Train train;
	private Float price;
	private int emptySeats;

	public TravelInfo() {

	}

	public TravelInfo(Long routeId, String routeName, String routeDescription,
			Train train, Float price,int emptySeats) {
		this.routeName = routeName;
		this.routeDescription = routeDescription;
		this.train = train;
		this.routeId = routeId;
		this.price = price;
		this.emptySeats=emptySeats;
	}

	public int getEmptySeats() {
		return emptySeats;
	}

	public void setEmptySeats(int emptySeats) {
		this.emptySeats = emptySeats;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
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

}
