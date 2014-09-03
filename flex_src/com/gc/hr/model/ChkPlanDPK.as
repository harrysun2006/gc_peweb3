package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.ChkPlanDPK")]
  [Bindable]
  public dynamic class ChkPlanDPK
  {
    public var plan:ChkPlan;
    public var no:int;

    public function ChkPlanDPK(plan:ChkPlan=null, no:int=0)
    {
      this.plan=plan;
      this.no=no;
    }

    public function get planId():ChkPlanPK
    {
      return plan ? plan.id : null;
    }

    public function get branch():Branch
    {
      var id:ChkPlanPK=planId;
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      var id:ChkPlanPK=planId;
      return id ? id.branchId : 0;
    }

    public function get hdNo():String
    {
      var id:ChkPlanPK=planId;
      return id ? id.no : null;
    }

    public function get label():String
    {
      return no ? no.toString() : Constants.NULL_LABEL;
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
      return "(belong=" + branchId + ", hdNo=" + hdNo + ", no=" + no + ")";
    }

  }
}