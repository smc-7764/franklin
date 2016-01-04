package com.smc.franklin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smc.franklin.commands.AddEntryCommand;
import com.smc.franklin.commands.ConstructPlannerCommand;
import com.smc.franklin.commands.FindNodeCommand;
import com.smc.franklin.commands.MoveEntryCommand;
import com.smc.franklin.commands.UpdateNodeCommand;
import com.smc.franklin.commands.user.FindUserByIdCommand;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.User;
import com.smc.franklin.dao.enumeration.MoveDirection;
import com.smc.franklin.dao.repository.PlanRepository;
import com.smc.franklin.response.ResponseToken;
import com.smc.franklin.view.Planner;
import com.smc.franklin.view.PlannerNode;

// @formatter:off
/**

 * 
 * @author chq-seanc
 *
 */

@RestController
public class MainController { 

	@Autowired private PlanRepository planRepository;
	@Autowired private FindUserByIdCommand findUserByIdCommand;
	@Autowired private AddEntryCommand addEntryCommand;
	@Autowired private FindNodeCommand findNodeCommand;
	@Autowired private UpdateNodeCommand updateNodeCommand;
	@Autowired private ConstructPlannerCommand constructPlannerCommand;
	@Autowired private MoveEntryCommand moveEntryCommand;
	/**
	 * 
	 * @return the newly created plan
	 */
	@RequestMapping(value = "/plans/create/{name:.+}", method = RequestMethod.PUT, headers="Accept=application/json", produces={"application/json"})
	public ResponseToken<Planner> create(@PathVariable String name, @RequestParam("userId") String userId) {
		ResponseToken<Planner> responseToken = new ResponseToken<Planner>();
		ResponseToken<User> userToken = findUserByIdCommand.findUserById(userId);
		if ( !userToken.isSuccessful() ) {
			responseToken.apply(userToken);
			return responseToken;
		}
		Requirement plan = new Requirement();
		plan.setName(name);
		plan.setUserId(userId);
		plan = planRepository.save(plan);
		responseToken.setPayload(constructPlannerCommand.execute(plan));
		return responseToken;
	}
	
	/**
	 * 
	 * @return the plan with the new entry
	 */
	@RequestMapping(value = "/plans/create/{planId}/entry/{predecessor}", method = RequestMethod.POST, headers="Accept=application/json", produces={"application/json"})
	public Planner createEntry(@PathVariable String planId, @PathVariable String predecessor) {
		Requirement plan = addEntryCommand.execute(planId, predecessor);
		return constructPlannerCommand.execute(plan);
	}
	
	/**
	 * 
	 * @return all plans
	 */
	@RequestMapping(value = "/plans", method = RequestMethod.GET)
	public List<Requirement> findAll() {
		return planRepository.findAll();
	}
	
	/**
	 * 
	 * @return all plans
	 */
	@RequestMapping(value = "/plans/{id}", method = RequestMethod.GET)
	public Planner findPlan(@PathVariable String id) {
		Requirement plan = planRepository.findOne(id);
		return constructPlannerCommand.execute(plan);
	}
	
	/**
	 * 
	 * @return all plans
	 */
	@RequestMapping(value = "/plans/{planId}/{nodeId}", method = RequestMethod.GET)
	public PlannerNode findNode(@PathVariable String planId, @PathVariable String nodeId) {
		return findNodeCommand.execute(planId, nodeId);
	}
	
	/**
	 * 
	 * @return the plan with the new entry
	 */
	@RequestMapping(value = "/plans/{planId}/updateNode", method = RequestMethod.PUT, headers="Accept=application/json", produces={"application/json"})
	public Planner updateNode(@PathVariable String planId
			, @RequestBody final PlannerNode node) {
		Requirement plan = updateNodeCommand.execute(planId,node);
		return constructPlannerCommand.execute(plan);
	}
	
	/**
	 * 
	 * @return the plan with the new entry
	 */
	@RequestMapping(value = "/plans/{planId}/{nodeId}/{direction}", method = RequestMethod.PUT, headers="Accept=application/json", produces={"application/json"})
	public Planner move(@PathVariable String planId
			, @PathVariable String nodeId
			, @PathVariable MoveDirection direction) {
		return moveEntryCommand.execute(planRepository.findOne(planId), nodeId, direction);
	}

}