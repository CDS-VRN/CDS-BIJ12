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

public class GebiedVerwervingTest extends AbstractGebiedTest<LandelijkGebiedVerwerving> {

private DBWriterFactory<LandelijkGebiedVerwerving> dbWriterFactory;	
	

	private LandelijkGebiedVerwerving gebied;
	
	@Before
	public void setUp() throws ParseException {
		dbWriterFactory = new DBWriterFactory<LandelijkGebiedVerwerving>(LandelijkGebiedVerwerving.class, "dataset_id", TEST_DATASET_ID);
		gebied = new LandelijkGebiedVerwerving();
		gebied.setId(TEST_DATASET_ID);
		gebied.setDoelVerwerving(new CodeType ("CodeDoelVerwerving", "http://www.namespace.com"));
		gebied.setStatusVerwerving(new CodeType ("CodeStatusVerwerving", "http://www.namespace.com"));
		gebied.setTypeEigenaar(new CodeType ("CodeTypeEigenaar", "http://www.namespace.com"));
		writeGebied(gebied);
	}
	
	
	@Test
	public void test() throws ParseException {
		
		StringWriter stringWriter = new StringWriter();
		DBWriter<LandelijkGebiedVerwerving> dbWriter = dbWriterFactory.getDBWriter(new PrintWriter(stringWriter));	
		
		dbWriter.writeObject(gebied);
		System.out.println(stringWriter.getBuffer().toString());
		String output = new String("\"0\",\"CodeStatusVerwerving\",\"CodeTypeEigenaar\",\"CodeDoelVerwerving\",\"2014-12-15 14:57:27.094\",\"2014-12-15 14:59:55.565\",\"TEST.ID.0\",\"imnaBronhouder\",\"2\",\"0103000020407100000100000005000000000000006835FB4000000000944A214100000000385AFB4000000000944A214100000000385AFB4000000000CC4C2141000000006835FB4000000000CC4C2141000000006835FB4000000000944A2141\",\"23\",\"0\"" + System.lineSeparator()); 
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
		LandelijkGebiedVerwerving gebied2 = (LandelijkGebiedVerwerving) ois.readObject();
		ois.close();

		assertEquals(gebied, gebied2);
	}
}
