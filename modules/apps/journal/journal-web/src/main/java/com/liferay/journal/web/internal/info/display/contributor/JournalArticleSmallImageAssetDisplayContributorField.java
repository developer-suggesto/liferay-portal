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

package com.liferay.journal.web.internal.info.display.contributor;

import com.liferay.info.display.contributor.BaseInfoDisplayContributorField;
import com.liferay.info.display.contributor.InfoDisplayContributorField;
import com.liferay.info.display.contributor.InfoDisplayContributorFieldType;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = InfoDisplayContributorField.class
)
public class JournalArticleSmallImageAssetDisplayContributorField
	extends BaseInfoDisplayContributorField<JournalArticle> {

	@Override
	public String getKey() {
		return "smallImage";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, "com.liferay.journal.lang");

		return LanguageUtil.get(resourceBundle, "small-image");
	}

	@Override
	public InfoDisplayContributorFieldType getType() {
		return InfoDisplayContributorFieldType.IMAGE;
	}

	@Override
	public Object getValue(JournalArticle article, Locale locale) {
		ThemeDisplay themeDisplay = getThemeDisplay();

		if (themeDisplay != null) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("url", article.getArticleImageURL(themeDisplay));

			return jsonObject;
		}

		return StringPool.BLANK;
	}

}