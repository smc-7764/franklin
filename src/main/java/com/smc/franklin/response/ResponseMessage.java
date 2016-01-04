package com.smc.franklin.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * 
 * @author sean_
 *
 */
public class ResponseMessage {
	private String message;
	private final ResponseSeverity severity;
	private String bundleKey;
	private Map<String,Object> bundleParams = new HashMap<String,Object>();

	/**
	 * 
	 * @param severity
	 * @param message
	 */
	@JsonCreator
	public ResponseMessage(ResponseSeverity severity) {
		this.severity = severity;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the severity
	 */
	public ResponseSeverity getSeverity() {
		return severity;
	}

	/**
	 * @return the bundleKey
	 */
	public String getBundleKey() {
		return bundleKey;
	}
	/**
	 * @param bundleKey the bundleKey to set
	 */
	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}
	/**
	 * @return the bundleParams
	 */
	public Map<String, Object> getBundleParams() {
		return bundleParams;
	}
	/**
	 * @param bundleParams the bundleParams to set
	 */
	public void setBundleParams(Map<String, Object> bundleParams) {
		this.bundleParams = bundleParams;
	}

}