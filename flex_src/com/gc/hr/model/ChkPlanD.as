package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;
  import com.gc.common.model.Person;
  import com.gc.util.DateUtil;

  [RemoteClass(alias="com.gc.hr.po.ChkPlanD")]
  [Bindable]
  public dynamic class ChkPlanD
  {
    public var id:ChkPlanDPK;
    public var person:Person;
    public var date:Date;
    public var holiday:ChkHoliday;
    public var work:ChkWork;
    public var extra:ChkExtra;

    public function ChkPlanD(branch:Branch=null, hdNo:String=null, no:int=0)
    {
      this.id=new ChkPlanDPK(new ChkPlan(branch, hdNo), no);
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get hdNo():String
    {
      return id ? id.hdNo : null;
    }

    public function get no():int
    {
      return id ? id.no : 0;
    }

    public function get personId():int
    {
      return person ? person.id : 0;
    }

    public function get holidayId():int
    {
      return holiday ? holiday.id : 0;
    }

    public function get workId():int
    {
      return work ? work.id : 0;
    }

    public function get extraId():int
    {
      return extra ? extra.id : 0;
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
      // return "ChkPlanD{id=" + id + ", person=" + person + "}";
      return "ChkPlanD{belong=" + branchId + ", hdNo=" + hdNo + ", no=" + no + ", person=" + personId 
        + ", date=" + DateUtil.formatDateTime(date)  + ", holiday=" + holidayId + ", work=" + workId
        + ", extra=" + extraId + "}";
    }

  }
}