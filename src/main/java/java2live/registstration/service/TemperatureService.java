package java2live.registstration.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java2live.registstration.constants.Constants;
import java2live.registstration.domain.TemperatureInfo;
import java2live.registstration.repository.TemperatureInfoRepository;

@Service
public class TemperatureService implements Constants{
	
	public static final RestTemplate restTemplate=new RestTemplate(); 
	@Autowired
	TemperatureInfoRepository temperatureInfoRepository;

	public String validateTemparatureExpiryDate(String pincode,String country) {
		TemperatureInfo temperatureInfo = temperatureInfoRepository.findByPincode(pincode);
        if (temperatureInfo == null) {
        	temperatureInfo=new TemperatureInfo(pincode,country);
        	temperatureInfo.setTempInfo(saveTemperatureByPincode(pincode,country));
    		temperatureInfoRepository.save(temperatureInfo);
        }

        final Calendar cal = Calendar.getInstance();
        if ((temperatureInfo.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
        	return TOKEN_EXPIRED;
    		
        }else
        return TOKEN_VALID;
        
	}

	public TemperatureInfo getTemperatureByPincode(String pincode, String country) {
		TemperatureInfo temperatureInfo=temperatureInfoRepository.findByPincode(pincode);
		if(temperatureInfo.getPincode().isEmpty()){
			temperatureInfo.setTempInfo(saveTemperatureByPincode(pincode,country));
			temperatureInfoRepository.save(temperatureInfo);
			return temperatureInfoRepository.findByPincode(pincode);
		}else
		
		return temperatureInfo;
	}
	public TemperatureInfo getUpdatedTemperatureByPincode(String pincode, String country) {
		TemperatureInfo temperatureInfo=temperatureInfoRepository.findByPincode(pincode);
		temperatureInfo.setTempInfo(saveTemperatureByPincode(pincode,country));
		temperatureInfoRepository.save(temperatureInfo);
		return temperatureInfoRepository.findByPincode(pincode);
	}
	private String saveTemperatureByPincode(String pincode, String country) {
		String url="http://samples.openweathermap.org/data/2.5/weather?zip="+pincode+","+country+"&appid=b1b15e88fa797225412429c1c50c122a1";
		String tempInfo= restTemplate.getForObject(url, String.class);
		
		return tempInfo;
		
	}
	/*call scheduler on every 1 min and update older temperature info by calling new service */ 
	@Scheduled(cron="${update.cron.expression}")
	public void callExecute(){
		List<TemperatureInfo> temperatureInfos=temperatureInfoRepository.findAll();
		temperatureInfos.forEach(t->getTemperatureByPincode(t));
	}
	public void getTemperatureByPincode(TemperatureInfo temperatureInfo){
		String pincode=temperatureInfo.getPincode();
		String country=temperatureInfo.getCountry();
		final String result = validateTemparatureExpiryDate(pincode, country);
        if (result.equals("valid")) 
        	
        	 getTemperatureByPincode(pincode,country);
        	
        else
        	getUpdatedTemperatureByPincode(pincode, country);
	}

}
