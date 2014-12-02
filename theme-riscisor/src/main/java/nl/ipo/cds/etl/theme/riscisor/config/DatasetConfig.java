package nl.ipo.cds.etl.theme.riscisor.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.etl.theme.riscisor.KwetsbaarObjectThemeConfig;
import nl.ipo.cds.etl.theme.riscisor.KwetsbaarObjectValidator;
import nl.ipo.cds.validation.execute.CompilerException;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration (value = "riscisor.DatasetConfig")
public class DatasetConfig {

	@Bean
	@Inject
	public KwetsbaarObjectThemeConfig kwetsbaarObjectThemeConfig (
			final KwetsbaarObjectValidator validator,
			final OperationDiscoverer operationDiscoverer) {
		
		return new KwetsbaarObjectThemeConfig (validator, operationDiscoverer);
	}
	
	@Configuration (value = "riscisor.Validators")
	public static class Validators {
		@Bean
		@Inject
		public KwetsbaarObjectValidator kwetsbaarObjectValidator (
				final @Named ("riscisorValidationMessages") Properties validatorMessages) throws CompilerException {
			return new KwetsbaarObjectValidator (validatorMessages);
		}
	}
	
	@Configuration (value = "riscisor.Messages")
	public static class Messages {
		@Bean
		public PropertiesFactoryBean riscisorValidationMessages () {
			final PropertiesFactoryBean properties = new PropertiesFactoryBean ();
			
			properties.setLocation (new ClassPathResource ("nl/ipo/cds/etl/theme/riscisor/validator.messages"));

			return properties;
		}
	}
}
