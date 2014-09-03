package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.WorkTypePK")]
  [Bindable]
  public dynamic class WorkTypePK
  {
    public var branch:Branch;
    public var name:String;

    public function WorkTypePK(branch:Branch=null, name:String=null)
    {
      this.branch=branch;
      this.name=name;
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
      return name ? name : Constants.NULL_VALUE;
    }

    public function get icon():Class
    {
      return null;
    }

    public function toString():String
    {
      return "(belong=" + branchId + ", name=" + name + ")";
    }
  }
}