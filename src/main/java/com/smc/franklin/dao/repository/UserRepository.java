package com.smc.franklin.dao.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.smc.franklin.dao.User;

/**
 * 
 * @author chq-seanc
 *
 */
public interface UserRepository extends MongoRepository<User, String> {

	/**
	 * 
	 * @param userName
	 * @return a user record by userName
	 */
	@Query("{userName : '?0'}")
	public User findByUserName(String userName);
	

	/**
	 * 
	 * @param userName
	 * @param credential
	 * @return a theoritically authenticated record (by userName and password)
	 */
	@Query("{userName : '?0', credential : '?1'}")
	public User login(String userName, String credential);

}
