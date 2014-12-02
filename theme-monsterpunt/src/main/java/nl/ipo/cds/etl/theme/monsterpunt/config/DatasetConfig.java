package nl.ipo.cds.etl.theme.monsterpunt.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.etl.theme.monsterpunt.MonsterpuntThemeConfig;
import nl.ipo.cds.etl.theme.monsterpunt.MonsterpuntValidator;
import nl.ipo.cds.validation.execute.CompilerException;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration (value = "monsterpunt.DatasetConfig")
public class DatasetConfig {

	@Bean
	@Inject
	public MonsterpuntThemeConfig monsterpuntThemeConfig (
			final MonsterpuntValidator validator,
			final OperationDiscoverer operationDiscoverer) {

		return new MonsterpuntThemeConfig (validator, operationDiscoverer);
	}

	@Configuration (value = "monsterpunt.Validators")
	public static class Validators {
		@Bean
		@Inject
		public MonsterpuntValidator monsterpuntValidator (
				final @Named ("monsterpuntValidationMessages") Properties validatorMessages) throws CompilerException {
			return new MonsterpuntValidator (validatorMessages);
		}
	}

	@Configuration (value = "monsterpunt.Messages")
	public static class Messages {
		@Bean
		public PropertiesFactoryBean monsterpuntValidationMessages () {
			final PropertiesFactoryBean properties = new PropertiesFactoryBean ();
			properties.setLocation (new ClassPathResource ("nl/ipo/cds/etl/theme/monsterpunt/validator.messages"));
			return properties;
		}
	}
}
