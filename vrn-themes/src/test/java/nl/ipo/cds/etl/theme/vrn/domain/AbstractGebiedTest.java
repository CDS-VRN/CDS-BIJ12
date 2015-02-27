package nl.ipo.cds.etl.theme.vrn.domain;

import com.vividsolutions.jts.io.ParseException;
import org.deegree.commons.tom.ows.CodeType;
import org.deegree.cs.persistence.CRSManager;
import org.deegree.cs.refs.coordinatesystem.CRSRef;
import org.deegree.geometry.io.WKTReader;
import org.deegree.geometry.primitive.Polygon;
import org.junit.rules.TemporaryFolder;

import java.math.BigInteger;
import java.sql.Timestamp;


/**
 * @author annes
 *
 */
public class AbstractGebiedTest <T extends AbstractGebied> {

	protected static final String TEST_DATASET_ID = "0";	
	private static final String TEST_ID = "TEST.ID.0";
	private Polygon polygon;

	private static CRSRef rdCrsRef = CRSManager.getCRSRef("EPSG:28992");

	public final TemporaryFolder testFolder = new TemporaryFolder();

	public void writeGebied(T gebied) throws ParseException {
		gebied.setRelatienummer(BigInteger.valueOf(23));
		gebied.setBegintijd(new Timestamp(1418651847094L));
		gebied.setEindtijd(new Timestamp(1418651995565L));
		WKTReader reader = new WKTReader(null);
		polygon = (Polygon) reader.read("POLYGON((111446.5 566602,112035.5 566602,112035.5 566886,111446.5 566886,111446.5 566602))");
		polygon.setCoordinateSystem(rdCrsRef);
		gebied.setGeometrie(polygon);
		gebied.setId(TEST_DATASET_ID);
		gebied.setIdentificatie(TEST_ID);
		gebied.setImnaBronhouder((new CodeType("imnaBronhouder", "http://www.namespace.com")));
		gebied.setContractnummer(BigInteger.valueOf(2));
	}


	public Polygon getPolygon() {
		return polygon;
	}


	public void setPolygon(Polygon polygon) {
		this.polygon = polygon;
	}
	
}
