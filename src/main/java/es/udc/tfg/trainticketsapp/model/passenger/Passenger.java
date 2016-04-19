package es.udc.tfg.trainticketsapp.model.passenger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Passenger {
	private Long passengerId;
	private int age;
	private String firstName;
	private String lastName;
	private String email;
	private String dni;

	
	public Passenger() {
	}

	public Passenger(int age, String firstName, String lastName, String email,
			String dni) {
		this.age = age;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dni = dni;
	}

	@SequenceGenerator(
	name = "PassengerIdGenerator", sequenceName = "PassengerSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PassengerIdGenerator")	
	public Long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Passenger [passengerId=" + passengerId + ", age=" + age
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", dni=" + dni + "]";
	}
	
	
}
