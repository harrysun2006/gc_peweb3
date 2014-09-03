package org.swizframework.autowire.helper {
	import mx.core.UIComponent;
	
	public class TestView extends UIComponent {
		
		[Autowire( bean="barController" )]
		public var barController:Object;
		
		public function TestView() {
			super();
		}
	
	}
}