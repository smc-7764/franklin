package com.smc.franklin.commands.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.commands.messaging.MessageRoller;
import com.smc.franklin.dao.User;
import com.smc.franklin.dao.repository.UserRepository;
import com.smc.franklin.response.ResponseMessage;
import com.smc.franklin.response.ResponseSeverity;
import com.smc.franklin.response.ResponseToken;
import com.smc.franklin.view.UserModel;

/**
 * 
 * @author sean_
 *
 */
@Component
public class LoginCommand {

	@Autowired UserRepository userRepository;
	
	public ResponseToken<UserModel> execute(String account, String credential) {
		ResponseToken<UserModel> loginToken = new ResponseToken<UserModel>();
		User user = userRepository.login(account,credential);
		
		
		if ( user == null ) {
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setBundleKey("user.account-not-found");
			loginToken.add(message);
		} else {
			UserModel userModel = new UserModel();
			userModel.setEmail(user.getEmail());
			userModel.setId(user.getId());
			userModel.setFirstName(user.getFirstName());
			userModel.setLastName(user.getLastName());
			userModel.setScreenName(user.getScreenName());
			userModel.setUserName(user.getUserName());
			userModel.setLastChanged(user.getLastChanged());
			loginToken.setPayload(userModel);
		}
		MessageRoller.roll(loginToken);
		return loginToken;
	}
}
