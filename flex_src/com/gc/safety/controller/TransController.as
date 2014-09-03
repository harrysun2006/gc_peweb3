package com.gc.safety.controller
{
  import com.gc.CommonEvent;
  import com.gc.common.model.SecurityLimit;
  import com.gc.util.FaultUtil;

  import mx.collections.ArrayCollection;
  import mx.rpc.AsyncToken;
  import mx.rpc.events.ResultEvent;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class TransController
  {
    public static var _service:RemoteObject;

    [Autowire(bean="safetyTransService")]
    public var transService:RemoteObject;

    [Bindable]
    public static var transInfoList:ArrayCollection=new ArrayCollection();
    [Bindable]
    public static var transTypeList:ArrayCollection=new ArrayCollection();

    public static const ALL_LISTNAMES:Array = ["transInfoList", "transTypeList"];

    public function TransController()
    {
    }

    protected static function get service():RemoteObject
    {
      if (_service == null)
      {
        _service = Swiz.getBean("safetyTransService") as RemoteObject;
      }
      return _service;
    }

    protected static function exec(call:AsyncToken, resultHandler:Function, faultHandler:Function=null, eventArgs:Array=null):void
    {
      Swiz.executeServiceCall(call, resultHandler, faultHandler, eventArgs);
    }

    public static function preloadLists(branchId:int, force:Boolean=false, successHandler:Function=null):void
    {
      if (transTypeList == null || force)
        getTransTypes(branchId, successHandler);
    }

//==================================== TransType ====================================

    public static function getTransTypes(branchId:int, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getTransTypes(branchId), function(evt:ResultEvent, arg:Object = null):void
        {
          transTypeList = evt.result as ArrayCollection;
          if (arg is Function)
            arg(evt);	
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== TransInfo ====================================
    public static function saveTransInfos(oldList:ArrayCollection, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveTransInfos(oldList, transInfoList), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveTrans(newList:ArrayCollection,oldList:ArrayCollection, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveTrans(newList,oldList), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
    //修改
    public static function saveTrans2(newList:ArrayCollection,oldList:ArrayCollection,departId:int,closeDate:Date, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.saveTrans2(oldList,newList,departId,closeDate), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getTransInfosForModify(branchId:int, accNo:String, doDate:String, dealDate:String, closeDate:Date, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getTransInfosForModify(branchId, accNo, doDate, dealDate, closeDate), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getTransInfoList(obj:Object, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getTransInfoList(obj), function(evt:ResultEvent, args:Object=null):void
        {
          transInfoList = evt.result as ArrayCollection;
          if (args is Function)
            args(evt);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    //单车违章查询
    public static function getTransByUIdOrWId(branchId:int, useId:String, workerId:String, dateFrom:Date, dateTo:Date, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getTransByUIdOrWId(branchId,useId,workerId,dateFrom,dateTo), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    //违章处理，部门、结账日期查询
    public static function getTransListByDeptCloseD(branchId:int, departId:int, closeDate:Date, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getTransListByDeptCloseD(branchId, departId, closeDate), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    //违章所有条件查询
    public static function getTransListByAll(limit:SecurityLimit, qo:Object, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getTransListByAll(limit,qo), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//===================================
    public static function getTransInfoForSafetyTree(limit:SecurityLimit, dateFrom:Date, sucessHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getTransInfoForSafetyTree(limit, dateFrom), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [sucessHandler]);
    }

  }
}