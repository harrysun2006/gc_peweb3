package org.swizframework.factory {
	import flash.utils.describeType;
	import flash.utils.getDefinitionByName;
	
	import net.digitalprimates.fluint.tests.TestCase;
	
	import org.swizframework.SecondBean;
	import org.swizframework.Swiz;
	import org.swizframework.TestBean;
	
	public class PrototypeTest extends TestCase {
		
		[Test( description="Tests Prototype's getObject() to make sure it returns an instance of the correct type." )]
		public function createObject() : void {
			// make a prototype bean
			var proto : Prototype = new Prototype();
			proto.className = "org.swizframework.TestBean";
			
			// retrieve an object
			var beanOne : * = proto.getObject();
			
			// make sure the result is the correct class
			assertTrue( beanOne is TestBean );
			
			// also make sure that Prototype returns the correct className
			assertTrue( proto.className == "org.swizframework.TestBean" );
		}
		
		[Test( description="Tests getBean from Swiz to ensure getObject() is called on the prototype bean." )]
		public function getObjectFromSwiz() : void {
			// make a prototype bean
			var proto : Prototype = new Prototype();
			proto.className = "org.swizframework.TestBean";
			
			// give it to Swiz
			Swiz.addBean( "proto", proto );
			
			// retrieve an object
			var beanOne : * = Swiz.getBean( "proto" );
			
			// make sure the result is the correct class
			assertTrue( beanOne is TestBean );
		}
		
		// todo: construct a proper test for getBeanByType and autowire by type
		[Test( description="Tests getObjectType() from Prototype to make sure it returns the same fully qualified class name as describeType." )]
		public function getObjectType() : void {
			// make a prototype bean
			var proto : Prototype = new Prototype();
			proto.className = "org.swizframework.TestBean";
			
			// get the actual bean XML description
			var bean : TestBean = new TestBean();
			var beanDesc : XML = describeType( bean );
			
			// and get the prototype's class name, as well as xml description
			var protoType : String = proto.getObjectType();
			var protoDesc : XML = proto.getObjectDescription();
			
			trace( "proto type: " + protoType );
			trace( "beanType: " + beanDesc.@name );
			
			// make sure the result is the correct class
			assertEquals( beanDesc.@name, protoType );
		}
		
		[Test( description="Tests Prototype's getObject() to return new objects for each call. Does not use the bean factory." )]
		public function createNonSingletons() : void {
			
			// make a prototype bean
			var proto : Prototype = new Prototype();
			proto.className = "org.swizframework.TestBean";
			
			// make 2 instances from it
			var beanOne : TestBean = proto.getObject() as TestBean;
			var beanTwo : TestBean = proto.getObject() as TestBean;
			
			trace( "Bean One: " + beanOne.getId() );
			trace( "Bean Two: " + beanTwo.getId() );
			
			// now are they the same?
			assertTrue( beanOne.getId() != beanTwo.getId() );
		}
		
		[Test( description="Tests Prototype to return singletons. This will use the bean factory to retrieve the object from cache." )]
		public function createSingletons() : void {
			
			// make a prototype bean
			var proto : Prototype = new Prototype();
			proto.className = "org.swizframework.TestBean";
			proto.singleton = true;
			
			// make 2 instances from it
			var beanOne : TestBean = proto.getObject();
			var beanTwo : TestBean = proto.getObject();
			
			trace( "Bean One: " + beanOne.getId() );
			trace( "Bean Two: " + beanTwo.getId() );
			
			// now are they the same?
			assertTrue( beanOne.getId() == beanTwo.getId() );
		}
		
		[Test( description="Tests placing a prototype bean into Swiz, and seeing if we get back the object it creates." )]
		public function fetchBeanFromSwiz() : void {
			
			// make a prototype bean
			var proto : Prototype = new Prototype();
			proto.className = "org.swizframework.TestBean";
			proto.singleton = true;
			
			// give it to Swiz
			Swiz.addBean( "proto", proto );
			
			// this time we will get one bean from Swiz, and another from the prototype. They should be the same
			var beanOne : TestBean = Swiz.getBean( "proto" ) as TestBean;
			var beanTwo : TestBean = proto.getObject();
			
			// now are they the same?
			assertTrue( beanOne.getId() == beanTwo.getId() );
		}
		
		[Test( description="Tests two beans, one with a reference to the other, made by prototype." )]
		public function fetchAutowiredSingletonBeansFromSwiz() : void {
			
			// make a prototype bean
			var proto : Prototype = new Prototype();
			proto.className = "org.swizframework.TestBean";
			proto.singleton = true;
			// and a second
			var protoTwo : Prototype = new Prototype();
			protoTwo.className = "org.swizframework.SecondBean";
			protoTwo.singleton = true;
			
			// give it to Swiz, and a bean that depends on it
			Swiz.addBean( "protoBean", proto );
			Swiz.addBean( "secondBean", protoTwo );
			
			// fetch these beans, they should have references to eachother
			var beanOne : * = Swiz.getBean( "protoBean" );
			var beanTwo : * = Swiz.getBean( "secondBean" );
			
			assertTrue( beanOne is TestBean );
			assertTrue( beanTwo is SecondBean );
			
			// make these beans say something
			TestBean( beanOne ).sayHi();
			SecondBean( beanTwo ).sayHi();
			
			// now are they the same?
			assertTrue( TestBean( beanOne ).getId() == SecondBean( beanTwo ).friendBean.getId() );
		}
		
		[Test( description="Tests two beans, one with a reference to the other, made by prototype." )]
		public function fetchAutowiredNonSingletonsBeansFromSwiz() : void {
			
			// make a prototype bean
			var proto : Prototype = new Prototype();
			proto.className = "org.swizframework.TestBean";
			proto.singleton = false;
			// and a second
			var protoTwo : Prototype = new Prototype();
			protoTwo.className = "org.swizframework.SecondBean";
			protoTwo.singleton = false;
			
			// give it to Swiz, and a bean that depends on it
			Swiz.addBean( "protoBean", proto );
			Swiz.addBean( "secondBean", protoTwo );
			
			// fetch these beans, they should have references to eachother, but two different graphs
			var beanOne : * = Swiz.getBean( "protoBean" );
			var beanTwo : * = Swiz.getBean( "secondBean" );
			
			// make these beans say something
			TestBean( beanOne ).sayHi();
			SecondBean( beanTwo ).sayHi();
			
			// they should not have similar references to eachother...
			assertTrue( TestBean( beanOne ).getId() != SecondBean( beanTwo ).friendBean.getId() );
			
			// make sure they are internally consistent
			assertTrue( TestBean( beanOne ).getId() == TestBean( beanOne ).friendBean.friendBean.getId() );
			assertTrue( SecondBean( beanTwo ).getId() == SecondBean( beanTwo ).friendBean.friendBean.getId() );
		}
	}
}