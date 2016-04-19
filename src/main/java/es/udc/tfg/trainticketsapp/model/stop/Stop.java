package es.udc.tfg.trainticketsapp.model.stop;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import es.udc.tfg.trainticketsapp.model.route.Route;
import es.udc.tfg.trainticketsapp.model.station.Station;

@Entity
public class Stop {
	private Long stopId;
	private Long departTime;
	private Long arrivalTime;
	private Route route;
	private Station station;
	
	public Stop() {
	}

	public Stop(Long departTime, Long arrivalTime, Route route, Station station) {
		super();
		this.departTime = departTime;
		this.arrivalTime = arrivalTime;
		this.route = route;
		this.station = station;
	}

	@SequenceGenerator(name = "StopIdGenerator", sequenceName = "StopSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "StopIdGenerator")			
	public Long getStopId() {
		return stopId;
	}

	public void setStopId(Long stopId) {
		this.stopId = stopId;
	}

	public Long getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Long departTime) {
		this.departTime = departTime;
	}

	public Long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="routeId")		
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="statioId")		
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Override
	public String toString() {
		return "Stop [stopId=" + stopId + ", departTime=" + departTime
				+ ", arrivalTime=" + arrivalTime + ", route=" + route
				+ ", station=" + station + "]";
	}

	
}
