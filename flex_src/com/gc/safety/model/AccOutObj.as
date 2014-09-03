package com.gc.safety.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.safety.po.AccOutObj")]
  [Bindable]
  public dynamic class AccOutObj
  {
    public var id:AccOutObjPK;
    public var obj:AccObject;
    public var tel:String;
    public var address:String;
    public var duty:AccDuty;
    public var desc:String;
    public var payFee:Number;
    public var payDate:Date;
    public var payDesc:String;
    public var payPsn:Person;
    public var fkAccident:Accident;

    public function AccOutObj(branch:Branch=null, accId:int =0, no:int=0)
    {
      this.id = new AccOutObjPK(branch, accId, no);
    }

  }
}