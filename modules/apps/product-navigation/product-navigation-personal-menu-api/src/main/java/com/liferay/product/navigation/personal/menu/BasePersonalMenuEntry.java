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

package com.liferay.product.navigation.personal.menu;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides a skeletal implementation of the {@link PersonalMenuEntry} to
 * minimize the effort required to implement this interface.
 *
 * To implement a user personal menu entry, this class should be extended and
 * {@link #getPortletId()} and {@link#setPortlet(Portlet)} should be overridden.
 *
 * @author Pei-Jung Lan
 * @review
 */
public abstract class BasePersonalMenuEntry implements PersonalMenuEntry {

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			getResourceBundle(locale),
			JavaConstants.JAVAX_PORTLET_TITLE + StringPool.PERIOD +
				getPortletId());
	}

	@Override
	public String getPortletURL(HttpServletRequest request)
		throws PortalException {

		if (Validator.isNull(getPortletId())) {
			return null;
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			request, getPortletId(), PortletRequest.RENDER_PHASE);

		return portletURL.toString();
	}

	/**
	 * Returns the portlet's ID associated with the user personal menu entry.
	 *
	 * @return the portlet's ID associated with the user personal menu entry
	 * @review
	 */
	protected abstract String getPortletId();

	protected ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(locale, getClass());
	}

}