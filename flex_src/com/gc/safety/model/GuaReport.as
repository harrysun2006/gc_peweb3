package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.GuaReport")]
	[Bindable]
	public dynamic class GuaReport
	{
		public var id:GuaReportPK;
		public var reportNo:String;
		public var closeNo:String;
		
		public function GuaReport(branch:Branch=null, accId:int = 0, insurer:Insurer=null)
		{
			this.id = new GuaReportPK(branch,accId,insurer);
		}

	}
}