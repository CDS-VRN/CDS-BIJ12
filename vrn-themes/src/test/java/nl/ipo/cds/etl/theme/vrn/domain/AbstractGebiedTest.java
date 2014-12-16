package nl.ipo.cds.etl.theme.vrn.domain;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;

import nl.ipo.cds.etl.db.DBWriter;
import nl.ipo.cds.etl.db.DBWriterFactory;

import org.junit.Before;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * @author annes
 *
 */
public class AbstractGebiedTest <T extends AbstractGebied> {

	protected static final String TEST_DATASET_ID = "0";	
	private static final String TEST_ID = "TEST.ID.0";
	
	
	public void writeGebied(T gebied) throws ParseException{
		gebied.setRelatienummer(23);
		gebied.setBegintijd(new Timestamp(1418651847094L));
		gebied.setEindtijd(new Timestamp(1418651995565L));
		GeometryFactory geometryFactory = new GeometryFactory();
		WKTReader reader = new WKTReader(geometryFactory);
		Polygon polygon = (Polygon) reader.read("POLYGON((111446.5 566602,112035.5 566602,112035.5 566886,111446.5 566886,111446.5 566602))");
		gebied.setGeometrie(polygon);
		gebied.setId(TEST_DATASET_ID);
		gebied.setIdentificatie(TEST_ID);
		gebied.setImnaBronhouder("imnaBronhouder");
		gebied.setContractnummer(2);
		
		/*StringWriter stringWriter = new StringWriter();
		DBWriter<AbstractGebied> dbWriter = dbWriterFactory.getDBWriter(new PrintWriter(stringWriter));	
		
		dbWriter.writeObject(gebied);
		System.out.println(stringWriter.getBuffer().toString());*/
		
	}
	
}
