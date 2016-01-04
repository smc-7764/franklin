package com.smc.franklin.commands;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoAnnotatedContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.smc.franklin.dao.Entry;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.enumeration.MoveDirection;
import com.smc.franklin.dao.repository.RequirementRepository;
import com.smc.franklin.view.Planner;
import com.smc.franklin.view.PlannerNode;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CommandConfig.class, 
     loader = SpringockitoAnnotatedContextLoader.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MoveNodeCommandTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired 
	@ReplaceWithMock 
	private RequirementRepository requirementRepository;
	
	@Autowired private MoveEntryCommand moveEntryCommand;
	
	@Test
	public void testDemoteHeader() {
		Requirement requirement = new Requirement();
		requirement.setName("foo");
		requirement.setId("planId");
		String nodeId = "nodeId";
		Entry entry = new Entry();
		entry.setId(nodeId);
		requirement.getEntries().add(entry);
		
		Entry entryB = new Entry();
		String nodeBId = "nodeBId";
		entryB.setId(nodeBId);
		requirement.getEntries().add(entryB);

		when(requirementRepository.save(requirement)).thenReturn(requirement);
		
		Planner planner = moveEntryCommand.execute(requirement, nodeBId, MoveDirection.DEMOTE);
		PlannerNode washed = planner.getNodes().get(1);		
		Assert.assertEquals("1.1", washed.getCoordinate());
		
		planner = moveEntryCommand.execute(requirement, nodeBId, MoveDirection.PROMOTE);
		washed = planner.getNodes().get(1);		
		Assert.assertEquals("2", washed.getCoordinate());
		
		planner = moveEntryCommand.execute(requirement, nodeBId, MoveDirection.UP);
		washed = planner.getNodes().get(1);		
		Assert.assertEquals("2", washed.getCoordinate());
		Assert.assertEquals(nodeId, washed.getNodeId());
		
		planner = moveEntryCommand.execute(requirement, nodeBId, MoveDirection.DOWN);
		washed = planner.getNodes().get(1);		
		Assert.assertEquals("2", washed.getCoordinate());
		Assert.assertEquals(nodeBId, washed.getNodeId());
	}
}
