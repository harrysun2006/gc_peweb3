package com.gc.hr.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.hr.po.SalFactPK")]
  [Bindable]
  public dynamic class SalFactPK
  {
    public var branch:Branch;
    public var no:String;

    public function SalFactPK(branch:Branch=null, no:String=null)
    {
      this.branch=branch;
      this.no=no;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function toString():String
    {
      return "(belong=" + branchId + ", no=" + no + ")";
    }

  }

}