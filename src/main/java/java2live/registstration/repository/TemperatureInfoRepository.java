package java2live.registstration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java2live.registstration.domain.TemperatureInfo;

public interface TemperatureInfoRepository extends JpaRepository<TemperatureInfo, String>{

	public TemperatureInfo findByPincode(String pincode);
	

}
