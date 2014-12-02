package nl.ipo.cds.etl.theme.bvb;

import static java.util.Collections.emptyList;
import static nl.ipo.cds.etl.process.HarvesterMessageKey.METADATA_FEATURETYPE_INVALID;
import static org.apache.xerces.xs.XSConstants.ELEMENT_DECLARATION;
import static org.apache.xerces.xs.XSModelGroup.COMPOSITOR_SEQUENCE;
import static org.apache.xerces.xs.XSTypeDefinition.COMPLEX_TYPE;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import nl.ipo.cds.etl.process.DatasetMetadata;
import nl.ipo.cds.etl.process.HarvesterException;
import nl.ipo.cds.etl.theme.schema.SchemaHarvester;

import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSModel;
import org.apache.xerces.xs.XSModelGroup;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTerm;
import org.apache.xerces.xs.XSTypeDefinition;
import org.deegree.commons.tom.gml.property.PropertyType;
import org.deegree.commons.tom.primitive.PrimitiveType;
import org.deegree.commons.xml.schema.XMLSchemaInfoSet;
import org.deegree.feature.types.AppSchema;
import org.deegree.feature.types.FeatureType;
import org.deegree.feature.types.GenericAppSchema;
import org.deegree.feature.types.GenericFeatureType;
import org.deegree.feature.types.property.SimplePropertyType;

/**
 * {@link SchemaHarvester} for BVB-style XML schema documents.  
 * <p>
 * Requirements for BVB-schemas:
 * <ul>
 * <li>Valid XML schema document</li>
 * <li>A global element declaration with local name "BVB" (the container for the features elements)</li>
 * <li>BVB's complex type definition is based on a sequence</li>
 * <li>BVB's sequence allows for exactly one type of child elements (the feature elements)</li>
 * <li>The feature element's complex type definition is based on a sequence</li>
 * <li>The feature element's sequence only defines simple-valued properties</li>
 * </p>
 * 
 * @author <a href="mailto:schneider@occamlabs.de">Markus Schneider </a>
 */
public class BvbAppSchemaReader implements SchemaHarvester {

	public AppSchema parseApplicationSchema (final DatasetMetadata metadata) throws HarvesterException {
		XMLSchemaInfoSet infoSet;
		try {
			infoSet = loadInfoSet(metadata.getSchemaUrl());
			return createAppSchema(infoSet);
		} catch (IOException e) {
			throw new HarvesterException (e, METADATA_FEATURETYPE_INVALID, metadata.getSchemaUrl (), metadata.getFeatureTypeName(), e.getMessage());
		}
	}
	
	public AppSchema read(URL url) throws IOException {
		XMLSchemaInfoSet infoSet = loadInfoSet(url.toString());
		return createAppSchema(infoSet);		
	}

	public AppSchema read(String schemaUrl) throws IOException {
		XMLSchemaInfoSet infoSet = loadInfoSet(schemaUrl);
		return createAppSchema(infoSet);
	}

	AppSchema createAppSchema(XMLSchemaInfoSet infoSet) {
		XSElementDeclaration bvbElDecl = findBvbElement(infoSet);
		if (bvbElDecl == null) {
			String msg = "Invalid BVB schema. Cannot find a global element declaration for 'BVB'.";
			throw new IllegalArgumentException(msg);
		}
		List<XSParticle> childElements = getChildElements(bvbElDecl);
		if (childElements.size() != 1) {
			String msg = "Invalid BVB schema. Element 'BVB' must allow exactly one type of child elements.";
			throw new IllegalArgumentException(msg);
		}
		XSElementDeclaration ftElDecl = (XSElementDeclaration) childElements.get(0).getTerm();
		String prefix = infoSet.getNamespacePrefixes().get(ftElDecl.getNamespace());
		QName ftName = new QName(ftElDecl.getNamespace(), ftElDecl.getName());
		if (prefix != null) {
			ftName = new QName(ftElDecl.getNamespace(), ftElDecl.getName(), prefix);
		}
		FeatureType ft = createFeatureType(ftName, (XSComplexTypeDefinition) ftElDecl.getTypeDefinition());
		FeatureType[] fts = new FeatureType[] { ft };
		Map<String, String> prefixToNs = infoSet.getNamespacePrefixes();
		return new GenericAppSchema(fts, null, prefixToNs, null, null, null);
	}

