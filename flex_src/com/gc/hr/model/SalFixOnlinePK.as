package com.gc.hr.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.hr.po.SalFixOnlinePK")]
  [Bindable]
  public dynamic class SalFixOnlinePK
  {
    public var id:int;
    public var branch:Branch;
    public var depart:Department;
    public var person:Person;
    public var item:SalItem;
    public var onDate:Date;

    public function SalFixOnlinePK()
    {
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get departId():int
    {
      return depart ? depart.id : 0;
    }

    public function get personId():int
    {
      return person ? person.id : 0;
    }

    public function get itemId():int
    {
      return item ? item.id : 0;
    }

    public function toString():String
    {
      return "(belong=" + branchId + ", depart=" + departId + ", person=" + personId + ", item=" + itemId + ")";
    }
  }
}