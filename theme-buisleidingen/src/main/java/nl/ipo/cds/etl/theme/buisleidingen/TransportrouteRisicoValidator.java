package nl.ipo.cds.etl.theme.buisleidingen;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.validation.AttributeExpression;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.callbacks.Callback;
import nl.ipo.cds.validation.callbacks.UnaryCallback;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.geometry.GeometryExpression;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

import org.deegree.geometry.Geometry;
import org.deegree.geometry.multi.MultiPolygon;
import org.deegree.geometry.multi.MultiSurface;
import org.deegree.geometry.primitive.Polygon;
import org.deegree.geometry.primitive.Surface;

public class TransportrouteRisicoValidator extends AbstractBuisleidingenValidator<TransportrouteRisico> {

	private final Transportroutes transportroutes;
	
	public TransportrouteRisicoValidator(final Map<Object, Object> validatorMessages, final Transportroutes transportroutes) throws CompilerException {
		super (TransportrouteRisico.class, validatorMessages);
	
		this.transportroutes = transportroutes;
		
		compile ();
	}
	
	@Override
	public Context beforeJob (final EtlJob job, final CodeListFactory codeListFactory, final ValidationReporter<Message, Context> reporter) {
		return new Context (codeListFactory, reporter, transportroutes);
	}
	
	// =========================================================================
	// Validations:
	// =========================================================================
	private final AttributeExpression<Message, Context, String> transportrouteId = stringAttr ("transportrouteId");
	private final GeometryExpression<Message, Context, Geometry> geom = geometry ("risicocontour", Geometry.class);
	private final AttributeExpression<Message, Context, Timestamp> laatsteMutatiedatumtijd = attribute ("laatsteMutatiedatumtijd", Timestamp.class);
	
	public Validator<Message, Context> getTransportrouteIdExistsValidator () {
		final UnaryCallback<Message, Context, Boolean, String> callback = new UnaryCallback<Message, Context, Boolean, String> () {
			@Override
			public Boolean call (final String input, final Context context) throws Exception {
				return context.hasTransportroute (input);
			}
		};
		
		return validate (callback (Boolean.class, transportrouteId, callback)).message (Message.RISICOCONTOUR_TRANSPORTROUTE_NOT_FOUND);
	}
	
	public Validator<Message, Context> getLaatsteMutatiedatumtijdValidator () {
		final Callback<Message, Context, Date> now = new Callback<Message, Context, Date> () {
			@Override
			public Date call (final Context context) throws Exception {
				return new Date ();
			}
		};
		
		return validate (
				and (
					validate (not (laatsteMutatiedatumtijd.isNull ())).message (Message.RISICOCONTOUR_LAATSTE_MUTATIEDATUM_NULL),
					validate (lte (laatsteMutatiedatumtijd.as (Date.class), callback (Date.class, now))).message (Message.RISICOCONTOUR_LAATSTE_MUTATIEDATUM_FUTURE, laatsteMutatiedatumtijd)
				).shortCircuit ()
			);
	}
	
	public Validator<Message, Context> getRisicocontourValidator () {
		return validate (
				and (
					validate (not (geom.isNull ())).message (Message.RISICOCONTOUR_NULL),
					and (
						// The following validations short-circuit, there must be a non-null and non-empty geometry:
						validate (not (geom.isEmptyMultiGeometry ())).message (Message.RISICOCONTOUR_EMPTY),
						validate (or (geom.is (MultiPolygon.class), geom.is (Polygon.class), geom.is (MultiSurface.class), geom.is (Surface.class))).message (Message.RISICOCONTOUR_NOT_MULTIPOLYGON),
						
						// Non short-circuited validations:
						and (
							// Short circuit to prevent the interiorDisconnected validation if
							// any of the other validations fail:
							and (
								and (
									validate (not (geom.hasCurveDuplicatePoint ())).message (Message.RISICOCONTOUR_POINT_DUPLICATION, lastLocation ()),
									validate (not (geom.hasCurveDiscontinuity ())).message (Message.RISICOCONTOUR_DISCONTINUITY),
									validate (not (geom.hasCurveSelfIntersection ())).message (Message.RISICOCONTOUR_SELF_INTERSECTION, lastLocation ()),
									validate (not (geom.hasUnclosedRing ())).message (Message.RISICOCONTOUR_RING_NOT_CLOSED),
									validate (not (geom.hasRingSelfIntersection ())).message (Message.RISICOCONTOUR_RING_SELF_INTERSECTION, lastLocation ()),
									validate (not (geom.hasTouchingInteriorRings ())).message(Message.RISICOCONTOUR_INTERIOR_RINGS_TOUCH, lastLocation ()),
									validate (not (geom.hasInteriorRingsWithin ())).message (Message.RISICOCONTOUR_INTERIOR_RINGS_WITHIN)
								),
								validate (not (geom.isInteriorDisconnected ())).message (Message.RISICOCONTOUR_INTERIOR_DISCONNECTED)
							).shortCircuit (),

							// Non-blocking validations:
							validate (not (geom.hasExteriorRingCW ())).nonBlocking ().message (Message.RISICOCONTOUR_EXTERIOR_RING_CW),
							validate (not (geom.hasInteriorRingCCW ())).nonBlocking ().message (Message.RISICOCONTOUR_INTERIOR_RING_CCW),
							validate (not (geom.hasInteriorRingTouchingExterior ())).nonBlocking ().message (Message.RISICOCONTOUR_INTERIOR_RING_TOUCHES_EXTERIOR, lastLocation ()),
							validate (not (geom.hasInteriorRingOutsideExterior ())).nonBlocking ().message (Message.RISICOCONTOUR_INTERIOR_RING_OUTSIDE_EXTERIOR),
							
							// SRS validations:
							and (
								validate (geom.hasSrs ()).message (Message.RISICOCONTOUR_SRS_NULL),
								validate (geom.isSrs (constant ("28992"))).message (Message.RISICOCONTOUR_SRS_NOT_RD, geom.srsName ())
							).shortCircuit ()
						)
					).shortCircuit ()
				).shortCircuit ()
			);
	}
}
