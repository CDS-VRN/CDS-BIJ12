package nl.ipo.cds.etl.theme.riscisor;

import java.util.ArrayList;
import java.util.List;

import nl.idgis.commons.jobexecutor.JobLogger.LogLevel;
import nl.ipo.cds.etl.ValidatorMessageKey;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.Expression;

public enum Message implements ValidatorMessageKey<Message, Context> {
	EXTERN_ID_NULL,						// id
	EXTERN_ID_EMPTY,					// id
	EXTERN_ID_TOO_LONG,					// externId, length, maxLength
	EXTERN_ID_DUPLICATE,				// externId

	RISICOKAART_MEDEWERKER_NAAM_NULL,	// externId
	RISICOKAART_MEDEWERKER_NAAM_EMPTY,	// externId
	RISICOKAART_MEDEWERKER_NAAM_NOT_CONSTANT,	// externId, risicokaartMedewerkerNaam
	
	LAATSTE_MUTATIEDATUMTIJD_NULL,		// externId
	LAATSTE_MUTATIEDATUMTIJD_FUTURE,	// externId
	
	INSTELLINGCODE_NULL,				// externId
	INSTELLINGCODE_INVALID_CODESPACE,	// externId
	INSTELLINGCODE_INVALID,				// externId, instellingcode
	
	NAAM_NULL,							// externId
	NAAM_EMPTY,							// externId
	NAAM_TOO_LONG,						// externId, length, maxLength
	
	POSTCODE_INVALID,					// externId, postcode
	
	HUISNUMMER_INVALID,					// externId, huisnummer
	
	HUISLETTER_INVALID,					// externId, huisletter
	
	HUISNUMMERTOEVOEGING_INVALID,		// externId, huisnummertoevoeging
	
	IDENTIFICATIECODE_NUMMERAANDUIDING_BAG_EMPTY, // externId
	IDENTIFICATIECODE_NUMMERAANDUIDING_BAG_TOO_LONG, // externId, identificatiecodeNummeraanduidingBAG
	
	COORDINAAT_NULL,					// externId
	COORDINAAT_NOT_POINT,				// externId
	COORDINAAT_NO_SRS,					// externId
	COORDINAAT_NOT_RD,					// externId, srs
	
	AANTAL_AANWEZIGEN_INVALID,			// externId, aantalAanwezigen
	
	AANTAL_BOUWLAGEN_INVALID,			// externId, aantalBouwlagen
	
	PREVAPCODE_NULL,					// externId
	PREVAPCODE_INVALID,					// externId, prevapcode
	PREVAPCODE_INVALID_CODESPACE,		// externId
	
	PREVAP_PRIO_INVALID,				// externId, prevapPrio
	
	// Nummeraanduiding or postcode/huisnummer constraint:
	NUMMERAANDUIDING_AND_POSTCODE_NULL,	// externId
	POSTCODE_NULL,						// externId
	HUISNUMMER_NULL,					// externId
	POSTCODE_MUST_BE_NULL,				// externId
	HUISNUMMER_MUST_BE_NULL,			// externId
	HUISLETTER_MUST_BE_NULL,			// externId
	HUISNUMMERTOEVOEGING_MUST_BE_NULL,	// externId
	
	HAS_MORE_ERRORS
	;

	@Override
	public boolean isBlocking () {
		return true;
	}

	@Override
	public List<Expression<Message, Context, ?>> getMessageParameters () {
		final List<Expression<Message, Context, ?>> params = new ArrayList<> ();
		
		params.add (new AttributeExpression<Message, Context, String> ("id", String.class));
		params.add (new AttributeExpression<Message, Context, String> ("id", String.class));
		params.add (new AttributeExpression<Message, Context, String> ("externId", String.class));
		
		return params;
	}

	@Override
	public int getMaxMessageLog() {
		return 10;
	}

	@Override
	public boolean isAddToShapeFile () {
		return false;
	}

	@Override
	public LogLevel getLogLevel() {
		return LogLevel.ERROR;
	}

	@Override
	public Message getMaxMessageKey() {
		return HAS_MORE_ERRORS;
	}
}
