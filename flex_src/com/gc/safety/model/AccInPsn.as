package com.gc.safety.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;
  import com.gc.util.CommonUtil;

  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.safety.po.AccInPsn")]
  [Bindable]
  public dynamic class AccInPsn
  {
    public var id:AccInPsnPK;
    public var name:String;
    public var sex:String;
    public var age:int;
    public var regAddress:String;
    public var address:String;
    public var tel:String;
    public var duty:AccDuty;
    public var desc:String;
    public var accInPsnPays:ArrayCollection;
    public var fkAccident:Accident;

    public var maim:Object;
//		public var mediFee:Number;
//		public var other1:Number;
//		public var other2:Number;

//		public var payDesc:String;
//		public var payDate:Date;
//		public var payPsn:Person;

    public var getDate:Date;
    public var getRefNo:String;
    public var getSum:Number=0;

    public var statusStr:String;

    public function AccInPsn(branch:Branch = null, accId:int = 0, no:int = 0)
    {
      this.id = new AccInPsnPK(branch, accId, no);
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