package com.smc.franklin.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.smc.franklin.dao.enumeration.EntryStatus;
import com.smc.franklin.dao.enumeration.MoveDirection;

public class PlannerNode {

	private String nodeId;
	private String summary;
	private String coordinate;
	private String description;
	private Set<MoveDirection> directions = new HashSet<MoveDirection>();
	private List<PlannerNode> linkedTo = new ArrayList<PlannerNode>();
	private String release;
	private EntryStatus status;
	//how nested (used as a hint for UI nesting)
	private Integer depth;
	private Boolean qaVerified;
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
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
	 * @return the coordinate
	 */
	public String getCoordinate() {
		return coordinate;
	}
	/**
	 * @param coordinate the coordinate to set
	 */
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
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
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the directions
	 */
	public Set<MoveDirection> getDirections() {
		return directions;
	}
	/**
	 * @param directions the directions to set
	 */
	public void setDirections(Set<MoveDirection> directions) {
		this.directions = directions;
	}
	/**
	 * @return the linkedTo
	 */
	public List<PlannerNode> getLinkedTo() {
		return linkedTo;
	}
	/**
	 * @param linkedTo the linkedTo to set
	 */
	public void setLinkedTo(List<PlannerNode> linkedTo) {
		this.linkedTo = linkedTo;
	}
	/**
	 * @return the release
	 */
	public String getRelease() {
		return release;
	}
	/**
	 * @param release the release to set
	 */
	public void setRelease(String releaseId) {
		this.release = releaseId;
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
	 * @return the depth
	 */
	public Integer getDepth() {
		return depth;
	}
	/**
	 * @param depth the depth to set
	 */
	public void setDepth(Integer depth) {
		this.depth = depth;
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
