package es.udc.tfg.trainticketsapp.model.car;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import es.udc.tfg.trainticketsapp.model.train.Train;

@Entity
public class Car {
	public enum CarType {PREFERENTE, TURISTA};	
	private Long carId;
	private int carNum;
	private int capacity;
	private CarType carType;
	private Train train;
	
	public Car() {
	}

	public Car(int capacity, CarType carType, Train train, int carNum) {
		this.capacity = capacity;
		this.carType = carType;
		this.train = train;
		this.carNum= carNum;
	}

	@SequenceGenerator(name = "CarIdGenerator", sequenceName = "CarSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "CarIdGenerator")		
	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@JoinColumn(name="trainId")	
	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public int getCarNum() {
		return carNum;
	}

	public void setCarNum(int carNum) {
		this.carNum = carNum;
	}

	
}
