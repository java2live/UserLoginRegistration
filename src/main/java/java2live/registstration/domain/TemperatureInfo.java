
package java2live.registstration.domain;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TemperatureInfo {
	private static final int EXPIRATION = 30;
	
	@Id
	@GeneratedValue
	private String id;
    private String pincode;
    private String country;
    @Column(length = 4000)
    private String tempInfo;
    private Date expiryDate;
	
	public TemperatureInfo(String pincode,String country,String tempInfo) {
		super();
		this.pincode = pincode;
		this.tempInfo=tempInfo;
		this.country=country;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	public TemperatureInfo(String pincode,String country) {
		super();
		this.pincode = pincode;
		this.country=country;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	public TemperatureInfo() {
		super();
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	
	private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.SECOND, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public static int getExpiration() {
		return EXPIRATION;
	}
	public String getTempInfo() {
		return tempInfo;
	}
	public void setTempInfo(String tempInfo) {
		this.tempInfo = tempInfo;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
        final TemperatureInfo temperatureInfo = (TemperatureInfo) obj;
        if (!pincode.equals(temperatureInfo.pincode)) {
            return false;
        }
        return true;
    }

}
