package com.smc.franklin.commands;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

/**
 * on the annonyance side of Spring MVC, harnessing a WebMV container for a Spring 3.1 load is a pain but without it component scanning will
 * choke on unnecessary code that needs Spring MVC so we include/exclude as necessary
 * 
 * The lightweight test config
 * 
 * @author chq-seanc
 *
 */

@ComponentScan(basePackages = { "com.smc.franklin.commands", "com.smc.franklin.doa.repository"}, useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = MoveEntryCommand.class) 
		, @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UpdateNodeCommand.class)})
public class CommandConfig {
	//@formatter:off
	/**
	 * 
	 * @return the translation bean (hey its there if you need it)
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(new String[] { "classpath:i18n/application", "classpath:i18n/messages" });
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}

	/**
	 * 
	 * @return the property bean (powers @Value("${foo}" )
	 */
	@Bean
	PropertyPlaceholderConfigurer propConfig() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocation(new ClassPathResource("application-test.properties"));
		return ppc;
	}
}
