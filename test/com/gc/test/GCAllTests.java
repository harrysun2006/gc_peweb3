package com.gc.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class GCAllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(HRUnitTest.class);
		return suite;
	}
}
