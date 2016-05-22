package es.udc.tfg.trainticketsapp.model.purchaseService;

import java.util.List;

import es.udc.tfg.trainticketsapp.model.car.Car;

public class CarInfo {

	private List<Integer> occupiedSeats;
	private Car car;
	
	
	public CarInfo(Car car,List<Integer> occupiedSeats) {
		this.occupiedSeats = occupiedSeats;
		this.car = car;
	}
	public List<Integer> getOccupiedSeats() {
		return occupiedSeats;
	}
	public void setOccupiedSeats(List<Integer> occupiedSeats) {
		this.occupiedSeats = occupiedSeats;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	
}
