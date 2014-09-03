package com.gc.hr.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;
  import com.gc.common.model.Person;


  [RemoteClass(alias="com.gc.hr.po.SalDeptPsn")]
  [Bindable]
  public dynamic class SalDeptPsn
  {
    public var id:SalDeptPsnPK;
    public var bank:String;
    public var bankCard:String;
    public var comment:String;

    public function SalDeptPsn()
    {
    }

    public function get uid():String
    {
      return "sdp#" + branchId + "#" + departId + "#" + personId;
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

    public function get personWorkerId():String
    {
      return id ? id.personWorkerId : null;
    }

    public function toString():String
    {
      return "SalDeptPsn{belong=" + branchId + ", depart=" + departId + ", person=" + personId + "}";
    }
  }
}