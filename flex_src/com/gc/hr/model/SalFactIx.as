package com.gc.hr.model
{
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.SalFactIx")]
  [Bindable]
  public dynamic class SalFactIx
  {
    public var id:SalFactIxPK;
    public var no:int;

    public function SalFactIx(branch:Branch=null, hdNo:String=null, no:int=0)
    {
      this.id=new SalFactIxPK(new SalFact(branch, hdNo));
      this.no=no;
    }

    public function get fact():SalFact
    {
      return id ? id.fact : null;
    }

    public function get branch():Branch
    {
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get hdNo():String
    {
      return id ? id.hdNo : null;
    }

    public function get item():SalItem
    {
      return id ? id.item : null;
    }

    public function get itemId():int
    {
      return item ? item.id : 0;
    }

    public function get itemNo():String
    {
      return item ? item.no : null;
    }

    public function toString():String
    {
      return "SalFactIx{belong=" + branchId + ", hdNo=" + hdNo + ", item=" + itemId + ", no=" + no + "}";
    }

  }
}