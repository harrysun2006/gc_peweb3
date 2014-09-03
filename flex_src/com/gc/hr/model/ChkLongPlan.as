package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.hr.po.ChkLongPlan")]
  [Bindable]
  public dynamic class ChkLongPlan
  {
    public var id:ChkLongPlanPK;
    public var person:Person;
    public var fromDate:Date;
    public var endDate:Date;
    public var holiday:ChkHoliday;
    public var lastModifier:Person;
    public var checker:Person;
    public var checkDate:Date;
    public var checkDescription:String;

    public function ChkLongPlan(branch:Branch=null, no:String=null)
    {
      this.id=new ChkLongPlanPK(branch, no);
    }

    public function get branch():Branch
    {
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get no():String
    {
      return id ? id.no : null;
    }

    public function get personId():int
    {
      return person ? person.id : 0;
    }

    public function get lastModifierName():String
    {
      return lastModifier ? lastModifier.name : null;
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
      // return "ChkLongPlan{id=" + id + ", person=" + person + "}";
      return "ChkLongPlan{belong=" + branchId + ", no=" + no + ", person=" + personId + "}";
    }

  }
}