/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.vulcan.message.json;

import aQute.bnd.annotation.ProviderType;

import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Creates JSON objects. Instances of this interface should be used to write a
 * complete path in each call.
 *
 * <p>
 * For example, this {@code nestedField} call produces the JSON object that
 * follows it:
 * </p>
 *
 * <p>
 * <pre>
 * {@code
 * jsonObjectBuilder.nestedField("object", "inner", "other").value(42);
 * { "object": { "inner": { "other": 42 } } }
 * }
 * </pre>
 * </p>
 *
 * <p>
 * {@code JSONObjectBuilder} is incremental, so additional calls add paths to
 * previous calls, respecting the previous state. For example, this {@code
 * nestedField} call adds to the preceding one:
 * </p>
 *
 * <p>
 * <pre>
 * {@code
 * jsonObjectBuilder.nestedField("object", "inner","another").value("Hello World!");
 * { "object": { "inner": { "another": "Hello World!", "other": 42 } } }
 * }
 * </pre>
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
@ProviderType
@SuppressWarnings("unused")
public interface JSONObjectBuilder {

	/**
	 * Returns the JSON object constructed by the JSON object builder.
	 *
	 * @return the JSON object
	 */
	public JsonObject build();

	/**
	 * Begins creating a field inside the JSON object.
	 *
	 * @param  name the field's name
	 * @return the builder's next step
	 */
	public FieldStep field(String name);

	/**
	 * Conditionally begins creating a field inside the JSON object. If the
	 * condition is met, this method returns the field step that {@code
	 * ifFunction} creates. Otherwise, this method returns the field step that
	 * {@code elseFunction} creates.
	 *
	 * @param  condition the condition to check
	 * @param  ifFunction the function that creates the field step if the
	 *         condition is {@code <code>true</code>}
	 * @param  elseFunction the function that creates the field step if the
	 *         condition is {@code <code>false</code>}
	 * @return the builder's field step
	 */
	public FieldStep ifElseCondition(
		boolean condition, Function<JSONObjectBuilder, FieldStep> ifFunction,
		Function<JSONObjectBuilder, FieldStep> elseFunction);

	/**
	 * Begins creating a nested field inside the JSON object.
	 *
	 * @param  parentName the parent field's name
	 * @param  nestedNames the nested field's list of names
	 * @return the builder's field step
	 */
	public FieldStep nestedField(String parentName, String... nestedNames);

	/**
	 * Begins creating a nested field inside the JSON object, adding a prefix to
	 * each level.
	 *
	 * <p>
	 * For example, the following {@code nestedPrefixedField} call produces the
	 * JSON object that follows it:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * {@code
	 * jsonObjectBuilder.nestedPrefixedField("prefix", "first", "second").value(42);
	 * {"prefix": { "first": { "prefix": { "second": 42 } } } }
	 * }
	 * </pre>
	 * </p>
	 *
	 * @param  prefix each field's prefix
	 * @param  parentName the parent field's name
	 * @param  nestedNames the list of the nested field names
	 * @return the builder's field step
	 */
	public FieldStep nestedPrefixedField(
		String prefix, String parentName, String... nestedNames);

	/**
	 * Begins creating a nested field inside the JSON object, adding a suffix to
	 * each level.
	 *
	 * <p>
	 * For example, the following {@code nestedSuffixedField} call produces the
	 * JSON object that follows it:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * {@code jsonObjectBuilder.nestedSuffixedField("suffix", "first", "second").value(42);}
	 * { "first": { "suffix": { "second": { "suffix": 42 } } } }
	 * }
	 * </pre>
	 * </p>
	 *
	 * @param  suffix each field's suffix
	 * @param  parentName the parent field's name
	 * @param  nestedNames the list of the nested field names
	 * @return the builder's field step
	 */
	public FieldStep nestedSuffixedField(
		String suffix, String parentName, String... nestedNames);

	/**
	 * Defines the step that adds the value of a field as a JSON array.
	 */
	public interface ArrayValueStep {

		/**
		 * Adds a new JSON object, created by the provided consumer, to the JSON
		 * array.
		 *
		 * @param consumer the consumer that creates the new JSON object
		 */
		public void add(Consumer<JSONObjectBuilder> consumer);

		/**
		 * Adds the JSON object, created by the provided JSON object builder, to
		 * the JSON array.
		 *
		 * @param jsonObjectBuilder the JSON object builder containing the JSON
		 *        object to add to the JSON array
		 */
		public void add(JSONObjectBuilder jsonObjectBuilder);

		/**
		 * Adds all elements of a boolean collection as elements of the JSON
		 * array.
		 *
		 * @param collection the boolean collection to add to the JSON array
		 */
		public void addAllBooleans(Collection<Boolean> collection);

