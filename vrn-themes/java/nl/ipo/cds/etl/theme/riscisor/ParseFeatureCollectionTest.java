package nl.ipo.cds.etl.theme.riscisor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import nl.ipo.cds.domain.FeatureType;
import nl.ipo.cds.etl.GenericFeature;
import nl.ipo.cds.etl.featurecollection.FeatureCollection;
import nl.ipo.cds.etl.featurecollection.WFSResponse;
import nl.ipo.cds.etl.featurecollection.WFSResponseReader;
import nl.ipo.cds.etl.featuretype.GMLFeatureTypeParser;

import org.deegree.commons.tom.ows.CodeType;
import org.deegree.feature.types.AppSchema;
import org.junit.Before;
import org.junit.Test;

public class ParseFeatureCollectionTest {

	private InputStream featureCollectionStream;
	private InputStream applicationSchemaStream;
	private AppSchema appSchema;
	private FeatureType featureType;
	
	@Before
	public void openStreams () throws Exception {
		// Open streams:
		featureCollectionStream = getClass ().getClassLoader ().getResourceAsStream ("nl/ipo/cds/etl/theme/riscisor/featurecollection.xml");
		applicationSchemaStream = getClass ().getClassLoader ().getResourceAsStream ("nl/ipo/cds/etl/theme/riscisor/RISC-ISOR-Aanleverformat-v0.4.xsd");
		
		assertNotNull (featureCollectionStream);
		assertNotNull (applicationSchemaStream);
		
		// Parse schema:
		final GMLFeatureTypeParser parser = new GMLFeatureTypeParser ();
		appSchema = parser.parseApplicationSchema (applicationSchemaStream, "UTF-8");
		featureType = parser.parseSchema (appSchema, "KwetsbaarObject");
		
		assertNotNull (appSchema);
		assertNotNull (featureType);
	}
	
	@Test
	public void test () throws Exception {
		final XMLStreamReader streamReader = XMLInputFactory.newFactory().createXMLStreamReader (featureCollectionStream);
		final WFSResponseReader wfsReader = new WFSResponseReader ();
		final WFSResponse response = wfsReader.parseWFSResponse (streamReader, appSchema, "KwetsbaarObject");
		final FeatureCollection featureCollection = response.getFeatureCollection ();
		
		for (final GenericFeature feature: featureCollection) {
			assertNotNull (feature);
			
			final Object obj = feature.get ("instellingcode");
			
			assertTrue (obj instanceof CodeType);
			assertEquals ("http://www.risicokaart.nl/codes/instellingen", ((CodeType)obj).getCodeSpace ());
		}
	}
}
