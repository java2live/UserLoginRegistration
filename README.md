# UserLoginRegistration

Technologies used :

Spring Boot 1.5.6.RELEASE
Spring data JPA
Spring mail
Spring 4.3.4.RELEASE
Tomcat Embedded 
Maven 3
Java 8
H2 DB embedded

Toools Used:
Spring STS
Restclient
postman plugin
Github


Rest API's for the below problems: 

Problem Statement

#1: 

Create a restful api for user registration with following fields 
  -	Name
  -	Email
  -	Pincode

Rest API: http://localhost:9090/userRegistration/v0
Method: POST
  
  Request Boday : 
      {
        "name": "java2live",
        "email": "java2live@gmail.com",
        "pincode": "94040"
     }

  #2 :
  
  Problem statement: 
   Create a restful api to get login for registered user. 
        -	Call login api with UserId
        -	Send login link (http-link) to his registered email.
        -	Login-link expires in 15 min.

    Rest API: http://localhost:9090/userRegistration/v0
    Method: POST
  
  Request Boday : 
      {
        "name": "java2live",
        "email": "java2live@gmail.com",
        "pincode": "94040"
      }
  
    Rest API: http://localhost:9090/validateRegistration/v0?token=
    Method: GET
    
#3:
 Problem Statement:
  User opens given login-link. If login-link is valid and not expired. Show user details.
    -	Name
    -	Email
    -	Pincode
    You can just represent data as JSON.
  
     Rest API: http://localhost:9090/validateRegistration/v0?token=
     Method: GET
    
 #4 :
  Problem Statement: 
  Create a restful api to get current temperature by pincode
      -	Use https://openweathermap.org/current api
      -	Save temperature-info into database.
      -	If temperature-info already exist in database and it is less than 30-sec old. Then give saved-value else refresh data (call open-weather api) then return new value.

    Rest API: http://localhost:9090/temparatureByPincode/v0?p=94040&c=us
    Method: GET
 #5: 
    Problem Statement:
    As in step 3, Instead of showing user details. Redirect user to show current-temperature for his/her pincode (saved at registration time).
    
    Rest API: http://localhost:9090/userRegistraion/v1
    Method: POST
    
  
      Request Boday : 
        {
          "name": "java2live",
          "email": "java2live@gmail.com",
          "pincode": "94040"
      }
      
     Note: 
         Below properties to be updated in application.properties based on your sender email and port to start your server.
         
          server.port:9090
          server.host.uri=http://localhost:9090
          spring.mail.host=smtp.gmail.com
          spring.mail.username=test@gmail.com
          spring.mail.password=*****
          spring.mail.properties.mail.smtp.auth=true
          spring.mail.properties.mail.smtp.starttls.enable=true
          spring.mail.properties.mail.smtp.starttls.required=true
