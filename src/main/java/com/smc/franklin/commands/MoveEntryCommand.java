package com.smc.franklin.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.commands.NodeLocator.Located;
import com.smc.franklin.dao.Entry;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.enumeration.MoveDirection;
import com.smc.franklin.dao.repository.PlanRepository;
import com.smc.franklin.view.Planner;

@Component
public class MoveEntryCommand {

	@Autowired private PlanRepository planRepository;
	
	/**
	 * 
	 * @param planId
	 * @param entityId
	 * @param direction
	 * @return the rejiggered listing
	 */
	public Planner execute(Requirement plan, String entityId, MoveDirection direction) {
		NodeLocator locator = new NodeLocator();
		Located located = locator.locate(plan, entityId);
		switch( direction ) {
		case DEMOTE:			
			located.getNeighborhood().remove(located.getEntry());
			located.getUp().getChildNodes().add(located.getEntry());
			break;
		case PROMOTE:
			located.getNeighborhood().remove(located.getEntry());
			Located parent = locator.locate(plan, located.getParent().getId());
			if ( parent.getParent() == null ) {
				//promoted to top level
				plan.getEntries().add(located.getEntry());
			} else {
				parent.getParent().getChildNodes().add(located.getEntry());
			}
			break;
		case DOWN:
			List<Entry> revised = new ArrayList<Entry>();
			for ( Entry entry : located.getNeighborhood() ) {
				if (entry.equals(located.getEntry())) {
					continue;
				}
				if ( entry.equals(located.getDown())) {
					//invert the target with the one below
					revised.add(located.getDown());
					revised.add(located.getEntry());
				} else {
					revised.add(entry);
				}
			}
			located.getNeighborhood().clear();
			located.getNeighborhood().addAll(revised);
			break;
		case UP:
			revised = new ArrayList<Entry>();
			Entry prev = null;
			for ( Entry entry : located.getNeighborhood() ) {
				if ( prev == null ) { 
					//technical rule : you cannot move the top node up
					prev = entry; continue; 
				}
				
				if (entry.equals(located.getUp())) {
					continue;
				}
				if ( entry.equals(located.getEntry())) {
					//invert the target with the one above		revised.add(located.getEntry());
					revised.remove(prev);
					revised.add(entry);
					revised.add(prev);
					break;
				} else {
					revised.add(entry);
				}
				
				prev = entry;
			}
			located.getNeighborhood().clear();
			located.getNeighborhood().addAll(revised);
		}
		
		plan = planRepository.save(plan);
		ConstructPlannerCommand viewer = new ConstructPlannerCommand();
		Planner planner = viewer.execute(plan);
	
		return planner;
	}
}
