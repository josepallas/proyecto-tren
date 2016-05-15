package es.udc.tfg.trainticketsapp.model.station;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@org.hibernate.annotations.BatchSize(size = 10)
public class Station {
	private Long stationId;
	private String stationName;
	private String address;
	private String city;
	
	public Station() {
	}

	public Station(String stationName, String address, String city) {
		this.stationName = stationName;
		this.address = address;
		this.city = city;
	}

	@SequenceGenerator(name = "StationIdGenerator", sequenceName = "StationSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "StationIdGenerator")			
	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	

	
}
