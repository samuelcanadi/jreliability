package de.cs12.reliability.javabdd;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.javabdd.BDDFactory;
import net.sf.javabdd.JFactory;
import de.cs12.reliability.bdd.BDD;
import de.cs12.reliability.bdd.BDDProvider;

/**
 * The {@code JBDDProvider} used to get {@code JBDD} BDDs. 
 * 
 * @author glass, reimann
 * @param <T>
 *            the type of the variables
 */
public class JBDDProvider<T> implements BDDProvider<T> {
	private int variableOffset = 0;

	BDDFactory bddFactory;

	Map<T, Integer> variableToInt = new HashMap<T, Integer>();
	Map<Integer, T> intToVariable = new HashMap<Integer, T>();
	int vars;

	private boolean factoryInit = false;

	/**
	 * Constructs a {@code JDDProvider} with a given number of variables.
	 * 
	 * @param vars
	 *            the number of variables
	 */
	public JBDDProvider(int vars) {
		if (!factoryInit) {
			factoryInit = true;
			bddFactory = JFactory.init(1000000, 200000);
			bddFactory.setVarNum(vars);
			this.vars = vars;
			bddFactory.autoReorder(BDDFactory.REORDER_SIFT);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#add(java.util.List)
	 */
	public void add(List<T> variables) {
		for (T variable : variables) {
			variableToInt.put(variable, variableOffset);
			intToVariable.put(variableOffset, variable);
			variableOffset++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#add(T[])
	 */
	public void add(T... variables) {
		add(Arrays.asList(variables));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#zero()
	 */
	public JBDD<T> zero() {
		return new JBDD<T>(this, bddFactory.zero());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#one()
	 */
	public JBDD<T> one() {
		return new JBDD<T>(this, bddFactory.one());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#get(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public BDD<T> get(T variable) {
		if (!variableToInt.containsKey(variable)) {
			add(variable);
		}
		
		if(vars < variableToInt.size()){
			vars *= 2;
			bddFactory.setVarNum(vars);
		}
		
		return new JBDD<T>(this, bddFactory.ithVar(variableToInt.get(variable)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.bdd.BDDProvider#get(de.cs12.reliability.bdd.BDD)
	 */
	public T get(BDD<T> bdd) {
		return intToVariable.get(((JBDD<T>) bdd).bdd.var());
	}
}
