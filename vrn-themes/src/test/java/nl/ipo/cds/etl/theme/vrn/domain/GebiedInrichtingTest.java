package nl.ipo.cds.etl.theme.vrn.domain;

import java.io.PrintWriter;
import java.io.StringWriter;

import nl.ipo.cds.etl.db.DBWriter;
import nl.ipo.cds.etl.db.DBWriterFactory;

import org.deegree.commons.tom.ows.CodeType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.io.ParseException;

public class GebiedInrichtingTest extends AbstractGebiedTest<LandelijkGebiedInrichting> {

private DBWriterFactory<LandelijkGebiedInrichting> dbWriterFactory;	
	
	
	
	@Before
	public void setUp() {
		dbWriterFactory = new DBWriterFactory<LandelijkGebiedInrichting>(LandelijkGebiedInrichting.class, "dataset_id", TEST_DATASET_ID);
	}
	
	
	@Test
	public void test() throws ParseException {
		
		LandelijkGebiedInrichting gebied = new LandelijkGebiedInrichting();
		gebied.setId(TEST_DATASET_ID);
		gebied.setDoelRealisatie(new CodeType ("CodeDoelInrichting", "http://www.namespace.com"));
		gebied.setStatusInrichting(new CodeType ("CodeStatusInrichting", "http://www.namespace.com"));
		gebied.setTypeBeheerder(new CodeType ("CodeTypeBeheerder", "http://www.namespace.com"));
		writeGebied(gebied);
		StringWriter stringWriter = new StringWriter();
		DBWriter<LandelijkGebiedInrichting> dbWriter = dbWriterFactory.getDBWriter(new PrintWriter(stringWriter));	
		dbWriter.writeObject(gebied);
		String output = "\"0\",\"CodeStatusInrichting\",\"CodeDoelInrichting\",\"CodeTypeBeheerder\",\"2014-12-15 14:57:27.094\",\"2014-12-15 14:59:55.565\",\"TEST.ID.0\",\"imnaBronhouder\",\"2\",\"0103000020407100000100000005000000000000006835FB4000000000944A214100000000385AFB4000000000944A214100000000385AFB4000000000CC4C2141000000006835FB4000000000CC4C2141000000006835FB4000000000944A2141\",\"23\",\"0\""+System.lineSeparator(); 
		Assert.assertEquals(output, stringWriter.getBuffer().toString());
	}
	

}
