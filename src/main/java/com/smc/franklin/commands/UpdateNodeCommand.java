package com.smc.franklin.commands;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.dao.Entry;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.repository.RequirementRepository;
import com.smc.franklin.dao.repository.UserRepository;
import com.smc.franklin.response.ResponseMessage;
import com.smc.franklin.response.ResponseSeverity;
import com.smc.franklin.response.ResponseToken;
import com.smc.franklin.view.PlannerNode;
import com.smc.franklin.view.UserModel;

@Component
public class UpdateNodeCommand {

	@Autowired private RequirementRepository requirementRepository;
	@Autowired private UserRepository userRepository;
	/**
	 * 
	 * @param planId
	 * @param nodeId
	 * @param node
	 */
	public ResponseToken<Requirement> execute(String planId, PlannerNode node, String updatorId) {
		Requirement plan = requirementRepository.findOne(planId);	
		return execute(plan, node, updatorId);
	}
	
	/**
	 * 
	 * @param plan
	 * @param node
	 * @param updatorId
	 * @return the requirement
	 */
	public ResponseToken<Requirement> execute(Requirement requirement, PlannerNode node, String updatorId) {
	    ResponseToken<Requirement> response = new  ResponseToken<Requirement>();
		UserModel userModel = userRepository.findModelById(updatorId);
		if ( userModel == null ) {
			ResponseMessage message = new ResponseMessage(ResponseSeverity.ERROR);
			message.setMessage( "WIP!! - Updator cannot be null ");
			message.setBundleKey("requirement.updator-cannot-be-null");
			message.getBundleParams().put("requirementId", requirement.getId());
			message.getBundleParams().put("nodeId", node.getNodeId());
			response.add(message);
			return response;
		}
		NodeLocator locator = new NodeLocator();
		Entry entry = locator.locate(requirement,node.getNodeId()).getEntry();
		entry.setDescription(node.getDescription());
		entry.setRelease(node.getRelease());
		entry.setStatus(node.getStatus());
		entry.setSummary(node.getSummary());
		
		requirement.setLastChangedDate(new Date(System.currentTimeMillis()));
		requirement.setLastChangedByUserId(updatorId);
	
		response.setPayload(requirementRepository.save(requirement));
		return response;
	}
}
