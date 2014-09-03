package com.gc.safety.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.safety.po.AccInPsnPay")]
  [Bindable]
  public dynamic class AccInPsnPay
  {
    public var id:AccInPsnPayPK;
    public var payPsn:Person;
    public var mediFee:Number;
    public var other1:Number;
    public var other2:Number;
    public var payDesc:String;
    public var fkAccInPsn:AccInPsn;

    public function AccInPsnPay(branch:Branch=null, accId:int=0, no:int=0, payDate:Date=null)
    {
      this.id = new AccInPsnPayPK(branch, accId, no, payDate);
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get accId():int
    {
      return id ? id.accId : 0;
    }

    public function get no():int
    {
      return id ? id.no : 0;
    }

    public function get payDate():Date
    {
      return id ? id.payDate : null;
    }

    public function toString():String
    {
      return "AccInPsnPay{belong="+branchId+", accId="+accId+", no="+no+", payDate="+payDate.toLocaleDateString()
        +"mediFee="+mediFee+", payPsn="+payPsn+"}";
    }

  }
}