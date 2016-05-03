package es.udc.tfg.trainticketsapp.model.train;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Train {
	public enum TrainType {AVE, ALVIA};	
	private Long trainId;
	private String trainName;
	private TrainType trainType;
	
	public Train() {
	}

	public Train(String trainName, TrainType trainType) {
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

	public TrainType getTrainType() {
		return trainType;
	}

	public void setTrainType(TrainType trainType) {
		this.trainType = trainType;
	}
	
}
