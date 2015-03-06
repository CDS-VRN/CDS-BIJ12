/**
 *
 */
package nl.ipo.cds.deegree;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.deegree.extension.vrnfilter.VRNFilterSQLFeatureStoreProvider;
import nl.ipo.cds.deegree.security.DeegreeVrnWebSecurityConfigurerAdapter;
import nl.ipo.cds.properties.ConfigDirPropertyPlaceholderConfigurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;

/**
 * @author reinoldp
 */
@Configuration
@Import({ DeegreeVrnWebSecurityConfigurerAdapter.class, DataSourceConfig.class })
@ImportResource(value = { "classpath:nl/ipo/cds/dao/dao-applicationContext.xml", "classpath:aspects.xml" })
@EnableAspectJAutoProxy
public class RootConfig {

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

	@Autowired
	private BaseLdapPathContextSource ldapServer;

	@Autowired
	public void setManagerDao(ManagerDao managerDao) {
		VRNFilterSQLFeatureStoreProvider.setManagerDao(managerDao);
	}

	@Bean
	public LdapTemplate ldapTemplate() throws Exception {
		return new LdapTemplate(ldapServer);
	}

}
