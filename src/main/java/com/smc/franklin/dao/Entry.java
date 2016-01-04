package com.smc.franklin.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

import com.smc.franklin.dao.enumeration.EntryStatus;


public class Entry {

	private String id;
	private String parentId;
	@Indexed private String release;
	private Integer level;
	private Integer index;
	private Integer section;
	private String summary;
	private String description;
	@Indexed private EntryStatus status;
	private Boolean qaVerified;
	
	private List<Entry> childNodes = new ArrayList<Entry>();
	/**
	 * @return the release
	 */
	public String getRelease() {
		return release;
	}
	/**
	 * @param release the release to set
	 */
	public void setRelease(String release) {
		this.release = release;
	}
	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String details) {
		this.description = details;
	}
	/**
	 * @return the status
	 */
	public EntryStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(EntryStatus status) {
		this.status = status;
	}
	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}
	/**
	 * @return the section
	 */
	public Integer getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(Integer section) {
		this.section = section;
	}
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
	 * @return the childNodes
	 */
	public List<Entry> getChildNodes() {
		return childNodes;
	}
	/**
	 * @param childNodes the childNodes to set
	 */
	public void setChildNodes(List<Entry> childNodes) {
		this.childNodes = childNodes;
	}
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the qaVerified
	 */
	public Boolean getQaVerified() {
		return qaVerified;
	}
	/**
	 * @param qaVerified the qaVerified to set
	 */
	public void setQaVerified(Boolean qaVerified) {
		this.qaVerified = qaVerified;
	}
	
}

