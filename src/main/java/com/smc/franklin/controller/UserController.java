package com.smc.franklin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smc.franklin.commands.user.LoginCommand;
import com.smc.franklin.commands.user.SaveUserCommand;
import com.smc.franklin.dao.User;
import com.smc.franklin.response.ResponseMessage;
import com.smc.franklin.response.ResponseSeverity;
import com.smc.franklin.response.ResponseToken;

// @formatter:off
/**

 * 
 * @author chq-seanc
 *
 */

@RestController
public class UserController { 

	@Autowired private SaveUserCommand saveUserCommand;
	@Autowired private LoginCommand loginCommand;
	/**
	 * 
	 * @return the plan with the new entry
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers="Accept=application/json", produces={"application/json"})
	public ResponseToken<User> login(@RequestParam("account") String account, @RequestParam("credential") String credential) {
		return loginCommand.execute(account, credential);
	}
	
	/**
	 * 
	 * @return the plan with the new entry
	 */
	@RequestMapping(value = "/users/create", method = RequestMethod.PUT, headers="Accept=application/json", produces={"application/json"})
	public ResponseToken<User> create(@RequestBody final User user) {
		return saveUserCommand.execute(user);
	}
	
	/**
	 * 
	 * @return the plan with the new entry
	 */
	@RequestMapping(value = "/users/update", method = RequestMethod.PUT, headers="Accept=application/json", produces={"application/json"})
	public ResponseToken<User> update(@RequestBody final User user) {
		if ( user.getId() == null ) {
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setBundleKey("user.userId-cannot-be-null");
			ResponseToken<User> responseToken = new ResponseToken<User>();
			responseToken.add(message);
		}
		
		return saveUserCommand.execute(user);
	}
}