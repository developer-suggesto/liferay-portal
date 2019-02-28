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
import com.liferay.portal.kernel.model.PasswordPolicyRel;
import com.liferay.portal.kernel.model.PasswordPolicyRelModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.sql.Types;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the PasswordPolicyRel service. Represents a row in the &quot;PasswordPolicyRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>PasswordPolicyRelModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PasswordPolicyRelImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyRelImpl
 * @generated
 */
@ProviderType
public class PasswordPolicyRelModelImpl
	extends BaseModelImpl<PasswordPolicyRel> implements PasswordPolicyRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a password policy rel model instance should use the <code>PasswordPolicyRel</code> interface instead.
	 */
	public static final String TABLE_NAME = "PasswordPolicyRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"passwordPolicyRelId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"passwordPolicyId", Types.BIGINT},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("passwordPolicyRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("passwordPolicyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table PasswordPolicyRel (mvccVersion LONG default 0 not null,passwordPolicyRelId LONG not null primary key,companyId LONG,passwordPolicyId LONG,classNameId LONG,classPK LONG)";

	public static final String TABLE_SQL_DROP = "drop table PasswordPolicyRel";

	public static final String ORDER_BY_JPQL =
		" ORDER BY passwordPolicyRel.passwordPolicyRelId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY PasswordPolicyRel.passwordPolicyRelId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.PasswordPolicyRel"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.PasswordPolicyRel"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.PasswordPolicyRel"),
		true);

	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	public static final long PASSWORDPOLICYID_COLUMN_BITMASK = 4L;

	public static final long PASSWORDPOLICYRELID_COLUMN_BITMASK = 8L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.PasswordPolicyRel"));

	public PasswordPolicyRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _passwordPolicyRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setPasswordPolicyRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _passwordPolicyRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return PasswordPolicyRel.class;
	}

	@Override
	public String getModelClassName() {
		return PasswordPolicyRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<PasswordPolicyRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<PasswordPolicyRel, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<PasswordPolicyRel, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((PasswordPolicyRel)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<PasswordPolicyRel, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<PasswordPolicyRel, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(PasswordPolicyRel)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<PasswordPolicyRel, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<PasswordPolicyRel, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<PasswordPolicyRel, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<PasswordPolicyRel, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<PasswordPolicyRel, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<PasswordPolicyRel, Object>>();
		Map<String, BiConsumer<PasswordPolicyRel, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap<String, BiConsumer<PasswordPolicyRel, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", PasswordPolicyRel::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<PasswordPolicyRel, Long>)
				PasswordPolicyRel::setMvccVersion);
		attributeGetterFunctions.put(
			"passwordPolicyRelId", PasswordPolicyRel::getPasswordPolicyRelId);
		attributeSetterBiConsumers.put(
			"passwordPolicyRelId",
			(BiConsumer<PasswordPolicyRel, Long>)
				PasswordPolicyRel::setPasswordPolicyRelId);
		attributeGetterFunctions.put(
			"companyId", PasswordPolicyRel::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<PasswordPolicyRel, Long>)
				PasswordPolicyRel::setCompanyId);
		attributeGetterFunctions.put(
			"passwordPolicyId", PasswordPolicyRel::getPasswordPolicyId);
		attributeSetterBiConsumers.put(
			"passwordPolicyId",
			(BiConsumer<PasswordPolicyRel, Long>)
				PasswordPolicyRel::setPasswordPolicyId);
		attributeGetterFunctions.put(
			"classNameId", PasswordPolicyRel::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId",
			(BiConsumer<PasswordPolicyRel, Long>)
				PasswordPolicyRel::setClassNameId);
		attributeGetterFunctions.put("classPK", PasswordPolicyRel::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK",
			(BiConsumer<PasswordPolicyRel, Long>)PasswordPolicyRel::setClassPK);

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
	public long getPasswordPolicyRelId() {
		return _passwordPolicyRelId;
	}

	@Override
	public void setPasswordPolicyRelId(long passwordPolicyRelId) {
		_passwordPolicyRelId = passwordPolicyRelId;
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
	public long getPasswordPolicyId() {
		return _passwordPolicyId;
	}

	@Override
	public void setPasswordPolicyId(long passwordPolicyId) {
		_columnBitmask |= PASSWORDPOLICYID_COLUMN_BITMASK;

		if (!_setOriginalPasswordPolicyId) {
			_setOriginalPasswordPolicyId = true;

			_originalPasswordPolicyId = _passwordPolicyId;
		}

		_passwordPolicyId = passwordPolicyId;
	}

	public long getOriginalPasswordPolicyId() {
		return _originalPasswordPolicyId;
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

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), PasswordPolicyRel.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public PasswordPolicyRel toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (PasswordPolicyRel)ProxyUtil.newProxyInstance(
				_classLoader, _escapedModelInterfaces,
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		PasswordPolicyRelImpl passwordPolicyRelImpl =
			new PasswordPolicyRelImpl();

		passwordPolicyRelImpl.setMvccVersion(getMvccVersion());
		passwordPolicyRelImpl.setPasswordPolicyRelId(getPasswordPolicyRelId());
		passwordPolicyRelImpl.setCompanyId(getCompanyId());
		passwordPolicyRelImpl.setPasswordPolicyId(getPasswordPolicyId());
		passwordPolicyRelImpl.setClassNameId(getClassNameId());
		passwordPolicyRelImpl.setClassPK(getClassPK());

		passwordPolicyRelImpl.resetOriginalValues();

		return passwordPolicyRelImpl;
	}

	@Override
	public int compareTo(PasswordPolicyRel passwordPolicyRel) {
		long primaryKey = passwordPolicyRel.getPrimaryKey();

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

		if (!(obj instanceof PasswordPolicyRel)) {
			return false;
		}

		PasswordPolicyRel passwordPolicyRel = (PasswordPolicyRel)obj;

		long primaryKey = passwordPolicyRel.getPrimaryKey();

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
		PasswordPolicyRelModelImpl passwordPolicyRelModelImpl = this;

		passwordPolicyRelModelImpl._originalPasswordPolicyId =
			passwordPolicyRelModelImpl._passwordPolicyId;

		passwordPolicyRelModelImpl._setOriginalPasswordPolicyId = false;

		passwordPolicyRelModelImpl._originalClassNameId =
			passwordPolicyRelModelImpl._classNameId;

		passwordPolicyRelModelImpl._setOriginalClassNameId = false;

		passwordPolicyRelModelImpl._originalClassPK =
			passwordPolicyRelModelImpl._classPK;

		passwordPolicyRelModelImpl._setOriginalClassPK = false;

		passwordPolicyRelModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<PasswordPolicyRel> toCacheModel() {
		PasswordPolicyRelCacheModel passwordPolicyRelCacheModel =
			new PasswordPolicyRelCacheModel();

		passwordPolicyRelCacheModel.mvccVersion = getMvccVersion();

		passwordPolicyRelCacheModel.passwordPolicyRelId =
			getPasswordPolicyRelId();

		passwordPolicyRelCacheModel.companyId = getCompanyId();

		passwordPolicyRelCacheModel.passwordPolicyId = getPasswordPolicyId();

		passwordPolicyRelCacheModel.classNameId = getClassNameId();

		passwordPolicyRelCacheModel.classPK = getClassPK();

		return passwordPolicyRelCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<PasswordPolicyRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<PasswordPolicyRel, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<PasswordPolicyRel, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((PasswordPolicyRel)this));
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
		Map<String, Function<PasswordPolicyRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<PasswordPolicyRel, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<PasswordPolicyRel, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((PasswordPolicyRel)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader =
		PasswordPolicyRel.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
		PasswordPolicyRel.class, ModelWrapper.class
	};

	private long _mvccVersion;
	private long _passwordPolicyRelId;
	private long _companyId;
	private long _passwordPolicyId;
	private long _originalPasswordPolicyId;
	private boolean _setOriginalPasswordPolicyId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _columnBitmask;
	private PasswordPolicyRel _escapedModel;

}