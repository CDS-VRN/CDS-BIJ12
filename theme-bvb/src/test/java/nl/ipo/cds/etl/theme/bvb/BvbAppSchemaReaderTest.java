package nl.ipo.cds.etl.theme.bvb;

import static org.deegree.commons.tom.primitive.BaseType.INTEGER;
import static org.deegree.commons.tom.primitive.BaseType.STRING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSElementDeclaration;
import org.apache.xerces.xs.XSParticle;
import org.deegree.commons.xml.schema.XMLSchemaInfoSet;
import org.deegree.feature.types.AppSchema;
import org.deegree.feature.types.FeatureType;
import org.deegree.feature.types.property.SimplePropertyType;
import org.junit.Before;
import org.junit.Test;

public class BvbAppSchemaReaderTest {

	private final BvbAppSchemaReader reader = new BvbAppSchemaReader();

	private XMLSchemaInfoSet infoSet;

	@Before
	public void setup() throws IOException {
		infoSet = reader.loadInfoSet(BvbAppSchemaReaderTest.class.getResource("bvb-example.xsd").toString());
	}

	@Test
	public void testFindBvbElement() throws IOException {
		XSElementDeclaration bvbElDecl = reader.findBvbElement(infoSet);
		assertNotNull(bvbElDecl);
		assertEquals("BVB", bvbElDecl.getName());
	}

	@Test
	public void testFindChildElementsOfBvbElement() throws IOException {
		XSElementDeclaration bvbElDecl = reader.findBvbElement(infoSet);
		List<XSParticle> childElements = reader.getChildElements(bvbElDecl);
		assertEquals(1, childElements.size());
		assertEquals("Stalgroep", childElements.get(0).getTerm().getName());
	}

	@Test
	public void testFindChildElementsOfStalgroepElement() throws IOException {
		XSParticle stalgroepEl = reader.getChildElements(reader.findBvbElement(infoSet)).get(0);
		List<XSParticle> childElements = reader.getChildElements((XSElementDeclaration) stalgroepEl.getTerm());
		assertEquals(19, childElements.size());
	}

	@Test
	public void testCreateFeatureTypeFromStalgroepElement() throws IOException {
		XSElementDeclaration stalgroepEl = (XSElementDeclaration) reader
				.getChildElements(reader.findBvbElement(infoSet)).get(0).getTerm();
		QName ftName = new QName(stalgroepEl.getNamespace(), stalgroepEl.getName());
		XSComplexTypeDefinition typeDef = (XSComplexTypeDefinition) stalgroepEl.getTypeDefinition();
		FeatureType ft = reader.createFeatureType(ftName, typeDef);
		assertEquals("Stalgroep", ft.getName().getLocalPart());
		assertEquals(19, ft.getPropertyDeclarations().size());
		SimplePropertyType gemeentePt = (SimplePropertyType) ft.getPropertyDeclarations().get(0);
		assertEquals("Gemeente", gemeentePt.getName().getLocalPart());
		assertEquals(1, gemeentePt.getMinOccurs());
		assertEquals(1, gemeentePt.getMaxOccurs());
		assertEquals(INTEGER, gemeentePt.getPrimitiveType().getBaseType());
		SimplePropertyType gebouwnummerPt = (SimplePropertyType) ft.getPropertyDeclarations().get(3);
		assertEquals("Gebouwnummer", gebouwnummerPt.getName().getLocalPart());
		assertEquals(0, gebouwnummerPt.getMinOccurs());
		assertEquals(1, gebouwnummerPt.getMaxOccurs());
		assertEquals(STRING, gebouwnummerPt.getPrimitiveType().getBaseType());
	}

	@Test
	public void testCreateAppSchema() throws IOException {
		AppSchema appSchema = reader.createAppSchema(infoSet);
		assertEquals(1, appSchema.getFeatureTypes().length);
		assertEquals("Stalgroep", appSchema.getFeatureTypes()[0].getName().getLocalPart());
		assertEquals("http://www.web-bvb.nl/", appSchema.getFeatureTypes()[0].getName().getNamespaceURI());
		assertEquals("ns1", appSchema.getFeatureTypes()[0].getName().getPrefix());
	}

}
