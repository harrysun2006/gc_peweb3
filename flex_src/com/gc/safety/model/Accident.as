package com.gc.safety.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;
  import com.gc.common.model.Equipment;
  import com.gc.common.model.Line;
  import com.gc.common.model.Person;
  import com.gc.common.model.Weather;
  import com.gc.util.CommonUtil;

  import mx.collections.ArrayCollection;
  import mx.utils.ObjectProxy;

  [RemoteClass(alias="com.gc.safety.po.Accident")]
  [Bindable]
  public dynamic class Accident
  {
    public var id:AccidentPK;
    public var no:String;
    public var date:Date;
    public var dept:Department;
    public var line:Line;
    public var bus:Equipment;
    public var driver:Person;
    public var level:AccLevel;
    public var type:AccType;
    public var duty:AccDuty;
    public var weather:Weather;
    public var address:String;
    public var roadFacility:String;
    public var desc1:String;
    public var desc2:String;
    public var desc3:String;
    public var reason:String;
    public var course:String;
    public var processInfo:String;
    public var processor:AccProcessor;
    public var policeNo:String;
    public var billNum:int;
    public var report:int;
    public var extent:AccExtent;
    public var destroy:String;
    public var lost:Number;
    public var status:int;
    public var initor:Person;
    public var initDate:Date;
    public var initDesc:String;
    public var deptor:Person;
    public var deptDate:Date;
    public var deptDesc:String;
    public var compor:Person;
    public var compDate:Date;
    public var compDesc:String;
    public var archor:Person;
    public var archDate:Date;
    public var accOutPsns:ArrayCollection;
    public var accInPsns:ArrayCollection;
    public var accOutObjs:ArrayCollection;
    public var guaReports:ArrayCollection;

    //状态
    public var statusStr:String;
    //存档时显示事故列表
    public var outPayFee:Number;
    public var outGuaFee:Number;
    public var outGuaPayFee:Number;
    public var inPsnPayFee:Number;
    public var inPsnGuaFee:Number;
    public var inPsnGuaPayFee:Number;

    //全局公用变量
    public static const qo:Object=new ObjectProxy();

    //query conditions
//		public var dateFrom:Date;
//		public var dateTo:Date;
//		public var initDateFrom:Date;
//		public var initDateTo:Date;
//		public var deptDateFrom:Date;
//		public var deptDateTo:Date;
//		public var compDateFrom:Date;
//		public var compDateTo:Date;
//		public var lostFrom:Number;
//		public var lostTo:Number;


    public function Accident(branch:Branch = null, id:int = 0)
    {
      this.id = new AccidentPK(branch, id);
    }

    public function get label():String
    {
      return (bus == null) ? Constants.NULL_LABEL : bus.useId;
    }

    public function get value():Object
    {
      return (no == null) ? Constants.NULL_VALUE : no;
    }

    public function get icon():Class
    {
      return Constants.ICON16_ACCIDENT;
    }

    public function toString():String
    {
      return "Accident(id= "+id.id+")";
    }

    public static function init():void
    {
      CommonUtil.empty(qo);
    }

  }
}