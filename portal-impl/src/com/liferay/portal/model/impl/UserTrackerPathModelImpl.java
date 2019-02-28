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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.UserTrackerPath;
import com.liferay.portal.kernel.model.UserTrackerPathModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.sql.Types;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the UserTrackerPath service. Represents a row in the &quot;UserTrackerPath&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>UserTrackerPathModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link UserTrackerPathImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserTrackerPathImpl
 * @generated
 */
@ProviderType
public class UserTrackerPathModelImpl
	extends BaseModelImpl<UserTrackerPath> implements UserTrackerPathModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a user tracker path model instance should use the <code>UserTrackerPath</code> interface instead.
	 */
	public static final String TABLE_NAME = "UserTrackerPath";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"userTrackerPathId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userTrackerId", Types.BIGINT},
		{"path_", Types.VARCHAR}, {"pathDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userTrackerPathId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userTrackerId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("path_", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("pathDate", Types.TIMESTAMP);
	}

	public static final String TABLE_SQL_CREATE =
		"create table UserTrackerPath (mvccVersion LONG default 0 not null,userTrackerPathId LONG not null primary key,companyId LONG,userTrackerId LONG,path_ STRING null,pathDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table UserTrackerPath";

	public static final String ORDER_BY_JPQL =
		" ORDER BY userTrackerPath.userTrackerPathId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY UserTrackerPath.userTrackerPathId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.UserTrackerPath"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.UserTrackerPath"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.UserTrackerPath"),
		true);

	public static final long USERTRACKERID_COLUMN_BITMASK = 1L;

	public static final long USERTRACKERPATHID_COLUMN_BITMASK = 2L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.UserTrackerPath"));

	public UserTrackerPathModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _userTrackerPathId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setUserTrackerPathId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _userTrackerPathId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return UserTrackerPath.class;
	}

	@Override
	public String getModelClassName() {
		return UserTrackerPath.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<UserTrackerPath, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<UserTrackerPath, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<UserTrackerPath, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((UserTrackerPath)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<UserTrackerPath, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<UserTrackerPath, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(UserTrackerPath)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<UserTrackerPath, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<UserTrackerPath, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<UserTrackerPath, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<UserTrackerPath, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<UserTrackerPath, Object>>
			attributeGetterFunctions =
				new LinkedHashMap<String, Function<UserTrackerPath, Object>>();
		Map<String, BiConsumer<UserTrackerPath, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<UserTrackerPath, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", UserTrackerPath::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<UserTrackerPath, Long>)UserTrackerPath::setMvccVersion);
		attributeGetterFunctions.put(
			"userTrackerPathId", UserTrackerPath::getUserTrackerPathId);
		attributeSetterBiConsumers.put(
			"userTrackerPathId",
			(BiConsumer<UserTrackerPath, Long>)
				UserTrackerPath::setUserTrackerPathId);
		attributeGetterFunctions.put(
			"companyId", UserTrackerPath::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<UserTrackerPath, Long>)UserTrackerPath::setCompanyId);
		attributeGetterFunctions.put(
			"userTrackerId", UserTrackerPath::getUserTrackerId);
		attributeSetterBiConsumers.put(
			"userTrackerId",
			(BiConsumer<UserTrackerPath, Long>)
				UserTrackerPath::setUserTrackerId);
		attributeGetterFunctions.put("path", UserTrackerPath::getPath);
		attributeSetterBiConsumers.put(
			"path",
			(BiConsumer<UserTrackerPath, String>)UserTrackerPath::setPath);
		attributeGetterFunctions.put("pathDate", UserTrackerPath::getPathDate);
		attributeSetterBiConsumers.put(
			"pathDate",
			(BiConsumer<UserTrackerPath, Date>)UserTrackerPath::setPathDate);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@Override
	public long getUserTrackerPathId() {
		return _userTrackerPathId;
	}

	@Override
	public void setUserTrackerPathId(long userTrackerPathId) {
		_userTrackerPathId = userTrackerPathId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getUserTrackerId() {
		return _userTrackerId;
	}

	@Override
	public void setUserTrackerId(long userTrackerId) {
		_columnBitmask |= USERTRACKERID_COLUMN_BITMASK;

		if (!_setOriginalUserTrackerId) {
			_setOriginalUserTrackerId = true;

			_originalUserTrackerId = _userTrackerId;
		}

		_userTrackerId = userTrackerId;
	}

	public long getOriginalUserTrackerId() {
		return _originalUserTrackerId;
	}

	@Override
	public String getPath() {
		if (_path == null) {
			return "";
		}
		else {
			return _path;
		}
	}

	@Override
	public void setPath(String path) {
		_path = path;
	}

	@Override
	public Date getPathDate() {
		return _pathDate;
	}

	@Override
	public void setPathDate(Date pathDate) {
		_pathDate = pathDate;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), UserTrackerPath.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public UserTrackerPath toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (UserTrackerPath)ProxyUtil.newProxyInstance(
				_classLoader, _escapedModelInterfaces,
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		UserTrackerPathImpl userTrackerPathImpl = new UserTrackerPathImpl();

		userTrackerPathImpl.setMvccVersion(getMvccVersion());
		userTrackerPathImpl.setUserTrackerPathId(getUserTrackerPathId());
		userTrackerPathImpl.setCompanyId(getCompanyId());
		userTrackerPathImpl.setUserTrackerId(getUserTrackerId());
		userTrackerPathImpl.setPath(getPath());
		userTrackerPathImpl.setPathDate(getPathDate());

		userTrackerPathImpl.resetOriginalValues();

		return userTrackerPathImpl;
	}

	@Override
	public int compareTo(UserTrackerPath userTrackerPath) {
		long primaryKey = userTrackerPath.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserTrackerPath)) {
			return false;
		}

		UserTrackerPath userTrackerPath = (UserTrackerPath)obj;

		long primaryKey = userTrackerPath.getPrimaryKey();

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
		UserTrackerPathModelImpl userTrackerPathModelImpl = this;

		userTrackerPathModelImpl._originalUserTrackerId =
			userTrackerPathModelImpl._userTrackerId;

		userTrackerPathModelImpl._setOriginalUserTrackerId = false;

		userTrackerPathModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<UserTrackerPath> toCacheModel() {
		UserTrackerPathCacheModel userTrackerPathCacheModel =
			new UserTrackerPathCacheModel();

		userTrackerPathCacheModel.mvccVersion = getMvccVersion();

		userTrackerPathCacheModel.userTrackerPathId = getUserTrackerPathId();

		userTrackerPathCacheModel.companyId = getCompanyId();

		userTrackerPathCacheModel.userTrackerId = getUserTrackerId();

		userTrackerPathCacheModel.path = getPath();

		String path = userTrackerPathCacheModel.path;

		if ((path != null) && (path.length() == 0)) {
			userTrackerPathCacheModel.path = null;
		}

		Date pathDate = getPathDate();

		if (pathDate != null) {
			userTrackerPathCacheModel.pathDate = pathDate.getTime();
		}
		else {
			userTrackerPathCacheModel.pathDate = Long.MIN_VALUE;
		}

		return userTrackerPathCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<UserTrackerPath, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<UserTrackerPath, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<UserTrackerPath, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((UserTrackerPath)this));
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
		Map<String, Function<UserTrackerPath, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<UserTrackerPath, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<UserTrackerPath, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((UserTrackerPath)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader =
		UserTrackerPath.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
		UserTrackerPath.class, ModelWrapper.class
	};

	private long _mvccVersion;
	private long _userTrackerPathId;
	private long _companyId;
	private long _userTrackerId;
	private long _originalUserTrackerId;
	private boolean _setOriginalUserTrackerId;
	private String _path;
	private Date _pathDate;
	private long _columnBitmask;
	private UserTrackerPath _escapedModel;

}