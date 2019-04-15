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

package com.liferay.talend.dataset;

import com.liferay.talend.data.store.InputDataStore;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Suggestable;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 * @review
 */
@DataSet("RESTDataSet")
@GridLayout({@GridLayout.Row("_inputDataStore"), @GridLayout.Row("_resource")})
public class RESTDataSet implements Serializable {

	public String getEndpoint() {
		return _endpoint;
	}

	public InputDataStore getInputDataStore() {
		return _inputDataStore;
	}

	public String getResource() {
		return _resource;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
	}

	public RESTDataSet setInputDataStore(InputDataStore inputDataStore) {
		_inputDataStore = inputDataStore;

		return this;
	}

	public void setResource(String resource) {
		_resource = resource;
	}

	private String _endpoint;

	@Option
	private InputDataStore _inputDataStore;

	/**
	 * DataStore parameter now is not needed, just an example how we can use it
	 * in UIActionService if needed.
	 */
	@Option
	@Required
	@Suggestable(parameters = "_inputDataStore", value = "fetchEndpoints")
	private String _resource;

}