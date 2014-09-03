package com.gc.common.controller
{
  import com.gc.Beans;
  import com.gc.CommonEvent;
  import com.gc.common.model.SecurityLimit;
  import com.gc.util.FaultUtil;

  import mx.collections.ArrayCollection;
  import mx.rpc.AsyncToken;
  import mx.rpc.events.ResultEvent;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class BaseController
  {
    private static var _service:RemoteObject;

    [Bindable]
    public static var departmentList:ArrayCollection;
    [Bindable]
    public static var lineList:ArrayCollection;
    [Bindable]
    public static var equipmentList:ArrayCollection;
    [Bindable]
    public static var equTypeList:ArrayCollection;
    [Bindable]
    public static var equOnlineList:ArrayCollection;
    [Bindable]
    public static var weatherList:ArrayCollection;

    public static const ALL_LISTNAMES:Array=["departmentList", "lineList", "equipmentList", "equTypeList", "weatherList"];
    public static const PERSONAL_LISTNAMES:Array=["departmentList"];

    public function BaseController()
    {
    }

    protected static function get service():RemoteObject
    {
      if (_service == null)
      {
        _service=Swiz.getBean(Beans.SERVICE_COMMON_BASE) as RemoteObject;
      }
      return _service;
    }

    protected static function exec(call:AsyncToken, resultHandler:Function, faultHandler:Function=null, eventArgs:Array=null):void
    {
      Swiz.executeServiceCall(call, resultHandler, faultHandler, eventArgs);
    }

    public static function getFaultHandler(handler:Function):Function
    {
      return FaultUtil.getGenericFaultHandler(service, handler);
    }

    public static function preloadLists(branchId:int, names:Array=null, force:Boolean=false):void
    {
      if (names == null)
        names = ALL_LISTNAMES;
      if ((departmentList == null || force) && names.indexOf("departmentList") >= 0)
        getDepartments(branchId);
      if ((lineList == null || force) && names.indexOf("lineList") >= 0)
        getLines(branchId);
      if ((equipmentList == null || force) && names.indexOf("equipmentList") >= 0)
        getEquipmentsByBranchId(branchId);
      if ((equTypeList == null || force) && names.indexOf("equTypeList") >= 0)
        getEquTypes();
      if ((weatherList == null || force) && names.indexOf("weatherList") >= 0)
        getWeathers(branchId);
    }

    public static function addObject(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.addObject(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function deleteObject(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.deleteObject(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    protected static function saveObject(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.saveObject(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function updateObject(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.updateObject(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function addObjects(pos:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.addObjects(pos), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function deleteObjects(pos:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.deleteObjects(pos), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveObjects(opos:Array, npos:Array, params:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.saveObjects(opos, npos, params), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveVoucher(header:Object, opos:Array, nheader:Object, npos:Array, params:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      /**
         var f:Function=function(e:ResultEvent):void
         {
         if (e.result == null) {
         nheader["@deleted"]=true;
         successHandler(new ResultEvent(e.type, e.bubbles, e.cancelable, nheader, e.token, e.message));
         }
         };
       **/
      exec(service.saveVoucher(header, opos, nheader, npos, params), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getSettings(successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getSettings(), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== Department ====================================

    /**
     * TOFIX: 和部门相关的列表应该增加一个departId参数(调用时传hrDepartId或safetyDepartId)
     **/
    public static function getDepartments(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDepartments(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          departmentList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getDepartmentsAndOffices(branchId:int, departId:int=0, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDepartmentsAndOffices(branchId, departId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getDepartmentsAndOLEs(branchId:int, departId:int=0, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDepartmentsAndOLEs(branchId, departId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== Office ====================================

//==================================== Line ====================================

    public static function getLines(branchId:int, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getLines(branchId), function(e:ResultEvent, arg:Object = null):void
        {
          lineList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    /**
     * 返回投保有效期内的Lines
     **/
    public static function getLinesByBD(branchId:int, onDate:Date, downDate:Date,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getLinesByBD(branchId, onDate, downDate), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER,FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== Equipment ====================================

    public static function getEquipmentsByBranchId(branchId:int, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getEquipmentsByBranchId(branchId), function(e:ResultEvent, arg:Object = null):void
        {
          equipmentList =  e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service,faultHandler),[successHandler]);
    }

//==================================== EquType ====================================

    public static function getEquTypes(successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getEquTypes(), function(e:ResultEvent, arg:Object = null):void
        {
          equTypeList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== EquOnline ====================================

    public static function getEquOnlines(branchId:int, departId:int, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getEquOnlines(branchId, departId), function(e:ResultEvent, arg:Object=null):void
        {
          equOnlineList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getEquOnlineList(branchId:int, accDate:Date, departId:int,successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getEquOnlineList(branchId, accDate, departId), function(e:ResultEvent, arg:Object = null):void
        {
          equOnlineList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    /**
     * 返回投保有效期内的EquOnlines
     **/
    public static function getEquOnlinesByBD(branchId:int, onDate:Date, downDate:Date, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getEquOnlinesByBD(branchId,onDate,downDate),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getEquOnlineLasts(branchId:int, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getEquOnlineLasts(branchId),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//======================  安全主页面的树结构(部门-线路-车辆)  =======================
    // TODO: delete
    public static function getDeptsLinesBusesForSafetyTree(limit:SecurityLimit, orderColumn:Array, from:Date, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDeptsLinesBusesForSafetyTree(limit, orderColumn, from), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getEquOnlinesForSafetyTree(limit:SecurityLimit, from:Date, order:Array, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getEquOnlinesForSafetyTree(limit, from, order), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== Weather ====================================

    public static function getWeathers(branchId:int, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getWeathers(branchId), function(evt:ResultEvent, arg:Object = null):void
        {
          weatherList = evt.result as ArrayCollection;
          if (arg is Function)
            arg(evt);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

  }
}