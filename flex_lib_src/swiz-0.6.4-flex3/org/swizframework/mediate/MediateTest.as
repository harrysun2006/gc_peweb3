package org.swizframework.mediate {
	import flash.events.Event;
	
	import mx.core.Application;
	import mx.core.UIComponent;
	import mx.managers.ISystemManager;
	
	import org.flexunit.Assert;
	import org.swizframework.Swiz;
	import org.swizframework.events.CentralDispatcher;
	import org.swizframework.mediate.helper.FooEvent;
	
	/**
	 *
	 * Tests
	 * - basic mediate
	 * - strict
	 * - eventPackages
	 * - priority
	 * - bubbeling
	 * - pass typed event
	 *
	 */
	public class MediateTest extends UIComponent {
		
		[BeforeClass]
		public static function beforeClass() : void {
			Swiz.setStrict( true, "org.swizframework.mediate.helper" );
			Swiz.setMediateBubbledEvents( true );
		}
		
		[AfterClass]
		public static function afterClass() : void {
		}
		
		[Before]
		public function before() : void {
			UIComponent( Swiz.application ).addChild( this );
		}
		
		[After]
		public function after() : void {
			UIComponent( Swiz.application ).removeChild( this );
		}
		
		// ######################################################################
		
		private var bar:Object;
		
		[Test]
		public function testMediate() : void {
			dispatchEvent( new BarEvent( BarEvent.BAR ) );
			Assert.assertNotNull( bar );
		}
		
		[Mediate( event="bar" )]
		public function barHandler() : void {
			bar = {};
		}
		
		// ######################################################################
		
		[Test]
		public function testPriorityBubble() : void {
			var e:Event = new FooEvent( FooEvent.FOO );
			var notCanceled:Boolean = dispatchEvent( e );
			Assert.assertTrue( notCanceled );
			
			Assert.assertTrue( e.currentTarget is ISystemManager );
			Assert.assertEquals( e.target, this );
		}
		
		[Test]
		public function testPriorityCentralEventDispatcher() : void {
			var e:Event = new FooEvent( FooEvent.FOO );
			var notCanceled:Boolean = Swiz.dispatchEvent( e );
			Assert.assertTrue( notCanceled );
			
			Assert.assertTrue( e.currentTarget is CentralDispatcher );
			Assert.assertTrue( e.target is CentralDispatcher );
		}
		
		[Mediate( event="FooEvent.FOO",priority="0" )]
		public function handleLast( e : Event ) : void {
			e.preventDefault();
		}
		
		[Mediate( event="FooEvent.FOO",priority="1" )]
		public function handleSecond( e : FooEvent ) : void {
			e.stopImmediatePropagation();
		}
	
	}
}

import flash.events.Event;

internal class BarEvent extends Event {
	public static const BAR : String = "bar";
	
	public function BarEvent( type : String, bubbles : Boolean = true, cancelable : Boolean = true ) {
		super( type, bubbles, cancelable );
	}

}