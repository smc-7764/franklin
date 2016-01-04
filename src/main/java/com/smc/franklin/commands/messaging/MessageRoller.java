package com.smc.franklin.commands.messaging;

import com.smc.franklin.response.ResponseMessage;
import com.smc.franklin.response.ResponseSeverity;
import com.smc.franklin.response.ResponseToken;

/**
 * I translate and summarize messages
 * 
 * @author sean_
 *
 */
public class MessageRoller {

	public static void roll(ResponseToken<?> token) {
		if ( token.getMessages().isEmpty() ) return;
		
		for ( ResponseMessage message : token.getMessages() ) {
			if ( !message.getSeverity().equals(ResponseSeverity.SUCCESS)) {
				token.getSummaries().add(message.getBundleKey());
			}
		}
	}
}
