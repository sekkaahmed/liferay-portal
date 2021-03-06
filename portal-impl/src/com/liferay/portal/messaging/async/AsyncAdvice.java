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

package com.liferay.portal.messaging.async;

import com.liferay.portal.internal.messaging.async.AsyncInvokeThreadLocal;
import com.liferay.portal.internal.messaging.async.AsyncProcessCallable;
import com.liferay.portal.kernel.aop.AopMethodInvocation;
import com.liferay.portal.kernel.aop.ChainableMethodAdvice;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.async.Async;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class AsyncAdvice extends ChainableMethodAdvice {

	public AsyncAdvice(String destinationName) {
		_destinationName = destinationName;
	}

	@Override
	public Object createMethodContext(
		Class<?> targetClass, Method method,
		Map<Class<? extends Annotation>, Annotation> annotations) {

		Annotation annotation = annotations.get(Async.class);

		if (annotation == null) {
			return null;
		}

		if (method.getReturnType() != void.class) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Async annotation on method " + method.getName() +
						" does not return void");
			}

			return null;
		}

		return nullResult;
	}

	@Override
	protected Object before(
		AopMethodInvocation aopMethodInvocation, Object[] arguments) {

		if (AsyncInvokeThreadLocal.isEnabled()) {
			return null;
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				MessageBusUtil.sendMessage(
					_destinationName,
					new AsyncProcessCallable(aopMethodInvocation, arguments));

				return null;
			});

		return nullResult;
	}

	private static final Log _log = LogFactoryUtil.getLog(AsyncAdvice.class);

	private final String _destinationName;

}