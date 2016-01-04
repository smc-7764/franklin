package com.smc.franklin.commands.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.controller.MainController;
import com.smc.franklin.dao.repository.UserRepository;
import com.smc.franklin.response.ResponseMessage;
import com.smc.franklin.response.ResponseSeverity;
import com.smc.franklin.response.ResponseToken;
import com.smc.franklin.view.UserModel;

@Component
public class FindUserModelByIdCommand {

	@Autowired UserRepository userRepository;
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
	
	/**
	 * 
	 * @param userId
	 * @return the user model as a token payload ( check the token for problems )
	 */
	public ResponseToken<UserModel> execute(String userId) {
		ResponseToken<UserModel> responseToken = new ResponseToken<UserModel>();
		UserModel model = userRepository.findModelById(userId);
		if (model == null ) {
			LOGGER.error("Failed to find user for id { " + userId + " }");
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setBundleKey("user.account-not-found");
			responseToken.add(message);
		} else {
			responseToken.setPayload(model);
		}
		return responseToken;
		
	}
}
