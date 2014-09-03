package org.swizframework.util {
	import net.digitalprimates.fluint.tests.TestCase;
	
	import org.swizframework.EventConstants;
	import org.swizframework.EventHolder;
	import org.swizframework.Swiz;
	import org.swizframework.error.ExpressionError;
	
	public class ExpressionUtilsTest extends TestCase {
		
		[Test( description="Tests ExpressionUtils to retrieve static members from beans." )]
		public function findStaticProps() : void {
			Swiz.addBean( "events", new EventConstants() );
			// now fetch some static event types
			var type : String;
			type = ExpressionUtils.evaluate( "${events.EVENT_ONE}" );
			assertEquals( type, EventConstants.EVENT_ONE );
			type = ExpressionUtils.evaluate( "${events.EVENT_TWO}" );
			assertEquals( type, EventConstants.EVENT_TWO );
			type = ExpressionUtils.evaluate( "${events.EVENT_THREE}" );
			assertEquals( type, EventConstants.EVENT_THREE );
		}
		
		[Test( description="Tests ExpressionUtils to retrieve instance members from beans." )]
		public function findInstanceProps() : void {
			// make an event constants object, give it to Swiz
			var events : EventConstants = new EventConstants();
			Swiz.addBean( "events", events );
			// now fetch some instance event types
			var type : String;
			type = ExpressionUtils.evaluate( "${events.eventFour}" );
			assertEquals( type, events.eventFour );
			type = ExpressionUtils.evaluate( "${events.eventFive}" );
			assertEquals( type, events.eventFive );
		}
		
		[Test( description="Tests ExpressionUtils to retrieve nested members from beans." )]
		public function findNestedProps() : void {
			var holder : EventHolder = new EventHolder();
			var events : EventConstants = new EventConstants();
			Swiz.addBean( "holder", holder );
			var type : String;
			// test a nested static type, and instance type
			type = ExpressionUtils.evaluate( "${holder.EVENTS.EVENT_ONE}" );
			assertEquals( type, EventConstants.EVENT_ONE );
			type = ExpressionUtils.evaluate( "${holder.EVENTS.eventFour}" );
			assertEquals( type, events.eventFour );
			type = ExpressionUtils.evaluate( "${holder.events.EVENT_ONE}" );
			assertEquals( type, EventConstants.EVENT_ONE );
			type = ExpressionUtils.evaluate( "${holder.events.eventFour}" );
			assertEquals( type, events.eventFour );
		}
		
		[Test( description="Tests ExpressionUtils to recieve a proper error when bean / property is not found." )]
		public function causePropertyError() : void {
			try {
				var type : String = ExpressionUtils.evaluate( "${foo.BAR}" );
			} catch ( e : Error ) {
				assertTrue( e is ExpressionError );
			}
		}
	}
}