package com.smc.franklin.dao.enumeration;

public enum UserAccessLevel {
	
	//can make any changes to a plannation
	CREATOR,
	//can edit any plan for which the CREATOR has granted access
	CONTRIBUTOR,
	//can comment/ask questions/respond to requirement questions/clarifications
	COMMENTATOR,
	//readonly - can run reports
	SPECTATOR;
}
