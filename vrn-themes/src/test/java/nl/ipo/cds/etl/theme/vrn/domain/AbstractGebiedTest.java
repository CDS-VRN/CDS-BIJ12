package nl.ipo.cds.etl.theme.vrn.domain;

import java.io.PrintWriter;
import java.io.StringWriter;

import nl.ipo.cds.etl.db.DBWriter;
import nl.ipo.cds.etl.db.DBWriterFactory;

import org.junit.Before;
import org.junit.Test;

/**
 * @author annes
 *
 */
public class AbstractGebiedTest {

	protected static final String TEST_DATASET_ID = "0";	
	//private static final String TEST_ID = "TEST.ID.0";
	private DBWriterFactory<ProvinciaalGebiedBeheer> dbWriterFactory;	
	
	@Before
	public void setUp() {
		dbWriterFactory = new DBWriterFactory<ProvinciaalGebiedBeheer>(ProvinciaalGebiedBeheer.class, "dataset_id", TEST_DATASET_ID);
	}
	
	
	@Test
	public void testProvinciaalGebiedBeheer(){
		ProvinciaalGebiedBeheer gebied = new ProvinciaalGebiedBeheer();
		gebied.setContractnummer(2);
		
		StringWriter stringWriter = new StringWriter();
		DBWriter<ProvinciaalGebiedBeheer> dbWriter = dbWriterFactory.getDBWriter(new PrintWriter(stringWriter));	
		
		dbWriter.writeObject(gebied);
		System.out.println(stringWriter.getBuffer().toString());
		
	}
	
}
