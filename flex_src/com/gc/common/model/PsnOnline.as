package com.gc.common.model
{

  [RemoteClass(alias="com.gc.common.po.PsnOnline")]
  [Bindable]
  public dynamic class PsnOnline
  {
    public var id:int;
    public var branch:Branch;
    public var person:Person;
    public var onDate:Date;
    public var allotReason:String;
    public var depart:Department;
    public var office:String;
    public var line:Line;
    public var bus:Equipment;
    public var cert2No:String;
    public var cert2NoHex:String;
    public var downDate:Date;
    public var alloter:Person;

    public function PsnOnline()
    {
    }

    public function get personId():int
    {
      return person ? person.id : 0;
    }

    public function get personName():String
    {
      return person ? person.name : "";
    }

    public function get personWorkerId():String
    {
      return person ? person.workerId : "";
    }
  }
}