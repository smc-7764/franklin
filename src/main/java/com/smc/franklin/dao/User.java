package com.smc.franklin.dao;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User {

	@Id private String id;
	@Indexed private String firstName;
	@Indexed private String lastName;
	@Indexed private String email;
	@Indexed private String userName;
	//the encrypted credential
	private String credential;
	//the encrypted credential confirmation
	private String confirmation;
	private Date lastChanged;
	private Boolean emailNotifications;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the credential
	 */
	public String getCredential() {
		return credential;
	}
	/**
	 * @param credential the credential to set
	 */
	public void setCredential(String credential) {
		this.credential = credential;
	}
	/**
	 * @return the confirmation
	 */
	public String getConfirmation() {
		return confirmation;
	}
	/**
	 * @param confirmation the confirmation to set
	 */
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	/**
	 * @return the lastChanged
	 */
	public Date getLastChanged() {
		return lastChanged;
	}
	/**
	 * @param lastChanged the lastChanged to set
	 */
	public void setLastChanged(Date lastChanged) {
		this.lastChanged = lastChanged;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the emailNotifications
	 */
	public Boolean getEmailNotifications() {
		return emailNotifications;
	}
	/**
	 * @param emailNotifications the emailNotifications to set
	 */
	public void setEmailNotifications(Boolean emailNotifications) {
		this.emailNotifications = emailNotifications;
	}
}

