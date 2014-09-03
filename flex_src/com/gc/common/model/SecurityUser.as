package com.gc.common.model
{
  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.common.po.SecurityUser")]
  [Bindable]
  public dynamic class SecurityUser
  {
    public var id:int;
    public var useId:String;
    public var person:Person;
    public var branch:Branch;
    public var group:SecurityGroup;
    public var password1:String;
    public var password2:String;
    public var password3:String;
    public var description:String;
    public var comment:String;
    public var authorities:Array;

    // none database column properties
    public var remoteAddr:String;
    public var remoteHost:String;
    public var remotePort:int;
    public var remoteUser:String;
    public var systemTime:Date;

    // query properties
    public var limit:SecurityLimit;

    public static const DEFAULT_USER:SecurityUser=new SecurityUser();

    public function SecurityUser()
    {
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

  }
}