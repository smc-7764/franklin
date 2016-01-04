package com.smc.franklin.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.smc.franklin.commands.requirements.RetrieveRequirementsCommand;
import com.smc.franklin.commands.user.FindUserModelByIdCommand;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.enumeration.MoveDirection;
import com.smc.franklin.dao.repository.RequirementRepository;
import com.smc.franklin.response.ResponseToken;
import com.smc.franklin.view.Planner;
import com.smc.franklin.view.PlannerNode;
import com.smc.franklin.view.RequirementListing;
import com.smc.franklin.view.UserModel;

// @formatter:off
/**

 * 
 * @author chq-seanc
 *
 */

@RestController
public class MainController { 

	@Autowired private RequirementRepository requirementRepository;
	@Autowired private FindUserModelByIdCommand findUserModelByUserIdCommand;
	@Autowired private AddEntryCommand addEntryCommand;
	@Autowired private FindNodeCommand findNodeCommand;
	@Autowired private UpdateNodeCommand updateNodeCommand;
	@Autowired private ConstructPlannerCommand constructPlannerCommand;
	@Autowired private MoveEntryCommand moveEntryCommand;
	@Autowired private RetrieveRequirementsCommand retrieveRequirementsCommand;
	

	/**
	 * 
	 * @return the newly created plan
	 */
	@RequestMapping(value = "/requirements/create/{name:.+}", method = RequestMethod.PUT, headers="Accept=application/json", produces={"application/json"})
	public ResponseEntity<ResponseToken<Planner>> create(@PathVariable String name, @RequestParam("userId") String creatorId) {
		HttpHeaders responseHeaders = new HttpHeaders();
		ResponseToken<Planner> responseToken = new ResponseToken<Planner>();
		
		ResponseToken<UserModel> userToken = findUserModelByUserIdCommand.execute(creatorId);
		if ( !userToken.isSuccessful() ) {
			responseToken.apply(userToken);
			return new ResponseEntity<ResponseToken<Planner>>(responseToken,
					responseHeaders, HttpStatus.UNAUTHORIZED);
		}
		Requirement requirement = new Requirement();
		requirement.setName(name);
		requirement.setCreatorUserId(creatorId);
		requirement.setLastChangedByUserId(creatorId);
		requirement.setLastChangedDate(new Date(System.currentTimeMillis()));
		requirement = requirementRepository.save(requirement);
		responseToken.setPayload(constructPlannerCommand.execute(requirement));
		
		return new ResponseEntity<ResponseToken<Planner>>(responseToken,
				responseHeaders, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @return the plan with the new entry
	 */
	@RequestMapping(value = "/requirements/create/{planId}/entry/{predecessor}", method = RequestMethod.POST, headers="Accept=application/json", produces={"application/json"})
	public Planner createEntry(@PathVariable String planId, @PathVariable String predecessor) {
		Requirement plan = addEntryCommand.execute(planId, predecessor);
		return constructPlannerCommand.execute(plan);
	}
	
	/**
	 * 
	 * @return all plans
	 */
	@RequestMapping(value = "/requirements", method = RequestMethod.GET)
	public ResponseEntity<ResponseToken<List<RequirementListing>>>  findAll() {
		ResponseToken<List<RequirementListing>> responseToken = retrieveRequirementsCommand.execute();
		
		return new ResponseEntity<ResponseToken<List<RequirementListing>>>(responseToken,
				new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @return all plans
	 */
	@RequestMapping(value = "/requirements/{id}", method = RequestMethod.GET)
	public Planner findPlan(@PathVariable String id) {
		Requirement plan = requirementRepository.findOne(id);
		return constructPlannerCommand.execute(plan);
	}
	
	/**
	 * 
	 * @return all plans
	 */
	@RequestMapping(value = "/requirements/{planId}/{nodeId}", method = RequestMethod.GET)
	public PlannerNode findNode(@PathVariable String planId, @PathVariable String nodeId) {
		return findNodeCommand.execute(planId, nodeId);
	}
	
	/**
	 * 
	 * @return the plan with the new entry
	 */
	@RequestMapping(value = "/requirements/{planId}/updateNode", method = RequestMethod.PUT, headers="Accept=application/json", produces={"application/json"})
	public ResponseEntity<ResponseToken<Planner>> updateNode(@PathVariable String planId
			, @RequestBody final PlannerNode node
			, @RequestParam("updatorId") String updatorId) {
		
		ResponseToken<Planner> responseToken = new ResponseToken<Planner>();
		ResponseToken<Requirement> requirementToken = updateNodeCommand.execute(planId,node, updatorId);
		
		HttpStatus status = null;
		if ( requirementToken.isSuccessful() ) {
			Planner planner = constructPlannerCommand.execute(requirementToken.getPayload());
			responseToken.setPayload(planner);
			status = HttpStatus.OK;
		} else {
			responseToken.apply(requirementToken);
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<ResponseToken<Planner>>(responseToken,
				new HttpHeaders(), status);
		
	}
	
	/**
	 * 
	 * @return the plan with the new entry
	 */
	@RequestMapping(value = "/requirements/{planId}/{nodeId}/{direction}", method = RequestMethod.PUT, headers="Accept=application/json", produces={"application/json"})
	public Planner move(@PathVariable String planId
			, @PathVariable String nodeId
			, @PathVariable MoveDirection direction) {
		return moveEntryCommand.execute(requirementRepository.findOne(planId), nodeId, direction);
	}

}