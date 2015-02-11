/**
 * 
 */
package nl.ipo.cds.deegree.security;

import javax.sql.DataSource;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.dao.ManagerDaoAuthenticationProvider;
import nl.ipo.cds.properties.ConfigDirPropertyPlaceholderConfigurer;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

/**
 * Configures security for VRN workspaces
 * 
 * @author reinoldp
 * 
 */
@Configuration
@EnableWebSecurity
@ImportResource(value = { "classpath:nl/ipo/cds/dao/initDB.xml",
// "classpath:nl/ipo/cds/admin/admin-applicationContext.xml",
// "classpath:nl/ipo/cds/admin/admin-securityContext.xml",
// "classpath:nl/ipo/cds/dao/dataSource-applicationContext.xml",
		"classpath:nl/ipo/cds/dao/dao-applicationContext.xml",
// "classpath:nl/ipo/cds/dao/metadata/dao-applicationContext.xml",
// "classpath:nl/ipo/cds/nagios/nagios-status-client.xml",
// "classpath:nl/ipo/cds/etl/reporting/geom/geometry-applicationContext.xml"
})
public class DeegreeVrnWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationProvider authenticationProvider;

	

	@Bean
	public ManagerDaoAuthenticationProvider createAuthenticationProvider(ManagerDao managerDao) {
		return new ManagerDaoAuthenticationProvider(managerDao);
	};

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
//		FIXME auth.authenticationProvider(authenticationProvider);
	}

	/**
	 * Use Basic authentication. No forms.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}
}
