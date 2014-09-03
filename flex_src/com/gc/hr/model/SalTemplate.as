package com.gc.hr.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;

  [RemoteClass(alias="com.gc.hr.po.SalTemplate")]
  [Bindable]
  public dynamic class SalTemplate
  {
    public var id:int;
    public var branch:Branch;
    public var depart:Department;
    public var name:String;
    public var comment:String;

    public function SalTemplate()
    {
    }

    public function get uid():String
    {
      return "st#" + id;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get departId():int
    {
      return depart ? depart.id : 0;
    }

    public function get departName():String
    {
      return depart ? depart.name : null;
    }

    public function toString():String
    {
      return "SalTemplate{id=" + id + ", belong=" + branchId + ", depart=" + departId + ", name=" + name + "}";
    }

  }
}