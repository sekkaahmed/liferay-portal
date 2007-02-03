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

package com.liferay.portlet.messageboards.service;

/**
 * <a href="MBStatsUserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBStatsUserLocalServiceUtil {
	public static void deleteStatsUserByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		MBStatsUserLocalService mbStatsUserLocalService = MBStatsUserLocalServiceFactory.getService();
		mbStatsUserLocalService.deleteStatsUserByGroupId(groupId);
	}

	public static void deleteStatsUserByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		MBStatsUserLocalService mbStatsUserLocalService = MBStatsUserLocalServiceFactory.getService();
		mbStatsUserLocalService.deleteStatsUserByUserId(userId);
	}

	public static com.liferay.portlet.messageboards.model.MBStatsUser getStatsUser(
		long groupId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBStatsUserLocalService mbStatsUserLocalService = MBStatsUserLocalServiceFactory.getService();

		return mbStatsUserLocalService.getStatsUser(groupId, userId);
	}

	public static java.util.List getStatsUsers(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		MBStatsUserLocalService mbStatsUserLocalService = MBStatsUserLocalServiceFactory.getService();

		return mbStatsUserLocalService.getStatsUsers(groupId, begin, end);
	}

	public static int getStatsUsersCount(long groupId)
		throws com.liferay.portal.SystemException {
		MBStatsUserLocalService mbStatsUserLocalService = MBStatsUserLocalServiceFactory.getService();

		return mbStatsUserLocalService.getStatsUsersCount(groupId);
	}

	public static void updateStatsUser(long groupId, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBStatsUserLocalService mbStatsUserLocalService = MBStatsUserLocalServiceFactory.getService();
		mbStatsUserLocalService.updateStatsUser(groupId, userId);
	}
}