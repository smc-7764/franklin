package com.smc.franklin.view;

import java.util.ArrayList;
import java.util.List;

public class Planner {

	private String name;
	private String planId;
	private List<PlannerNode> nodes = new ArrayList<PlannerNode>();
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
	 * @return the nodes
	 */
	public List<PlannerNode> getNodes() {
		return nodes;
	}
	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(List<PlannerNode> nodes) {
		this.nodes = nodes;
	}
	
}
