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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="ContactSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ContactSoap implements Serializable {
	public static ContactSoap toSoapModel(Contact model) {
		ContactSoap soapModel = new ContactSoap();
		soapModel.setContactId(model.getContactId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAccountId(model.getAccountId());
		soapModel.setParentContactId(model.getParentContactId());
		soapModel.setFirstName(model.getFirstName());
		soapModel.setMiddleName(model.getMiddleName());
		soapModel.setLastName(model.getLastName());
		soapModel.setNickName(model.getNickName());
		soapModel.setPrefixId(model.getPrefixId());
		soapModel.setSuffixId(model.getSuffixId());
		soapModel.setMale(model.getMale());
		soapModel.setBirthday(model.getBirthday());
		soapModel.setSmsSn(model.getSmsSn());
		soapModel.setAimSn(model.getAimSn());
		soapModel.setIcqSn(model.getIcqSn());
		soapModel.setJabberSn(model.getJabberSn());
		soapModel.setMsnSn(model.getMsnSn());
		soapModel.setSkypeSn(model.getSkypeSn());
		soapModel.setYmSn(model.getYmSn());
		soapModel.setEmployeeStatusId(model.getEmployeeStatusId());
		soapModel.setEmployeeNumber(model.getEmployeeNumber());
		soapModel.setJobTitle(model.getJobTitle());
		soapModel.setJobClass(model.getJobClass());
		soapModel.setHoursOfOperation(model.getHoursOfOperation());

		return soapModel;
	}

	public static ContactSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			Contact model = (Contact)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (ContactSoap[])soapModels.toArray(new ContactSoap[0]);
	}

	public ContactSoap() {
	}

	public String getPrimaryKey() {
		return _contactId;
	}

	public void setPrimaryKey(String pk) {
		setContactId(pk);
	}

	public String getContactId() {
		return _contactId;
	}

	public void setContactId(String contactId) {
		_contactId = contactId;
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

	public String getAccountId() {
		return _accountId;
	}

	public void setAccountId(String accountId) {
		_accountId = accountId;
	}

	public String getParentContactId() {
		return _parentContactId;
	}

	public void setParentContactId(String parentContactId) {
		_parentContactId = parentContactId;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public String getMiddleName() {
		return _middleName;
	}

	public void setMiddleName(String middleName) {
		_middleName = middleName;
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public String getNickName() {
		return _nickName;
	}

	public void setNickName(String nickName) {
		_nickName = nickName;
	}

	public int getPrefixId() {
		return _prefixId;
	}

	public void setPrefixId(int prefixId) {
		_prefixId = prefixId;
	}

	public int getSuffixId() {
		return _suffixId;
	}

	public void setSuffixId(int suffixId) {
		_suffixId = suffixId;
	}

	public boolean getMale() {
		return _male;
	}

	public boolean isMale() {
		return _male;
	}

	public void setMale(boolean male) {
		_male = male;
	}

	public Date getBirthday() {
		return _birthday;
	}

	public void setBirthday(Date birthday) {
		_birthday = birthday;
	}

	public String getSmsSn() {
		return _smsSn;
	}

	public void setSmsSn(String smsSn) {
		_smsSn = smsSn;
	}

	public String getAimSn() {
		return _aimSn;
	}

	public void setAimSn(String aimSn) {
		_aimSn = aimSn;
	}

	public String getIcqSn() {
		return _icqSn;
	}

	public void setIcqSn(String icqSn) {
		_icqSn = icqSn;
	}

	public String getJabberSn() {
		return _jabberSn;
	}

	public void setJabberSn(String jabberSn) {
		_jabberSn = jabberSn;
	}

	public String getMsnSn() {
		return _msnSn;
	}

	public void setMsnSn(String msnSn) {
		_msnSn = msnSn;
	}

	public String getSkypeSn() {
		return _skypeSn;
	}

	public void setSkypeSn(String skypeSn) {
		_skypeSn = skypeSn;
	}

	public String getYmSn() {
		return _ymSn;
	}

	public void setYmSn(String ymSn) {
		_ymSn = ymSn;
	}

	public String getEmployeeStatusId() {
		return _employeeStatusId;
	}

	public void setEmployeeStatusId(String employeeStatusId) {
		_employeeStatusId = employeeStatusId;
	}

	public String getEmployeeNumber() {
		return _employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		_employeeNumber = employeeNumber;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public String getJobClass() {
		return _jobClass;
	}

	public void setJobClass(String jobClass) {
		_jobClass = jobClass;
	}

	public String getHoursOfOperation() {
		return _hoursOfOperation;
	}

	public void setHoursOfOperation(String hoursOfOperation) {
		_hoursOfOperation = hoursOfOperation;
	}

	private String _contactId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _accountId;
	private String _parentContactId;
	private String _firstName;
	private String _middleName;
	private String _lastName;
	private String _nickName;
	private int _prefixId;
	private int _suffixId;
	private boolean _male;
	private Date _birthday;
	private String _smsSn;
	private String _aimSn;
	private String _icqSn;
	private String _jabberSn;
	private String _msnSn;
	private String _skypeSn;
	private String _ymSn;
	private String _employeeStatusId;
	private String _employeeNumber;
	private String _jobTitle;
	private String _jobClass;
	private String _hoursOfOperation;
}