package com.gc.common.model
{

  import com.gc.Constants;
  import com.gc.util.DateUtil;
  import com.gc.util.LangUtil;

  [RemoteClass(alias="com.gc.common.po.PsnStatus")]
  [Bindable]
  public dynamic class PsnStatus
  {
    public var id:int;
    public var branch:Branch;
    public var person:Person;
    public var onDate:Date;
    public var upgradeReason:String;
    public var regType:String;
    public var type:String;
    public var salaryType:String;
    public var position:String;
    public var fkPosition:Position;
    public var workType:String;
    public var regBelong:String;
    public var party:String;
    public var grade:String;
    public var schooling:String;
    public var downDate:Date;
    public var upgrader:Person;
    public var isModContract:String;
    public var contractEnd:Date;

    public function PsnStatus()
    {
    }

    public function get personId():int
    {
      return person ? person.id : 0;
    }

    public function get contractEnd$():String
    {
      return (contractEnd && contractEnd.time >= Constants.MAX_DATE.time) ? LangUtil.getString("gcc", "person.contr1End.no") 
        : DateUtil.formatDate(contractEnd);
    }
  }
}