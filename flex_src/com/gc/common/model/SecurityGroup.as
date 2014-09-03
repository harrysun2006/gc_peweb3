package com.gc.common.model
{
  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.common.po.SecurityGroup")]
  [Bindable]
  public dynamic class SecurityGroup
  {
    public var id:int;
    public var useId:String;
    public var supervise:Boolean;
    public var description:String;
    public var comment:String;
    public var limits:ArrayCollection;

    public function SecurityGroup()
    {
    }

  }
}