	FeatureType createFeatureType(QName ftName, XSComplexTypeDefinition typeDef) {
		List<XSParticle> propertyParticleDecls = getChildElements(typeDef);
		List<PropertyType> propDecls = new ArrayList<PropertyType>(propertyParticleDecls.size());
		for (XSParticle propertyParticleDecl : propertyParticleDecls) {
			propDecls.add(createPropertyType(propertyParticleDecl));
		}
		return new GenericFeatureType(ftName, propDecls, false);
	}

	PropertyType createPropertyType(XSParticle propertyParticleDecl) {
		XSElementDeclaration propertyElDecl = (XSElementDeclaration) propertyParticleDecl.getTerm();
		XSTypeDefinition typeDef = propertyElDecl.getTypeDefinition();
		if (typeDef.getTypeCategory() == COMPLEX_TYPE) {
			throw new IllegalArgumentException("Invalid BVB schema. Only simple properties are allowed, but element "
					+ propertyElDecl.getName() + " is complex.");
		}
		PrimitiveType pt = new PrimitiveType((XSSimpleTypeDefinition) typeDef);
		QName name = new QName(propertyElDecl.getNamespace(), propertyElDecl.getName());
		int minOccurs = propertyParticleDecl.getMinOccurs();
		int maxOccurs = propertyParticleDecl.getMaxOccursUnbounded() ? -1 : propertyParticleDecl.getMaxOccurs();
		return new SimplePropertyType(name, minOccurs, maxOccurs, pt.getBaseType(), propertyElDecl, null);
	}

	XSElementDeclaration findBvbElement(XMLSchemaInfoSet infoSet) {
		XSNamedMap elementDecls = infoSet.getXSModel().getComponents(ELEMENT_DECLARATION);
		for (Object o : elementDecls.values()) {
			XSElementDeclaration elDecl = (XSElementDeclaration) o;
			if (elDecl.getName().equalsIgnoreCase("BVB")) {
				return elDecl;
			}
		}
		return null;
	}

	List<XSParticle> getChildElements(XSElementDeclaration elDecl) {
		XSTypeDefinition typeDef = elDecl.getTypeDefinition();
		if (typeDef.getTypeCategory() == COMPLEX_TYPE) {
			return getChildElements((XSComplexTypeDefinition) typeDef);
		}
		return emptyList();
	}

	private List<XSParticle> getChildElements(XSComplexTypeDefinition typeDef) {
		XSParticle particle = typeDef.getParticle();
		if (particle == null) {
			throw new IllegalArgumentException("Invalid BVB schema. Expecting element-only complex types.");
		}
		XSTerm term = particle.getTerm();
		if (!(term instanceof XSModelGroup) || ((XSModelGroup) term).getCompositor() != COMPOSITOR_SEQUENCE) {
			throw new IllegalArgumentException("Invalid BVB schema. Expecting sequence-based complex types.");
		}
		return getChildElements((XSModelGroup) term);
	}

	private List<XSParticle> getChildElements(XSModelGroup modelGroup) {
		XSObjectList particles = modelGroup.getParticles();
		List<XSParticle> elDecls = new ArrayList<XSParticle>();
		for (int i = 0; i < particles.getLength(); i++) {
			XSParticle particle = (XSParticle) particles.get(i);
			XSTerm term = particle.getTerm();
			if (!(term instanceof XSElementDeclaration)) {
				throw new IllegalArgumentException("Invalid BVB schema. Expecting sequence-based complex types.");
			}
			elDecls.add(particle);
		}
		return elDecls;
	}

	XMLSchemaInfoSet loadInfoSet(String schemaUrl) throws IOException {
		XSModel model;
		try {
			model = XMLSchemaInfoSet.loadModel(schemaUrl);
		} catch (Exception e) {
			throw new IOException("Unable to load BVB schema from URL '" + schemaUrl + "': " + e.getMessage());
		}
		return new XMLSchemaInfoSet(model);
	}

}
