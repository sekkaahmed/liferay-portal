/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.documentlibrary.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="DLFileShortcutSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLFileShortcutSoap implements Serializable {
	public static DLFileShortcutSoap toSoapModel(DLFileShortcut model) {
		DLFileShortcutSoap soapModel = new DLFileShortcutSoap();
		soapModel.setFileShortcutId(model.getFileShortcutId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFolderId(model.getFolderId());
		soapModel.setToFolderId(model.getToFolderId());
		soapModel.setToName(model.getToName());

		return soapModel;
	}

	public static DLFileShortcutSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			DLFileShortcut model = (DLFileShortcut)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (DLFileShortcutSoap[])soapModels.toArray(new DLFileShortcutSoap[0]);
	}

	public DLFileShortcutSoap() {
	}

	public long getPrimaryKey() {
		return _fileShortcutId;
	}

	public void setPrimaryKey(long pk) {
		setFileShortcutId(pk);
	}

	public long getFileShortcutId() {
		return _fileShortcutId;
	}

	public void setFileShortcutId(long fileShortcutId) {
		_fileShortcutId = fileShortcutId;
	}

	public String getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(String companyId) {
		_companyId = companyId;
	}

	public String getUserId() {
		return _userId;
	}

	public void setUserId(String userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getFolderId() {
		return _folderId;
	}

	public void setFolderId(String folderId) {
		_folderId = folderId;
	}

	public String getToFolderId() {
		return _toFolderId;
	}

	public void setToFolderId(String toFolderId) {
		_toFolderId = toFolderId;
	}

	public String getToName() {
		return _toName;
	}

	public void setToName(String toName) {
		_toName = toName;
	}

	private long _fileShortcutId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _folderId;
	private String _toFolderId;
	private String _toName;
}