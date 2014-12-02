package nl.ipo.cds.etl.theme.riscisor;

import java.util.HashSet;
import java.util.Set;

import nl.ipo.cds.validation.DefaultValidatorContext;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

public class Context extends DefaultValidatorContext<Message, Context> {

	private final Set<String> ids = new HashSet<> ();
	private String risicokaartMedewerkerNaam = null;
	
	public Context (
			final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter) {
		super (codeListFactory, reporter);
	}
	
	public boolean hasId (final String id) {
		return ids.contains (id);
	}
	
	public void addId (final String id) {
		ids.add (id);
	}
	
	public boolean isMedewerkerNaamConstant (final String name) {
		if (risicokaartMedewerkerNaam == null) {
			risicokaartMedewerkerNaam = name;
			return true;
		}
		
		return risicokaartMedewerkerNaam.equals (name);
	}
}
