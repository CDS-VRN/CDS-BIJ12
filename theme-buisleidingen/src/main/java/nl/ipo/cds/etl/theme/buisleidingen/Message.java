package nl.ipo.cds.etl.theme.buisleidingen;

import java.util.ArrayList;
import java.util.List;

import nl.idgis.commons.jobexecutor.JobLogger.LogLevel;
import nl.ipo.cds.etl.ValidatorMessageKey;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.Expression;

public enum Message implements ValidatorMessageKey<Message, Context> {
	TRANSPORTROUTE_ID_NULL,				// id
	TRANSPORTROUTE_ID_EMPTY,			// id
	TRANSPORTROUTE_ID_TOO_LONG,			// id, maxLength
	
	TRANSPORTROUTEDEEL_ID_NULL,			// id
	TRANSPORTROUTEDEEL_ID_EMPTY,		// id
	TRANSPORTROUTEDEEL_ID_DUPLICATE,	// id, transportroutedeelId
	TRANSPORTROUTEDEEL_ID_TOO_LONG,		// id, transportroutedeelId, maxLength
	
	RISICOKAART_MEDEWERKER_NAAM_NULL,	// id, transportroutedeelId
	RISICOKAART_MEDEWERKER_NAAM_EMPTY,	// id, transportroutedeelId
	RISICOKAART_MEDEWERKER_NAAM_NOT_CONSTANT,	// id, transportroutedeelId
	
	LAATSTE_MUTATIEDATUM_NULL,			// id, transportroutedeelId
	LAATSTE_MUTATIEDATUM_FUTURE,		// id, transportroutedeelId, laatsteMutatiedatum
	
	TRANSPORTROUTE_NAAM_NULL,			// id, transportroutedeelId
	TRANSPORTROUTE_NAAM_EMPTY,			// id, transportroutedeelId
	TRANSPORTROUTE_NAAM_CHANGED,		// id, transportroutedeelId, transportrouteNaam
	
	BUISLEIDING_TYPE_NULL,				// id, transportroutedeelId
	BUISLEIDING_TYPE_INVALID,			// id, transportroutedeelId, buisleidingType
	
	OMSCHRIJVING_NULL,					// id, transportroutedeelId
	OMSCHRIJVING_EMPTY,					// id, transportroutedeelId
	OMSCHRIJVING_TOO_LONG,				// id, transportroutedeelId, maxLength
	
	NAAM_EIGENAAR_NULL,					// id, transportroutedeelId
	NAAM_EIGENAAR_EMPTY,				// id, transportroutedeelId
	NAAM_EIGENAAR_TOO_LONG,				// id, transportroutedeelId, maxLength
	
	UITWENDIGE_DIAMETER_NULL,			// id, transportroutedeelId
	UITWENDIGE_DIAMETER_INVALID,		// id, transportroutedeelId, uitwendigeDiameter
	
	WAND_DIKTE_NULL,					// id, transportroutedeelId
	WAND_DIKTE_INVALID,					// id, transportroutedeelId, wandDikte
	
	MAXIMALE_WERKDRUK_NULL,				// id, transportroutedeelId
	MAXIMALE_WERKDRUK_INVALID,			// id, transportroutedeelId, wandDikte
	
	GEOMETRIE_NULL,						// id, transportroutedeelId
	GEOMETRIE_NOT_LINESTRING,			// id, transportroutedeelId
	GEOMETRIE_NO_SRS,					// id, transportroutedeelId
	GEOMETRIE_NOT_RD,					// id, transportroutedeelId, srsName
	GEOMETRIE_POINT_DUPLICATION,		// id, transportroutedeelId, lastLocation
	GEOMETRIE_DISCONTINUITY,			// id, transportroutedeelId
	GEOMETRIE_SELF_INTERSECTION(LogLevel.WARNING),		// id, transportroutedeelId, lastLocation
	
	LIGGING_BOVENKANT_NULL,				// id, transportroutedeelId

	MATERIAAL_SOORT_NULL,				// id, transportroutedeelId
	MATERIAAL_SOORT_EMPTY,				// id, transportroutedeelId
	MATERIAAL_SOORT_TOO_LONG,			// id, transportroutedeelId, maxLength
	
	CAS_NR_MAATGEVENDE_STOF_NULL,		// id, transportroutedeelId
	CAS_NR_MAATGEVENDE_STOF_EMPTY,		// id, transportroutedeelId
	CAS_NR_MAATGEVENDE_STOF_INVALID,	// id, transportroutedeelId, casNrMaatgevendeStof
	
	TOELICHTING_MAATGEVENDE_STOF_NULL,	// id, transportroutedeelId
	TOELICHTING_MAATGEVENDE_STOF_EMPTY,	// id, transportroutedeelId
	
	STATUS_NULL,						// id, transportroutedeelId
	STATUS_INVALID,						// id, transportroutedeelId, status
	
