package com.gc.hr.controller
{
  import com.gc.Beans;
  import com.gc.CommonEvent;
  import com.gc.Constants;
  import com.gc.hr.model.HrClose;
  import com.gc.util.FaultUtil;

  import mx.rpc.AsyncToken;
  import mx.rpc.events.ResultEvent;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class CommonController
  {

    private static var _service:RemoteObject;

    [Bindable]
    public static var lastCloseDate:Date;

    public static const ALL_LISTNAMES:Array=["closeList"];

    public function CommonController()
    {
    }

    protected static function get service():RemoteObject
    {
      if (_service == null)
      {
        _service=Swiz.getBean(Beans.SERVICE_HR_COMMON) as RemoteObject;
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
    }
//==================================== Voucher Head NO ====================================

    public static function getLongPlanNo(branchId:int, date:Date, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getLongPlanNo(branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== Close ====================================

    public static function getCloseList(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getCloseList(branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getLastCloseDate(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getLastCloseDate(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          lastCloseDate=e.result as Date;
          if (lastCloseDate == null) lastCloseDate=Constants.MIN_DATE;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function closeAcc(close:HrClose, user:String, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.closeAcc(close, user), function(e:ResultEvent, arg:Object=null):void
        {
          lastCloseDate=e.result as Date;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function decloseAcc(close:HrClose, user:String, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.decloseAcc(close, user), function(e:ResultEvent, arg:Object=null):void
        {
          lastCloseDate=e.result as Date;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

  }
}