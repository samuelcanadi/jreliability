/**
 * JReliability is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * JReliability is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */
package org.jreliability.function;

import org.jreliability.common.Transformer;

/**
 * The {@code FunctionTransformer} transforms an object {@code T} into a {@code
 * ReliabilityFunction}.
 * 
 * @author glass
 * 
 * @param <T>
 *            the object t
 */
public interface FunctionTransformer<T> extends
		Transformer<T, ReliabilityFunction> {

}
