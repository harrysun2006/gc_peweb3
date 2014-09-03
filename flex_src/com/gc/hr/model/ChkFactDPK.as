package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.ChkFactDPK")]
  [Bindable]
  public dynamic class ChkFactDPK
  {
    public var fact:ChkFact;
    public var no:int;

    public function ChkFactDPK(fact:ChkFact=null, no:int=0)
    {
      this.fact=fact;
      this.no=no;
    }

    public function get factId():ChkFactPK
    {
      return fact ? fact.id : null;
    }

    public function get branch():Branch
    {
      var id:ChkFactPK=factId;
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      var id:ChkFactPK=factId;
      return id ? id.branchId : 0;
    }

    public function get hdNo():String
    {
      var id:ChkFactPK=factId;
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
      // return "(fact=" + fact + ", no=" + no + ")";
      return "(belong=" + branchId + ", hdNo=" + hdNo + ", no=" + no + ")";
    }

  }
}