	EFFECTAFSTAND_DODELIJK_INVALID,		// id, transportroutedeelId, effectafstandDodelijk
	
	MAATGEVEND_SCENARIO_DODELIJK_INVALID,// id, transportroutedeelId, maatgevendScenarioDodelijk
	
	RISICOCONTOUR_TRANSPORTROUTE_NOT_FOUND, // id, transportrouteId
	RISICOCONTOUR_MISSING,					// list of offending ids.
	
	RISICOCONTOUR_NULL,					// id, transportroutedeelId
	RISICOCONTOUR_IN_M_AND_REDEN,		// id, transportroutedeelId
	RISICOCONTOUR_IN_M_AND_RISICOCONTOUR,// id, transportroutedeelId
	RISICOCONTOUR_IN_M_INVALID,			// id, transportroutedeelId, risicocontourInM
	RISICOCONTOUR_AND_GEEN_REDEN,		// id, transportroutedeelId
	RISICOCONTOUR_EMPTY,
	RISICOCONTOUR_POINT_DUPLICATION,		// id, transportroutedeelId, lastLocation
	RISICOCONTOUR_DISCONTINUITY,		// id, transportroutedeelId
	RISICOCONTOUR_SELF_INTERSECTION,		// id, transportroutedeelId, lastLocation
	RISICOCONTOUR_RING_NOT_CLOSED,		// id, transportroutedeelId
	RISICOCONTOUR_RING_SELF_INTERSECTION,		// id, transportroutedeelId, lastLocation
	RISICOCONTOUR_INTERIOR_RINGS_TOUCH,		// id, transportroutedeelId, lastLocation
	RISICOCONTOUR_INTERIOR_RINGS_WITHIN,		// id, transportroutedeelId
	RISICOCONTOUR_INTERIOR_DISCONNECTED,		// id, transportroutedeelId
	RISICOCONTOUR_EXTERIOR_RING_CW(LogLevel.WARNING),		// id, transportroutedeelId
	RISICOCONTOUR_INTERIOR_RING_CCW,		// id, transportroutedeelId
	RISICOCONTOUR_INTERIOR_RING_TOUCHES_EXTERIOR,		// id, transportroutedeelId, lastLocation
	RISICOCONTOUR_INTERIOR_RING_OUTSIDE_EXTERIOR,		// id, transportroutedeelId
	RISICOCONTOUR_SRS_NULL,		// id, transportroutedeelId
	RISICOCONTOUR_SRS_NOT_RD,		// id, transportroutedeelId, srsName
	GEEN_RISICOCONTOUR_REDEN_EMPTY, // id, transportroutedeelId
	RISICOCONTOUR_NOT_MULTIPOLYGON,	// id, transportroutedeelId
	
	RISICOCONTOUR_LAATSTE_MUTATIEDATUM_NULL,			// id, transportrouteId
	RISICOCONTOUR_LAATSTE_MUTATIEDATUM_FUTURE,		// id, transportrouteId, laatsteMutatiedatum
	
	RISICOCONTOUR_NOT_SET,			// id, transportroutedeelId
	
	HAS_MORE_ERRORS(LogLevel.WARNING)
	;

	private final LogLevel logLevel;
	
	Message () {
		this (LogLevel.ERROR);
	}
	
	Message (final LogLevel logLevel) {
		this.logLevel = logLevel;
	}
	
	@Override
	public boolean isBlocking() {
		return getLogLevel () == LogLevel.ERROR;
	}

	@Override
	public List<Expression<Message, Context, ?>> getMessageParameters() {
			
		final List<Expression<Message, Context, ?>> params = new ArrayList<> ();
		
		// RISICOCONTOUR_MISSING has no default parameters, it is a postcondition:
		if (this.equals (RISICOCONTOUR_MISSING)) {
			return params;
		}
		
		params.add (new AttributeExpression<Message, Context, String> ("id", String.class));
		params.add (new AttributeExpression<Message, Context, String> ("id", String.class));
		if (toString ().contains ("RISICOCONTOUR") 
				|| this == TRANSPORTROUTE_ID_EMPTY 
				|| this == TRANSPORTROUTE_ID_NULL
				|| this == TRANSPORTROUTE_ID_TOO_LONG
				|| this == TRANSPORTROUTEDEEL_ID_EMPTY
				|| this == TRANSPORTROUTEDEEL_ID_NULL) {
			params.add (new AttributeExpression<Message, Context, String> ("transportrouteId", String.class));
		} else {
			params.add (new AttributeExpression<Message, Context, String> ("transportroutedeelId", String.class));
		}
		
		return params;
	}

	@Override
	public int getMaxMessageLog() {
		return 10;
	}

	@Override
	public boolean isAddToShapeFile() {
		return false;
	}

	@Override
	public LogLevel getLogLevel() {
		return logLevel;
	}

	@Override
	public Message getMaxMessageKey() {
		return HAS_MORE_ERRORS;
	}
}