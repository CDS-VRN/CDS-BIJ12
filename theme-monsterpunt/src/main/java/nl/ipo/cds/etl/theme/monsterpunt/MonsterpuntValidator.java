package nl.ipo.cds.etl.theme.monsterpunt;

import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_DISCONTINUITY;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_EMPTY_MULTIGEOMETRY;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_EXTERIOR_RING_CW;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_INTERIOR_DISCONNECTED;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_INTERIOR_RINGS_TOUCH;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_INTERIOR_RINGS_WITHIN;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_INTERIOR_RING_CCW;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_INTERIOR_RING_TOUCHES_EXTERIOR;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_ONLY_SURFACE_OR_MULTISURFACE;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_POINT_DUPLICATION;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_RING_NOT_CLOSED;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_RING_SELF_INTERSECTION;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_SELF_INTERSECTION;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_SRS_NOT_RD;
import static nl.ipo.cds.etl.theme.monsterpunt.Message.GEOMETRY_SRS_NULL;

import java.util.Map;

import nl.ipo.cds.domain.EtlJob;
import nl.ipo.cds.etl.AbstractValidator;
import nl.ipo.cds.validation.ValidationReporter;
import nl.ipo.cds.validation.Validator;
import nl.ipo.cds.validation.execute.CompilerException;
import nl.ipo.cds.validation.geometry.GeometryExpression;
import nl.ipo.cds.validation.gml.codelists.CodeListFactory;

import org.deegree.geometry.Geometry;

public class MonsterpuntValidator extends AbstractValidator<Monsterpunt, Message, Context> {

	private final GeometryExpression<Message, Context, Geometry> locatie = geometry ("locatie");

	public MonsterpuntValidator(final Map<Object, Object> validatorMessages) throws CompilerException {
		super(Context.class, Monsterpunt.class, validatorMessages);
		compile();
	}

	@Override
	public Context beforeJob(final EtlJob job, final CodeListFactory codeListFactory,
			final ValidationReporter<Message, Context> reporter) {
		return new Context(codeListFactory, reporter);
	}

	public Validator<Message, Context> getDummyValidator () {
		return validate (constant (true));
	}

	public Validator<Message, Context> getLocatieValidator () {
		return validate (
		    ifExp(
		    	locatie.isNull (),
	    		constant (true),
				and (
					// The following validations short-circuit, there must be a non-empty, Surface geometry:
					validate (not (locatie.isEmptyMultiGeometry())).message (GEOMETRY_EMPTY_MULTIGEOMETRY),
					// Non short-circuited validations:
					and (
						// Short circuit to prevent the interiorDisconnected validation if
						// any of the other validations fail:
						and (
							and (
								validate (not (locatie.hasCurveDuplicatePoint ())).message (GEOMETRY_POINT_DUPLICATION, lastLocation ()),
								validate (not (locatie.hasCurveDiscontinuity ())).message (GEOMETRY_DISCONTINUITY),
								validate (not (locatie.hasCurveSelfIntersection ())).message (GEOMETRY_SELF_INTERSECTION, lastLocation ()),
								validate (not (locatie.hasUnclosedRing ())).message (GEOMETRY_RING_NOT_CLOSED),
								validate (not (locatie.hasRingSelfIntersection ())).message (GEOMETRY_RING_SELF_INTERSECTION, lastLocation ()),
								validate (not (locatie.hasTouchingInteriorRings ())).message(GEOMETRY_INTERIOR_RINGS_TOUCH, lastLocation ()),
								validate (not (locatie.hasInteriorRingsWithin ())).message (GEOMETRY_INTERIOR_RINGS_WITHIN)
							),
							validate (not (locatie.isInteriorDisconnected ())).message (GEOMETRY_INTERIOR_DISCONNECTED)
						).shortCircuit (),

						// Non-blocking validations:
						validate (not (locatie.hasExteriorRingCW ())).nonBlocking ().message (GEOMETRY_EXTERIOR_RING_CW),
						validate (not (locatie.hasInteriorRingCCW ())).nonBlocking ().message (GEOMETRY_INTERIOR_RING_CCW),
						validate (not (locatie.hasInteriorRingTouchingExterior ())).nonBlocking ().message (GEOMETRY_INTERIOR_RING_TOUCHES_EXTERIOR, lastLocation ()),
						validate (not (locatie.hasInteriorRingOutsideExterior ())).nonBlocking ().message (GEOMETRY_DISCONTINUITY),

						// SRS validations:
						and (
						    validate (locatie.hasSrs ()).message (GEOMETRY_SRS_NULL),
						    validate (locatie.isSrs (constant ("28992"))).message (GEOMETRY_SRS_NOT_RD, locatie.srsName ())
						).shortCircuit()
					)
				).shortCircuit ()
			)
		);
	}

}
