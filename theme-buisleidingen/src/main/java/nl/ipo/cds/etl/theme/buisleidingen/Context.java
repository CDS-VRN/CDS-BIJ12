package nl.ipo.cds.etl.theme.buisleidingen;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nl.ipo.cds.validation.DefaultValidatorContext;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

public class Context extends DefaultValidatorContext<Message, Context> {
	private final Set<String> ids = new HashSet<> ();
	private final Map<String, String> transportrouteNamen = new HashMap<> ();
	private final Set<String> allTransportrouteIds;
	private String risicokaartMedewerkerNaam = null;
	
	public Context (final CodeListFactory codeListFactory, final ValidationReporter<Message, Context> reporter) {
		super (codeListFactory, reporter);
		
		this.allTransportrouteIds = Collections.emptySet ();
	}
	
	public Context (final CodeListFactory codeListFactory, final ValidationReporter<Message, Context> reporter, final Transportroutes transportroutes) {
		super (codeListFactory, reporter);
		
		this.allTransportrouteIds = transportroutes != null ? transportroutes.getTransportrouteIds () : Collections.<String>emptySet ();
	}

	public boolean hasId (final String id) {
		return ids.contains (id);
	}
	
	public void addId (final String id) {
		ids.add (id);
	}
	
	public String getTransportrouteNaam (final String transportrouteId) {
		return transportrouteNamen.get (transportrouteId);
	}
	
	public void addTransportrouteNaam (final String transportrouteId, final String transportrouteNaam) {
		transportrouteNamen.put (transportrouteId, transportrouteNaam);
	}

	public boolean hasTransportroute (final String transportrouteId) {
		return allTransportrouteIds.contains (transportrouteId);
	}
	
	public boolean isMedewerkerNaamConstant (final String name) {
		if (risicokaartMedewerkerNaam == null) {
			risicokaartMedewerkerNaam = name;
			return true;
		}
		
		return risicokaartMedewerkerNaam.equals (name);
	}
}
