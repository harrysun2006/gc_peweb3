package com.gc.common.model
{
  import com.gc.Constants;

  [RemoteClass(alias="com.gc.common.po.Position")]
  [Bindable]
  public dynamic class Position
  {
    public var id:PositionPK;
    public var name:String;
    public var responsibility:String;
    public var reqDescription:String;
    public var reqCert:String;
    public var reqPersonCount:int;
    public var genLevel:int;
    public var salaryLevel:int;
    public var techLevel:int;
    public var securityLevel:int;
    public var techDescription:String;
    public var securityDescription:String;
    public var comment:String;

    public function Position(branch:Branch=null, no:String=null, name:String=null)
    {
      this.id=new PositionPK(branch, no);
      this.name=name;
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

    public function get label():String
    {
      return name ? name : Constants.NULL_LABEL;
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
      return "Position{id=" + id + ", name=" + name + "}";
    }

  }

}