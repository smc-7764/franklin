package com.smc.franklin.response;

/**
 * Types the severity of a message
 * 
 * @author chq-seanc
 *
 */
public enum ResponseSeverity {

	/**
	 * Token level default
	 * 
	 */
	SUCCESS,
	
	/**
	 * A theoretically recoverable problem
	 * 
	 * Use for validation and logic issues
	 * 
	 * 
	 */
	ERROR,

	/**
	 * Unrecoverables - a future release will include the ability to email
	 * these errors to someone who can fix em
	 */
	FAILURE;

}