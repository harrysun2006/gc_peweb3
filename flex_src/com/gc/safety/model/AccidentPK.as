package com.gc.safety.model
{
	import com.gc.Constants;
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.AccidentPK")]
	[Bindable]
	public dynamic class AccidentPK
	{
		public var branch:Branch;
		public var id:int;
		
		public function AccidentPK(branch:Branch = null, id:int = 0)
		{
			this.branch = branch;
			this.id = id;
		}
		
		public function get label():String
		{
			return (id == 0) ? Constants.NULL_LABEL : String(id);
		}
		
		public function get value():Object
		{
			return (id == 0) ? Constants.NULL_VALUE : id;
		}
		
		public function get icon():Class
		{
			return Constants.ICON16_ACCIDENT;
		}

	}
}