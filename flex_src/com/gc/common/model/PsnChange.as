package com.gc.common.model
{

  [RemoteClass(alias="com.gc.common.po.PsnChange")]
  [Bindable]
  public dynamic class PsnChange
  {
    public var id:int;
    public var branch:Branch;
    public var person:Person;
    public var date:Date;
    public var change:int; // 变动类型, 0:注册, 1:人事调动, 2:状态变更, 3:人事调动&状态变更, 9:注销
    // T_PSN_ONLINE
    public var alloter:Person;
    public var allotReason:String;
    public var changedDepart:int;
    public var oldDepart:Department;
    public var newDepart:Department;
    public var changedOffice:int;
    public var oldOffice:String;
    public var newOffice:String;
    public var changedLine:int;
    public var oldLine:Line;
    public var newLine:Line;
    public var changedBus:int;
    public var oldBus:Equipment;
    public var newBus:Equipment;
    public var changedCert2No:int;
    public var oldCert2No:String;
    public var newCert2No:String;
    public var changedCert2NoHex:int;
    public var oldCert2NoHex:String;
    public var newCert2NoHex:String;

    // T_PSN_STATUS
    public var upgrader:Person;
    public var upgradeReason:String;
    public var changedRegType:int;
    public var oldRegType:String;
    public var newRegType:String;
    public var changedType:int;
    public var oldType:String;
    public var newType:String;
    public var changedSalaryType:int;
    public var oldSalaryType:String;
    public var newSalaryType:String;
    public var changedPosition:int;
    public var oldPosition:Position;
    public var newPosition:Position;
    public var changedWorkType:int;
    public var oldWorkType:String;
    public var newWorkType:String;
    public var changedRegBelong:int;
    public var oldRegBelong:String;
    public var newRegBelong:String;
    public var changedParty:int;
    public var oldParty:String;
    public var newParty:String;
    public var changedGrade:int;
    public var oldGrade:String;
    public var newGrade:String;
    public var changedSchooling:int;
    public var oldSchooling:String;
    public var newSchooling:String;
    public var changedContractNo:int;
    public var oldContractNo:String;
    public var newContractNo:String;
    public var changedContr1End:int;
    public var oldContr1End:Date;
    public var newContr1End:Date;

    // OTHER
    public var reason:String;
    public var source:String;
    public var dest:String;
    public var on:String;
    public var down:String;

    public function PsnChange()
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