package nl.ipo.cds.etl.theme.vrn.domain;

import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_DOEL_REALISATIE;
import static nl.ipo.cds.etl.theme.vrn.Constants.CODESPACE_STATUS_INRICHTING;
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
 * Baseclass for IMNa theme Inrichting
 *
 */
@Table
public abstract class AbstractGebiedInrichting extends AbstractGebied {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "status_inrichting")
    private transient CodeType statusInrichting;

    @Column(name = "doelinrichting")
    private transient CodeType doelInrichting;

    @Column(name = "type_beheerder")
    private transient CodeType typeBeheerder;


    /**
     * Custom deserialization because Geometry type is not serializable by default, nor is CodeType.
     * @param ois The input stream.
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        // Read default serializable properties.
        ois.defaultReadObject();

        statusInrichting = codeTypeReader(ois);
        doelInrichting = codeTypeReader(ois);
        typeBeheerder = codeTypeReader(ois);

    }

    /**
     * Custom serialization because deegree types are not serializable.
     * @param oos The output stream.
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream oos) throws IOException, ParseException {
        // Write default serializable properties.
        oos.defaultWriteObject();

        codeTypeWriter(statusInrichting, oos);
        codeTypeWriter(doelInrichting, oos);
        codeTypeWriter(typeBeheerder, oos);
    }


    public boolean equals(Object o) {
        if(!(o instanceof AbstractGebiedInrichting)) {
            return false;
        }

        AbstractGebiedInrichting that = (AbstractGebiedInrichting)o;
        return super.equals(that) &&
                this.getDoelInrichting().equals(that.getDoelInrichting()) &&
                this.getStatusInrichting().equals(that.getStatusInrichting()) &&
                this.getTypeBeheerder().equals(that.getTypeBeheerder());
    }

    @MappableAttribute
    @CodeSpace(CODESPACE_STATUS_INRICHTING)
	public CodeType getStatusInrichting() {
		return statusInrichting;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_STATUS_INRICHTING)
	public void setStatusInrichting(CodeType statusInrichting) {
		this.statusInrichting = statusInrichting;
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_DOEL_REALISATIE)
	public CodeType getDoelInrichting() {
		return doelInrichting;
	}
    
    
    /**
	 * Returns 
	 * @return
	 */
	public String getDoelRealisatieValue() {
		return doelInrichting==null?null:doelInrichting.getCode();
	}

    @MappableAttribute
    @CodeSpace(CODESPACE_DOEL_REALISATIE)
	public void setDoelInrichting(CodeType doelInrichting) {
		this.doelInrichting = doelInrichting;
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


}
