/**
 * 
 */
package nl.ipo.cds.deegree.security;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.dao.ManagerDaoAuthenticationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;

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
        "classpath:META-INF/nl/ipo/cds/deegree/extension/vrnfilter/applicationContext.xml",
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
		// auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
		auth.authenticationProvider(authenticationProvider);
	}

	/**
	 * Use Basic authentication. No forms.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().hasAuthority("ROLE_RAADPLEGER");
		http.httpBasic();
		http.logout();
		// do not create session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
}
