package org.swizframework.mediate.helper {
	import flash.events.Event;
	
	public class FooEvent extends Event {
		public static const FOO : String = "foo";
		
		public function FooEvent( type : String, bubbles : Boolean = true, cancelable : Boolean = true ) {
			super( type, bubbles, cancelable );
		}
	}
}