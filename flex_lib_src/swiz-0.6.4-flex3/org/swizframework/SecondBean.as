package org.swizframework {
	import mx.utils.UIDUtil;
	
	public class SecondBean {
		[Autowire( bean="protoBean" )]
		public var friendBean : TestBean;
		
		public function SecondBean() {
		}
		
		public function sayHi() : void {
			trace( "Hello! My UID is " + getId() + ", my friend's UID is " + friendBean.getId() );
		}
		
		public function getId() : String {
			return UIDUtil.getUID( this );
		}
	}
}