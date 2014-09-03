package com.gc.safety.controller
{
  import com.gc.CommonEvent;
  import com.gc.common.model.SecurityLimit;
  import com.gc.safety.model.*;
  import com.gc.util.FaultUtil;

  import mx.collections.ArrayCollection;
  import mx.rpc.AsyncToken;
  import mx.rpc.events.ResultEvent;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class GuaranteeController
  {
    public static var _service:RemoteObject;

    [Autowire(bean="safetyGuaranteeService")]
    public var guaranteeService:RemoteObject;

    [Bindable]
    public var guaranteeList:ArrayCollection;
    [Bindable]
    public var guarDG2List:ArrayCollection;
    [Bindable]
    public var guaranteeDGList:ArrayCollection;
    [Bindable]
    public var guarDGList:ArrayCollection;
    [Bindable]
    public var guarInfoList:ArrayCollection;
    [Bindable]
    public static var guaTypeList:ArrayCollection;
    [Bindable]
    public static var insurerList:ArrayCollection;
    public static const ALL_LISTNAMES:Array=["insurerList", "guaTypeList"];

    public function GuaranteeController()
    {
    }

    protected static function get service():RemoteObject
    {
      if (_service == null)
      {
        _service = Swiz.getBean("safetyGuaranteeService") as RemoteObject;
      }
      return _service;
    }

    protected static function exec(call:AsyncToken, resultHandler:Function, faultHandler:Function=null, eventArgs:Array=null):void
    {
      Swiz.executeServiceCall(call, resultHandler, faultHandler, eventArgs);
    }

    public static function preloadLists(branchId:int, names:Array=null, force:Boolean=false):void
    {
      if (names == null)
        names = ALL_LISTNAMES;
      if ((insurerList == null || force) && names.indexOf("insurerList") >= 0)
        getInsurers(branchId);
      if ((guaTypeList == null || force) && names.indexOf("guaTypeList") >= 0)
        getGuaTypes(branchId);
    }
//==================================== Guarantee ====================================

    public function getGuaranteeList(obj:Object, successHandler:Function = null, faultHandler:Function = null):void
    {
      Swiz.executeServiceCall(guaranteeService.findGuarList(obj), getGuaranteesResultHandler, FaultUtil.getGenericFaultHandler(guaranteeService, faultHandler), [successHandler]);
    }

    private function getGuaranteesResultHandler(evt:ResultEvent, arg:Object = null):void
    {
      guaranteeList = evt.result as ArrayCollection;
      if (arg is Function)
        arg(evt);
    }

    public function saveGuarantee(obj:Object, guarDGList:ArrayCollection = null, list1:ArrayCollection = null, successHandler:Function = null, faultHandler:Function = null):void
    {
      Swiz.executeServiceCall(guaranteeService.saveGuarAndInfo(obj,guarDGList,list1), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(guaranteeService, faultHandler), [successHandler]);
    }

    public function findDownDateList(date1:String, date2:String, branchId:String, successHandler:Function = null, faultHandler:Function = null):void
    {
      Swiz.executeServiceCall(guaranteeService.findDownDateList(date1,date2,branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(guaranteeService, faultHandler), [successHandler]);
    }

    public function findGuarList(obj:Object, successHandler:Function = null, faultHandler:Function = null):void
    {
      Swiz.executeServiceCall(guaranteeService.findGuarList(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(guaranteeService, faultHandler), [successHandler]);
    }

    public static function saveGua(gua:Guarantee,obj:Object,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveGua(gua,obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    //结账后保单
    public static function getGuasByBCloseD(branchId:int,closeDate:Date,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getGuasByBCloseD(branchId,closeDate),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getGuaByAccNo(branchId:int, accNo:String, closeDate:Date, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getGuaByAccNo(branchId,accNo, closeDate),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service,faultHandler), [successHandler]);
    }

    public static function delGua(gua:Guarantee,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.deleteGuarantee(gua),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service,faultHandler), [successHandler]);
    }

//==================================== GuarInfo ====================================

    public function getGuarInfoList(branchId:int,busId:int,date:Date,successHandler:Function = null, faultHandler:Function = null):void
    {
      Swiz.executeServiceCall(guaranteeService.getGuarInfoList(branchId,busId,date), getGuarInfoListResultHandler, FaultUtil.getGenericFaultHandler(guaranteeService, faultHandler), [successHandler]);
    }

    private function getGuarInfoListResultHandler(evt:ResultEvent, arg:Object = null):void
    {
      guarInfoList = evt.result as ArrayCollection;
      if (arg is Function)
        arg(evt);
    }

    public function delGuarInfo(obj:Object, successHandler:Function = null, faultHandler:Function = null):void
    {
      Swiz.executeServiceCall(guaranteeService.deleteGuarInfo(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(guaranteeService, faultHandler),[successHandler]);
    }

    public function findGuarAndInfoList(obj1:Object, obj2:Object, successHandler:Function = null, faultHandler:Function = null):void
    {
      Swiz.executeServiceCall(guaranteeService.findGuarAndInfoList(obj1,obj2), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(guaranteeService, faultHandler), [successHandler]);
    }

    //将要失效保单
    public static function getMatureGuas(branchId:int, date1:Date, date2:Date,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getMatureGuas(branchId,date1,date2), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    //通过车 和 事故日期查保单
    public static function getGIsByBusIdAndAccDate(branchId:int, busId:int, accDate:Date,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getGIsByBusIdAndAccDate(branchId,busId,accDate), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getGuaInfosByDateFrom(limit:SecurityLimit, orderColumns:Array, dateFrom:Date,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getGuaInfosByDateFrom(limit,orderColumns,dateFrom),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    //单车保单查询
    public static function getGIsByUid(branchId:int, useId:String, dateFrom:Date, dateTo:Date, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getGIsByUid(branchId,useId,dateFrom,dateTo),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
//==================================== GuaranteeType ====================================

    public static function getGuaTypes(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getGuaTypes(branchId), function(evt:ResultEvent, arg:Object=null):void
        {
          guaTypeList = evt.result as ArrayCollection; 
          if (arg is Function)
            arg(evt);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== Insurer ====================================

    public static function getInsurers(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getInsurers(branchId), function(evt:ResultEvent, arg:Object=null):void
        {
          insurerList = evt.result as ArrayCollection;
          if (arg is Function)
            arg(evt);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

  }
}