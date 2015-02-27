package nl.ipo.cds.deegree;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.deegree.sqldialect.SQLDialect;
import org.deegree.sqldialect.postgis.PostGISDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

@Configuration
public class DataSourceConfig {
	/**
	 * <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close"> <property
	 * name="driverClassName" value="${jdbc.driverClassName}" /> <property name="url" value="${jdbc.dburl}" /> <property
	 * name="username" value="${jdbc.username}" /> <property name="password" value="${jdbc.password}" /> <property
	 * name="defaultAutoCommit" value="false" /> <property name="maxWait" value="3000" /> <property name="maxIdle"
	 * value="-1" /> <property name="maxActive" value="-1" /> </bean>
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * 
	 * @return
	 */
	@Bean
	public DataSource dataSource(@Value("${jdbc.driverClassName}") String driverClassName,
			@Value("${jdbc.dburl}") String url, @Value("${jdbc.username}") String username,
			@Value("${jdbc.password}") String password) {
		final BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driverClassName);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setDefaultAutoCommit(false);
		ds.setMaxWait(3000);
		ds.setMaxIdle(-1);
		ds.setMaxActive(-1);
		ds.setUrl(url);

		return ds;
	}

	/**
	 * <!-- LDAP datasource: --> <s:ldap-server id="ldapServer" url="${ldap.ldapurl}" manager-dn="${ldap.managerdn}"
	 * manager-password="${ldap.managerpw}" />
	 * 
	 * @return
	 * @throws Exception
	 */
	@Bean
	public BaseLdapPathContextSource ldapServer(@Value("${ldap.ldapurl}") String ldapUrl,
			@Value("${ldap.managerdn}") String managerDN, @Value("${ldap.managerpw}") String managerPassword)
			throws Exception {
		DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(ldapUrl);

		contextSource.setUserDn(managerDN);
		contextSource.setPassword(managerPassword);
		contextSource.setReferral("ignore");
		contextSource.afterPropertiesSet();
		return contextSource;
	}

	@Bean
	public SQLDialect sqlDialect() {
		return new PostGISDialect("1.5");
	}

}
