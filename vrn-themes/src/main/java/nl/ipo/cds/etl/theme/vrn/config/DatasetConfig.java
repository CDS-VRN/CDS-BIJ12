package nl.ipo.cds.etl.theme.vrn.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.etl.theme.vrn.VrnThemeConfig;
import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedBeheer;
import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedInrichting;
import nl.ipo.cds.etl.theme.vrn.domain.LandelijkGebiedVerwerving;
import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedBeheer;
import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedInrichting;
import nl.ipo.cds.etl.theme.vrn.domain.ProvinciaalGebiedVerwerving;
import nl.ipo.cds.etl.theme.vrn.validation.LandelijkGebiedBeheerValidator;
import nl.ipo.cds.etl.theme.vrn.validation.LandelijkGebiedInrichtingValidator;
import nl.ipo.cds.etl.theme.vrn.validation.LandelijkGebiedVerwervingValidator;
import nl.ipo.cds.etl.theme.vrn.validation.ProvinciaalGebiedBeheerValidator;
import nl.ipo.cds.etl.theme.vrn.validation.ProvinciaalGebiedInrichtingValidator;
import nl.ipo.cds.etl.theme.vrn.validation.ProvinciaalGebiedVerwervingValidator;
import nl.ipo.cds.validation.execute.CompilerException;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration(value = "vrn.DatasetConfig")
public class DatasetConfig {

	@Bean
	@Inject
	public VrnThemeConfig<LandelijkGebiedBeheer> VrnThemeConfig(final LandelijkGebiedBeheerValidator validator,
	final OperationDiscoverer operationDiscoverer) {
		return new VrnThemeConfig<LandelijkGebiedBeheer>(validator, operationDiscoverer, LandelijkGebiedBeheer.class);
	}

	@Bean
	@Inject
	public VrnThemeConfig<ProvinciaalGebiedBeheer> VrnThemeConfig(final ProvinciaalGebiedBeheerValidator validator, final OperationDiscoverer operationDiscoverer) {
		return new VrnThemeConfig<ProvinciaalGebiedBeheer>(validator, operationDiscoverer, ProvinciaalGebiedBeheer.class);
	}

	@Bean
	@Inject
	public VrnThemeConfig<ProvinciaalGebiedInrichting> VrnThemeConfig(final ProvinciaalGebiedInrichtingValidator validator, final OperationDiscoverer operationDiscoverer) {
		return new VrnThemeConfig<ProvinciaalGebiedInrichting>(validator, operationDiscoverer, ProvinciaalGebiedInrichting.class);
	}

	@Bean
	@Inject
	public VrnThemeConfig<LandelijkGebiedInrichting> VrnThemeConfig(final LandelijkGebiedInrichtingValidator validator, final OperationDiscoverer operationDiscoverer) {
		return new VrnThemeConfig<LandelijkGebiedInrichting>(validator, operationDiscoverer, LandelijkGebiedInrichting.class);
	}

	@Bean
	@Inject
	public VrnThemeConfig<LandelijkGebiedVerwerving> VrnThemeConfig(final LandelijkGebiedVerwervingValidator validator, final OperationDiscoverer operationDiscoverer) {
		return new VrnThemeConfig<LandelijkGebiedVerwerving>(validator, operationDiscoverer, LandelijkGebiedVerwerving.class);
	}

	@Bean
	@Inject
	public VrnThemeConfig<ProvinciaalGebiedVerwerving> VrnThemeConfig(final ProvinciaalGebiedVerwervingValidator validator, final OperationDiscoverer operationDiscoverer) {
		return new VrnThemeConfig<ProvinciaalGebiedVerwerving>(validator, operationDiscoverer, ProvinciaalGebiedVerwerving.class);
	}

	@Configuration(value = "vrn.Validators")
	public static class Validators {
		@Bean
		@Inject
		public ProvinciaalGebiedBeheerValidator provinciaalGebiedBeheerValidator(final @Named("VrnValidationMessages") Properties validatorMessages)
		throws CompilerException {
			return new ProvinciaalGebiedBeheerValidator(validatorMessages);
		}

		@Bean
		@Inject
		public LandelijkGebiedBeheerValidator landelijkGebiedBeheerValidator(final @Named("VrnValidationMessages") Properties validatorMessages)
		throws CompilerException {
			return new LandelijkGebiedBeheerValidator(validatorMessages);
		}

	}

	@Configuration(value = "vrn.Messages")
	public static class Messages {
		@Bean
		public PropertiesFactoryBean VrnValidationMessages() {
			final PropertiesFactoryBean properties = new PropertiesFactoryBean();
			properties.setLocation(new ClassPathResource("nl/ipo/cds/etl/theme/vrn/validator.messages"));
			return properties;
		}
	}

}
