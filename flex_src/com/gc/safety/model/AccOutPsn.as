package com.gc.safety.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;
  import com.gc.util.CommonUtil;

  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.safety.po.AccOutPsn")]
  [Bindable]
  public dynamic class AccOutPsn
  {
    public var id:AccOutPsnPK;
    public var name:String;
    public var sex:String;
    public var age:int;
    public var regAddress:String;
    public var address:String;
    public var tel:String;
    public var duty:AccDuty;
    public var desc:String;
    public var maim:Object;
    public var accOutPsnPays:ArrayCollection;
    public var fkAccident:Accident;

//		public var mediFee:Number;
//		public var other1:Number;
//		public var other2:Number;
//		public var payDesc:String;
//		public var payDate:Date;
//		public var payPsn:Person;

    public var statusStr:String;

    public function AccOutPsn(branch:Branch = null, accId:int = 0, no:int = 0)
    {
      this.id = new AccOutPsnPK(branch, accId, no);
    }

    public function get status():int
    {
      return (maim == null) ? 0 : maim.value;
    }

    public function set status(value:int):void
    {
      var k:int=CommonUtil.indexOfKey(Constants.MAIMS, value, "value");
      maim=(k >= 0 && k <= 1) ? Constants.MAIMS[k] : null;
    }
  }
}