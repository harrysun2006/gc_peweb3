package com.gc.safety.model
{
	import com.gc.Constants;
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.SafetyClosePK")]
	[Bindable]
	public dynamic class SafetyClosePK
	{
		public var branch:Branch;
		public var date:Date;
		
		public function SafetyClosePK(branch:Branch = null, date:Date = null)
		{
			this.branch = branch;
			this.date = date;
		}

	}
}