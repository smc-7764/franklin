package com.smc.franklin.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.smc.franklin.dao.enumeration.UserAccessLevel;

@Document(collection="requirementUser")
public class RequirementUser {

	@Id private String id;
	@Indexed private String planId;
	@Indexed private String userId;
	private UserAccessLevel access;
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
	 * @return the planId
	 */
	public String getPlanId() {
		return planId;
	}
	/**
	 * @param planId the planId to set
	 */
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the access
	 */
	public UserAccessLevel getAccess() {
		return access;
	}
	/**
	 * @param access the access to set
	 */
	public void setAccess(UserAccessLevel access) {
		this.access = access;
	}
}

