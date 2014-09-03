package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.SafetyClose")]
	[Bindable]
	public dynamic class SafetyClose
	{
		public var id:SafetyClosePK;
		public var comment:String;
		
		public function SafetyClose(branch:Branch=null, date:Date=null)
		{
			this.id = new SafetyClosePK(branch, date);
		}

	}
}