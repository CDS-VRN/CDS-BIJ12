package nl.ipo.cds.deegree.extension.vrnfilter;

import com.vividsolutions.jts.io.ParseException;
import nl.ipo.cds.dao.ManagerDao;
import nl.ipo.cds.deegree.persistence.jaxb.VRNFilterSQLFeatureStoreConfig;
import nl.ipo.cds.domain.Bronhouder;
import nl.ipo.cds.domain.Gebruiker;
import nl.ipo.cds.domain.GebruikerThemaAutorisatie;
import org.deegree.commons.annotations.LoggingNotes;
import org.deegree.commons.tom.gml.GMLObject;
import org.deegree.cs.coordinatesystems.ICRS;
import org.deegree.cs.persistence.CRSManager;
import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.FeatureStoreException;
import org.deegree.feature.persistence.FeatureStoreTransaction;
import org.deegree.feature.persistence.lock.LockManager;
import org.deegree.feature.persistence.query.Query;
import org.deegree.feature.stream.CombinedFeatureInputStream;
import org.deegree.feature.stream.FeatureInputStream;
import org.deegree.feature.types.AppSchema;
import org.deegree.feature.types.GenericFeatureType;
import org.deegree.filter.Expression;
import org.deegree.filter.Filter;
import org.deegree.filter.FilterEvaluationException;
import org.deegree.filter.OperatorFilter;
import org.deegree.filter.expression.ValueReference;
import org.deegree.filter.logical.And;
import org.deegree.filter.spatial.Overlaps;
import org.deegree.geometry.Envelope;
import org.deegree.geometry.Geometry;
import org.deegree.geometry.io.WKTReader;
import org.deegree.protocol.wfs.getfeature.TypeName;
import org.deegree.workspace.Resource;
import org.deegree.workspace.ResourceMetadata;
import org.deegree.workspace.Workspace;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.xml.namespace.QName;
import java.util.Collection;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author annes
 */

@LoggingNotes(info = "logs problems when connecting to the DB/getting data from the DB", debug = "logs the SQL statements sent to the SQL server", trace = "logs stack traces")
public class VRNFilterSQLFeatureStore implements FeatureStore {
    static final Logger LOG = getLogger(VRNFilterSQLFeatureStore.class);

    private VRNFilterSQLFeatureStoreMetadata metadata = null;

    GenericFeatureType featureType;

    private boolean available;

    private FeatureStore featureStore;
    private VRNFilterSQLFeatureStoreConfig config;

    ManagerDao managerDao;


    public VRNFilterSQLFeatureStore(FeatureStore featureStore, VRNFilterSQLFeatureStoreMetadata metadata,
                                    VRNFilterSQLFeatureStoreConfig config, Workspace workspace, ManagerDao managerDao) {
//        ApplicationContextHolder contextHolder = workspace.getResource(ApplicationContextHolderProvider.class, config.getApplicationContextHolder());

        this.featureStore = featureStore;
        this.metadata = metadata;
        this.config = config;
        this.managerDao = managerDao;
    }

    public FeatureStoreTransaction acquireTransaction() throws FeatureStoreException {
        return featureStore.acquireTransaction();
    }

    public Envelope calcEnvelope(QName ftName) throws FeatureStoreException {
        return featureStore.calcEnvelope(ftName);
    }

    public void destroy() {
        featureStore.destroy();
    }

    public Envelope getEnvelope(QName ftName) throws FeatureStoreException {
        return featureStore.getEnvelope(ftName);
    }

    public LockManager getLockManager() throws FeatureStoreException {
        return featureStore.getLockManager();
    }

    public ResourceMetadata<? extends Resource> getMetadata() {
        return metadata;
    }

    public GMLObject getObjectById(String id) throws FeatureStoreException {
        return featureStore.getObjectById(id);
    }

    public AppSchema getSchema() {
        return featureStore.getSchema();
    }

    public void init() {
        // nothing to init
    }

    public boolean isAvailable() {
        return true;
    }

    public boolean isMapped(QName ftName) {
        return featureStore.isMapped(ftName);
    }

    public FeatureInputStream query(Query query) throws FeatureStoreException, FilterEvaluationException {

        return featureStore.query(query);
    }

    public FeatureInputStream query(Query[] arg0) throws FeatureStoreException, FilterEvaluationException {
        // need to check filter for authorized geometries, for every type name

        if (this.config.isFilterBronHouderGeometry() || this.config.isFilterGebruikerThemaAutorisatie()) {
            final String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            final Gebruiker gebruiker = managerDao.getGebruiker(principal);

            //getGebruikerThemaAutorisatie filtert al op het recht en gebruiker
            final List<GebruikerThemaAutorisatie> themaAutorisaties = managerDao.getGebruikerThemaAutorisatie(gebruiker);

            Bronhouder bronhouder = null;

            Query query = arg0[0];
            Filter filter = query.getFilter();
            TypeName[] typeNames = query.getTypeNames();

            // er kan maar een typename in een wfs getfeature request voorkomen
            if (typeNames.length > 0) {
                String typename = typeNames[0].getFeatureTypeName().getLocalPart(); // ProvinciaalGebiedBeheer
                String uri = typeNames[0].getFeatureTypeName().getNamespaceURI(); //http://www.cds.nl/imna/beheer
                boolean addtofilter = false;

                // naming moet gelijk zijn: volg de namen van  de autorisatie in de datasources
                for (GebruikerThemaAutorisatie gta : themaAutorisaties) {
                    String themanaam = gta.getBronhouderThema().getThema().getNaam(); // ProvinciaalGebiedBeheer
                    if (themanaam.equals(typename)) {
                        addtofilter = true;
                        if(typename.startsWith("Provincie")) {
                            bronhouder = gta.getBronhouderThema().getBronhouder();
                        }
                    }
                }

                if (!addtofilter) {
                    //return a FeatureInputStream with not records, or exception
                }
                else {
                    if(bronhouder!=null) {
                        // need extra geo filtering
                        Geometry g = managerDao.getBronhouderGeometry(bronhouder);
                        Expression exp = new ValueReference(new QName(uri, "geometrie", "imna"));
                        Overlaps overlapsOperator = new Overlaps(exp, g);
                        if (filter == null) {
                            filter = new OperatorFilter(overlapsOperator);
                        } else {
                            And andOperator = new And(overlapsOperator, ((OperatorFilter) filter).getOperator());
                            filter = new OperatorFilter(andOperator);
                        }
                    }
                }
            }
            arg0[0] = new Query(query.getTypeNames(), filter, null, null, null);
        }
        return featureStore.query(arg0);
    }

    public int queryHits(Query arg0) throws FeatureStoreException, FilterEvaluationException {
        return featureStore.queryHits(arg0);
    }

    public int[] queryHits(Query[] arg0) throws FeatureStoreException, FilterEvaluationException {
        return featureStore.queryHits(arg0);
    }

}
