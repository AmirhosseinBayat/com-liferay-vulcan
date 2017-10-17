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

package com.liferay.vulcan.sample.liferay.portal;

import static org.mockito.Matchers.any;

import com.liferay.vulcan.consumer.TriConsumer;
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.wiring.osgi.internal.resource.builder.RepresentorBuilderImpl;

import java.util.function.Function;

import org.junit.Before;

import org.mockito.Mockito;

/**
 * @author Javier Gamarra
 */
public class CollectionResourceTest {

	@Before
	public void setUp() {
		RepresentorBuilderImpl representorBuilderImpl =
			new RepresentorBuilderImpl(
				null, Mockito.mock(TriConsumer.class), null);

		representorBuilderSpy = Mockito.spy(representorBuilderImpl);

		_identifierSpy = Mockito.spy(representorBuilderImpl.identifier(null));

		Mockito.when(
			representorBuilderSpy.identifier(any(Function.class))
		).thenReturn(
			_identifierSpy
		);
	}

	protected RepresentorBuilder.FirstStep verifyIdentifier() {
		return Mockito.verify(_identifierSpy);
	}

	protected RepresentorBuilderImpl representorBuilderSpy;

	private RepresentorBuilder.FirstStep _identifierSpy;

}