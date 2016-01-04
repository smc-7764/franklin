package com.smc.franklin.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smc.franklin.dao.Requirement;

/**
 * 
 * @author chq-seanc
 *
 */

public interface RequirementRepository extends MongoRepository<Requirement, String> {
}
