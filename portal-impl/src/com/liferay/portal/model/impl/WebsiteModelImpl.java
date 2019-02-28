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

package com.liferay.portal.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.model.WebsiteModel;
import com.liferay.portal.kernel.model.WebsiteSoap;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the Website service. Represents a row in the &quot;Website&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>WebsiteModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link WebsiteImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WebsiteImpl
 * @generated
 */
@JSON(strict = true)
@ProviderType
public class WebsiteModelImpl
	extends BaseModelImpl<Website> implements WebsiteModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a website model instance should use the <code>Website</code> interface instead.
	 */
	public static final String TABLE_NAME = "Website";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"websiteId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"url", Types.VARCHAR}, {"typeId", Types.BIGINT},
		{"primary_", Types.BOOLEAN}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("websiteId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("url", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("typeId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("primary_", Types.BOOLEAN);
		TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table Website (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,websiteId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,url STRING null,typeId LONG,primary_ BOOLEAN,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table Website";

	public static final String ORDER_BY_JPQL =
		" ORDER BY website.createDate ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY Website.createDate ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.Website"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.Website"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.Website"),
		true);

	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	public static final long COMPANYID_COLUMN_BITMASK = 4L;

	public static final long PRIMARY_COLUMN_BITMASK = 8L;

	public static final long USERID_COLUMN_BITMASK = 16L;

	public static final long UUID_COLUMN_BITMASK = 32L;

	public static final long CREATEDATE_COLUMN_BITMASK = 64L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static Website toModel(WebsiteSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		Website model = new WebsiteImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setUuid(soapModel.getUuid());
		model.setWebsiteId(soapModel.getWebsiteId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setUrl(soapModel.getUrl());
		model.setTypeId(soapModel.getTypeId());
		model.setPrimary(soapModel.isPrimary());
		model.setLastPublishDate(soapModel.getLastPublishDate());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<Website> toModels(WebsiteSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<Website> models = new ArrayList<Website>(soapModels.length);

		for (WebsiteSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.Website"));

	public WebsiteModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _websiteId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setWebsiteId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _websiteId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return Website.class;
	}

	@Override
	public String getModelClassName() {
		return Website.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<Website, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<Website, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Website, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName, attributeGetterFunction.apply((Website)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<Website, Object>> attributeSetterBiConsumers =
			getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<Website, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(Website)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<Website, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<Website, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<Website, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<Website, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<Website, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<Website, Object>>();
		Map<String, BiConsumer<Website, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<Website, ?>>();

		attributeGetterFunctions.put("mvccVersion", Website::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion", (BiConsumer<Website, Long>)Website::setMvccVersion);
		attributeGetterFunctions.put("uuid", Website::getUuid);
		attributeSetterBiConsumers.put(
			"uuid", (BiConsumer<Website, String>)Website::setUuid);
		attributeGetterFunctions.put("websiteId", Website::getWebsiteId);
		attributeSetterBiConsumers.put(
			"websiteId", (BiConsumer<Website, Long>)Website::setWebsiteId);
		attributeGetterFunctions.put("companyId", Website::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId", (BiConsumer<Website, Long>)Website::setCompanyId);
		attributeGetterFunctions.put("userId", Website::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<Website, Long>)Website::setUserId);
		attributeGetterFunctions.put("userName", Website::getUserName);
		attributeSetterBiConsumers.put(
			"userName", (BiConsumer<Website, String>)Website::setUserName);
		attributeGetterFunctions.put("createDate", Website::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate", (BiConsumer<Website, Date>)Website::setCreateDate);
		attributeGetterFunctions.put("modifiedDate", Website::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<Website, Date>)Website::setModifiedDate);
		attributeGetterFunctions.put("classNameId", Website::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId", (BiConsumer<Website, Long>)Website::setClassNameId);
		attributeGetterFunctions.put("classPK", Website::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK", (BiConsumer<Website, Long>)Website::setClassPK);
		attributeGetterFunctions.put("url", Website::getUrl);
		attributeSetterBiConsumers.put(
			"url", (BiConsumer<Website, String>)Website::setUrl);
		attributeGetterFunctions.put("typeId", Website::getTypeId);
		attributeSetterBiConsumers.put(
			"typeId", (BiConsumer<Website, Long>)Website::setTypeId);
		attributeGetterFunctions.put("primary", Website::getPrimary);
		attributeSetterBiConsumers.put(
			"primary", (BiConsumer<Website, Boolean>)Website::setPrimary);
		attributeGetterFunctions.put(
			"lastPublishDate", Website::getLastPublishDate);
		attributeSetterBiConsumers.put(
			"lastPublishDate",
			(BiConsumer<Website, Date>)Website::setLastPublishDate);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public String getUuid() {
		if (_uuid == null) {
			return "";
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		_columnBitmask |= UUID_COLUMN_BITMASK;

		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@JSON
	@Override
	public long getWebsiteId() {
		return _websiteId;
	}

	@Override
	public void setWebsiteId(long websiteId) {
		_websiteId = websiteId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getClassName() {
		if (getClassNameId() <= 0) {
			return "";
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	@Override
	public void setClassName(String className) {
		long classNameId = 0;

		if (Validator.isNotNull(className)) {
			classNameId = PortalUtil.getClassNameId(className);
		}

		setClassNameId(classNameId);
	}

	@JSON
	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public void setClassNameId(long classNameId) {
		_columnBitmask |= CLASSNAMEID_COLUMN_BITMASK;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = _classNameId;
		}

		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	@JSON
	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@JSON
	@Override
	public String getUrl() {
		if (_url == null) {
			return "";
		}
		else {
			return _url;
		}
	}

	@Override
	public void setUrl(String url) {
		_url = url;
	}

	@JSON
	@Override
	public long getTypeId() {
		return _typeId;
	}

	@Override
	public void setTypeId(long typeId) {
		_typeId = typeId;
	}

	@JSON
	@Override
	public boolean getPrimary() {
		return _primary;
	}

	@JSON
	@Override
	public boolean isPrimary() {
		return _primary;
	}

	@Override
	public void setPrimary(boolean primary) {
		_columnBitmask |= PRIMARY_COLUMN_BITMASK;

		if (!_setOriginalPrimary) {
			_setOriginalPrimary = true;

			_originalPrimary = _primary;
		}

		_primary = primary;
	}

	public boolean getOriginalPrimary() {
		return _originalPrimary;
	}

	@JSON
	@Override
	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(
			PortalUtil.getClassNameId(Website.class.getName()),
			getClassNameId());
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), Website.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Website toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (Website)ProxyUtil.newProxyInstance(
				_classLoader, _escapedModelInterfaces,
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		WebsiteImpl websiteImpl = new WebsiteImpl();

		websiteImpl.setMvccVersion(getMvccVersion());
		websiteImpl.setUuid(getUuid());
		websiteImpl.setWebsiteId(getWebsiteId());
		websiteImpl.setCompanyId(getCompanyId());
		websiteImpl.setUserId(getUserId());
		websiteImpl.setUserName(getUserName());
		websiteImpl.setCreateDate(getCreateDate());
		websiteImpl.setModifiedDate(getModifiedDate());
		websiteImpl.setClassNameId(getClassNameId());
		websiteImpl.setClassPK(getClassPK());
		websiteImpl.setUrl(getUrl());
		websiteImpl.setTypeId(getTypeId());
		websiteImpl.setPrimary(isPrimary());
		websiteImpl.setLastPublishDate(getLastPublishDate());

		websiteImpl.resetOriginalValues();

		return websiteImpl;
	}

	@Override
	public int compareTo(Website website) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(), website.getCreateDate());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Website)) {
			return false;
		}

		Website website = (Website)obj;

		long primaryKey = website.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		WebsiteModelImpl websiteModelImpl = this;

		websiteModelImpl._originalUuid = websiteModelImpl._uuid;

		websiteModelImpl._originalCompanyId = websiteModelImpl._companyId;

		websiteModelImpl._setOriginalCompanyId = false;

		websiteModelImpl._originalUserId = websiteModelImpl._userId;

		websiteModelImpl._setOriginalUserId = false;

		websiteModelImpl._setModifiedDate = false;

		websiteModelImpl._originalClassNameId = websiteModelImpl._classNameId;

		websiteModelImpl._setOriginalClassNameId = false;

		websiteModelImpl._originalClassPK = websiteModelImpl._classPK;

		websiteModelImpl._setOriginalClassPK = false;

		websiteModelImpl._originalPrimary = websiteModelImpl._primary;

		websiteModelImpl._setOriginalPrimary = false;

		websiteModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Website> toCacheModel() {
		WebsiteCacheModel websiteCacheModel = new WebsiteCacheModel();

		websiteCacheModel.mvccVersion = getMvccVersion();

		websiteCacheModel.uuid = getUuid();

		String uuid = websiteCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			websiteCacheModel.uuid = null;
		}

		websiteCacheModel.websiteId = getWebsiteId();

		websiteCacheModel.companyId = getCompanyId();

		websiteCacheModel.userId = getUserId();

		websiteCacheModel.userName = getUserName();

		String userName = websiteCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			websiteCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			websiteCacheModel.createDate = createDate.getTime();
		}
		else {
			websiteCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			websiteCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			websiteCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		websiteCacheModel.classNameId = getClassNameId();

		websiteCacheModel.classPK = getClassPK();

		websiteCacheModel.url = getUrl();

		String url = websiteCacheModel.url;

		if ((url != null) && (url.length() == 0)) {
			websiteCacheModel.url = null;
		}

		websiteCacheModel.typeId = getTypeId();

		websiteCacheModel.primary = isPrimary();

		Date lastPublishDate = getLastPublishDate();

		if (lastPublishDate != null) {
			websiteCacheModel.lastPublishDate = lastPublishDate.getTime();
		}
		else {
			websiteCacheModel.lastPublishDate = Long.MIN_VALUE;
		}

		return websiteCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<Website, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<Website, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Website, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((Website)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<Website, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<Website, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<Website, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((Website)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader =
		Website.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
		Website.class, ModelWrapper.class
	};

	private long _mvccVersion;
	private String _uuid;
	private String _originalUuid;
	private long _websiteId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _url;
	private long _typeId;
	private boolean _primary;
	private boolean _originalPrimary;
	private boolean _setOriginalPrimary;
	private Date _lastPublishDate;
	private long _columnBitmask;
	private Website _escapedModel;

}