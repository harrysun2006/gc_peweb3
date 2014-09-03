package com.gc.safety.view.claims
{
	import mx.validators.ValidationResult;
	import mx.validators.Validator;

	public class BooleanValidator extends Validator
	{
		private var _bool:Boolean;
		private var results:Array;
		
		
		public function BooleanValidator(boolVal:Boolean)
		{
			super();
			_bool = boolVal;
		}
		
		override protected function doValidation(value:Object):Array
		{
			results = [];
			results = super.doValidation(value);
			if (results.length > 0)
				return results;
			
			if (value is Boolean)
			{
				if (value != _bool)
					results.push(new ValidationResult(true, "value", null, null));
			}
			else
			{
				results.push(new ValidationResult(true, "value", "Error args type!", "Enter right type!"));
			}
			
			return results;
			
		}
		
	}
}