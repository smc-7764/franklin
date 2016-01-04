package com.smc.franklin.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author chq-seanc
 *
 */
public class ResponseToken<T> {
	private ResponseSeverity severity = ResponseSeverity.SUCCESS;
	//rollup of problems
	private List<String> summaries = new ArrayList<String>();
	private List<ResponseMessage> messages = new ArrayList<ResponseMessage>();
	private T payload;

	/**
	 * apply a deciding token, payload is skipped. applied messages can dictate state
	 * @param responseToken
	 */
	public void apply(ResponseToken<?> responseToken) {
		for ( ResponseMessage message : responseToken.getMessages() ) {
			this.add(message);
		}
	}
	/**
	 * 
	 * @return whether token is successful
	 */
	public boolean isSuccessful() {
		return severity.equals(ResponseSeverity.SUCCESS);
	}
	/**
	 * 
	 * @return the messages
	 */
	public List<ResponseMessage> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	/**
	 * 
	 * @param message
	 */
	public void add(ResponseMessage message) {
		messages.add(message);
		if ( severity.equals(ResponseSeverity.SUCCESS)) {
			severity = message.getSeverity();
		} else if ( severity.equals(ResponseSeverity.ERROR) && message.getSeverity().equals(ResponseSeverity.FAILURE)) {
			severity = message.getSeverity();
		}
	}

	/**
	 * @return the severity
	 */
	public ResponseSeverity getSeverity() {
		return severity;
	}

	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(ResponseSeverity severity) {
		this.severity = severity;
	}

	/**
	 * @return the payload
	 */
	public T getPayload() {
		return payload;
	}

	/**
	 * @param payload the payload to set
	 */
	public void setPayload(T payload) {
		this.payload = payload;
	}

	/**
	 * @return the summaries
	 */
	public List<String> getSummaries() {
		return summaries;
	}

	/**
	 * @param summaries the summaries to set
	 */
	public void setSummaries(List<String> summaries) {
		this.summaries = summaries;
	}
	
	@JsonIgnore
	public String printToken() {

		if (!messages.isEmpty()) {
			StringBuilder builder = null;
			for (ResponseMessage message : messages) {
				if (builder == null) {
					builder = new StringBuilder(message.getMessage());
				} else {
					builder.append(";\n" + message.getMessage());
				}
			}
			return builder.toString();
		} else {
			return "";// (sigh) no reason
		}
	
		
	}

}
