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
@ImportResource(value = {
		"classpath:nl/ipo/cds/dao/initDB.xml",
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

	/**
	 * <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close"> <property
	 * name="driverClassName" value="${jdbc.driverClassName}" /> <property name="url" value="${jdbc.dburl}" /> <property
	 * name="username" value="${jdbc.username}" /> <property name="password" value="${jdbc.password}" /> <property
	 * name="defaultAutoCommit" value="false" /> <property name="maxWait" value="3000" /> <property name="maxIdle"
	 * value="-1" /> <property name="maxActive" value="-1" /> </bean>
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource(@Value("${jdbc.driverClassName}") String driverClassName) {
		final BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driverClassName);
		return ds;
	}

	@Value("${ldap.ldapurl}")
	private String ldapUrl;

	@Value("${ldap.managerdn}")
	private String managerDN;

	@Value("${ldap.managerpw}")
	private String managerPassword;

	/**
	 * <!-- LDAP datasource: --> <s:ldap-server id="ldapServer" url="${ldap.ldapurl}" manager-dn="${ldap.managerdn}"
	 * manager-password="${ldap.managerpw}" />
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public BaseLdapPathContextSource ldapServer() throws Exception {
		DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(ldapUrl);

		contextSource.setUserDn(managerDN);
		contextSource.setPassword(managerPassword);
		contextSource.setReferral("ignore");
		contextSource.afterPropertiesSet();
		return contextSource;
	}

	@Bean
	public LdapTemplate ldapTemplate() throws Exception {
		return new LdapTemplate(ldapServer());
	}

//	/**
//	 * <bean id="propertyPlaceholderConfigurer" class="nl.ipo.cds.properties.ConfigDirPropertyPlaceholderConfigurer"/>
//	 * 
//	 * @return
//	 */
//	@Bean
//	public static ConfigDirPropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
//		final ConfigDirPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ConfigDirPropertyPlaceholderConfigurer();
////		propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
//		return propertyPlaceholderConfigurer;
//	}

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
		http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}
}
