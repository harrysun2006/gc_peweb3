package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.AccOutGuaPK")]
	[Bindable]
	public dynamic class AccOutGuaPK
	{
		public var branch:Branch;
		public var refNo:String;
		public var no:String;
		
		public function AccOutGuaPK(branch:Branch=null, refNo:String=null, no:String=null)
		{
			this.branch = branch;
			this.refNo = refNo;
			this.no = no;
		}
		
		public function toString():String
		{
			return "AccOutGuaPK{refNo = " + refNo + ", no = " + no + "}";
		}

	}
}