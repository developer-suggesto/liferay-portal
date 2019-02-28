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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.model.WorkflowInstanceLinkModel;
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

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the WorkflowInstanceLink service. Represents a row in the &quot;WorkflowInstanceLink&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>WorkflowInstanceLinkModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link WorkflowInstanceLinkImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLinkImpl
 * @generated
 */
@ProviderType
public class WorkflowInstanceLinkModelImpl
	extends BaseModelImpl<WorkflowInstanceLink>
	implements WorkflowInstanceLinkModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a workflow instance link model instance should use the <code>WorkflowInstanceLink</code> interface instead.
	 */
	public static final String TABLE_NAME = "WorkflowInstanceLink";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"workflowInstanceLinkId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"workflowInstanceId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("workflowInstanceLinkId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("workflowInstanceId", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE =
		"create table WorkflowInstanceLink (mvccVersion LONG default 0 not null,workflowInstanceLinkId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,workflowInstanceId LONG)";

	public static final String TABLE_SQL_DROP =
		"drop table WorkflowInstanceLink";

	public static final String ORDER_BY_JPQL =
		" ORDER BY workflowInstanceLink.createDate DESC";

	public static final String ORDER_BY_SQL =
		" ORDER BY WorkflowInstanceLink.createDate DESC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.WorkflowInstanceLink"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.WorkflowInstanceLink"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.WorkflowInstanceLink"),
		true);

	public static final long CLASSNAMEID_COLUMN_BITMASK = 1L;

	public static final long CLASSPK_COLUMN_BITMASK = 2L;

	public static final long COMPANYID_COLUMN_BITMASK = 4L;

	public static final long GROUPID_COLUMN_BITMASK = 8L;

	public static final long CREATEDATE_COLUMN_BITMASK = 16L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.WorkflowInstanceLink"));

	public WorkflowInstanceLinkModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _workflowInstanceLinkId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setWorkflowInstanceLinkId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _workflowInstanceLinkId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return WorkflowInstanceLink.class;
	}

	@Override
	public String getModelClassName() {
		return WorkflowInstanceLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<WorkflowInstanceLink, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<WorkflowInstanceLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WorkflowInstanceLink, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((WorkflowInstanceLink)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<WorkflowInstanceLink, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<WorkflowInstanceLink, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(WorkflowInstanceLink)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<WorkflowInstanceLink, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<WorkflowInstanceLink, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static final Map<String, Function<WorkflowInstanceLink, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<WorkflowInstanceLink, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<WorkflowInstanceLink, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<WorkflowInstanceLink, Object>>();
		Map<String, BiConsumer<WorkflowInstanceLink, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<WorkflowInstanceLink, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", WorkflowInstanceLink::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<WorkflowInstanceLink, Long>)
				WorkflowInstanceLink::setMvccVersion);
		attributeGetterFunctions.put(
			"workflowInstanceLinkId",
			WorkflowInstanceLink::getWorkflowInstanceLinkId);
		attributeSetterBiConsumers.put(
			"workflowInstanceLinkId",
			(BiConsumer<WorkflowInstanceLink, Long>)
				WorkflowInstanceLink::setWorkflowInstanceLinkId);
		attributeGetterFunctions.put(
			"groupId", WorkflowInstanceLink::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<WorkflowInstanceLink, Long>)
				WorkflowInstanceLink::setGroupId);
		attributeGetterFunctions.put(
			"companyId", WorkflowInstanceLink::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<WorkflowInstanceLink, Long>)
				WorkflowInstanceLink::setCompanyId);
		attributeGetterFunctions.put("userId", WorkflowInstanceLink::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<WorkflowInstanceLink, Long>)
				WorkflowInstanceLink::setUserId);
		attributeGetterFunctions.put(
			"userName", WorkflowInstanceLink::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<WorkflowInstanceLink, String>)
				WorkflowInstanceLink::setUserName);
		attributeGetterFunctions.put(
			"createDate", WorkflowInstanceLink::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<WorkflowInstanceLink, Date>)
				WorkflowInstanceLink::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", WorkflowInstanceLink::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<WorkflowInstanceLink, Date>)
				WorkflowInstanceLink::setModifiedDate);
		attributeGetterFunctions.put(
			"classNameId", WorkflowInstanceLink::getClassNameId);
		attributeSetterBiConsumers.put(
			"classNameId",
			(BiConsumer<WorkflowInstanceLink, Long>)
				WorkflowInstanceLink::setClassNameId);
		attributeGetterFunctions.put(
			"classPK", WorkflowInstanceLink::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK",
			(BiConsumer<WorkflowInstanceLink, Long>)
				WorkflowInstanceLink::setClassPK);
		attributeGetterFunctions.put(
			"workflowInstanceId", WorkflowInstanceLink::getWorkflowInstanceId);
		attributeSetterBiConsumers.put(
			"workflowInstanceId",
			(BiConsumer<WorkflowInstanceLink, Long>)
				WorkflowInstanceLink::setWorkflowInstanceId);

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
	public long getWorkflowInstanceLinkId() {
		return _workflowInstanceLinkId;
	}

	@Override
	public void setWorkflowInstanceLinkId(long workflowInstanceLinkId) {
		_workflowInstanceLinkId = workflowInstanceLinkId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

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

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
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

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_columnBitmask = -1L;

		_createDate = createDate;
	}

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

	@Override
	public long getWorkflowInstanceId() {
		return _workflowInstanceId;
	}

	@Override
	public void setWorkflowInstanceId(long workflowInstanceId) {
		_workflowInstanceId = workflowInstanceId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), WorkflowInstanceLink.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public WorkflowInstanceLink toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (WorkflowInstanceLink)ProxyUtil.newProxyInstance(
				_classLoader, _escapedModelInterfaces,
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		WorkflowInstanceLinkImpl workflowInstanceLinkImpl =
			new WorkflowInstanceLinkImpl();

		workflowInstanceLinkImpl.setMvccVersion(getMvccVersion());
		workflowInstanceLinkImpl.setWorkflowInstanceLinkId(
			getWorkflowInstanceLinkId());
		workflowInstanceLinkImpl.setGroupId(getGroupId());
		workflowInstanceLinkImpl.setCompanyId(getCompanyId());
		workflowInstanceLinkImpl.setUserId(getUserId());
		workflowInstanceLinkImpl.setUserName(getUserName());
		workflowInstanceLinkImpl.setCreateDate(getCreateDate());
		workflowInstanceLinkImpl.setModifiedDate(getModifiedDate());
		workflowInstanceLinkImpl.setClassNameId(getClassNameId());
		workflowInstanceLinkImpl.setClassPK(getClassPK());
		workflowInstanceLinkImpl.setWorkflowInstanceId(getWorkflowInstanceId());

		workflowInstanceLinkImpl.resetOriginalValues();

		return workflowInstanceLinkImpl;
	}

	@Override
	public int compareTo(WorkflowInstanceLink workflowInstanceLink) {
		int value = 0;

		value = DateUtil.compareTo(
			getCreateDate(), workflowInstanceLink.getCreateDate());

		value = value * -1;

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

		if (!(obj instanceof WorkflowInstanceLink)) {
			return false;
		}

		WorkflowInstanceLink workflowInstanceLink = (WorkflowInstanceLink)obj;

		long primaryKey = workflowInstanceLink.getPrimaryKey();

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
		WorkflowInstanceLinkModelImpl workflowInstanceLinkModelImpl = this;

		workflowInstanceLinkModelImpl._originalGroupId =
			workflowInstanceLinkModelImpl._groupId;

		workflowInstanceLinkModelImpl._setOriginalGroupId = false;

		workflowInstanceLinkModelImpl._originalCompanyId =
			workflowInstanceLinkModelImpl._companyId;

		workflowInstanceLinkModelImpl._setOriginalCompanyId = false;

		workflowInstanceLinkModelImpl._setModifiedDate = false;

		workflowInstanceLinkModelImpl._originalClassNameId =
			workflowInstanceLinkModelImpl._classNameId;

		workflowInstanceLinkModelImpl._setOriginalClassNameId = false;

		workflowInstanceLinkModelImpl._originalClassPK =
			workflowInstanceLinkModelImpl._classPK;

		workflowInstanceLinkModelImpl._setOriginalClassPK = false;

		workflowInstanceLinkModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<WorkflowInstanceLink> toCacheModel() {
		WorkflowInstanceLinkCacheModel workflowInstanceLinkCacheModel =
			new WorkflowInstanceLinkCacheModel();

		workflowInstanceLinkCacheModel.mvccVersion = getMvccVersion();

		workflowInstanceLinkCacheModel.workflowInstanceLinkId =
			getWorkflowInstanceLinkId();

		workflowInstanceLinkCacheModel.groupId = getGroupId();

		workflowInstanceLinkCacheModel.companyId = getCompanyId();

		workflowInstanceLinkCacheModel.userId = getUserId();

		workflowInstanceLinkCacheModel.userName = getUserName();

		String userName = workflowInstanceLinkCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			workflowInstanceLinkCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			workflowInstanceLinkCacheModel.createDate = createDate.getTime();
		}
		else {
			workflowInstanceLinkCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			workflowInstanceLinkCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			workflowInstanceLinkCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		workflowInstanceLinkCacheModel.classNameId = getClassNameId();

		workflowInstanceLinkCacheModel.classPK = getClassPK();

		workflowInstanceLinkCacheModel.workflowInstanceId =
			getWorkflowInstanceId();

		return workflowInstanceLinkCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<WorkflowInstanceLink, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<WorkflowInstanceLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WorkflowInstanceLink, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((WorkflowInstanceLink)this));
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
		Map<String, Function<WorkflowInstanceLink, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<WorkflowInstanceLink, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<WorkflowInstanceLink, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((WorkflowInstanceLink)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader =
		WorkflowInstanceLink.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
		WorkflowInstanceLink.class, ModelWrapper.class
	};

	private long _mvccVersion;
	private long _workflowInstanceLinkId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
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
	private long _workflowInstanceId;
	private long _columnBitmask;
	private WorkflowInstanceLink _escapedModel;

}