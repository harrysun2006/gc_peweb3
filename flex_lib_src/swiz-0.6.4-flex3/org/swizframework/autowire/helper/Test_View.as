package org.swizframework.autowire.helper {
	import mx.core.UIComponent;
	
	public class Test_View extends UIComponent {
		
		[Autowire( bean="barController" )]
		public var barController:Object;
		
		public function Test_View() {
			super();
		}
	
	}
}