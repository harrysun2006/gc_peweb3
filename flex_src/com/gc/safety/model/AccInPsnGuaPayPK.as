package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.AccInPsnGuaPayPK")]
	[Bindable]
	public dynamic class AccInPsnGuaPayPK
	{
		public var branch:Branch;
		public var refNo:String;
		public var no:String;
		
		public function AccInPsnGuaPayPK(branch:Branch=null, refNo:String=null, no:String=null)
		{
			this.branch = branch;
			this.refNo = refNo;
			this.no = no;
		}

	}
}