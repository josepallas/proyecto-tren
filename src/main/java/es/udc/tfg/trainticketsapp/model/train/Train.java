package es.udc.tfg.trainticketsapp.model.train;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Train {
	private Long trainId;
	private String trainName;
	private String trainType;
	
	public Train() {
	}

	public Train(String trainName, String trainType) {
		super();
		this.trainName = trainName;
		this.trainType = trainType;
	}
	
	@SequenceGenerator(name = "TrainIdGenerator", sequenceName = "TrainSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "TrainIdGenerator")		
	public Long getTrainId() {
		return trainId;
	}

	public void setTrainId(Long trainId) {
		this.trainId = trainId;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTrainType() {
		return trainType;
	}

	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}

	@Override
	public String toString() {
		return "Train [trainId=" + trainId + ", trainName=" + trainName
				+ ", trainType=" + trainType + "]";
	}

	
	
}
