package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.ChkPlanPK")]
  [Bindable]
  public dynamic class ChkPlanPK
  {
    public var branch:Branch;
    public var no:String;

    public function ChkPlanPK(branch:Branch=null, no:String=null)
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
      return no ? no : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return no ? no : Constants.NULL_VALUE;
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