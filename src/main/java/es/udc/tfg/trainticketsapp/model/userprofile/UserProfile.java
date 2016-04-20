package es.udc.tfg.trainticketsapp.model.userprofile;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UserProfile {
	
	public enum TypeUser {ADMINISTRATOR, SALESMAN, CLIENT};	
	private TypeUser typeUser;
	private Long userProfileId;
	private String loginName;
	private String encryptedPassword;
	private String firstName;
	private String lastName;
	private String email;
	private String dni;
	private Calendar birthdate;

	public UserProfile() {
	}


	public UserProfile(String loginName, String encryptedPassword,
			String firstName, String lastName, String email, String dni,
			Calendar birthdate, TypeUser typeUser) {
		super();
		this.loginName = loginName;
		this.encryptedPassword = encryptedPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dni = dni;
		this.birthdate = birthdate;
		this.typeUser = typeUser;
	}



	@Column(name = "usrId")
	@SequenceGenerator( // It only takes effect for
	name = "UserProfileIdGenerator", // databases providing identifier
	sequenceName = "UserProfileSeq")
	// generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UserProfileIdGenerator")
	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "enPassword")
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
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
	
	public TypeUser getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(TypeUser typeUser) {
		this.typeUser = typeUser;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Calendar birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public String toString() {
		return "UserProfile [userProfileId=" + userProfileId + ", typeUser="
				+ typeUser + ", loginName=" + loginName
				+ ", encryptedPassword=" + encryptedPassword + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email
				+ ", dni=" + dni + ", birthdate=" + birthdate + "]";
	}	

}
