package com.gc.test;

import java.io.File;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class HRTestWeb {

	private final static String[] WELCOME_FILES = {"index.html", "index.jsp"};

	private static void runTestWeb() throws Exception {
		Server server = new Server(80);
		String[] apps = {
			"www",
			// "spring-security-samples-tutorial-2.0.4.war",
			// "spring-security-samples-contacts-2.0.4.war",
			// "testdrive.war",
		};
		String[] paths = {
			"/",
			// "/ss1",
			// "/ss2",
			// "/td",
		};
		// URL u = ClassLoader.getSystemResource("./");
		// System.out.println(u);
		File fapp;
		WebAppContext wapp;
		for (int i = 0; i < apps.length; i++) {
			fapp = new File(apps[i]);
			wapp = new WebAppContext(server, fapp.getCanonicalPath(), paths[i]);
			wapp.setClassLoader(ClassLoader.getSystemClassLoader());
			wapp.setWelcomeFiles(WELCOME_FILES);
			wapp.setMaxFormContentSize(4096000);
		}
		server.start();
	}

	public static void main(String[] args) throws Exception {
		try {
			runTestWeb();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
