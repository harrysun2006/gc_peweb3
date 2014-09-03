package com.gc.safety.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.safety.po.AccOutPsnPay")]
  [Bindable]
  public dynamic class AccOutPsnPay
  {
    public var id:AccOutPsnPayPK;
    public var mediFee:Number;
    public var other1:Number;
    public var other2:Number;
    public var payDesc:String;
    public var payPsn:Person;
    public var fkAccOutPsn:AccOutPsn;

    public function AccOutPsnPay(branch:Branch=null, accId:int=0, no:int=0, payDate:Date=null)
    {
      this.id = new AccOutPsnPayPK(branch, accId, no, payDate);
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
      return "AccOutPsnPay{belong="+branchId+", accId="+accId+", no="+no+", payDate="+payDate.toLocaleDateString()
        +", mediFee="+mediFee+", other1="+other1+", other2="+other2+", payPsn="+payPsn + "}";
    }

  }
}