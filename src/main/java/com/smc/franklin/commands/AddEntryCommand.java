package com.smc.franklin.commands;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.dao.Entry;
import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.enumeration.EntryStatus;
import com.smc.franklin.dao.repository.PlanRepository;

/**
 * I add an entry after its predecessor
 * @author sean_
 *
 */
@Component
public class AddEntryCommand {

	@Autowired private PlanRepository planRepository;
	
	/**
	 * 
	 * @param planId
	 * @param preceedingId
	 * @return the plan
	 */
	public Requirement execute(String planId, String preceedingId) {
		Requirement plan = planRepository.findOne(planId);
		return execute(plan, preceedingId);
	}
	/**
	 * 
	 * @param plan
	 * @param preceedingId
	 * @return the plan
	 */
	public Requirement execute(Requirement plan, String preceedingId) {
		Entry entry = new Entry();
		entry.setId(UUID.randomUUID().toString());
		entry.setStatus(EntryStatus.DRAFT);

		if ( plan.getEntries().isEmpty() || plan.getId().equals(preceedingId) || preceedingId.equals("-1")) {
			plan.getEntries().add(entry);
		} else {
			Entry preceeder = locate(plan.getEntries(), preceedingId);
			preceeder.getChildNodes().add(entry);
		}
		
		return planRepository.save(plan);
	}
	
	/**
	 * 
	 * @param entries
	 * @param preceedingId
	 * @return the entry
	 */
	private Entry locate(List<Entry> entries, String preceedingId) {
		Entry locator = null;
		for ( Entry entry: entries) {
			if ( entry.getId().equals(preceedingId)) {
				locator = entry; break;
			} else {
				locator = locate(entry.getChildNodes(), preceedingId);
				if ( locator != null ) {
					break;
				}
			}
		}
		return locator;
		
	}
	
	
}
