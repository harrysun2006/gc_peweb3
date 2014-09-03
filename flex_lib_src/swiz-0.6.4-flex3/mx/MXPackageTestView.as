package mx {
	import mx.core.UIComponent;
	
	public class MXPackageTestView extends UIComponent {
		[Autowire( bean="barController" )]
		public var barController:Object;
		
		public function MXPackageTestView() {
			super();
		}
	
	}
}