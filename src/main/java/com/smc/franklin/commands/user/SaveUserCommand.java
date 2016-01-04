package com.smc.franklin.commands.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.commands.messaging.MessageRoller;
import com.smc.franklin.dao.User;
import com.smc.franklin.dao.repository.UserRepository;
import com.smc.franklin.response.ResponseMessage;
import com.smc.franklin.response.ResponseSeverity;
import com.smc.franklin.response.ResponseToken;

/**
 * 
 * @author sean_
 *
 */
@Component
public class SaveUserCommand {

	@Autowired private UserRepository userRepository;
	
	public ResponseToken<User> execute(User user) {
		ResponseToken<User> responseToken = new ResponseToken<User>();
		
		if ( user.getFirstName() == null ) {
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setBundleKey("user.firstname-cannot-be-null");
			responseToken.add(message);
		}
		
		if ( user.getUserName() == null ) {
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setBundleKey("user.username-cannot-be-null");
			responseToken.add(message);
		}
		if ( user.getCredential() == null ) {
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setBundleKey("user.username-password-be-null");
			responseToken.add(message);
		}
		if ( user.getConfirmation() == null ) {
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setBundleKey("user.username-confirmation-be-null");
			responseToken.add(message);
		}		
		if ( !user.getCredential().equals(user.getConfirmation() ) ) {
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setBundleKey("user.could-not-confirm-user-password");
			responseToken.add(message);
		}
		if ( !responseToken.isSuccessful()) {
			MessageRoller.roll(responseToken);		
			return responseToken;
		}
		User exists = userRepository.findByUserName(user.getUserName());
		
		if ( user.getId() == null ) {
			//create flow
			if ( exists == null) {
				//new user with unique name
				responseToken.setPayload(userRepository.save(user));
			} else {
				//new user with unique name
				ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
				message.setBundleKey("user.user-already-exists");
				message.getBundleParams().put("userName", user.getUserName());
				responseToken.add(message);
			}
		} else {
			//update
			exists.setConfirmation(user.getConfirmation());
			exists.setCredential(user.getCredential());
			exists.setEmail(user.getEmail());
			exists.setEmailNotifications(user.getEmailNotifications());
			exists.setFirstName(user.getFirstName());
			exists.setLastChanged(new Date(System.currentTimeMillis()));
			exists.setLastName(user.getUserName());
			exists.setUserName(user.getUserName());
			responseToken.setPayload(userRepository.save(exists));
		}

		MessageRoller.roll(responseToken);		
		return responseToken;
	}
}
