package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.AccOutPsnPK")]
	[Bindable]
	public dynamic class AccOutPsnPK
	{
		public var branch:Branch;
		public var accId:int;
		public var no:int;
		
		public function AccOutPsnPK(branch:Branch=null, accId:int = 0, no:int=0)
		{
			this.branch = branch;
			this.accId = accId;
			this.no = no;
		}

	}
}