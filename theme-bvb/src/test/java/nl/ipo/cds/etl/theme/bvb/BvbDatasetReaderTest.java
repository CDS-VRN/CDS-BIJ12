package nl.ipo.cds.etl.theme.bvb;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.ipo.cds.etl.GenericFeature;
import nl.ipo.cds.etl.featurecollection.FeatureCollection;
import nl.ipo.cds.etl.featuretype.FeatureTypeNotFoundException;

import org.deegree.feature.types.AppSchema;
import org.junit.Before;
import org.junit.Test;

public class BvbDatasetReaderTest {

	private BvbDatasetReader reader;

	@Before
	public void setup() throws IOException {
		BvbAppSchemaReader reader = new BvbAppSchemaReader();
		AppSchema appSchema = reader.read(BvbDatasetReaderTest.class.getResource("bvb-example.xsd"));
		this.reader = new BvbDatasetReader(appSchema);
	}

	@Test
	public void parseFeatureCollection() throws XMLStreamException, FactoryConfigurationError, IOException,
			NoSuchElementException, FeatureTypeNotFoundException {
		URL datasetUrl = BvbDatasetReaderTest.class.getResource("bvb-example.xml");
		InputStream is = datasetUrl.openStream();
		XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(is);
		FeatureCollection fc = reader.parseCollection(xmlReader);
		Iterator<GenericFeature> iterator = fc.iterator();
		List<GenericFeature> features = new ArrayList<GenericFeature>();
		while (iterator.hasNext()) {
			features.add(iterator.next());
		}
		assertEquals (18, features.size());
		GenericFeature lastFeature = features.get(17);
		assertEquals (18, lastFeature.getValues().size());
		assertEquals (BigInteger.valueOf(1734), lastFeature.getValues().get("Gemeente"));
		assertEquals (BigInteger.valueOf(6), lastFeature.getValues().get("Gebouwhoogte"));
		assertEquals ("E3.100", lastFeature.getValues().get("RAV_code"));
	}

}
