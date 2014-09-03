package org.swizframework {
	import org.swizframework.factory.IInitializingBean;
	
	public class InitBeanTwo implements IInitializingBean {
		[Autowire( bean="initOne" )]
		public var bean : InitBeanOne;
		
		private var _data : String;
		
		public function InitBeanTwo() {
		}
		
		public function initialize() : void {
			trace( "Initializing data!" );
			_data = "I'm InitBeanTwo!";
		}
		
		public function getData() : String {
			return _data;
		}
		
		public function getFriend() : InitBeanOne {
			return bean;
		}
	
	}
}