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

package com.liferay.bulk.rest.client.serdes.v1_0;

import com.liferay.bulk.rest.client.dto.v1_0.TaxonomyCategory;
import com.liferay.bulk.rest.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
public class TaxonomyCategorySerDes {

	public static TaxonomyCategory toDTO(String json) {
		TaxonomyCategoryJSONParser taxonomyCategoryJSONParser =
			new TaxonomyCategoryJSONParser();

		return taxonomyCategoryJSONParser.parseToDTO(json);
	}

	public static TaxonomyCategory[] toDTOs(String json) {
		TaxonomyCategoryJSONParser taxonomyCategoryJSONParser =
			new TaxonomyCategoryJSONParser();

		return taxonomyCategoryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(TaxonomyCategory taxonomyCategory) {
		if (taxonomyCategory == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (taxonomyCategory.getTaxonomyCategoryId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryId\": ");

			sb.append(taxonomyCategory.getTaxonomyCategoryId());
		}

		if (taxonomyCategory.getTaxonomyCategoryName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"taxonomyCategoryName\": ");

			sb.append("\"");

			sb.append(_escape(taxonomyCategory.getTaxonomyCategoryName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(TaxonomyCategory taxonomyCategory) {
		if (taxonomyCategory == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (taxonomyCategory.getTaxonomyCategoryId() == null) {
			map.put("taxonomyCategoryId", null);
		}
		else {
			map.put(
				"taxonomyCategoryId",
				String.valueOf(taxonomyCategory.getTaxonomyCategoryId()));
		}

		if (taxonomyCategory.getTaxonomyCategoryName() == null) {
			map.put("taxonomyCategoryName", null);
		}
		else {
			map.put(
				"taxonomyCategoryName",
				String.valueOf(taxonomyCategory.getTaxonomyCategoryName()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class TaxonomyCategoryJSONParser
		extends BaseJSONParser<TaxonomyCategory> {

		@Override
		protected TaxonomyCategory createDTO() {
			return new TaxonomyCategory();
		}

		@Override
		protected TaxonomyCategory[] createDTOArray(int size) {
			return new TaxonomyCategory[size];
		}

		@Override
		protected void setField(
			TaxonomyCategory taxonomyCategory, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "taxonomyCategoryId")) {
				if (jsonParserFieldValue != null) {
					taxonomyCategory.setTaxonomyCategoryId(
						Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(
						jsonParserFieldName, "taxonomyCategoryName")) {

				if (jsonParserFieldValue != null) {
					taxonomyCategory.setTaxonomyCategoryName(
						(String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}