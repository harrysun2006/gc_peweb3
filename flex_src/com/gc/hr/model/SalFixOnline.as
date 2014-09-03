package com.gc.hr.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.hr.po.SalFixOnline")]
  [Bindable]
  public dynamic class SalFixOnline
  {
    public var id:SalFixOnlinePK;
    public var downDate:Date;
    public var amount:Number;
    public var comment:String;

    public function SalFixOnline()
    {
      id=new SalFixOnlinePK();
    }

    public function get branch():Branch
    {
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get depart():Department
    {
      return id ? id.depart : null;
    }

    public function get departId():int
    {
      return id ? id.departId : 0;
    }

    public function get person():Person
    {
      return id ? id.person : null;
    }

    public function get personId():int
    {
      return id ? id.personId : 0;
    }

    public function get item():SalItem
    {
      return id ? id.item : null;
    }

    public function get itemId():int
    {
      return id ? id.itemId : 0;
    }

    public function get onDate():Date
    {
      return id ? id.onDate : null;
    }

    public function set onDate(value:Date):void
    {
      if (id)
        id.onDate=value;
    }

    public function toString():String
    {
      return "SalFixOnline{belong=" + branchId + ", depart=" + departId + ", person=" + personId + ", item=" + itemId + ", amount=" + amount + "}";
    }
  }
}