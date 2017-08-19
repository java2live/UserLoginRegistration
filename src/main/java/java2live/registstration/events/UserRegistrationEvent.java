package java2live.registstration.events;

import org.springframework.context.ApplicationEvent;

import java2live.registstration.domain.Users;

public class UserRegistrationEvent extends ApplicationEvent {
    private String appUrl;
    //private Locale locale;
    private Users user;
    private String versionFlag;
 
    public UserRegistrationEvent(
      Users user, String appUrl,String versionFlag) {
        super(user);
         
        this.user = user;
        this.versionFlag = versionFlag;
        this.appUrl = appUrl;
    }

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}


	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getVersionFlag() {
		return versionFlag;
	}

	public void setVersionFlag(String versionFlag) {
		this.versionFlag = versionFlag;
	}
     
}

