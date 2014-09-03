package com.gc.hr.model
{
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.SalFactDPK")]
  [Bindable]
  public dynamic class SalFactDPK
  {
    public var fact:SalFact;
    public var no:int;
    public var item:SalItem;

    public function SalFactDPK(fact:SalFact=null, no:int=0)
    {
      this.fact=fact;
      this.no=no;
    }

    public function get factId():SalFactPK
    {
      return fact ? fact.id : null;
    }

    public function get branch():Branch
    {
      var id:SalFactPK=factId;
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      var id:SalFactPK=factId;
      return id ? id.branchId : 0;
    }

    public function get hdNo():String
    {
      var id:SalFactPK=factId;
      return id ? id.no : null;
    }

    public function get itemId():int
    {
      return item ? item.id : 0;
    }

    public function toString():String
    {
      return "(belong=" + branchId + ", hdNo=" + hdNo + ", no=" + no + ", item=" + itemId + ")";
    }

  }
}