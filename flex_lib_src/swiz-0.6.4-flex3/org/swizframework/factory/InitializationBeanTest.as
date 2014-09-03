package org.swizframework.factory {
	import net.digitalprimates.fluint.tests.TestCase;
	
	import org.swizframework.InitBeanOne;
	import org.swizframework.InitBeanTwo;
	import org.swizframework.Swiz;
	import org.swizframework.TestBeans;
	
	public class InitializationBeanTest extends TestCase {
		public function createSingleBean() : void {
			// give an initializing bean
			Swiz.addBean( "one", new InitBeanOne() );
			// now get it
			var bean : InitBeanOne = Swiz.getBean( "one" ) as InitBeanOne;
			Swiz.autowire( bean );
			
			// make sure data is not null
			assertNotNull( bean.getData() );
		}
		
		[Test( description="Tests a single initializing bean to make sure initialize() is called by Swiz." )]
		public function fetchSingleBean() : void {
			// give an initializing bean
			Swiz.loadBeans( [ TestBeans ] );
			// now get it
			var bean : InitBeanOne = Swiz.getBean( "initBean" ) as InitBeanOne;
			
			// make sure data is not null
			assertNotNull( bean.getData() );
		}
		
		[Test( description="Tests a single initializing bean to make sure initialize() is called by Swiz." )]
		public function fetchSinglePrototype() : void {
			// give an initializing bean
			Swiz.loadBeans( [ TestBeans ] );
			// now get it
			var bean : InitBeanOne = Swiz.getBean( "initOne" ) as InitBeanOne;
			
			// make sure data is not null
			assertNotNull( bean.getData() );
		}
		
		[Test( description="Tests a single initializing bean to make sure initialize() is called by Swiz." )]
		public function fetchBeanWithDependency() : void {
			// give an initializing bean
			Swiz.loadBeans( [ TestBeans ] );
			// now get it
			var bean : InitBeanTwo = Swiz.getBean( "initTwo" ) as InitBeanTwo;
			
			// make sure data is not null
			assertNotNull( bean.getData() );
			
			// make sure friend's data is not null
			assertNotNull( bean.getFriend().getData() );
		}
	}
}