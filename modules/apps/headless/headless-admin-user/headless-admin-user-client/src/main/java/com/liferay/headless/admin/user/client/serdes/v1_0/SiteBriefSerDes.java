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

package com.liferay.headless.admin.user.client.serdes.v1_0;

import com.liferay.headless.admin.user.client.dto.v1_0.SiteBrief;
import com.liferay.headless.admin.user.client.json.BaseJSONParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class SiteBriefSerDes {

	public static SiteBrief toDTO(String json) {
		SiteBriefJSONParser siteBriefJSONParser = new SiteBriefJSONParser();

		return siteBriefJSONParser.parseToDTO(json);
	}

	public static SiteBrief[] toDTOs(String json) {
		SiteBriefJSONParser siteBriefJSONParser = new SiteBriefJSONParser();

		return siteBriefJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SiteBrief siteBrief) {
		if (siteBrief == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (siteBrief.getId() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"id\": ");

			sb.append(siteBrief.getId());
		}

		if (siteBrief.getName() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"name\": ");

			sb.append("\"");

			sb.append(_escape(siteBrief.getName()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, String> toMap(SiteBrief siteBrief) {
		if (siteBrief == null) {
			return null;
		}

		Map<String, String> map = new HashMap<>();

		if (siteBrief.getId() == null) {
			map.put("id", null);
		}
		else {
			map.put("id", String.valueOf(siteBrief.getId()));
		}

		if (siteBrief.getName() == null) {
			map.put("name", null);
		}
		else {
			map.put("name", String.valueOf(siteBrief.getName()));
		}

		return map;
	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		return string.replaceAll("\"", "\\\\\"");
	}

	private static class SiteBriefJSONParser extends BaseJSONParser<SiteBrief> {

		@Override
		protected SiteBrief createDTO() {
			return new SiteBrief();
		}

		@Override
		protected SiteBrief[] createDTOArray(int size) {
			return new SiteBrief[size];
		}

		@Override
		protected void setField(
			SiteBrief siteBrief, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "id")) {
				if (jsonParserFieldValue != null) {
					siteBrief.setId(Long.valueOf((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "name")) {
				if (jsonParserFieldValue != null) {
					siteBrief.setName((String)jsonParserFieldValue);
				}
			}
			else {
				throw new IllegalArgumentException(
					"Unsupported field name " + jsonParserFieldName);
			}
		}

	}

}