package com.gc.common.model
{
  import com.gc.Constants;

  [RemoteClass(alias="com.gc.common.po.PositionPK")]
  [Bindable]
  public dynamic class PositionPK
  {
    public var branch:Branch;
    public var no:String;

    public function PositionPK(branch:Branch=null, no:String=null)
    {
      this.branch=branch;
      this.no=no;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get label():String
    {
      return (no == null) ? Constants.NULL_LABEL : no;
    }

    public function get value():Object
    {
      return (no == null) ? Constants.NULL_VALUE : no;
    }

    public function get icon():Class
    {
      return null;
    }

    public function toString():String
    {
      return "(belong=" + branchId + ", no=" + no + ")";
    }
  }
}