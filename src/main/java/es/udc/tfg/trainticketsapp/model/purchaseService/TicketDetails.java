package es.udc.tfg.trainticketsapp.model.purchaseService;

import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.fare.Fare;

public class TicketDetails {

	private String firstName;
	private String lastName;
	private String dni;
	private String email;
	private Car car;
	private Car carReturn;
	private int seatReturn;
	private Fare fareFamily;
	private int seat;
	public TicketDetails() {

	}

	public TicketDetails(String firstName, String lastName, String dni,
			String email, CarType carType, Fare fareFamily) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dni = dni;
		this.email = email;
		this.fareFamily = fareFamily;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getDni() {
		return dni;
	}

	public String getEmail() {
		return email;
	}

	public Car getCar() {
		return car;
	}

	public int getSeat() {
		return seat;
	}

	public Fare getFareFamily() {
		return fareFamily;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFareFamily(Fare fareFamily) {
		this.fareFamily = fareFamily;
	}

	public Car getCarReturn() {
		return carReturn;
	}

	public void setCarReturn(Car carReturn) {
		this.carReturn = carReturn;
	}

	public int getSeatReturn() {
		return seatReturn;
	}

	public void setSeatReturn(int seatReturn) {
		this.seatReturn = seatReturn;
	}

}
