package com.smc.franklin.commands.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.dao.User;
import com.smc.franklin.dao.repository.UserRepository;
import com.smc.franklin.response.ResponseMessage;
import com.smc.franklin.response.ResponseSeverity;
import com.smc.franklin.response.ResponseToken;

@Component
public class FindUserByIdCommand {

	@Autowired UserRepository userRepository;
	
	public ResponseToken<User> findUserById(String id) {
		ResponseToken<User> responseToken = new ResponseToken<User>();
		User user = userRepository.findOne(id);
		if (user == null ) {
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setBundleKey("user.account-not-found");
			responseToken.add(message);
		} else {
			responseToken.setPayload(user);
		}
		return responseToken;
		
	}
}
