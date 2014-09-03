package org.swizframework.autowire {
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.core.Application;
	import mx.core.UIComponent;
	
	import org.flexunit.Assert;
	import org.swizframework.Swiz;
	
	public class AutowireViewTest extends UIComponent {
		
		private var _view:AutowireViewTest;
		
		[Autowire( view="true" )]
		public function set testView( view : AutowireViewTest ) : void {
			Assert.assertTrue( view is AutowireViewTest );
			_view = view;
		}
		
		[Before]
		public function before() : void {
		
		}
		
		
		[Test]
		public function testAutowireView() : void {
			Assert.assertNull( _view );
			var t:Timer = new Timer( 500, 1 );
			//t.addEventListener(TimerEvent.TIMER_COMPLETE, Async.asyncHandler( this, onTimer, 1000 ), false, 0, true );
			t.addEventListener( TimerEvent.TIMER_COMPLETE, function( e : TimerEvent ) : void
				{
					Assert.assertNotNull( _view );
					UIComponent( Swiz.application ).removeChild( _view );
				} );
			t.start();
			UIComponent( Swiz.application ).addChild( this );
		}
	
	}
}