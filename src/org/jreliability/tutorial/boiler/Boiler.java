package org.jreliability.tutorial.boiler;

import java.util.ArrayList;
import java.util.List;

import org.jreliability.bdd.BDDProvider;
import org.jreliability.bdd.BDDProviderFactory;
import org.jreliability.bdd.BDDTTRF;
import org.jreliability.bdd.javabdd.JBDDProviderFactory;
import org.jreliability.booleanfunction.Term;
import org.jreliability.booleanfunction.common.ANDTerm;
import org.jreliability.booleanfunction.common.LiteralTerm;
import org.jreliability.booleanfunction.common.ORTerm;
import org.jreliability.function.ReliabilityFunction;

/**
 * The {@code Boiler} models a boiler that is responsible for keeping the water
 * in a tank at the desired temperature and pumping it to a destination if
 * needed.
 * <p>
 * The {@code Boiler} consists of two {@code Sensors} that measure the water
 * temperature, a {@code Controller} that activates and deactivates a {@code
 * Heater} to control the water temperature as well as it activates and
 * deactivates one of two available {@code Pumps} to pump the water to its
 * destination if needed.
 * <p>
 * The non-minimized boolean function that describes whether this system works
 * correctly (evaluates to {@code 1}) or fails (evaluates to {@code 0}) is as
 * follows:
 * <p>
 * {@code ((Sensor1 AND Sensor2) AND Controller) AND (Controller AND Heater) AND
 * (Controller AND (Pump1 OR Pump2))}
 * 
 * @author glass
 * 
 */
public class Boiler {

	/**
	 * The first temperature sensor.
	 */
	protected Sensor sensor1 = new Sensor("Sensor1");
	/**
	 * The second temperature sensor.
	 */
	protected Sensor sensor2 = new Sensor("Sensor2");
	/**
	 * The controller.
	 */
	protected Controller controller = new Controller("Controller");
	/**
	 * The water heater.
	 */
	protected Heater heater = new Heater("Heater");
	/**
	 * The first pump.
	 */
	protected Pump pump1 = new Pump("Pump1");
	/**
	 * The second pump.
	 */
	protected Pump pump2 = new Pump("Pump2");

	/**
	 * All components that are included in the {@code Boiler}.
	 */
	protected List<BoilerComponent> components = new ArrayList<BoilerComponent>();
	/**
	 * The used {@code FunctionTransformer}.
	 */
	protected BoilerTransformer transformer;

	/**
	 * Constructs a {@code Boiler}.
	 * 
	 */
	public Boiler() {
		super();
		initialize();
		transformer = new BoilerTransformer(this);
	}

	/**
	 * Initializes the list of components of the {@code Boiler}.
	 */
	private void initialize() {
		components.add(sensor1);
		components.add(sensor2);
		components.add(controller);
		components.add(heater);
		components.add(pump1);
		components.add(pump2);
	}

	/**
	 * Returns a model of the {@code Boiler} as a {@code Term}.
	 * 
	 * @return the term representation of the boiler
	 */
	public Term getTerm() {
		LiteralTerm<BoilerComponent> sensor1Literal = new LiteralTerm<BoilerComponent>(sensor1);
		LiteralTerm<BoilerComponent> sensor2Literal = new LiteralTerm<BoilerComponent>(sensor2);
		LiteralTerm<BoilerComponent> controllerLiteral = new LiteralTerm<BoilerComponent>(controller);
		LiteralTerm<BoilerComponent> heaterLiteral = new LiteralTerm<BoilerComponent>(heater);
		LiteralTerm<BoilerComponent> pump1Literal = new LiteralTerm<BoilerComponent>(pump1);
		LiteralTerm<BoilerComponent> pump2Literal = new LiteralTerm<BoilerComponent>(pump2);

		ANDTerm sensorSubSystem = new ANDTerm();
		sensorSubSystem.add(sensor1Literal);
		sensorSubSystem.add(sensor2Literal);

		ANDTerm sensorControlSubSystem = new ANDTerm();
		sensorControlSubSystem.add(sensorSubSystem);
		sensorControlSubSystem.add(controllerLiteral);

		ANDTerm heatingSubSystem = new ANDTerm();
		heatingSubSystem.add(heaterLiteral);
		heatingSubSystem.add(controllerLiteral);

		ORTerm pumpSubSystem = new ORTerm();
		pumpSubSystem.add(pump1Literal);
		pumpSubSystem.add(pump2Literal);

		ANDTerm pumpControlSubSystem = new ANDTerm();
		pumpControlSubSystem.add(pumpSubSystem);
		pumpControlSubSystem.add(controllerLiteral);

		ANDTerm system = new ANDTerm();
		system.add(sensorControlSubSystem);
		system.add(heatingSubSystem);
		system.add(pumpControlSubSystem);

		return system;
	}

	/**
	 * Returns the {@code ReliabilityFunction} that represents the
	 * {@code Boiler}.
	 * 
	 * @return the reliability function of the boiler
	 */
	public ReliabilityFunction get() {
		Term term = getTerm();
		BDDProviderFactory bddProviderFactory = new JBDDProviderFactory();
		BDDProvider<BoilerComponent> bddProvider = bddProviderFactory.getProvider();
		BDDTTRF<BoilerComponent> bddTTRF = new BDDTTRF<BoilerComponent>(bddProvider);
		return bddTTRF.convert(term, transformer);
	}

	/**
	 * Returns the components of the {@code Boiler}.
	 * 
	 * @return the components
	 */
	public List<BoilerComponent> getComponents() {
		return components;
	}

	/**
	 * Returns the {@code BoilerTransformer}.
	 * 
	 * @return the transformer
	 */
	public BoilerTransformer getTransformer() {
		return transformer;
	}

}