		/**
		 * Adds all elements of a JSON object collection as elements of the JSON
		 * array.
		 *
		 * @param collection the JSON object collection to add to the JSON array
		 */
		public void addAllJsonObjects(Collection<JsonObject> collection);

		/**
		 * Adds all elements of a number collection as elements of the JSON
		 * array.
		 *
		 * @param collection the number collection to add to the JSON array
		 */
		public void addAllNumbers(Collection<Number> collection);

		/**
		 * Adds all elements of a string collection as elements of the JSON
		 * array.
		 *
		 * @param collection the string collection to add to the JSON array
		 */
		public void addAllStrings(Collection<String> collection);

		/**
		 * Adds a new boolean value to the JSON array.
		 *
		 * @param value the boolean value to add to the JSON array
		 */
		public void addBoolean(Boolean value);

		/**
		 * Adds a new number to the JSON array.
		 *
		 * @param value the number to add to the JSON array
		 */
		public void addNumber(Number value);

		/**
		 * Adds a new string to the JSON array.
		 *
		 * @param value the string to add to the JSON array
		 */
		public void addString(String value);

	}

	/**
	 * Defines the step to add the value of a field. The step can be another
	 * JSON object (field methods), a JSON array ({@link #arrayValue()}), or a
	 * primitive value ({@link #stringValue(String)}, {@link
	 * #numberValue(Number)}, or {@link #booleanValue(Boolean)}).
	 */
	public interface FieldStep {

		/**
		 * Begins creating a JSON array inside the field.
		 *
		 * @return the builder's array value step
		 */
		public ArrayValueStep arrayValue();

		/**
		 * Adds a new boolean value to the JSON array.
		 *
		 * @param value the boolean value to add to the JSON array
		 */
		public void booleanValue(Boolean value);

		/**
		 * Begins creating a new JSON object field.
		 *
		 * @param  name the new field's name
		 * @return the builder's field step
		 */
		public FieldStep field(String name);

		/**
		 * Begins creating a new JSON object field, only if a condition is met.
		 * If the condition is met, this method returns the field step created
		 * by {@code ifFunction}. Otherwise, no operation is performed.
		 *
		 * @param  condition the condition to check
		 * @param  ifFunction the function that creates the field step if the
		 *         condition is {@code <code>true</code>}
		 * @return the builder's field step
		 */
		public FieldStep ifCondition(
			boolean condition, Function<FieldStep, FieldStep> ifFunction);

		/**
		 * Begins creating a new JSON object field, where the resulting field
		 * step is conditional. If the condition is met, this method returns the
		 * field step created by {@code ifFunction}. Otherwise, this method
		 * returns the field step created by {@code elseFunction}.
		 *
		 * @param  condition the condition to check
		 * @param  ifFunction the function that creates the field step if the
		 *         condition is {@code <code>true</code>}
		 * @param  elseFunction the function that creates the field step if the
		 *         condition is {@code <code>false</code>}
		 * @return the builder's field step
		 */
		public FieldStep ifElseCondition(
			boolean condition, Function<FieldStep, FieldStep> ifFunction,
			Function<FieldStep, FieldStep> elseFunction);

		/**
		 * Begins creating a new nested JSON object field.
		 *
		 * @param  parentName the parent field's name
		 * @param  nestedNames the list of the nested field names
		 * @return the builder's field step
		 */
		public FieldStep nestedField(String parentName, String... nestedNames);

		/**
		 * Begins creating a new nested JSON object field, adding a prefix to
		 * each field. This method behaves like {@link
		 * JSONObjectBuilder#nestedPrefixedField(String, String, String...)}.
		 *
		 * @param  prefix each field's prefix
		 * @param  parentName the parent field's name
		 * @param  nestedNames the list of the nested field names
		 * @return the builder's field step
		 */
		public FieldStep nestedPrefixedField(
			String prefix, String parentName, String... nestedNames);

		/**
		 * Begins creating a new nested JSON object field, adding a suffix to
		 * each field. This method behaves like {@link
		 * JSONObjectBuilder#nestedSuffixedField(String, String, String...)}.
		 *
		 * @param  suffix each field's suffix
		 * @param  parentName the parent field's name
		 * @param  nestedNames the list of the nested field names
		 * @return the builder's field step
		 */
		public FieldStep nestedSuffixedField(
			String suffix, String parentName, String... nestedNames);

		/**
		 * Adds a new number to the JSON array.
		 *
		 * @param value the number to add to the JSON array
		 */
		public void numberValue(Number value);

		/**
		 * Adds a new string to the JSON array.
		 *
		 * @param value the string to add to the JSON array
		 */
		public void stringValue(String value);

	}

}