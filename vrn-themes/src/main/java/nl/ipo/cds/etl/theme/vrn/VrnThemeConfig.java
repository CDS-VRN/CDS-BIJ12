package nl.ipo.cds.etl.theme.vrn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.ipo.cds.admin.ba.controller.beans.mapping.Mapping;
import nl.ipo.cds.admin.ba.controller.beans.mapping.Mappings;
import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.DatasetHandlers;
import nl.ipo.cds.etl.DefaultDatasetHandlers;
import nl.ipo.cds.etl.Validator;
import nl.ipo.cds.etl.theme.AttributeDescriptor;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.etl.theme.ThemeConfigException;
import nl.ipo.cds.etl.theme.vrn.domain.AbstractGebied;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

public class VrnThemeConfig<T extends AbstractGebied> extends ThemeConfig<T>{

	private final Validator<T> validator;

	private final OperationDiscoverer operationDiscoverer;
	
	private final Mappings mappings;

	private final boolean taggable;
	
	/**
	 * List of strings that are srsName in gml and should be transformed to EPSG:28992 RD_new 
	 */
	private static List<String> rdReplacements = new ArrayList<>();

	
	
	public VrnThemeConfig(final Validator<T> validator, final OperationDiscoverer operationDiscoverer,
			final Class<T> clazz, final String mappingsClassPathResource, final boolean taggable) throws ThemeConfigException {
		super(clazz.getSimpleName(), clazz);
		this.validator = validator;
		this.taggable = taggable;
		this.operationDiscoverer = operationDiscoverer;
		// unmarshall default mappings
		final ClassPathResource resource = new ClassPathResource(mappingsClassPathResource);
		if (resource.exists()) {
			try {
				mappings = new ObjectMapper().readValue(resource.getInputStream(), Mappings.class);
			} catch (IOException e) {
				throw new ThemeConfigException("Failed to read default mappings for theme: " + clazz.getSimpleName(), e);
			}
		} else {
			mappings = null;
		}
	}
	

	@Override
	public DatasetHandlers<T> createDatasetHandlers(EtlJob job, ManagerDao managerDao) {
		return new DefaultDatasetHandlers<> (operationDiscoverer, this, managerDao);
	}

	@Override
	public Validator<T> getValidator() throws ThemeConfigException {
		return validator;
	}

	 

	@Override
	public Mapping getDefaultMappingForAttributeType(
			AttributeDescriptor<?> attributeDescriptor) {
		if (mappings == null) {
			return null;
		}
		for (Mapping mapping:mappings.getMappings()){
			if (mapping.getAttributeName().equals(attributeDescriptor.getName())){
				return mapping;
			}
		}
		return null;
	}

	@Override
	public boolean isTaggable() {
		return taggable;
	}


	public static List<String> getRdReplacements() {
		return rdReplacements;
	}

	@Value("#{'${vrn.rd_replacement_crs_list:_Netherlands-RD-New_0}'.split(',')}")
	public void setRdReplacements(List<String> rdReplacements) {
		VrnThemeConfig.rdReplacements = rdReplacements;
	}
	
}
