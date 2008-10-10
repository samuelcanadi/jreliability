package de.cs12.reliability.test;

import de.cs12.reliability.javabdd.CAL;
import de.cs12.reliability.javabdd.CALProviderFactory;

/**
 * 
 * The {@code CALProviderTest} is the {@code AbstractBDDProviderTest} for the
 * {@link CAL}.
 * 
 * @author lukasiewycz
 * 
 */
public class CALProviderTest extends AbstractBDDProviderTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.cs12.reliability.test.AbstractBDDProviderTest#init()
	 */
	public void init() {
		this.factory = new CALProviderFactory();
	}

}
