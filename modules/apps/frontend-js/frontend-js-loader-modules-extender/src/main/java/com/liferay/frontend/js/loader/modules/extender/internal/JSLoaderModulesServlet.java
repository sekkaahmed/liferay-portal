/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.frontend.js.loader.modules.extender.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.Details",
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.frontend.js.loader.modules.extender.internal.JSLoaderModulesServlet",
		"osgi.http.whiteboard.servlet.pattern=/js_loader_modules",
		"service.ranking:Integer=" + Details.MAX_VALUE_LESS_1K
	},
	service = {JSLoaderModulesServlet.class, Servlet.class}
)
public class JSLoaderModulesServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		_componentContext.enableComponent(
			JSLoaderModulesPortalWebResources.class.getName());
	}

	@Activate
	@Modified
	protected void activate(
		ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_details = ConfigurableUtil.createConfigurable(
			Details.class, properties);

		_componentContext = componentContext;
	}

	protected JSLoaderModulesTracker getJSLoaderModulesTracker() {
		return _jsLoaderModulesTracker;
	}

	@Reference(unbind = "-")
	protected void setJSLoaderModulesTracker(
		JSLoaderModulesTracker jsLoaderModulesTracker) {

		_jsLoaderModulesTracker = jsLoaderModulesTracker;
	}

	@Override
	protected void service(
		HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		StringWriter stringWriter = new StringWriter();

		PrintWriter printWriter = new PrintWriter(stringWriter);

		printWriter.println("(function() {");

		printWriter.println(
			"Liferay.EXPLAIN_RESOLUTIONS = " + _details.explainResolutions() +
			";\n");

		printWriter.println(
			"Liferay.EXPOSE_GLOBAL = " + _details.exposeGlobal() + ";\n");

		printWriter.println(
			"Liferay.WAIT_TIMEOUT = " + (_details.waitTimeout() * 1000) +
			";\n");

		printWriter.println("}());");

		printWriter.close();

		_writeResponse(response, stringWriter.toString());
	}

	protected void setDetails(Details details) {
		_details = details;
	}

	private void _writeResponse(HttpServletResponse response, String content)
		throws IOException {

		response.setContentType(Details.CONTENT_TYPE);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		PrintWriter printWriter = new PrintWriter(servletOutputStream, true);

		printWriter.write(_minifier.minify("/o/js_loader_modules", content));

		printWriter.close();
	}

	private ComponentContext _componentContext;
	private volatile Details _details;
	private JSLoaderModulesTracker _jsLoaderModulesTracker;

	@Reference
	private Minifier _minifier;

}