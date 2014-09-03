package com.gc.safety.controller
{
  import com.gc.CommonEvent;
  import com.gc.common.model.Department;
  import com.gc.common.model.SecurityLimit;
  import com.gc.safety.model.*;
  import com.gc.util.FaultUtil;

  import mx.collections.ArrayCollection;
  import mx.rpc.AsyncToken;
  import mx.rpc.events.ResultEvent;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class AccidentController
  {
    private static var _service:RemoteObject;

    [Bindable]
    public static var accTypeList:ArrayCollection;
    [Bindable]
    public static var accLevelList:ArrayCollection;
    [Bindable]
    public static var accDutyList:ArrayCollection;
    [Bindable]
    public static var accExtentList:ArrayCollection;
    [Bindable]
    public static var accObjectList:ArrayCollection;
    [Bindable]
    public static var accProcessorList:ArrayCollection;
    [Bindable]
    public static var accInPsnList:ArrayCollection;
    [Bindable]
    public static var accOutList:ArrayCollection;

    public static const ALL_BASELISTNAMES:Array=["accTypeList","accLevelList","accDutyList","accExtentList",
      "accObjectList","accProcessorList"];
    public static const All_ACC_ORDER_COLUMNS:Array=["status","depart.id","line.id","level.id","type.id","duty.id"
      ,"weather.id","process.id","extent.id"];
    public static const DEFAULT_ACC_ORDER_COLUMNS:Array=["depart.id","line.id"];

    public function AccidentController()
    {
    }

    protected static function get service():RemoteObject
    {
      if (_service == null)
      {
        _service = Swiz.getBean("safetyAccidentService") as RemoteObject;
      }
      return _service;
    }

    protected static function exec(call:AsyncToken, resultHandler:Function=null, faultHandler:Function=null, eventArgs:Array=null):void
    {
      Swiz.executeServiceCall(call, resultHandler, faultHandler, eventArgs);
    }

    public static function preloadBaseLists(branchId:int, names:Array=null, force:Boolean=false):void
    {
      if (names == null)
        names = ALL_BASELISTNAMES;
      if ((accTypeList == null || force) && names.indexOf("accTypeList") >= 0)
        getAccTypes(branchId);
      if ((accLevelList == null || force) && names.indexOf("accLevelList") >= 0)
        getAccLevels(branchId);
      if ((accDutyList == null || force) && names.indexOf("accDutyList") >= 0)
        getAccDutys(branchId);
      if ((accExtentList == null || force) && names.indexOf("accExtentList") >= 0)
        getAccExtents(branchId);
      if ((accObjectList == null || force) && names.indexOf("accObjectList") >= 0)
        getAccObjects(branchId);
      if ((accProcessorList == null || force) && names.indexOf("accProcessorList") >= 0)
        getAccProcessors(branchId);
    }
//==================================== Accident ====================================
    // 事故修改保存, 脏数据的比较
    public static function saveAccident1(oldData:Accident, newData:Accident,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveAccident1(oldData,newData),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    // 事故建立 和 处理 保存
    public static function saveAccident(accident:Accident, obj:Object,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveAccident(accident,obj),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    //事故 审核  保存
    public static function saveCAccident(accident:Accident,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveCAccident(accident),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
    // 删除事故
    public static function deleteAccident(oldData:Accident,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.deleteAccident(oldData),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAccsByStatus01(branchId:int,dept:Department,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccsByStatus01(branchId,dept),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAccidentByNo(branchId:int,no:String,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccidentByNo(branchId,no),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAccsAndObjPsnGuas(branchId:int,closeDate:Date,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccsAndObjPsnGuas(branchId,closeDate),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAccidentByNoForArch(branchId:int, no:String, closeDate:Date,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccidentByNoForArch(branchId,no,closeDate),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getOutInGuaAndPays(branchId:int, accId:int,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getOutInGuaAndPays(branchId,accId),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAccidents(limit:SecurityLimit,orderColumns:Array,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccidents(limit,orderColumns),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAccsByDateFrom(limit:SecurityLimit,dateFrom:Date,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccsByDateFrom(limit,dateFrom),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getPOPs(limit:SecurityLimit,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getPOPs(limit),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getPayList(limit:SecurityLimit,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getPayList(limit),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    //单车，单人，查询事故
    public static function getAccsByUIdOrWId(branchId:int,useId:String, workerId:String, dateFrom:Date, dateTo:Date,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccsByUIdOrWId(branchId,useId,workerId,dateFrom,dateTo),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    //事故所有条件查询
    public static function getAccsByAll(limit:SecurityLimit,qo:Object,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccsByAll(limit,qo),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
//==================================== AccType ====================================

    public static function getAccTypes(branchId:int, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccTypes(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          accTypeList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== AccLevel ====================================

    public static function getAccLevels(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getAccLevels(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          accLevelList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== AccDuty ====================================

    public static function getAccDutys(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getAccDutys(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          accDutyList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== AccExtent ====================================

    public static function getAccExtents(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getAccExtents(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          accExtentList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== AccObject ====================================

    public static function getAccObjects(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getAccObjects(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          accObjectList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== AccProcessor ====================================

    public static function getAccProcessors(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getAccProcessors(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          accProcessorList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//=================================== Accident and InPsn ===============================
    public static function addAccInPsn(aip:AccInPsn, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.addAccInPsn(aip),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function delAccInPsn(aip:AccInPsn, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.delAccInPsn(aip),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveAccInPsn(aip:AccInPsn, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveAccInPsn(aip),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAccAndInPsnListByInsurer(branchId:int, insurer:Insurer, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccAndInPsnListByInsurer(branchId, insurer), function(e:ResultEvent, arg:Object=null):void
        {
          accInPsnList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAIPByAccIdAndNo(branchId:int , accNo:String , no:int,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAIPByAccIdAndNo(branchId,accNo,no),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
//=================================== AccInPsnPay ===============================
    public static function addAccInPsnPay(aipp:AccInPsnPay, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.addAccInPsnPay(aipp),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function delAccInPsnPay(aipp:AccInPsnPay, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.delAccInPsnPay(aipp),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveAccInPsnPay(aipp:AccInPsnPay, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveAccInPsnPay(aipp),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
//============================== Accident and OutPsn, OutObj ==============================
    public static function addAccOutPsn(aop:AccOutPsn, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.addAccOutPsn(aop),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function delAccOutPsn(aop:AccOutPsn, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.delAccOutPsn(aop),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveAccOutPsn(aop:AccOutPsn, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveAccOutPsn(aop),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function addAccOutObj(aob:AccOutObj, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.addAccOutObj(aob),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function delAccOutObj(aob:AccOutObj, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.delAccOutObj(aob),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveAccOutObj(aob:AccOutObj, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveAccOutObj(aob),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAccAndOutByInsurer(branchId:int, insurer:Insurer, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAccAndOutByInsurer(branchId, insurer), function(e:ResultEvent, arg:Object=null):void
        {
          accOutList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAOPByAccIdAndNo(branchId:int , accNo:String , no:int,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAOPByAccIdAndNo(branchId,accNo,no),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getAOOByAccIdAndNo(branchId:int , accNo:String , no:int,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getAOOByAccIdAndNo(branchId,accNo,no),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
//============================== AccOutPsnPay ==============================
    public static function addAccOutPsnPay(aopp:AccOutPsnPay, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.addAccOutPsnPay(aopp),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function delAccOutPsnPay(aopp:AccOutPsnPay, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.delAccOutPsnPay(aopp),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveAccOutPsnPay(aopp:AccOutPsnPay, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveAccOutPsnPay(aopp),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
  }
}