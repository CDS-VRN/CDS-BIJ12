package nl.ipo.cds.etl.theme.vrn.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.etl.theme.vrn.AbstractVrnValidator;
import nl.ipo.cds.etl.theme.vrn.VrnThemeConfig;
import nl.ipo.cds.validation.execute.CompilerException;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration (value = "vrn.DatasetConfig")
public class DatasetConfig {


	@Bean
	@Inject
	public VrnThemeConfig VrnThemeConfig (
			final AbstractVrnValidator validator,
			final OperationDiscoverer operationDiscoverer) {

		/**
		 * TODO: maken we twee contructors van de VrnThemeConfig
		 *  met verschillende validators, een voor Landelijke datasets, en een voor provinciale datasets???
		 */
		return null;// new VrnThemeConfig (validator, operationDiscoverer);
	}

	@Configuration (value = "vrn.Validators")
	public static class Validators {
		@Bean
		@Inject
		public AbstractVrnValidator VrnValidator (
				final @Named ("VrnValidationMessages") Properties validatorMessages) throws CompilerException {
			/**
			 * TODO: maken we twee validators, een voor Landelijke datasets, en een voor provinciale datasets???
			 */
			return null; //new AbstractVrnValidator (validatorMessages);
		}
	}

	@Configuration (value = "vrn.Messages")
	public static class Messages {
		@Bean
		public PropertiesFactoryBean VrnValidationMessages () {
			final PropertiesFactoryBean properties = new PropertiesFactoryBean ();
			properties.setLocation (new ClassPathResource ("nl/ipo/cds/etl/theme/vrn/validator.messages"));
			return properties;
		}
	}
	
}
