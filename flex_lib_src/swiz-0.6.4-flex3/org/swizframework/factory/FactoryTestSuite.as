package org.swizframework.factory {
	import net.digitalprimates.fluint.tests.TestSuite;
	
	public class FactoryTestSuite extends TestSuite {
		public function FactoryTestSuite() {
			addTestCase( new PrototypeTest() );
			addTestCase( new InitializationBeanTest() );
		}
	}
}