package com.gc.common.model
{
  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.common.po.SecurityLimit")]
  [Bindable]
  public dynamic class SecurityLimit
  {
    public var id:SecurityLimitPK;
    public var description:String;
    public var equLimit:int;
    public var softLimit:int;
    public var lineLimit:int;
    public var fittingLimit:int;
    public var networkLimit:int;
    public var serviceLimit:int;
    public var projectLimit:int;
    public var woLimit:int;
    public var invLimit:int;
    public var reportLimit:int;
    public var systemLimit:int;
    public var lineTktLimit:int;
    public var typeLimit:int;
    public var engineLimit:int;
    public var hrLimit:int;
    public var hrLimitDepart:Department;
    public var safetyLimit:int;
    public var safetyLimitDepart:Department;
    public var comment:String;

    public function SecurityLimit()
    {
    }

    public function get branch():Branch
    {
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get group():SecurityGroup
    {
      return id ? id.group : null;
    }

    public function get groupId():int
    {
      return id ? id.groupId : 0;
    }
  }
}