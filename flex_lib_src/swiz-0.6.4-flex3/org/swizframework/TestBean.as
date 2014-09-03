package org.swizframework {
	import mx.utils.UIDUtil;
	
	public class TestBean implements ITestBean {
		[Autowire]
		public var friendBean : SecondBean;
		
		public function TestBean() {
		}
		
		public function sayHi() : void {
			trace( "Hello! My UID is " + getId() + ", my friend's UID is " + friendBean.getId() );
		}
		
		public function getId() : String {
			return UIDUtil.getUID( this );
		}
	}
}