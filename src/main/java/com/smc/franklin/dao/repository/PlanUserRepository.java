package com.smc.franklin.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smc.franklin.dao.RequirementUser;

/**
 * 
 * @author chq-seanc
 *
 */
public interface PlanUserRepository extends MongoRepository<RequirementUser, String> {

}
