package com.gc.safety.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.safety.po.AccOutGua")]
  [Bindable]
  public dynamic class AccOutGua
  {
    public var id:AccOutGuaPK;
    public var accident:Accident;
    public var insurer:Insurer;
    public var appDate:Date;
    public var objSum:Number;
    public var mediFee:Number;
    public var other1:Number;
    public var other2:Number;
    public var appDesc:String;
    public var appPsn:Person;
    public var fkGuaReport:GuaReport;

    // 以下字段仅用于做理赔凭证时显示用
    public var payObjSum:Number;
    public var payMediFee:Number;
    public var payOther1:Number;
    public var payOther2:Number;

    public var getDate:Date;
    public var getRefNo:String;
    public var getSum:Number=0;

    // 以下字段在建立理赔凭证时验证用
    public var hasGuarInfo:Boolean;
    public var hasAccOutGua:Boolean;
    //三则理赔信息
    public var guaSum:Number;

    public function AccOutGua(branch:Branch=null, refNo:String=null, no:String=null)
    {
      this.id = new AccOutGuaPK(branch, refNo, no);
    }

    public function get accId():int
    {
      return (accident == null || accident.id == null) ? 0 : accident.id.id;
    }

    public function set accId(accId:int):void
    {
      accident.id.id = accId;
    }

    public function toString():String
    {
      return "AccOutGua{ id = " + id + "}";
    }

  }
}