package es.udc.tfg.trainticketsapp.model.userservice;

import java.util.Calendar;
import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile.TypeUser;

public class UserProfileDetails {

	private String firstName;
	private String lastName;
	private String email;
	private String dni;
	private Calendar birthdate;
	private TypeUser typeUser;

	public UserProfileDetails(String firstName, String lastName, String email,
			String dni, Calendar birthdate, TypeUser typeUser) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dni = dni;
		this.birthdate = birthdate;
		this.typeUser = typeUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getDni() {
		return dni;
	}

	public Calendar getBirthdate() {
		return birthdate;
	}

	public TypeUser getTypeUser() {
		return typeUser;
	}

}
