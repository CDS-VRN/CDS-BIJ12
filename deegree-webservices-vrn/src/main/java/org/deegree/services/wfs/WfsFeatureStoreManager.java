//$HeadURL$

package org.deegree.services.wfs;

import static org.deegree.commons.xml.CommonNamespaces.GML3_2_NS;
import static org.deegree.commons.xml.CommonNamespaces.GMLNS;
import static org.deegree.services.i18n.Messages.get;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.namespace.QName;

import org.deegree.commons.config.ResourceInitException;
import org.deegree.commons.utils.QNameUtils;
import org.deegree.feature.persistence.FeatureStore;
import org.deegree.feature.persistence.FeatureStoreException;
import org.deegree.feature.persistence.FeatureStoreProvider;
import org.deegree.feature.types.AppSchema;
import org.deegree.feature.types.FeatureType;
import org.deegree.services.jaxb.wfs.DeegreeWFS;
import org.deegree.workspace.ResourceIdentifier;
import org.deegree.workspace.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the {@link FeatureStore} instances for the {@link WebFeatureService}.
 * 
 * @see WebFeatureService
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author$
 * 
 * @version $Revision$, $Date$
 */
public class WfsFeatureStoreManager {

    private static final Logger LOG = LoggerFactory.getLogger( WfsFeatureStoreManager.class );

    private final Map<QName, FeatureType> ftNameToFt = new HashMap<QName, FeatureType>();

    private final Map<AppSchema, FeatureStore> schemaToStore = new HashMap<AppSchema, FeatureStore>();

    private final Map<String, String> prefixToNs = new LinkedHashMap<String, String>();

    private Map<String, String> targetNsToPrefix = new LinkedHashMap<String, String>();

    private int indexPrefix = 0;

    /**
     * @param sc
     * @param baseURL
     * @throws FeatureStoreException
     */
    public void init( DeegreeWFS sc, Workspace workspace )
                            throws ResourceInitException {

        List<String> ids = sc.getFeatureStoreId();

        if ( ids.isEmpty() ) {
            LOG.debug( "Feature store ids not configured. Adding all active feature stores." );
            List<ResourceIdentifier<FeatureStore>> stores = workspace.getResourcesOfType( FeatureStoreProvider.class );
            for ( ResourceIdentifier<FeatureStore> id : stores ) {
                FeatureStore store = workspace.getResource( id.getProvider(), id.getId() );
                if ( store != null ) {
                    addStore( store );
                    addNotYetHintedNamespaces( store.getSchema().getNamespaceBindings().values() );
                }
            }
        } else {
            LOG.debug( "Adding configured feature stores." );
            for ( String id : ids ) {
                FeatureStore store = workspace.getResource( FeatureStoreProvider.class, id );
                if ( store == null ) {
                    String msg = "Cannot add feature store '" + id + "': no such feature store has been configured.";
                    throw new ResourceInitException( msg );
                }
                addStore( store );
                addNotYetHintedNamespaces( store.getSchema().getNamespaceBindings().values() );
            }
        }

        LOG.debug( "The following prefix-to-namespace and namespace-to-prefix bindings are used for resolution..." );
        for ( String prefix : prefixToNs.keySet() ) {
            LOG.debug( prefix + " --> " + prefixToNs.get( prefix ) );
        }
        for ( String ns : targetNsToPrefix.keySet() ) {
            LOG.debug( ns + " <-- " + targetNsToPrefix.get( ns ) );
        }
    }

    private void addNotYetHintedNamespaces( Collection<String> namespaces ) {
        for ( String ns : namespaces ) {
            if ( !targetNsToPrefix.containsKey( ns ) ) {
                String prefix = "app" + ( indexPrefix++ );
                prefixToNs.put( prefix, ns );
                targetNsToPrefix.put( ns, prefix );
            }
        }
    }

    /**
     * Returns the qualified names of all served {@link FeatureType}s.
     * 
     * @return the qualified names, never <code>null</code>
     */
    public QName[] getFeatureTypeNames() {
        return ftNameToFt.keySet().toArray( new QName[ftNameToFt.size()] );
    }

