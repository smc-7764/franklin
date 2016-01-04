package com.smc.franklin.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="requirement")
public class Requirement {

	@Id private String id;
	@NotNull
	@Indexed private String name;
	@NotNull
	@Indexed private String creatorUserId;
	
	@NotNull
	@Indexed private Date lastChangedDate;
	
	@NotNull
	@Indexed private String lastChangedByUserId;
	
	private List<Entry> entries = new ArrayList<Entry>();
	
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
	 * @return the entries
	 */
	public List<Entry> getEntries() {
		return entries;
	}
	/**
	 * @param entries the entries to set
	 */
	public void setEntries(List<Entry> entries) {
		this.entries = entries;
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

	/**
	 * @return the creatorUserId
	 */
	public String getCreatorUserId() {
		return creatorUserId;
	}
	/**
	 * @param creatorUserId the creatorUserId to set
	 */
	public void setCreatorUserId(String creatorUserId) {
		this.creatorUserId = creatorUserId;
	}
	/**
	 * @return the lastChangedDate
	 */
	public Date getLastChangedDate() {
		return lastChangedDate;
	}
	/**
	 * @param lastChangedDate the lastChangedDate to set
	 */
	public void setLastChangedDate(Date lastChangedDate) {
		this.lastChangedDate = lastChangedDate;
	}
	/**
	 * @return the lastChangedByUserId
	 */
	public String getLastChangedByUserId() {
		return lastChangedByUserId;
	}
	/**
	 * @param lastChangedByUserId the lastChangedByUserId to set
	 */
	public void setLastChangedByUserId(String lastChangedByUserId) {
		this.lastChangedByUserId = lastChangedByUserId;
	}
}

