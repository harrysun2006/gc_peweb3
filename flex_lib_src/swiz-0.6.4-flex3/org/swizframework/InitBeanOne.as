package org.swizframework {
	import org.swizframework.factory.IInitializingBean;
	
	public class InitBeanOne implements IInitializingBean {
		private var _data : String;
		
		public function InitBeanOne() {
		}
		
		public function initialize() : void {
			trace( "Initializing data!" );
			_data = "I'm InitBeanOne!";
		}
		
		public function getData() : String {
			return _data;
		}
	
	}
}