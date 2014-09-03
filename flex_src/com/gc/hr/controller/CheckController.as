package com.gc.hr.controller
{
  import com.gc.Beans;
  import com.gc.CommonEvent;
  import com.gc.hr.model.ChkFact;
  import com.gc.hr.model.ChkLongPlan;
  import com.gc.hr.model.ChkPlan;
  import com.gc.util.FaultUtil;

  import mx.collections.ArrayCollection;
  import mx.rpc.AsyncToken;
  import mx.rpc.events.ResultEvent;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class CheckController
  {

    private static var _service:RemoteObject;

    [Bindable]
    public static var holidayList:ArrayCollection;
    [Bindable]
    public static var workList:ArrayCollection;
    [Bindable]
    public static var extraList:ArrayCollection;
    [Bindable]
    public static var dispList:ArrayCollection;
    [Bindable]
    public static var groupList:ArrayCollection;
    [Bindable]
    public static var defaultItem:Object;

    public static const ALL_LISTNAMES:Array=["holidayList", "workList", "extraList", "dispList", "groupList"];

    public function CheckController()
    {
    }

    protected static function get service():RemoteObject
    {
      if (_service == null)
      {
        _service=Swiz.getBean(Beans.SERVICE_HR_CHECK) as RemoteObject;
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
      if ((holidayList == null || force) && names.indexOf("holidayList") >= 0)
        getHolidays(branchId);
      if ((workList == null || force) && names.indexOf("workList") >= 0)
        getWorks(branchId);
      if ((extraList == null || force) && names.indexOf("extraList") >= 0)
        getExtras(branchId);
      if ((dispList == null || force) && names.indexOf("dispList") >= 0)
        getDisps(branchId);
      if ((groupList == null || force) && names.indexOf("groupList") >= 0)
        getGroups(branchId);
    }

//==================================== ChkHoliday ====================================

    public static function getHolidays(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getHolidays(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          holidayList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== ChkWork ====================================

    public static function getWorks(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getWorks(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          workList = e.result as ArrayCollection;
          defaultItem = workList.length > 0 ? workList[0] : null;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== ChkExtr ====================================

    public static function getExtras(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getExtras(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          extraList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== ChkDisp ====================================

    public static function getDisps(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDisps(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          dispList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getDefaultItem(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      if (workList == null)
        getWorks(branchId, successHandler, faultHandler);
      else
        defaultItem = workList.length > 0 ? workList[0] : null;
    }

//==================================== ChkGroup ====================================

    public static function getGroups(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getGroups(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          groupList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getDepartmentsAndGroups(departId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDepartmentsAndGroups(departId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getGroupsByDepart(departId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getGroupsByDepart(departId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getCheckPersonsByDepart(departId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getCheckPersonsByDepart(departId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== ChkLongPlan ====================================

    public static function getLongPlans(qo:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getLongPlans(qo), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveLongPlan(po:ChkLongPlan, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.saveLongPlan(po), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== ChkPlan ====================================

    public static function getPlanDetails(plan:ChkPlan, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getPlanDetails(plan), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getCheckPersonsAndCLPs(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getCheckPersonsAndCLPs(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getCheckPersonsAndCPDs(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getCheckPersonsAndCPDs(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getPlans(plan:ChkPlan, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getPlans(plan), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== ChkFact ====================================

    public static function getFactDetails(fact:ChkFact, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getFactDetails(fact), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getCheckPersonsAndCPDsOrCLPs(fact:ChkFact, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getCheckPersonsAndCPDsOrCLPs(fact), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getCheckPersonsAndCFDs(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getCheckPersonsAndCFDs(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getFacts(fact:ChkFact, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getFacts(fact), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== Report ====================================

    public static function getPersonsOnlineByDepart(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getPersonsOnlineByDepart(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getOnlinePersonsAndCFDs(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getOnlinePersonsAndCFDs(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getOnlinePersonsAndCheckStat(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getOnlinePersonsAndCheckStat(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
  }
}