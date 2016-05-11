package es.udc.tfg.trainticketsapp.model.train;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.stop.Stop;

@Entity
public class Train {
	public enum TrainType {AVE, ALVIA,AVANT};	
	private Long trainId;
	private String trainName;
	private TrainType trainType;
	private List<Car> cars=new ArrayList<Car>();

	
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
	
    @OneToMany(mappedBy = "train")		
	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public void addCar(Car car) {
		cars.add(car);
		car.setTrain(this);
	}
	
}
