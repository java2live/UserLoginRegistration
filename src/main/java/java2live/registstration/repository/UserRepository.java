package java2live.registstration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java2live.registstration.domain.Users;
@Component
public interface UserRepository extends JpaRepository<Users, String> {
	
	public Users findByEmail(String email);
	public void deleteByEmail(String email);
	

}
