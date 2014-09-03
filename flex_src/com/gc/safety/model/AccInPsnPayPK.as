package com.gc.safety.model
{
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.safety.po.AccInPsnPayPK")]
  [Bindable]
  public dynamic class AccInPsnPayPK
  {
    public var branch:Branch;
    public var accId:int;
    public var no:int;
    public var payDate:Date;

    public function AccInPsnPayPK(branch:Branch=null, accId:int=0, no:int=0, payDate:Date=null)
    {
      this.branch = branch;
      this.accId = accId;
      this.no = no;
      this.payDate = payDate;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function toString():String
    {
      return "(belong="+branch+", accId="+accId+", no="+no+", payDate="+payDate.toLocaleDateString()+")";
    }

  }
}