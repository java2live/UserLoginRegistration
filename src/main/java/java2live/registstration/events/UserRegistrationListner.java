package java2live.registstration.events;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java2live.registstration.domain.Users;
import java2live.registstration.service.UserService;

@Component
public class UserRegistrationListner implements
  ApplicationListener<UserRegistrationEvent> {
  
    @Autowired
    private UserService userService;
    
    @Autowired
    private Environment env;
  
    @Autowired
    private JavaMailSender mailSender;
 
    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        this.confirmRegistration(event);
    } 

    private void confirmRegistration(final UserRegistrationEvent event) {
        final Users user = event.getUser();
        final String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }
    private final SimpleMailMessage constructEmailMessage(final UserRegistrationEvent event, final Users user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
       
        final String confirmationUrl = event.getAppUrl() + "/validateRegistration/"+event.getVersionFlag().toLowerCase()+"?token=" + token;
        
        final String message = "registration Succussfull";
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" +env.getProperty("server.host.uri")+ confirmationUrl);
        email.setFrom(env.getProperty("spring.mail.username"));
        return email;
    }
}
