package java2live.registstration.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java2live.registstration.constants.Constants;
import java2live.registstration.domain.*;
import java2live.registstration.repository.UserRepository;
import java2live.registstration.repository.VerificationTokenRepository;

@Service
public class UserService implements Constants{

	@Autowired
	UserRepository userRepositroy;
	@Autowired
	private VerificationTokenRepository tokenRepository;
	@Autowired
	TemperatureService temperatureService;
	
	public List<Users> getUsersList(){
		return userRepositroy.findAll();
	}
	public Users getUserByEmail(String email){
		return userRepositroy.findByEmail(email);
	}
	public void setUser(Users users){
		userRepositroy.save(users);
	}
	public void createVerificationToken(Users user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
	public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }
	public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final Users user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setValidated(true);
        userRepositroy.save(user);
        return TOKEN_VALID;
    }
	public void deleteUser(Users user) {
		userRepositroy.delete(user);
	}
	public TemperatureInfo getTemperatureInfo(Users user){
		if(user!=null){
		
		String pincode=user.getPincode();
		String country=COUNTRY;
		final String result = temperatureService.validateTemparatureExpiryDate(pincode, country);
        if (result.equals("valid")) 
        	
        	return temperatureService.getTemperatureByPincode(pincode,country);
        	
        else
        	return temperatureService.getUpdatedTemperatureByPincode(pincode, country);
	}else
		return null;
	}
    
}
