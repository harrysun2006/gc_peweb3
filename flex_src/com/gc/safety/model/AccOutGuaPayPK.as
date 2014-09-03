package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.AccOutGuaPayPK")]
	[Bindable]
	public dynamic class AccOutGuaPayPK
	{
		public var branch:Branch;
		public var refNo:String;
		public var no:String;
		
		public function AccOutGuaPayPK(branch:Branch=null, refNo:String=null, no:String=null)
		{
			this.branch = branch;
			this.refNo = refNo;
			this.no = no;
		}

	}
}