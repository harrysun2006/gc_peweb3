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

package com.gc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import javax.naming.spi.NamingManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.naming.ContextAccessController;
import org.apache.naming.ContextBindings;
import org.apache.naming.NamingContext;
import org.apache.naming.ResourceRef;
import org.apache.naming.factory.ResourceLinkFactory;

public class JNDIUtil {

	private final static Log _log = LogFactory.getLog(JNDIUtil.class);

	private static Map _cache = new HashMap();

	private static final String ROOT_NAME = "root";

	private static final String RESOURCE_NAME = "name";

	private static final String RESOURCE_TYPE = "type";

	private static NamingContext rootCtx = null;

	private static Context compCtx = null;

	private static Context envCtx = null;

	private JNDIUtil() {
	}

	public static Object lookup(String name) throws NamingException {
		return lookup(rootCtx, name);
	}

	public static Object lookup(Context ctx, String name) throws NamingException {
		return lookup(ctx, name, false);
	}

	public static Object lookup(Context ctx, String name, boolean cache)
			throws NamingException {
		Object obj = null;
		if (cache) {
			obj = _cache.get(name);
			if (obj == null) {
				obj = _lookup(ctx, name);
				_cache.put(name, obj);
			}
		} else {
			obj = _lookup(ctx, name);
		}
		return obj;
	}

	private static Object _lookup(Context ctx, String name)
			throws NamingException {
		if (_log.isDebugEnabled()) {
			_log.debug("Lookup " + name);
		}
		Object obj = null;
		try {
			obj = ctx.lookup(name);
		} catch (NamingException n1) {
			// java:comp/env/ObjectName to ObjectName
			if (name.indexOf("java:comp/env/") != -1) {
				try {
					String newName = StringUtil.replace(name, "java:comp/env/", "");
					if (_log.isDebugEnabled()) {
						_log.debug(n1.getMessage());
						_log.debug("Attempt " + newName);
					}
					obj = ctx.lookup(newName);
				} catch (NamingException n2) {
					// java:comp/env/ObjectName to java:ObjectName
					String newName = StringUtil.replace(name, "comp/env/", "");
					if (_log.isDebugEnabled()) {
						_log.debug(n2.getMessage());
						_log.debug("Attempt " + newName);
					}
					obj = ctx.lookup(newName);
				}
			}
			// java:ObjectName to ObjectName
			else if (name.indexOf("java:") != -1) {
				try {
					String newName = StringUtil.replace(name, "java:", "");
					if (_log.isDebugEnabled()) {
						_log.debug(n1.getMessage());
						_log.debug("Attempt " + newName);
					}
					obj = ctx.lookup(newName);
				} catch (NamingException n2) {
					// java:ObjectName to java:comp/env/ObjectName
					String newName = StringUtil.replace(name, "java:", "java:comp/env/");
					if (_log.isDebugEnabled()) {
						_log.debug(n2.getMessage());
						_log.debug("Attempt " + newName);
					}
					obj = ctx.lookup(newName);
				}
			}
			// ObjectName to java:ObjectName
			else if (name.indexOf("java:") == -1) {
				try {
					String newName = "java:" + name;
					if (_log.isDebugEnabled()) {
						_log.debug(n1.getMessage());
						_log.debug("Attempt " + newName);
					}
					obj = ctx.lookup(newName);
				} catch (NamingException n2) {
					// ObjectName to java:comp/env/ObjectName
					String newName = "java:comp/env/" + name;
					if (_log.isDebugEnabled()) {
						_log.debug(n2.getMessage());
						_log.debug("Attempt " + newName);
					}
					obj = ctx.lookup(newName);
				}
			} else {
				throw new NamingException();
			}
		}
		return obj;
	}

	private synchronized static void init() {
		if (envCtx == null) {
			try {
				Object container = new Object();
				String name = ROOT_NAME;
				Hashtable contextEnv = new Hashtable();
				rootCtx = new NamingContext(contextEnv, "ROOT");
				ContextAccessController.setSecurityToken(name, container);
				ContextBindings.bindContext(container, rootCtx, container);
				ContextBindings.bindClassLoader(container, rootCtx, ClassLoader
						.getSystemClassLoader());
				ContextAccessController.setWritable(name, container);
				compCtx = rootCtx.createSubcontext("java:comp");
				envCtx = compCtx.createSubcontext("env");
				ResourceLinkFactory.setGlobalContext(rootCtx);
				ContextBindings.bindClassLoader(container, container, ClassLoader
						.getSystemClassLoader());
				NamingManager
						.setInitialContextFactoryBuilder(ApplicationInitialContextFactoryBuilder.instance);
			} catch (NamingException ne) {
				ne.printStackTrace();
			}
		}
	}

	public static void addResource(String name) throws IOException {
		Properties props = new Properties();
		InputStream is = ClassLoader.getSystemResourceAsStream(name);
		props.load(is);
		is.close();
		addResource(props);
	}

	public static void addResource(Hashtable resource) {
		init();
		// Create a reference to the resource.
		String name = (String) resource.get(RESOURCE_NAME);
		String type = (String) resource.get(RESOURCE_TYPE);
		Reference ref = new ResourceRef(type, null, null, null);
		// Adding the additional parameters, if any
		Iterator params = resource.keySet().iterator();
		while (params.hasNext()) {
			String paramName = (String) params.next();
			String paramValue = (String) resource.get(paramName);
			StringRefAddr refAddr = new StringRefAddr(paramName, paramValue);
			ref.add(refAddr);
		}
		try {
			createSubcontexts(envCtx, name);
			envCtx.rebind(name, ref);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static void addValue(String name, Object value) {
		init();
		// Binding the object to the appropriate name
		if (value != null) {
			try {
				createSubcontexts(envCtx, name);
				envCtx.rebind(name, value);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}

	private static void createSubcontexts(Context ctx, String name)
			throws NamingException {
		Context currentContext = ctx;
		StringTokenizer tokenizer = new StringTokenizer(name, "/");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if ((!token.equals("")) && (tokenizer.hasMoreTokens())) {
				try {
					currentContext = currentContext.createSubcontext(token);
				} catch (NamingException e) {
					// Silent catch. Probably an object is already bound in
					// the context.
					currentContext = (Context) currentContext.lookup(token);
				}
			}
		}
	}

	private static final class ApplicationInitialContextFactoryBuilder implements
			InitialContextFactoryBuilder {
		private static final ApplicationInitialContextFactoryBuilder instance = new ApplicationInitialContextFactoryBuilder();

		private ApplicationInitialContextFactoryBuilder() {
		}

		public InitialContextFactory createInitialContextFactory(
				Hashtable environment) throws NamingException {
			return ApplicationInitialContextFactory.instance;
		}
	}

	private static final class ApplicationInitialContextFactory implements
			InitialContextFactory {
		private static final ApplicationInitialContextFactory instance = new ApplicationInitialContextFactory();

		public Context getInitialContext(Hashtable environment)
				throws NamingException {
			return rootCtx;
		}
	}

}