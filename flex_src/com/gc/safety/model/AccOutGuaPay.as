package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	import com.gc.common.model.Person;
	
	[RemoteClass(alias="com.gc.safety.po.AccOutGuaPay")]
	[Bindable]
	public dynamic class AccOutGuaPay
	{
		public var id:AccOutGuaPayPK;
		public var payDate:Date;
		public var objSum:Number;
		public var mediFee:Number;
		public var other1:Number;
		public var other2:Number;
		public var payDesc:String;
		public var payPsn:Person;
		public var appRefNo:String;
		public var appNo:String;
		public var fkAccOutGua:AccOutGua;
		
		//赔付总金额
		public var paySum:Number;
		// 以下字段仅用于做理赔凭证时显示用
		public var payObjSum:Number;
		public var payMediFee:Number;
		public var payOther1:Number;
		public var payOther2:Number;
		
		public function AccOutGuaPay(branch:Branch=null, refNo:String=null, no:String=null)
		{
			this.id = new AccOutGuaPayPK(branch, refNo, no);
		}

	}
}