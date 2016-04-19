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
	private Long carId;
	private int carNum;
	private int capacity;
	private String carType;
	private Train train;
	
	public Car() {
	}

	public Car(int capacity, String carType, Train train, int carNum) {
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

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
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

	@Override
	public String toString() {
		return "Car [carId=" + carId + ", carNum=" + carNum + ", capacity="
				+ capacity + ", carType=" + carType + ", train=" + train + "]";
	}	
	

	
}
