package com.smc.franklin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@formatter:off
/**
 * Mongo configurator, specifies what to scan 
 * Once scanned, builds up Mongo Hemisphere
 * @author chq-seanc
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = 
{ 	"com.smc.franklin.dao.repository"
	,"com.smc.franklin.dao" })
//@formatter:on
public class MongoRepositoryConfiguration {
}
