package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;
  import com.gc.util.DateUtil;

  [RemoteClass(alias="com.gc.hr.po.HrClosePK")]
  [Bindable]
  public dynamic class HrClosePK
  {
    public var branch:Branch;
    public var date:Date;

    public function HrClosePK(branch:Branch=null, date:Date=null)
    {
      this.branch=branch;
      this.date=date;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0
    }

    public function get label():String
    {
      return date ? DateUtil.formatDateTime(date) : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return date;
    }

    public function get icon():Class
    {
      return null;
    }

    public function toString():String
    {
      return "(belong=" + branchId + ", date=" + date + ")";
    }

  }
}