package java2live.registstration.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import java2live.registstration.domain.TemperatureInfo;
import java2live.registstration.domain.Users;
import java2live.registstration.domain.VerificationToken;
import java2live.registstration.events.UserRegistrationEvent;
import java2live.registstration.repository.VerificationTokenRepository;
import java2live.registstration.service.UserService;

@RestController
public class UserLoginController {
	@Autowired
	UserService userService;
	@Autowired
	VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	
	public static RestTemplate restTemplate=new RestTemplate(); 
	
	/* This api end point is used to get list of users registered */
	@GetMapping("/userList/v0")
	public List<Users> getUserList(){
		return userService.getUsersList();
	}
	/*This api end point will be made while user validates link from his/her email and also token will be validated with in 15min */
	@GetMapping("/validateRegistration/v0")
	public Users getValidateRegistration(@RequestParam("token") String token){
		
		final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) 
            return userService.getVerificationToken(token).getUser();
        else
        	return null;
	}
	@GetMapping("/validateRegistration/v1")
	public TemperatureInfo getValidateRegistration1(@RequestParam("token") String token){
		
		final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) 
        	return userService.getTemperatureInfo(userService.getVerificationToken(token).getUser());
        else
        	return null;
	}
	/* This api end point is used to get User JSON object by passing emailid as request @param */
	@GetMapping("/userByEmail/v0")
	public Users getUserByEmail(@RequestParam("email") String email){
		return userService.getUserByEmail(email);
	}
	
	/* This api end point is to get list of tokens stored in DB */
	@GetMapping("/tokens/v0")
	public List<VerificationToken> getTokens(){
		return verificationTokenRepository.findAll();
	}
	/* This api end point is used to delete user by passing emailid as request @param */
	@DeleteMapping("/deleteUser/v0")
	public void deleteUser(@RequestParam("email") String email){
		userService.deleteUser(userService.getUserByEmail(email));
	}
	/* This api is used to register the user by passing request body with name,email,pincode */
	@PostMapping("/userRegistration/v0")
	public Users setUser(@RequestBody Users users,  
		  WebRequest request, 
			  Errors errors){
	    userService.setUser(users);
	    Users registered=userService.getUserByEmail(users.getEmail());
	    if (registered == null) {
	    	return null;
	    }
	    try {
	        String appUrl = request.getContextPath();
	        eventPublisher.publishEvent(new UserRegistrationEvent(registered, appUrl,"v0"));
	    } catch (Exception me) {
	    	me.printStackTrace();
	    }
	    return users;

	}
	/* This api is used to register the user by passing request body with name,email,pincode */
	@PostMapping("/userRegistration/v1")
	public Users setUser1(@RequestBody Users users,  
		  WebRequest request, 
			  Errors errors){
	    userService.setUser(users);
	    Users registered=userService.getUserByEmail(users.getEmail());
	    if (registered == null) {
	    	return null;
	    }
	    try {
	        String appUrl = request.getContextPath();
	        eventPublisher.publishEvent(new UserRegistrationEvent(registered, appUrl,"v1"));
	    } catch (Exception me) {
	    	me.printStackTrace();
	    }
	    return registered;

	}
		
	

}
