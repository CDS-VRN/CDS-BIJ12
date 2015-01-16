package nl.ipo.cds.etl.theme.vrn.domain;

import java.io.*;

import nl.ipo.cds.etl.db.DBWriter;
import nl.ipo.cds.etl.db.DBWriterFactory;

import org.deegree.commons.tom.ows.CodeType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.io.ParseException;

import static org.junit.Assert.assertEquals;

public class GebiedInrichtingTest extends AbstractGebiedTest<LandelijkGebiedInrichting> {

private DBWriterFactory<LandelijkGebiedInrichting> dbWriterFactory;	
	

	private LandelijkGebiedInrichting gebied;
	
	@Before
	public void setUp() throws ParseException {
		dbWriterFactory = new DBWriterFactory<LandelijkGebiedInrichting>(LandelijkGebiedInrichting.class, "dataset_id", TEST_DATASET_ID);
		gebied = new LandelijkGebiedInrichting();
		gebied.setId(TEST_DATASET_ID);
		gebied.setDoelInrichting(new CodeType ("CodeDoelInrichting", "http://www.namespace.com"));
		gebied.setStatusInrichting(new CodeType ("CodeStatusInrichting", "http://www.namespace.com"));
		gebied.setTypeBeheerder(new CodeType ("CodeTypeBeheerder", "http://www.namespace.com"));
		writeGebied(gebied);
	}
	
	
	@Test
	public void test() throws ParseException {
		
		StringWriter stringWriter = new StringWriter();
		DBWriter<LandelijkGebiedInrichting> dbWriter = dbWriterFactory.getDBWriter(new PrintWriter(stringWriter));	
		dbWriter.writeObject(gebied);
		String output = "\"0\",\"CodeStatusInrichting\",\"CodeDoelInrichting\",\"CodeTypeBeheerder\",\"2014-12-15 14:57:27.094\",\"2014-12-15 14:59:55.565\",\"TEST.ID.0\",\"imnaBronhouder\",\"2\",\"0103000020407100000100000005000000000000006835FB4000000000944A214100000000385AFB4000000000944A214100000000385AFB4000000000CC4C2141000000006835FB4000000000CC4C2141000000006835FB4000000000944A2141\",\"23\",\"0\""+System.lineSeparator(); 
		assertEquals(output, stringWriter.getBuffer().toString());
	}


	@Test
	public void testSerialization() throws IOException, ClassNotFoundException {
		File file = testFolder.newFile("serialize-test");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(gebied);
		oos.flush();
		oos.close();

		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		LandelijkGebiedInrichting gebied2 = (LandelijkGebiedInrichting) ois.readObject();
		ois.close();

		assertEquals(gebied, gebied2);
	}
}
