package nl.ipo.cds.etl.theme.vrn.domain;

import org.deegree.commons.tom.ows.CodeType;

import nl.ipo.cds.etl.db.annotation.Column;
import nl.ipo.cds.etl.db.annotation.Table;
import nl.ipo.cds.etl.theme.annotation.CodeSpace;
import nl.ipo.cds.etl.theme.annotation.MappableAttribute;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;

import static nl.ipo.cds.etl.theme.vrn.Constants.*;

/**
 * @author annes
 *
 * Baseclass for IMNa theme verwerving
 */
@Table
public abstract  class AbstractGebiedVerwerving extends AbstractGebied {

    @Column(name = "status_verwerving")
    private transient CodeType statusVerwerving;

    @Column(name = "type_eigenaar")
    private transient CodeType typeEigenaar;


	@Column(name = "doelverwerving")
	 private transient CodeType doelVerwerving;

	/**
	 * Custom deserialization because Geometry type is not serializable by default, nor is CodeType.
	 * @param ois The input stream.
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		// Read default serializable properties.
		ois.defaultReadObject();

		statusVerwerving = codeTypeReader(ois);
		typeEigenaar = codeTypeReader(ois);
		doelVerwerving = codeTypeReader(ois);

	}

	/**
	 * Custom serialization because deegree types are not serializable.
	 * @param oos The output stream.
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException, ParseException {
		// Write default serializable properties.
		oos.defaultWriteObject();

		codeTypeWriter(statusVerwerving, oos);
		codeTypeWriter(typeEigenaar, oos);
		codeTypeWriter(doelVerwerving, oos);
	}


	public boolean equals(Object o) {
		if(!(o instanceof AbstractGebiedVerwerving)) {
			return false;
		}

		AbstractGebiedVerwerving that = (AbstractGebiedVerwerving)o;
		return super.equals(that) &&
				this.getDoelVerwerving().equals(that.getDoelVerwerving()) &&
				this.getStatusVerwerving().equals(that.getStatusVerwerving()) &&
				this.getTypeEigenaar().equals(that.getTypeEigenaar());
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_STATUS_VERWERVING)
    public CodeType getStatusVerwerving() {
		return statusVerwerving;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_STATUS_VERWERVING)
	public void setStatusVerwerving(CodeType statusVerwerving) {
		this.statusVerwerving = statusVerwerving;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_TYPE_BEHEERDER)
	public CodeType getTypeEigenaar() {
		return typeEigenaar;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_TYPE_BEHEERDER)
	public void setTypeEigenaar(CodeType typeEigenaar) {
		this.typeEigenaar = typeEigenaar;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_DOEL_REALISATIE)
	public CodeType getDoelVerwerving() {
		return doelVerwerving;
	}

	@MappableAttribute
	@CodeSpace(CODESPACE_DOEL_REALISATIE)
	public void setDoelVerwerving(CodeType doelRealisatie) {
		this.doelVerwerving = doelRealisatie;
	}

}
