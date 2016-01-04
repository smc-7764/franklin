package com.smc.franklin.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.dao.Entry;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.repository.PlanRepository;
import com.smc.franklin.view.PlannerNode;

@Component
public class UpdateNodeCommand {

	@Autowired private PlanRepository planRepository;
	
	/**
	 * 
	 * @param planId
	 * @param nodeId
	 * @param node
	 */
	public Requirement execute(String planId, PlannerNode node) {
		Requirement plan = planRepository.findOne(planId);	
		return execute(plan, node);
	}
	
	/**
	 * 
	 * @param plan
	 * @param node
	 * @return
	 */
	public Requirement execute(Requirement plan, PlannerNode node) {
		NodeLocator locator = new NodeLocator();
		Entry entry = locator.locate(plan,  node.getNodeId()).getEntry();
		entry.setDescription(node.getDescription());
		entry.setRelease(node.getRelease());
		entry.setStatus(node.getStatus());
		entry.setSummary(node.getSummary());
		return planRepository.save(plan);	
	}
}
