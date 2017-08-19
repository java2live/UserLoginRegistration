package java2live.registstration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java2live.registstration.domain.TemperatureInfo;
import java2live.registstration.service.TemperatureService;

@RestController
public class TemperatureInfoController {

	@Autowired
	TemperatureService temperatureService;
	
	/*This api end point will be called to get temperature details based on user Pincode 
 	-	Using https://openweathermap.org/current api
	-	Save temperature-info into database.
	-	If temperature-info already exist in database and it is less than 30-sec old. Then give saved-value else refresh data (call open-weather api then return new value. */
	@GetMapping("/temparatureByPincode/v0")
	public TemperatureInfo getTemperatureByPincode(@RequestParam("p") final String pincode,@RequestParam("c") final String country){
		
		
		final String result = temperatureService.validateTemparatureExpiryDate(pincode, country);
        if (result.equals("valid")) 
        	
        	return temperatureService.getTemperatureByPincode(pincode,country);
        	
        else
        	return temperatureService.getUpdatedTemperatureByPincode(pincode, country);
		
		
		
		
	}

}
