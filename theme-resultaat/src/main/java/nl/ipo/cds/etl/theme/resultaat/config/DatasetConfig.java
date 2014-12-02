package nl.ipo.cds.etl.theme.resultaat.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.etl.theme.resultaat.ResultaatThemeConfig;
import nl.ipo.cds.etl.theme.resultaat.ResultaatValidator;
import nl.ipo.cds.validation.execute.CompilerException;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration (value = "resultaat.DatasetConfig")
public class DatasetConfig {

	@Bean
	@Inject
	public ResultaatThemeConfig resultaatThemeConfig (
			final ResultaatValidator validator,
			final OperationDiscoverer operationDiscoverer) {

		return new ResultaatThemeConfig (validator, operationDiscoverer);
	}

	@Configuration (value = "resultaat.Validators")
	public static class Validators {
		@Bean
		@Inject
		public ResultaatValidator resultaatValidator (
				final @Named ("resultaatValidationMessages") Properties validatorMessages) throws CompilerException {
			return new ResultaatValidator (validatorMessages);
		}
	}

	@Configuration (value = "resultaat.Messages")
	public static class Messages {
		@Bean
		public PropertiesFactoryBean resultaatValidationMessages () {
			final PropertiesFactoryBean properties = new PropertiesFactoryBean ();
			properties.setLocation (new ClassPathResource ("nl/ipo/cds/etl/theme/resultaat/validator.messages"));
			return properties;
		}
	}
}
