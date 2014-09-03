package com.gc.safety.model
{
	import com.gc.common.model.Branch;
	import com.gc.common.model.Person;
	
	[RemoteClass(alias="com.gc.safety.po.AccInPsnGua")]
	[Bindable]
	public dynamic class AccInPsnGua
	{
		public var id:AccInPsnGuaPK;
		public var accident:Accident;
		public var insurer:Insurer;
//		public var cstmNo:int;
		public var appDate:Date;
		public var mediFee:Number;
		public var other1:Number;
		public var other2:Number;
		public var appDesc:String;
		public var appPsn:Person;
		public var fkGuaReport:GuaReport;
		public var fkAccInPsn:AccInPsn;
		
		// 客伤理赔信息显示
		public var cstmName:String;
		public var guaSum:Number;
		
		// 以下字段在建立理赔凭证时验证用
		public var hasGuarInfo:Boolean;
		public var hasAccInPsnGua:Boolean;
		
		public function AccInPsnGua(branch:Branch=null, refNo:String=null, no:String=null)
		{
			this.id = new AccInPsnGuaPK(branch, refNo, no);
		}
		
		public function toString():String
		{
			return "AccInPsnGua{id=" + id + "}";
		}

	}
}