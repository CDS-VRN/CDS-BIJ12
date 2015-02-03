package nl.ipo.cds.etl.theme.vrn.domain;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_BEHEER_PAKKET;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_DOEL_REALISATIE;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_STATUS_BEHEER;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_TYPE_BEHEERDER;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import org.deegree.commons.tom.ows.CodeType;

/**
 * @author annes
 * 
 * Baseclass for IMNa theme Beheer
 *
 */
@Table
public abstract class AbstractGebiedBeheer extends AbstractGebied {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "status_beheer")
    private transient CodeType statusBeheer;

    @Column(name = "beheerpakket")
    private transient CodeType beheerpakket;

    @Column(name = "doelbeheer")
    private transient CodeType doelBeheer;

    @Column(name = "type_beheerder")
    private transient CodeType typeBeheerder;

    @Column(name="eenheidnummer")
    private String eenheidnummer;


	/**
	 * Custom deserialization because Geometry type is not serializable by default, nor is CodeType.
	 * @param ois The input stream.
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException, ParseException {
		// Read default serializable properties.
		ois.defaultReadObject();

		statusBeheer = codeTypeReader(ois);
		beheerpakket = codeTypeReader(ois);
		doelBeheer = codeTypeReader(ois);
		typeBeheerder = codeTypeReader(ois);

	}

	/**
	 * Custom serialization because deegree types are not serializable.
	 * @param oos The output stream.
	 * @throws IOException
	 * @throws ParseException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException, ParseException {
		// Write default serializable properties.
		oos.defaultWriteObject();

		codeTypeWriter(statusBeheer, oos);
		codeTypeWriter(beheerpakket, oos);
		codeTypeWriter(doelBeheer, oos);
		codeTypeWriter(typeBeheerder, oos);
	}


	public boolean equals(Object o) {
		if(!(o instanceof AbstractGebiedBeheer)) {
			return false;
		}

		AbstractGebiedBeheer that = (AbstractGebiedBeheer)o;
		return super.equals(that) &&
				this.getBeheerpakket().equals(that.getBeheerpakket()) &&
				this.getDoelBeheer().equals(that.getDoelBeheer()) &&
				this.getEenheidnummer().equals(that.getEenheidnummer()) &&
				this.getStatusBeheer().equals(that.getStatusBeheer()) &&
				this.getTypeBeheerder().equals(that.getTypeBeheerder());
	}


    @MappableAttribute
    @CodeSpace(CODESPACE_STATUS_BEHEER)
	public CodeType getStatusBeheer() {
		return statusBeheer;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_STATUS_BEHEER)
	public void setStatusBeheer(CodeType statusBeheer) {
		this.statusBeheer = statusBeheer;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_BEHEER_PAKKET)
	public CodeType getBeheerpakket() {
		return beheerpakket;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_BEHEER_PAKKET)
	public void setBeheerpakket(CodeType beheerpakket) {
		this.beheerpakket = beheerpakket;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_DOEL_REALISATIE)
	public CodeType getDoelBeheer() {
		return doelBeheer;
	}
    
  
	/**
	 * Returns 
	 * @return
	 */
	public String getDoelRealisatieValue() {
		return doelBeheer==null?null:doelBeheer.getCode();
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_DOEL_REALISATIE)
	public void setDoelBeheer(CodeType doelBeheer) {
		this.doelBeheer = doelBeheer;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_TYPE_BEHEERDER)
	public CodeType getTypeBeheerder() {
		return typeBeheerder;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_TYPE_BEHEERDER)
	public void setTypeBeheerder(CodeType typeBeheerder) {
		this.typeBeheerder = typeBeheerder;
	}
    
    @MappableAttribute
	public String getEenheidnummer() {
		return eenheidnummer;
	}

    @MappableAttribute
	public void setEenheidnummer(String eenheidnummer) {
		this.eenheidnummer = eenheidnummer;
	}


}
