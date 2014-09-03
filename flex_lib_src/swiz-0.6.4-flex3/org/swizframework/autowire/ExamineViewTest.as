package org.swizframework.autowire {
	import flash.FlashPackageTestView;
	
	import mx.MXPackageTestView;
	import mx.core.UIComponent;
	import mx.logging.ILogger;
	import mx.logging.Log;
	import mx.logging.LogEventLevel;
	
	import org.flexunit.Assert;
	import org.swizframework.Swiz;
	import org.swizframework.autowire.helper.TestView;
	import org.swizframework.autowire.helper.Test_View;
	
	public class ExamineViewTest {
		private static const logger : ILogger = Log.getLogger( "ExamineViewTest" );
		
		[BeforeClass]
		public static function beforeClass() : void {
			logger.debug( "beforeClass" );
			var bc:BarController = new BarController();
			Swiz.setLogLevel( LogEventLevel.DEBUG ).addBean( "barController", bc );
		}
		
		[AfterClass]
		public static function afterClass() : void {
			Swiz.getInstance().resetViewPackages();
		}
		
		[Before]
		public function before() : void {
			logger.debug( "before" );
			Swiz.getInstance().resetViewPackages();
		}
		
		private function get app() : UIComponent {
			return Swiz.application as UIComponent;
		}
		
		[Test]
		public function testWithoutViewPackages() : void {
			var testView:TestView = new TestView();
			app.addChild( testView );
			Assert.assertNotNull( testView.barController );
			app.removeChild( testView );
		}
		
		[Test]
		public function testWithViewPackagesNegative() : void {
			Swiz.getInstance().addViewPackage( "foo" );
			var testView:TestView = new TestView();
			app.addChild( testView );
			Assert.assertNull( testView.barController );
			app.removeChild( testView );
		}
		
		[Test]
		public function testWithViewPackagesPositive1() : void {
			Swiz.getInstance().addViewPackage( "org" );
			var testView:TestView = new TestView();
			app.addChild( testView );
			Assert.assertNotNull( testView.barController );
			app.removeChild( testView );
		}
		
		[Test]
		public function testWithViewPackagesPositive2() : void {
			Swiz.getInstance().addViewPackage( "org.swizframework.autowire.helper" );
			var testView:TestView = new TestView();
			app.addChild( testView );
			Assert.assertNotNull( testView.barController );
			app.removeChild( testView );
		}
		
		[Test]
		public function testIgnoreUnderscore() : void {
			Swiz.getInstance().addViewPackage( "org.swizframework.autowire.helper" );
			var testView:Test_View = new Test_View();
			app.addChild( testView );
			Assert.assertNull( testView.barController );
			app.removeChild( testView );
		}
		
		[Test]
		public function testIgnoreFlashPackage() : void {
			var testView:FlashPackageTestView = new FlashPackageTestView();
			app.addChild( testView );
			Assert.assertNull( testView.barController );
			app.removeChild( testView );
		}
		
		[Test]
		public function testIgnoreMXPackage() : void {
			var testView:MXPackageTestView = new MXPackageTestView();
			app.addChild( testView );
			Assert.assertNull( testView.barController );
			app.removeChild( testView );
		}
		
		[Test]
		public function testAutowireSideEffect() : void {
			
			// Beanfactory.autowire adds and removes an empty view package so let's
			// test this has no side effects
			Swiz.autowire( this );
			
			Swiz.getInstance().addViewPackage( "com" );
			var testView:TestView = new TestView();
			app.addChild( testView );
			Assert.assertNull( testView.barController );
			app.removeChild( testView );
		}
	
	}
}

internal class BarController {
}