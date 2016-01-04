package com.smc.franklin.commands;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoAnnotatedContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.smc.franklin.dao.Entry;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.enumeration.EntryStatus;
import com.smc.franklin.dao.repository.PlanRepository;
import com.smc.franklin.view.PlannerNode;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CommandConfig.class, 
     loader = SpringockitoAnnotatedContextLoader.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UpdateNodeCommandTest {
	
	@Autowired 
	@ReplaceWithMock 
	private PlanRepository planRepository;
	
	@Autowired private UpdateNodeCommand updateCommand;
	
	@Test
	public void testPlan() {
		Requirement plan = new Requirement();
		plan.setId(UUID.randomUUID().toString());
		String nodeId = UUID.randomUUID().toString();
		Entry entry = new Entry();
		entry.setId(nodeId);
		plan.getEntries().add(entry);
		
		PlannerNode view = new PlannerNode();
		view.setNodeId(nodeId);

		view.setDescription("A DESCRIPTION");
		view.setStatus(EntryStatus.COMPLETED);
		view.setRelease("1");
		view.setSummary("A SUMMARY");
		
		updateCommand.execute(plan, view);
		Entry washed = plan.getEntries().get(0);
		
		Assert.assertEquals(view.getDescription(), washed.getDescription());
		Assert.assertEquals(view.getRelease(), washed.getRelease());
		Assert.assertEquals(view.getSummary(), washed.getSummary());
		Assert.assertEquals(view.getStatus(), washed.getStatus());
	}
}
