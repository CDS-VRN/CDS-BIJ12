/**
 *
 */
package nl.ipo.cds.deegree;

import nl.ipo.cds.properties.ConfigDirPropertyPlaceholderConfigurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author reinoldp
 */
@Configuration
@Import({ DataSourceConfig.class })
public class DeegreeConfig {

	/**
	 * <bean id="propertyPlaceholderConfigurer" class="nl.ipo.cds.properties.ConfigDirPropertyPlaceholderConfigurer"/>
	 * 
	 * @return
	 */
	@Bean
	public static ConfigDirPropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		final ConfigDirPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ConfigDirPropertyPlaceholderConfigurer();
		return propertyPlaceholderConfigurer;
	}

}
