package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.AccInPsnGuaPK")]
	[Bindable]
	public dynamic class AccInPsnGuaPK
	{
		public var branch:Branch;
		public var refNo:String;
		public var no:String;
		
		public function AccInPsnGuaPK(branch:Branch = null, refNo:String = null, no:String = null)
		{
			this.branch = branch;
			this.refNo = refNo;
			this.no = no;
		}
		
		public function toString():String
		{
			return "AccInPsnGuaPK{Branch=" + branch +" , refNo=" + refNo + " , no=" + no + "}";
		}
	}
}