    /**
     * Returns all {@link FeatureType}s.
     * 
     * @return served feature types, may be empty, but never <code>null</code>
     */
    public Collection<FeatureType> getFeatureTypes() {
        return ftNameToFt.values();
    }

    /**
     * Looks up the {@link FeatureType} with the given qualified name (in a namespace-tolerant manner).
     * <p>
     * This method is tolerant to improve interoperability with clients (especially WFS 1.0.0) that only provide the
     * local name or the prefixed name without a namespace binding.
     * </p>
     * 
     * @param ftName
     *            feature type to look up, must not be <code>null</code>
     * @return feature type with the given name, or <code>null</code> if no such feature type is served
     */
    public FeatureType lookupFeatureType( QName ftName ) {

        FeatureType ft = ftNameToFt.get( ftName );

        if ( ft == null ) {
            QName match = QNameUtils.findBestMatch( ftName, ftNameToFt.keySet() );
            if ( match != null && ( !match.equals( ftName ) ) ) {
                LOG.debug( "Repairing unqualified FeatureType name: " + QNameUtils.toString( ftName ) + " -> "
                           + QNameUtils.toString( match ) );
                ft = ftNameToFt.get( match );
            }
        }
        return ft;
    }

    /**
     * Returns the {@link FeatureStore} instance which is responsible for the specified feature type.
     * 
     * @param ftName
     *            name of the {@link FeatureType}
     * @return the responsible {@link FeatureStore} or <code>null</code> if no such store exists, i.e. the specified
     *         feature type is not served
     */
    public FeatureStore getStore( QName ftName ) {
        FeatureType ft = lookupFeatureType( ftName );
        if ( ft == null ) {
            return null;
        }
        return schemaToStore.get( ft.getSchema() );
    }

    /**
     * Get the prefix-to-namespace map that is constructed from the NamespaceHints in the configuration
     * 
     * @return the prefix-to-namespace map
     */
    public Map<String, String> getPrefixToNs() {
        return prefixToNs;
    }

    /**
     * Get the namespace-to-prefix bindings for the namespaces of the application schemas.
     * 
     * @return the namespace-to-prefix map
     */
    public Map<String, String> getTargetNsToPrefix() {
        return targetNsToPrefix;
    }

    /**
     * Returns all registered {@link FeatureStore} instances.
     * 
     * @return all registered feature stores
     */
    public FeatureStore[] getStores() {
        Set<FeatureStore> stores = new HashSet<FeatureStore>( schemaToStore.values() );
        return stores.toArray( new FeatureStore[stores.size()] );
    }

    /**
     * Registers a new {@link FeatureStore} to the WFS.
     * 
     * @param fs
     *            store to be registered
     */
    public void addStore( FeatureStore fs ) {
        synchronized ( this ) {
            if ( schemaToStore.containsValue( fs ) ) {
                String msg = get( "WFS_FEATURESTORE_ALREADY_REGISTERED", fs );
                LOG.error( msg );
                throw new IllegalArgumentException( msg );
            }
            for ( FeatureType ft : fs.getSchema().getFeatureTypes( null, false, false ) ) {
                if ( ft.getName().getNamespaceURI().equals( GMLNS )
                     || ft.getName().getNamespaceURI().equals( GML3_2_NS ) ) {
                    continue;
                }
                if ( ftNameToFt.containsKey( ft.getName() ) ) {
                    String msg = get( "WFS_FEATURETYPE_ALREADY_SERVED", ft.getName() );
                    LOG.error( msg );
                    // EDIT for VRN webservices: do not throw Exception, just override delegate ft
                    // throw new IllegalArgumentException( msg );
                }
                ftNameToFt.put( ft.getName(), ft );
            }

            schemaToStore.put( fs.getSchema(), fs );

            for ( Entry<String, String> e : fs.getSchema().getNamespaceBindings().entrySet() ) {
                prefixToNs.put( e.getKey(), e.getValue() );
                targetNsToPrefix.put( e.getValue(), e.getKey() );
            }
        }
    }
}
