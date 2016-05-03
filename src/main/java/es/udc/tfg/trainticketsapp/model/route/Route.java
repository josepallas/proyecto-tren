package es.udc.tfg.trainticketsapp.model.route;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import es.udc.tfg.trainticketsapp.model.stop.Stop;
import es.udc.tfg.trainticketsapp.model.ticket.Ticket;
import es.udc.tfg.trainticketsapp.model.train.Train;

@Entity
public class Route {
	private Long routeId;
	private String routeName;
	private String routeDescription;
	private List<String> days;
	private Train train;
	private List<Stop> stops=new ArrayList<Stop>();
	
	public Route() {
	}

	public Route(String routeName, String routeDescription, List<String> days,
			Train train) {

		this.routeName = routeName;
		this.routeDescription = routeDescription;
		this.days = days;
		this.train = train;
	}

	@SequenceGenerator(name = "RouteIdGenerator", sequenceName = "RouteSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "RouteIdGenerator")		
	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getRouteDescription() {
		return routeDescription;
	}

	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}

	@ElementCollection
	@CollectionTable(name="days", joinColumns=@JoinColumn(name="routeId"))
	@Column(name="day")	
	public List<String> getDays() {
		return days;
	}

	public void setDays(List<String> days) {
		this.days = days;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="trainId")		
	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

    @OneToMany(mappedBy = "route")	
	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}
	public void addStop(Stop stop) {
		stops.add(stop);
		stop.setRoute(this);
	}
	public void removeStop(Stop stop) {
		stops.remove(stop);
		stop.setRoute(null);
	}
	
}
