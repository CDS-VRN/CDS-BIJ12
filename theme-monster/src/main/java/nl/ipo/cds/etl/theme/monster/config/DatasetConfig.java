package nl.ipo.cds.etl.theme.monster.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.etl.theme.monster.MonsterThemeConfig;
import nl.ipo.cds.etl.theme.monster.MonsterValidator;
import nl.ipo.cds.validation.execute.CompilerException;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration (value = "monster.DatasetConfig")
public class DatasetConfig {

	@Bean
	@Inject
	public MonsterThemeConfig monsterThemeConfig (
			final MonsterValidator validator,
			final OperationDiscoverer operationDiscoverer) {

		return new MonsterThemeConfig (validator, operationDiscoverer);
	}

	@Configuration (value = "monster.Validators")
	public static class Validators {
		@Bean
		@Inject
		public MonsterValidator monsterValidator (
				final @Named ("monsterValidationMessages") Properties validatorMessages) throws CompilerException {
			return new MonsterValidator (validatorMessages);
		}
	}

	@Configuration (value = "monster.Messages")
	public static class Messages {
		@Bean
		public PropertiesFactoryBean monsterValidationMessages () {
			final PropertiesFactoryBean properties = new PropertiesFactoryBean ();
			properties.setLocation (new ClassPathResource ("nl/ipo/cds/etl/theme/monster/validator.messages"));
			return properties;
		}
	}
}
