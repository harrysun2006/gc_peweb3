package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.GuaReportPK")]
	[Bindable]
	public dynamic class GuaReportPK
	{
		public var branch:Branch;
		public var accId:int;
		public var insurer:Insurer;
		
		public function GuaReportPK(branch:Branch=null, accId:int = 0, insurer:Insurer=null)
		{
			this.branch = branch;
			this.accId = accId;
			this.insurer = insurer;
		}

	}
}