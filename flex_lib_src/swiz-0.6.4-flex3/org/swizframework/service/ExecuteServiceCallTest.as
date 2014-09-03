package org.swizframework.service {
	import mx.rpc.AsyncToken;
	import mx.rpc.events.ResultEvent;
	
	import org.flexunit.Assert;
	import org.swizframework.Swiz;
	import org.swizframework.util.TestUtil;
	
	public class ExecuteServiceCallTest {
		
		[Test]
		public function testEventArgs() : void {
			var result:Boolean = true;
			var call:AsyncToken = TestUtil.mockResult( result, 100, false );
			var foo:FakeClass = new FakeClass( 1 );
			
			Swiz.executeServiceCall( call, function( re : ResultEvent, fc : FakeClass, s : String, b : Boolean ) : void
				{
					Assert.assertNotNull( "faceClass param not passed", fc );
					Assert.assertNotNull( "s param not passed", s );
					Assert.assertTrue( "b param should be true", b )
				}, null, [ foo, "hey dude", true ] );
		}
	}
}

class FakeClass {
	public var id:Number;
	
	public function FakeClass( id : Number ) {
		this.id = id;
	}
}