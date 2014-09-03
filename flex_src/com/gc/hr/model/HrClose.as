package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;
  import com.gc.util.DateUtil;

  [RemoteClass(alias="com.gc.hr.po.HrClose")]
  [Bindable]
  public dynamic class HrClose
  {
    public var id:HrClosePK;
    public var comment:String;

    public function HrClose(branch:Branch=null, date:Date=null)
    {
      this.id=new HrClosePK(branch, date);
    }

    public function get branch():Branch
    {
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get date():Date
    {
      return id ? id.date : null;
    }

    public function get label():String
    {
      return id ? id.label : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return id ? id.value : Constants.NULL_VALUE;
    }

    public function get icon():Class
    {
      return null;
    }

    public function toString():String
    {
      return "HrClose{belong=" + branchId + ", date=" + DateUtil.formatDateTime(date) + ", comment=" + comment + "}";
    }

  }
}