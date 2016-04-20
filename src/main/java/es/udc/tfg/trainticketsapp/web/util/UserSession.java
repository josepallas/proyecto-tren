package es.udc.tfg.trainticketsapp.web.util;

import es.udc.tfg.trainticketsapp.model.userprofile.UserProfile.TypeUser;


public class UserSession {

	private Long userProfileId;
	private String firstName;
	private TypeUser typeUser;


	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public TypeUser getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(TypeUser typeUser) {
		this.typeUser = typeUser;
	}
	

}
