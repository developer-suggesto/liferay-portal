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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscape;

import java.util.Date;

/**
 * The base model interface for the UserGroup service. Represents a row in the &quot;UserGroup&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.portal.model.impl.UserGroupModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.portal.model.impl.UserGroupImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroup
 * @generated
 */
@ProviderType
public interface UserGroupModel
	extends BaseModel<UserGroup>, MVCCModel, ShardedModel, StagedAuditedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a user group model instance should use the {@link UserGroup} interface instead.
	 */

	/**
	 * Returns the primary key of this user group.
	 *
	 * @return the primary key of this user group
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this user group.
	 *
	 * @param primaryKey the primary key of this user group
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the mvcc version of this user group.
	 *
	 * @return the mvcc version of this user group
	 */
	@Override
	public long getMvccVersion();

	/**
	 * Sets the mvcc version of this user group.
	 *
	 * @param mvccVersion the mvcc version of this user group
	 */
	@Override
	public void setMvccVersion(long mvccVersion);

	/**
	 * Returns the uuid of this user group.
	 *
	 * @return the uuid of this user group
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this user group.
	 *
	 * @param uuid the uuid of this user group
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the external reference code of this user group.
	 *
	 * @return the external reference code of this user group
	 */
	@AutoEscape
	public String getExternalReferenceCode();

	/**
	 * Sets the external reference code of this user group.
	 *
	 * @param externalReferenceCode the external reference code of this user group
	 */
	public void setExternalReferenceCode(String externalReferenceCode);

	/**
	 * Returns the user group ID of this user group.
	 *
	 * @return the user group ID of this user group
	 */
	public long getUserGroupId();

	/**
	 * Sets the user group ID of this user group.
	 *
	 * @param userGroupId the user group ID of this user group
	 */
	public void setUserGroupId(long userGroupId);

	/**
	 * Returns the company ID of this user group.
	 *
	 * @return the company ID of this user group
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this user group.
	 *
	 * @param companyId the company ID of this user group
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this user group.
	 *
	 * @return the user ID of this user group
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this user group.
	 *
	 * @param userId the user ID of this user group
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this user group.
	 *
	 * @return the user uuid of this user group
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this user group.
	 *
	 * @param userUuid the user uuid of this user group
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this user group.
	 *
	 * @return the user name of this user group
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this user group.
	 *
	 * @param userName the user name of this user group
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this user group.
	 *
	 * @return the create date of this user group
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this user group.
	 *
	 * @param createDate the create date of this user group
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this user group.
	 *
	 * @return the modified date of this user group
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this user group.
	 *
	 * @param modifiedDate the modified date of this user group
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the parent user group ID of this user group.
	 *
	 * @return the parent user group ID of this user group
	 */
	public long getParentUserGroupId();

	/**
	 * Sets the parent user group ID of this user group.
	 *
	 * @param parentUserGroupId the parent user group ID of this user group
	 */
	public void setParentUserGroupId(long parentUserGroupId);

	/**
	 * Returns the name of this user group.
	 *
	 * @return the name of this user group
	 */
	@AutoEscape
	public String getName();

	/**
	 * Sets the name of this user group.
	 *
	 * @param name the name of this user group
	 */
	public void setName(String name);

	/**
	 * Returns the description of this user group.
	 *
	 * @return the description of this user group
	 */
	@AutoEscape
	public String getDescription();

	/**
	 * Sets the description of this user group.
	 *
	 * @param description the description of this user group
	 */
	public void setDescription(String description);

	/**
	 * Returns the added by ldap import of this user group.
	 *
	 * @return the added by ldap import of this user group
	 */
	public boolean getAddedByLDAPImport();

	/**
	 * Returns <code>true</code> if this user group is added by ldap import.
	 *
	 * @return <code>true</code> if this user group is added by ldap import; <code>false</code> otherwise
	 */
	public boolean isAddedByLDAPImport();

	/**
	 * Sets whether this user group is added by ldap import.
	 *
	 * @param addedByLDAPImport the added by ldap import of this user group
	 */
	public void setAddedByLDAPImport(boolean addedByLDAPImport);

}