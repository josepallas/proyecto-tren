package es.udc.tfg.trainticketsapp.model.purchaseService;

import java.util.List;

import es.udc.tfg.trainticketsapp.model.car.Car;
import es.udc.tfg.trainticketsapp.model.fare.Fare;

public class TicketDetails {
	
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private Car car;
    private List<Fare> fare;
    private String seat;
    
	public TicketDetails(String firstName, String lastName, String dni,
			String email, Car car,String seat, List<Fare> fare) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dni = dni;
		this.email = email;
		this.car = car;
		this.fare = fare;
		this.seat=seat;
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
	
	public String getSeat() {
		return seat;
	}

	public List<Fare> getFare() {
		return fare;
	}
    
	
    

}
