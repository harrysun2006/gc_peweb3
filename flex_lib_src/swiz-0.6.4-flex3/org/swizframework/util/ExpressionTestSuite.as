package org.swizframework.util {
	import net.digitalprimates.fluint.tests.TestSuite;
	
	public class ExpressionTestSuite extends TestSuite {
		public function ExpressionTestSuite() {
			addTestCase( new ExpressionUtilsTest() );
		}
	
	}
}