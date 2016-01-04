package com.smc.franklin.commands.requirements;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smc.franklin.dao.Requirement;
import com.smc.franklin.dao.repository.RequirementRepository;
import com.smc.franklin.dao.repository.UserRepository;
import com.smc.franklin.response.ResponseToken;
import com.smc.franklin.view.RequirementListing;

/**
 * I provide a headliner result set for available requirements
 * TBD : whether to filter the view by user 
 * 
 * @author chq-seanc
 *
 */
@Component
public class RetrieveRequirementsCommand {

	@Autowired RequirementRepository requirementRepository;
	@Autowired UserRepository userRepository;
	//private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveRequirementsCommand.class);
	
//
//	public ResponseToken<List<RequirementListing>> execute(String userId) {
//
//		
//	}
	
	public ResponseToken<List<RequirementListing>> execute() {
		ResponseToken<List<RequirementListing>> responseToken = new ResponseToken<List<RequirementListing>>();
		List<Requirement> requirements = requirementRepository.findAll();
		List<RequirementListing> listings = new ArrayList<RequirementListing>();
		for ( Requirement req : requirements ) {
			RequirementListing listing = new RequirementListing();
			//tech rule : creator and last change userId cannot be bull
				listing.setId(req.getId());
				listing.setName(req.getName());
				listing.setLastChanged(req.getLastChangedDate());
				listing.setCreator(userRepository.findModelById(req.getCreatorUserId() ));
				listing.setLastChangedBy(userRepository.findModelById(req.getLastChangedByUserId() ));
				
			listings.add(listing);
		}

		responseToken.setPayload(listings);
		return responseToken;
		
	}
	
	
}
