package com.smc.franklin.commands;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.dao.Entry;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.enumeration.MoveDirection;
import com.smc.franklin.dao.repository.UserRepository;
import com.smc.franklin.view.Planner;
import com.smc.franklin.view.PlannerNode;

@Component
public class ConstructPlannerCommand {

	@Autowired private UserRepository userRepository;
	/**
	 * 
	 * @param planId
	 * @param entityId
	 * @param direction
	 * @return the rejiggered listing
	 */
	public Planner execute(Requirement requirement) {
		Planner view = new Planner();
		view.setName(requirement.getName());
		view.setPlanId(requirement.getId());
		view.setCreator(userRepository.findModelById(requirement.getCreatorUserId()));

	
		rollEntries(view,requirement.getEntries(),1, null);
		
		return view;
	}
	
	/**
	 * 
	 * @param view
	 * @param entries
	 * @param depth
	 * @param parentCoordinate
	 */
	private void rollEntries(Planner view, List<Entry> entries, Integer depth, String parentCoordinate) {
		if ( entries.isEmpty() ) {
			return;
		}
		
		int neighborhod = entries.size(); //the number of like nodes
		for (int i = 0; i < entries.size(); i++ ) {
			String coordinate = 
					parentCoordinate == null ? String.valueOf(i + 1) : parentCoordinate + "." + (i+1);
			Entry entry = entries.get(i);
			PlannerNode node = new PlannerNode();
			node.setDepth(depth);
			node.setRelease(entry.getRelease());
			node.setNodeId(entry.getId());
			node.setStatus(entry.getStatus());
			node.setSummary(entry.getSummary());
			node.setDescription(entry.getDescription());
			node.setCoordinate(coordinate);
			calculateMovements(node, neighborhod, depth, i);
			view.getNodes().add(node);
			rollEntries(view, entry.getChildNodes(), (depth + 1), coordinate);
		}
	}
	
	private void calculateMovements(PlannerNode node, Integer neighborhood, Integer depth, Integer sequence) {
		if ( depth > 1 ) {
			//can prmote anything that is not already at the top level
			node.getDirections().add(MoveDirection.PROMOTE);
		}
		if ( sequence != 0 ) {
			//can demote anything that is not the first node in the hierarchy
			node.getDirections().add(MoveDirection.DEMOTE);
			//can move anything up that is not already at the top
			node.getDirections().add(MoveDirection.UP);
		}
		if ( (sequence + 1 ) < neighborhood) {
			//can move anything down that is not already at the bottom
			node.getDirections().add(MoveDirection.DOWN);
		}		
	}
}
