package com.gc.hr.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.hr.po.SalDeptPsnPK")]
  [Bindable]
  public dynamic class SalDeptPsnPK
  {
    public var branch:Branch;
    public var depart:Department;
    public var person:Person;

    public function SalDeptPsnPK()
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

    public function get personWorkerId():String
    {
      return person ? person.workerId : null;
    }

    public function toString():String
    {
      return "(belong=" + branchId + ", depart=" + departId + ", person=" + personId + ")";
    }
  }
}