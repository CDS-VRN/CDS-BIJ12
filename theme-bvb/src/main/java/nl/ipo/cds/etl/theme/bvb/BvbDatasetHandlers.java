package nl.ipo.cds.etl.theme.bvb;

import static javax.xml.stream.XMLInputFactory.newInstance;
import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.InputStream;
import java.net.URL;

import javax.xml.stream.XMLStreamReader;

import nl.ipo.cds.attributemapping.operations.discover.OperationDiscoverer;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.AbstractProcess;
import nl.ipo.cds.etl.DefaultDatasetHandlers;
import nl.ipo.cds.etl.FeatureProcessor;
import nl.ipo.cds.etl.featurecollection.FeatureCollection;
import nl.ipo.cds.etl.log.EventLogger;
import nl.ipo.cds.etl.process.DatasetMetadata;
import nl.ipo.cds.etl.process.Harvester;
import nl.ipo.cds.etl.process.MetadataHarvester;
import nl.ipo.cds.etl.theme.ThemeConfig;
import nl.ipo.cds.utils.UrlUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.deegree.feature.types.AppSchema;

class BvbDatasetHandlers extends DefaultDatasetHandlers<Bvb> {

	private static final Log technicalLog = LogFactory.getLog(AbstractProcess.class);

	private final BvbAppSchemaReader appSchemaReader = new BvbAppSchemaReader();

	public BvbDatasetHandlers(OperationDiscoverer operationDiscoverer, ThemeConfig<Bvb> themeConfig, ManagerDao managerDao) {
		super(operationDiscoverer, themeConfig, managerDao);
	}

	@Override
	public FeatureCollection retrieveFeaturesFromBronhouder(EtlJob job,
			FeatureProcessor featureProcessor,
			final EventLogger<AbstractProcess.MessageKey> userLog, DatasetMetadata metadata) {

		AppSchema appSchema = retrieveBronhouderAppSchema(metadata);
		if (appSchema != null) {
			return retrieveBronhouderDataset(metadata.getFeatureCollectionUrl(), appSchema, job, userLog);
		}
		return null;
	}

	private AppSchema retrieveBronhouderAppSchema(DatasetMetadata metadata) {
		try {
			return appSchemaReader.parseApplicationSchema(metadata);
		} catch (Exception e) {
			technicalLog.debug(String.format("Failed to get BVB application schema: %s", e.getMessage()), e);
		}
		return null;
	}

	private FeatureCollection retrieveBronhouderDataset(String datasetUrl,
			AppSchema appSchema, EtlJob job,
			final EventLogger<AbstractProcess.MessageKey> userLog) {
		InputStream is = null;
		try {
			is = UrlUtils.open(new URL(datasetUrl));
			XMLStreamReader xmlStreamReader = newInstance().createXMLStreamReader(is);
			BvbDatasetReader bvbDatasetReader = new BvbDatasetReader(appSchema);
			return bvbDatasetReader.parseCollection(xmlStreamReader);
		} catch (Exception e) {
			technicalLog.debug(String.format("Failed to init HTTP connection to retrieve BVB XML: %s", e.getMessage()), e);
			if (is != null) {
				closeQuietly(is);
			}
		}
		return null;
	}
}
