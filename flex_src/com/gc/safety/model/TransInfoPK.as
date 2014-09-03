package com.gc.safety.model
{
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.safety.po.TransInfoPK")]
  [Bindable]
  public dynamic class TransInfoPK
  {
    public var branch:Branch;
    public var accNo:String;
    public var no:int;

    public function TransInfoPK(branch:Branch=null,accNo:String=null,no:int=0)
    {
      this.branch = branch;
      this.accNo = accNo;
      this.no = no;
    }
  }
}