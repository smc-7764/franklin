package com.smc.franklin.commands.user;

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
public class LoginCommand {

	@Autowired UserRepository userRepository;
	
	public ResponseToken<User> execute(String account, String credential) {
		ResponseToken<User> loginToken = new ResponseToken<User>();
		User user = userRepository.login(account,credential);
		if ( user == null ) {
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setBundleKey("user.account-not-found");
			loginToken.add(message);
		} else {
			loginToken.setPayload(user);
		}
		MessageRoller.roll(loginToken);
		return loginToken;
	}
}
