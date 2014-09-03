package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.PolParty")]
  [Bindable]
  public dynamic class PolParty
  {
    public var id:PolPartyPK;
    public var no:Number=0;
    public var active:int=1;
    public var description:String;

    public function PolParty(branch:Branch=null, name:String=null)
    {
      this.id=new PolPartyPK(branch, name);
    }

    public function get branch():Branch
    {
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get name():String
    {
      return id ? id.name : null;
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
      return "PolParty{belong=" + branchId + ", name=" + name + ", no=" + no + "}";
    }

  }
}