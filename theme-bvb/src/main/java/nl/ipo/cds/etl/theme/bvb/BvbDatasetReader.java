package nl.ipo.cds.etl.theme.bvb;

import static nl.ipo.cds.etl.featurecollection.FeatureCollectionReader.createGenericFeature;
import static org.deegree.commons.xml.stax.XMLStreamUtils.nextElement;
import static org.deegree.commons.xml.stax.XMLStreamUtils.skipStartDocument;
import static org.deegree.gml.GMLInputFactory.createGMLStreamReader;
import static org.deegree.gml.GMLVersion.GML_31;

import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import nl.ipo.cds.domain.FeatureType;
import nl.ipo.cds.etl.GenericFeature;
import nl.ipo.cds.etl.featurecollection.FeatureCollection;
import nl.ipo.cds.etl.featuretype.FeatureTypeNotFoundException;
import nl.ipo.cds.etl.featuretype.GMLFeatureTypeParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.deegree.commons.xml.stax.XMLStreamUtils;
import org.deegree.feature.Feature;
import org.deegree.feature.types.AppSchema;
import org.deegree.geometry.Envelope;
import org.deegree.gml.GMLStreamReader;

public class BvbDatasetReader {

	private static final Log logger = LogFactory.getLog(BvbDatasetReader.class);
	
	private final AppSchema appSchema;

	BvbDatasetReader(AppSchema appSchema) {
		this.appSchema = appSchema;
	}

	FeatureCollection parseCollection(final XMLStreamReader streamReader) throws NoSuchElementException, XMLStreamException, FeatureTypeNotFoundException {
		skipStartDocument(streamReader);
		if (streamReader.isStartElement()) {
			if (!streamReader.getName().getLocalPart().equals("BVB")) {
				throw new RuntimeException("XML stream is not a BVB dataset. Root element: " + streamReader.getName());
			}			
			XMLStreamUtils.nextElement(streamReader);
			FeatureType ft = null;
			if (streamReader.isStartElement()) {
				ft = new GMLFeatureTypeParser ().parseSchema (appSchema, streamReader.getLocalName());
			}		
			return new BvbFeatureCollection(streamReader, ft);
		} else {
			throw new RuntimeException("Empty XML stream");
		}
	}

	private class BvbFeatureCollection implements FeatureCollection {

		private final XMLStreamReader streamReader;

		private final FeatureType ft;

		BvbFeatureCollection(final XMLStreamReader streamReader, final FeatureType ft) {
			this.streamReader = streamReader;
			this.ft = ft;
		}

		@Override
		public Envelope getBoundedBy() {
			return null;
		}

		@Override
		public FeatureType getFeatureType() {
			return ft;
		}

		@Override
		public Iterator<GenericFeature> iterator() {
			return new Iterator<GenericFeature>() {

				@Override
				public boolean hasNext() {
					return streamReader.isStartElement();
				}

				@Override
				public GenericFeature next() {
					if (!hasNext()) {
						throw new NoSuchElementException();
					}
					try {
						GMLStreamReader gmlStream = createGMLStreamReader(GML_31, streamReader);
						gmlStream.setApplicationSchema(appSchema);
						Feature gmlFeature = gmlStream.readFeature();
						nextElement(streamReader);
						return createGenericFeature(gmlFeature, ft);
					} catch (Exception e) {
						logger.debug("error: " + e.getMessage());
						throw new RuntimeException("Couldn't read feature", e);
					}
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}

			};
		}
	}
}
