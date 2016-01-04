package com.smc.franklin.commands;

import static org.mockito.Mockito.when;

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
import com.smc.franklin.dao.repository.RequirementRepository;
import com.smc.franklin.dao.repository.UserRepository;
import com.smc.franklin.response.ResponseToken;
import com.smc.franklin.view.PlannerNode;
import com.smc.franklin.view.UserModel;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CommandConfig.class, 
     loader = SpringockitoAnnotatedContextLoader.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UpdateNodeCommandTest {
	
	@Autowired 
	@ReplaceWithMock 
	private RequirementRepository requirementRepository;
	
	@Autowired 
	@ReplaceWithMock 
	private UserRepository userRepository;
	
	@Autowired private UpdateNodeCommand updateCommand;
	
	@Test
	public void testPlan() {
		Requirement requirement = new Requirement();
		requirement.setId(UUID.randomUUID().toString());
		String nodeId = UUID.randomUUID().toString();
		Entry entry = new Entry();
		entry.setId(nodeId);
		requirement.getEntries().add(entry);
		
		PlannerNode view = new PlannerNode();
		view.setNodeId(nodeId);

		view.setDescription("A DESCRIPTION");
		view.setStatus(EntryStatus.COMPLETED);
		view.setRelease("1");
		view.setSummary("A SUMMARY");
		
		final String updatorId = "user-123";
		UserModel testModel = new UserModel();
		testModel.setId(updatorId);
		
		when(userRepository.findModelById(updatorId) ).thenReturn(testModel);
		
		ResponseToken<Requirement> responseToken = updateCommand.execute(requirement, view,updatorId );
		Assert.assertTrue(responseToken.printToken(), responseToken.isSuccessful());
		Entry washed = requirement.getEntries().get(0);
		
		Assert.assertEquals(view.getDescription(), washed.getDescription());
		Assert.assertEquals(view.getRelease(), washed.getRelease());
		Assert.assertEquals(view.getSummary(), washed.getSummary());
		Assert.assertEquals(view.getStatus(), washed.getStatus());
	}
}
