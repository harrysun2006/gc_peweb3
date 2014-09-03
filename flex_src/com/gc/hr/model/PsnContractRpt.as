package com.gc.hr.model
{
	[RemoteClass(alias="com.gc.hr.po.PsnContractRpt")]
	[Bindable]
	public dynamic class PsnContractRpt
	{
		public var id:String;
	
		public var  workId:String;
	
		public var  name:String;
	
		public var  sex:String;
	
		public var  barthday:Date;
	
		public var  pid:String;
	
		public var  workLength:String;//进单位前工龄
	
		public var  regBlong:String; //所属公司（部门）
	
		public var  position:String;//所在岗位
	
		public var  workType:String;//职位
	
		public var  type:String;//用工类别
	
		public var  party:String;//政治面貌
	
		public var  totalContract:String;
	
		public var  contarctNo:String;
	
		public var  contractBegin:Date;
		
		public var  contractEnd:Date;
		
		public function PsnContractRpt()
		{
		}

	}
}