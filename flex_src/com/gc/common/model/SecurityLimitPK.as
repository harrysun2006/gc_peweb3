package com.gc.common.model
{
  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.common.po.SecurityLimitPK")]
  [Bindable]
  public dynamic class SecurityLimitPK
  {
    public var group:SecurityGroup;
    public var branch:Branch;

    public function SecurityLimitPK()
    {
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get groupId():int
    {
      return group ? group.id : 0;
    }
  }
}