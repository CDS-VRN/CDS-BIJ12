package nl.ipo.cds.etl.theme.buisleidingen.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.etl.theme.buisleidingen.DataSourceTransportroutes;
import nl.ipo.cds.etl.theme.buisleidingen.TransportrouteRisicoThemeConfig;
import nl.ipo.cds.etl.theme.buisleidingen.TransportrouteRisicoValidator;
import nl.ipo.cds.etl.theme.buisleidingen.TransportroutedeelThemeConfig;
import nl.ipo.cds.etl.theme.buisleidingen.TransportroutedeelValidator;
import nl.ipo.cds.etl.theme.buisleidingen.Transportroutes;
import nl.ipo.cds.executor.ConfigDir;
import nl.ipo.cds.validation.execute.CompilerException;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration (value = "buisleidingen.DatasetConfig")
public class DatasetConfig {

	@Bean
	@Inject
	public TransportroutedeelThemeConfig transportroutedeelThemeConfig (
			final ConfigDir configDir,
			final TransportroutedeelValidator validator,
			final OperationDiscoverer operationDiscoverer) {
		
		final TransportroutedeelThemeConfig config = new TransportroutedeelThemeConfig (validator, operationDiscoverer);
		
		return config;
	}
	
	@Bean
	@Inject
	public TransportrouteRisicoThemeConfig transportrouteRisicoThemeConfig (
			final ConfigDir configDir,
			final TransportrouteRisicoValidator validator,
			final OperationDiscoverer operationDiscoverer) {
		
		final TransportrouteRisicoThemeConfig config = new TransportrouteRisicoThemeConfig (validator, operationDiscoverer);
		
		return config;
	}

	@Configuration (value = "buisleidingen.Validators")
	public static class Validators {
		@Bean
		@Inject
		public TransportroutedeelValidator transportroutedeelValidator (
				final @Named("buisleidingenValidationMessages") Properties validatorMessages) 
						throws CompilerException {
			
			return new TransportroutedeelValidator (validatorMessages);
		}
		
		@Bean
		@Inject
		public TransportrouteRisicoValidator transportrouterisicoValidator (
				final @Named("buisleidingenValidationMessages") Properties validatorMessages,
				final Transportroutes transportroutes) 
						throws CompilerException {
			
			return new TransportrouteRisicoValidator (validatorMessages, transportroutes);
		}
	}
	
	@Configuration (value = "buisleidingen.Related")
	public static class Related {
		@Bean
		@Inject
		public Transportroutes transportroutes (final DataSource dataSource) {
			return new DataSourceTransportroutes (dataSource);
		}
	}
	
	@Configuration (value = "buisleidingen.Messages")
	public static class Messages {
		@Bean
		public PropertiesFactoryBean buisleidingenValidationMessages () {
			final PropertiesFactoryBean properties = new PropertiesFactoryBean ();
			
			properties.setLocation (new ClassPathResource ("nl/ipo/cds/etl/theme/buisleidingen/validator.messages"));

			return properties;
		}
	}
}
