package com.smc.franklin.view;

import java.util.Date;

public class RequirementListing {
	
	private String id;
	private UserModel creator;
	private String name;
	private Date lastChanged;
	private UserModel lastChangedBy;
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
	 * @return the creator
	 */
	public UserModel getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(UserModel creator) {
		this.creator = creator;
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
	 * @return the lastChangedBy
	 */
	public UserModel getLastChangedBy() {
		return lastChangedBy;
	}
	/**
	 * @param lastChangedBy the lastChangedBy to set
	 */
	public void setLastChangedBy(UserModel lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
