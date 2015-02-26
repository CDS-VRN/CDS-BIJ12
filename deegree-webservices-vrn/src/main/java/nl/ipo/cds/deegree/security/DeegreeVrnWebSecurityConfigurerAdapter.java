/**
 * 
 */
package nl.ipo.cds.deegree.security;

import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.dao.ManagerDaoAuthenticationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Configures security for VRN workspaces
 * 
 * @author reinoldp
 * 
 */
@Configuration
@EnableWebSecurity
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
		http.authorizeRequests()
			// no access restrictions on 'vastgesteld' services
			.antMatchers("/services/wfs-vastgesteld").permitAll()
			.antMatchers("/services/wms-vastgesteld").permitAll()
			.antMatchers("/services/wfs-vastgesteld/*").permitAll()
			.antMatchers("/services/wms-vastgesteld/*").permitAll()
			.anyRequest().hasAuthority("ROLE_RAADPLEGER");
			
		
		http.httpBasic();
		http.logout();
		// do not create session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
}
