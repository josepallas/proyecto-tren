package es.udc.tfg.trainticketsapp.model.station;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Station {
	private Long stationId;
	private String stationName;
	private String adress;
	private String city;
	
	public Station() {
	}

	public Station(String stationName, String adress, String city) {
		this.stationName = stationName;
		this.adress = adress;
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

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Station [stationId=" + stationId + ", stationName="
				+ stationName + ", adress=" + adress + ", city=" + city + "]";
	}
	

	
}
