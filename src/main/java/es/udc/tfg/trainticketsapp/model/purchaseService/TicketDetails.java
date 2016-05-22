package es.udc.tfg.trainticketsapp.model.purchaseService;

import java.util.List;

import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.car.Car.CarType;
import es.udc.tfg.trainticketsapp.model.fare.Fare;

public class TicketDetails {
	
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private Car car;
    private List<Fare> fare;
    private int seat;
    private CarType carType; 
    
    public TicketDetails(){
    	
    }
    
	public TicketDetails(String firstName, String lastName, String dni,
			String email,CarType carType,List<Fare> fare) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dni = dni;
		this.email = email;
		this.carType=carType;
		this.fare = fare;
	}

	public CarType getCarType() {
		return carType;
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

	public List<Fare> getFare() {
		return fare;
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

	public void setFare(List<Fare> fare) {
		this.fare = fare;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}	
	
    

}
