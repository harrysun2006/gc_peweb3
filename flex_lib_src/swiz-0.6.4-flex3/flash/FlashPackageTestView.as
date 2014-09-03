package flash {
	import mx.core.UIComponent;
	
	public class FlashPackageTestView extends UIComponent {
		[Autowire( bean="barController" )]
		public var barController:Object;
		
		public function FlashPackageTestView() {
			super();
		}
	
	}
}