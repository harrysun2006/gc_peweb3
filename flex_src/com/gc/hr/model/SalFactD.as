package com.gc.hr.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.hr.po.SalFactD")]
  [Bindable]
  public dynamic class SalFactD
  {
    public var id:SalFactDPK;
    public var person:Person;
    public var amount:Number;

    public function SalFactD(branch:Branch=null, hdNo:String=null, no:int=0)
    {
      this.id=new SalFactDPK(new SalFact(branch, hdNo), no);
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

    public function get no():int
    {
      return id ? id.no : 0;
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

    public function get personId():int
    {
      return person ? person.id : 0;
    }

    public function toString():String
    {
      return "SalFactD{belong=" + branchId + ", hdNo=" + hdNo + ", no=" + no + ", item=" + itemId + ", person=" + personId + "}";
    }

  }
}