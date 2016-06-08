package es.udc.tfg.trainticketsapp.model.fare;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Fare {

	private Long fareId;
	private String fareName;
	private String description;
	private int discount;
	private String typeFare;

	public Fare() {
	}

	public Fare(String fareName, String description, int discount,
			String typeFare) {
		this.fareName = fareName;
		this.description = description;
		this.discount = discount;
		this.typeFare = typeFare;
	}

	@SequenceGenerator(name = "FareIdGenerator", sequenceName = "FareSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "FareIdGenerator")
	public Long getFareId() {
		return fareId;
	}

	public void setFareId(Long fareId) {
		this.fareId = fareId;
	}

	public String getFareName() {
		return fareName;
	}

	public void setFareName(String fareName) {
		this.fareName = fareName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getTypeFare() {
		return typeFare;
	}

	public void setTypeFare(String typeFare) {
		this.typeFare = typeFare;
	}

}
