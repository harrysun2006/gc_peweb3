package com.gc.common.model
{
  import com.gc.Constants;

  [RemoteClass(alias="com.gc.common.po.OfficePK")]
  [Bindable]
  public dynamic class OfficePK
  {
    public var branch:Branch;
    public var depart:Department;
    public var name:String;

    public function OfficePK(branch:Branch=null, depart:Department=null, name:String=null)
    {
      this.branch=branch;
      this.depart=depart;
      this.name=name;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get departId():int
    {
      return depart ? depart.id : 0;
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
      return "(belong=" + branchId + ", departId=" + departId + ", name=" + name + ")";
    }
  }
}