package nl.ipo.cds.etl.theme.bvb.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.etl.theme.bvb.BvbThemeConfig;
import nl.ipo.cds.etl.theme.bvb.BvbValidator;
import nl.ipo.cds.validation.execute.CompilerException;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration (value = "bvb.DatasetConfig")
public class DatasetConfig {

	@Bean
	@Inject
	public BvbThemeConfig bvbThemeConfig (
			final BvbValidator validator,
			final OperationDiscoverer operationDiscoverer) {
		
		return new BvbThemeConfig (validator, operationDiscoverer);
	}
	
	@Configuration (value = "bvb.Validators")
	public static class Validators {
		@Bean
		@Inject
		public BvbValidator bvbValidator (
				final @Named ("bvbValidationMessages") Properties validatorMessages) throws CompilerException {
			return new BvbValidator (validatorMessages);
		}
	}
	
	@Configuration (value = "bvb.Messages")
	public static class Messages {
		@Bean
		public PropertiesFactoryBean bvbValidationMessages () {
			final PropertiesFactoryBean properties = new PropertiesFactoryBean ();
			properties.setLocation (new ClassPathResource ("nl/ipo/cds/etl/theme/bvb/validator.messages"));
			return properties;
		}
	}
}
