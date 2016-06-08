package es.udc.tfg.trainticketsapp.web.util;

import java.util.Calendar;

import es.udc.tfg.trainticketsapp.model.car.Car.CarType;

public class TravelSession {
	private String originStation;
	private String destinationStation;
	private Calendar departure;
	private Calendar arrival;
	private int numPassengers;
	private float price;
	private CarType carType;
	private CarType carTypeReturn;
	
	

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public CarType getCarTypeReturn() {
		return carTypeReturn;
	}

	public void setCarTypeReturn(CarType carTypeReturn) {
		this.carTypeReturn = carTypeReturn;
	}

	public String getOriginStation() {
		return originStation;
	}

	public void setOriginStation(String originStation) {
		this.originStation = originStation;
	}

	public String getDestinationStation() {
		return destinationStation;
	}

	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}

	public Calendar getDeparture() {
		return departure;
	}

	public void setDeparture(Calendar departure) {
		this.departure = departure;
	}

	public Calendar getArrival() {
		return arrival;
	}

	public void setArrival(Calendar arrival) {
		this.arrival = arrival;
	}

	public int getNumPassengers() {
		return numPassengers;
	}

	public void setNumPassengers(int numPassengers) {
		this.numPassengers = numPassengers;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
