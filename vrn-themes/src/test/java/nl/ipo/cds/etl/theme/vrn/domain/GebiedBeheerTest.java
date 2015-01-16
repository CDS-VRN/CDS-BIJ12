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

public class GebiedBeheerTest extends AbstractGebiedTest<LandelijkGebiedBeheer> {

    private DBWriterFactory<LandelijkGebiedBeheer> dbWriterFactory;
	

	private  LandelijkGebiedBeheer gebied;
	
	@Before
	public void setUp() throws ParseException {
		dbWriterFactory = new DBWriterFactory<LandelijkGebiedBeheer>(LandelijkGebiedBeheer.class, "dataset_id", TEST_DATASET_ID);
		gebied = new LandelijkGebiedBeheer();
		gebied.setId(TEST_DATASET_ID);
		gebied.setDoelBeheer(new CodeType ("CodeDoelBeheer", "http://www.namespace.com"));
		gebied.setBeheerpakket(new CodeType ("CodeBeheerPakket", "http://www.namespace.com"));
		gebied.setStatusBeheer(new CodeType ("CodeStatusBeheer", "http://www.namespace.com"));
		gebied.setTypeBeheerder(new CodeType ("CodeTypeBeheerder", "http://www.namespace.com"));
		gebied.setEenheidnummer("eenheidNummer");
		writeGebied(gebied);
	}
	
	@Test
	public void test() {
		StringWriter stringWriter = new StringWriter();
		DBWriter<LandelijkGebiedBeheer> dbWriter = dbWriterFactory.getDBWriter(new PrintWriter(stringWriter));	
		dbWriter.writeObject(gebied);
		System.out.println(stringWriter.getBuffer().toString());
		String output = "\"0\",\"CodeStatusBeheer\",\"CodeBeheerPakket\",\"CodeDoelBeheer\",\"CodeTypeBeheerder\",\"eenheidNummer\",\"2014-12-15 14:57:27.094\",\"2014-12-15 14:59:55.565\",\"TEST.ID.0\",\"imnaBronhouder\",\"2\",\"0103000020407100000100000005000000000000006835FB4000000000944A214100000000385AFB4000000000944A214100000000385AFB4000000000CC4C2141000000006835FB4000000000CC4C2141000000006835FB4000000000944A2141\",\"23\",\"0\""+System.lineSeparator(); 
		assertEquals(output, stringWriter.getBuffer().toString());
		
	}

	@Test
	public void testSerialization() throws Exception {
		File file = testFolder.newFile("serialize-test");
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(gebied);
		oos.flush();
		oos.close();

		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		LandelijkGebiedBeheer gebied2 = (LandelijkGebiedBeheer) ois.readObject();
		ois.close();

		assertEquals(gebied, gebied2);
	}
	
	

}
