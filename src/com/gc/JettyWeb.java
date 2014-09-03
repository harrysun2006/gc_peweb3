package com.gc;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;
import org.springframework.security.ui.session.HttpSessionEventPublisher;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.DispatcherServlet;

import com.gc.web.FlexServlet;

public class JettyWeb {

	private final static String[] WELCOME_FILES = {"index.html", "index.jsp", "index.swf"};
	private final static int MAX_FORM_CONTENT_SIZE = 4096000;
	private final static String DISPLAY_NAME = "GreatCreate Power Engineer";
	private final static String DESCRIPTOR = "GreatCreate Power Engineer";

	public static void main(String[] args) throws Exception {
		Server server = new Server(80);
		File fapp = new File("www");
		WebAppContext wapp = new WebAppContext(server, fapp.getCanonicalPath(), "/");
		wapp.setClassLoader(ClassLoader.getSystemClassLoader());
		wapp.setWelcomeFiles(WELCOME_FILES);
		wapp.setMaxFormContentSize(MAX_FORM_CONTENT_SIZE);
		wapp.setDisplayName(DISPLAY_NAME);
		wapp.setDescriptor(DESCRIPTOR);

		Map initParams = new Hashtable();
		initParams.put("contextClass", "com.gc.web.PropertiesApplicationContext");
		wapp.setInitParams(initParams);

		wapp.addEventListener(new ContextLoaderListener());
		wapp.addEventListener(new HttpSessionEventPublisher());
		wapp.addEventListener(new RequestContextListener());

		ServletHolder sh = wapp.addServlet(DispatcherServlet.class, "/messagebroker/*");
		sh.setInitParameter("contextConfigLocation", "");
		sh.setInitOrder(1);
		wapp.addServlet(FlexServlet.class, "/flex/*").setInitOrder(2);

		server.start();
		server.join();
	}
}
