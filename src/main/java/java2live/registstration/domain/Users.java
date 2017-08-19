package java2live.registstration.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Users {
	@Id
	@GeneratedValue
	private String userId;
	private String name;
	private String email;
	private String pincode;
	private boolean validated;
	public Users(String userId, String name, String email, String pincode) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.pincode = pincode;
		this.validated = false;
	}
	public Users() {
		super();
		this.validated=false;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	@Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Users user = (Users) obj;
        if (!email.equals(user.email)) {
            return false;
        }
        return true;
    }

	
}
