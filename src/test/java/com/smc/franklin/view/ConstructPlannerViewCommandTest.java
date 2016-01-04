package com.smc.franklin.view;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.smc.franklin.commands.ConstructPlannerCommand;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.Entry;



public class ConstructPlannerViewCommandTest {

	/**
	 * Confirm new node added under header entry
	 */
	@Test
	public void assertConstruction() {
		Requirement plan = genStarterKit();
		ConstructPlannerCommand command = new ConstructPlannerCommand();
		Planner view = command.execute(plan);
		Assert.assertEquals(2, view.getNodes().size());	
		
		PlannerNode entry = view.getNodes().get(0);
		Assert.assertEquals(entry.getCoordinate(), "1");		
		Entry child1 = new Entry();
		child1.setId(UUID.randomUUID().toString());
		child1.setParentId(entry.getNodeId());
		
		Entry child2 = new Entry();
		child2.setId(UUID.randomUUID().toString());
		child2.setParentId(entry.getNodeId());		
		
		plan.getEntries().get(0).getChildNodes().add(child1);
		plan.getEntries().get(0).getChildNodes().add(child2);
		
		 view = command.execute(plan);
		 Assert.assertEquals(view.getNodes().get(2).getCoordinate(), "1.2");
		 
		 child2 = plan.getEntries().get(0).getChildNodes().get(1);
		 Entry child2child = new Entry();
		 child2child.setId(UUID.randomUUID().toString());
		 child2child.setParentId(child2.getId());
		 child2.getChildNodes().add(child2child);
		 
		 view = command.execute(plan);
		 Assert.assertEquals(view.getNodes().get(3).getCoordinate(), "1.2.1");
		 
	}
	
	/**
	 * Create the first entry
	 * @return
	 */
	private Requirement genStarterKit() {
		Requirement plan = new Requirement();
		plan.setId(UUID.randomUUID().toString());
		plan.setName("Test Plan");
		Entry entry = new Entry();
		entry.setParentId(plan.getId());
		entry.setId(UUID.randomUUID().toString());
		plan.getEntries().add(entry);
		Entry entry2 = new Entry();
		entry2.setParentId(plan.getId());
		entry2.setId(UUID.randomUUID().toString());
		plan.getEntries().add(entry2);
		return plan;
	}
}
