package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;

  [RemoteClass(alias="com.gc.hr.po.ChkGroup")]
  [Bindable]
  public dynamic class ChkGroup
  {
    public var id:int;
    public var branch:Branch;
    public var no:String;
    public var name:String;
    public var depart:Department;
    public var comment:String;

    public function ChkGroup(id:int=0)
    {
      this.id=id;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get label():String
    {
      return name ? name : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return id;
    }

    public function get icon():Class
    {
      return null;
    }

    public function toString():String
    {
      return "CheckGroup{id=" + id + ", belong=" + branchId + ", no=" + no + ", name=" + name + "}";
    }

  }
}