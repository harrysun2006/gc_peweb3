package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	import com.gc.common.model.Person;
	
	[RemoteClass(alias="com.gc.safety.po.AccInPsnGuaPay")]
	[Bindable]
	public dynamic class AccInPsnGuaPay
	{
		public var id:AccInPsnGuaPayPK;
		public var payDate:Date;
		public var mediFee:Number;
		public var other1:Number;
		public var other2:Number;
		public var payDesc:String;
		public var payPsn:Person;
		public var appRefNo:String;
		public var appNo:String;
		public var fkAccInPsnGua:AccInPsnGua;
		
		//赔付总金额
		public var paySum:Number;
		
		public function AccInPsnGuaPay(branch:Branch=null, refNo:String=null, no:String=null)
		{
			this.id = new AccInPsnGuaPayPK(branch, refNo, no);
		}

	}
}