package org.swizframework.autowire {
	import org.flexunit.Assert;
	import org.swizframework.Swiz;
	
	public class AutowireTest {
		
		[Autowire( bean="primitiveString" )]
		public var primitiveString:String;
		
		[Autowire( bean="barController" )]
		public var barController:BarController;
		
		[Autowire]
		public var typeBarController:BarController;
		
		[Autowire]
		public var iBarController:IBarController;
		
		[Bindable]
		[Autowire( bean="barController",property="someValue",twoWay="true" )]
		public var someValue:int = -1;
		
		[Autowire( bean="barController",property="user" )]
		public var user:User;
		
		[Before]
		public function setUp() : void {
			Swiz.getInstance().addBean( "primitiveString", new String( "hey dude" ) );
			var bc:IBarController = new BarController();
			Swiz.getInstance().addBean( "barController", bc );
			Swiz.getInstance().autowire( this );
		}
		
		[Test( description="test autowire primitive String" )]
		public function testAutowirePrimitiveBean() : void {
			Assert.assertNotNull( "primitiveString still null. Autowire primitive bean does not work.", primitiveString )
		}
		
		[Test]
		public function testAutowireByName() : void {
			Assert.assertNotNull( "barController still null. AutowireByType does not work.", barController )
		}
		
		[Test]
		public function testAutowireByType() : void {
			Assert.assertNotNull( "typeBarController still null. AutowireByType does not work.", typeBarController )
		}
		
		[Test]
		public function testAutowireByInterfaceType() : void {
			Assert.assertNotNull( "iBarController still null. AutowireByType with interface does not work.", iBarController )
		}
		
		[Test]
		public function testAutowirePrimitiveProperty() : void {
			// check injection
			Assert.assertEquals( "someValue still -1. AutowireByType with primitive property does not work.", this.someValue, barController.someValue )
			
			// check twoWay
			someValue = -1;
			Assert.assertEquals( barController.someValue, this.someValue );
		}
		
		[Test]
		public function testAutowireProperty() : void {
			Assert.assertNotNull( "iBarController still null. AutowireByType with property does not work.", this.user );
			Assert.assertEquals( this.user.name, "Foo" );
		}
	
	
	}
}

internal interface IBarController {
}

internal class BarController implements IBarController {
	[Bindable]
	public var someValue:int = 5;
	public var user:User = new User( "Foo" );
}

internal class User {
	public var name:String;
	
	public function User( name : String ) {
		this.name = name;
	}
}