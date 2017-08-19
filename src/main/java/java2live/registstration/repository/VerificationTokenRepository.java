package java2live.registstration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java2live.registstration.domain.Users;
import java2live.registstration.domain.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String>{

	public VerificationToken findByToken(String verificationToken);
	

}
