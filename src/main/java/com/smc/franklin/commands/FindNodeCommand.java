package com.smc.franklin.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.dao.Entry;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.repository.RequirementRepository;
import com.smc.franklin.view.Planner;
import com.smc.franklin.view.PlannerNode;

@Component
public class FindNodeCommand {

	@Autowired private RequirementRepository planRepository;
	@Autowired private ConstructPlannerCommand constructPlannerCommand;
	/**
	 * 
	 * @param planId
	 * @param nodeId
	 * @param node
	 */
	public PlannerNode execute(String planId, String nodeId) {
		Requirement plan = planRepository.findOne(planId);	
		Planner planner = constructPlannerCommand.execute(plan);
		PlannerNode target = null;
		for ( PlannerNode node:  planner.getNodes() ) {
			if ( node.getNodeId().equals(nodeId)) {
				target = node; break;
			}
		}
		return target;
	}
	
	/**
	 * 
	 * @param plan
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
