package com.gc.safety.controller
{
  import com.gc.CommonEvent;
  import com.gc.safety.model.SafetyClose;
  import com.gc.util.FaultUtil;

  import mx.rpc.AsyncToken;
  import mx.rpc.events.ResultEvent;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class CommonController
  {
    public static var _service:RemoteObject;

    public static var closeDate:Date;
    public static var accNo:String;
    public static var now:Date;

    // 安全主页面树的排序
    public static const DEPART_ID:String = "depart.id";
    public static const LINE_ID:String = "line.id";
    public static const EQU_ID:String = "equipment.useId";
    public static const DOWNDATE:String = "downDate";
    public static const DEFAULT_SAFETY_ORDER_COLUMNS:Array=[DEPART_ID,LINE_ID, EQU_ID, DOWNDATE];

    public function CommonController()
    {
    }

    protected static function get service():RemoteObject
    {
      if (_service == null)
      {
        _service = Swiz.getBean("safetyCommonService") as RemoteObject;
      }
      return _service;
    }

    protected static function exec(call:AsyncToken, resultHandler:Function, faultHandler:Function=null, eventArgs:Array=null):void
    {
      Swiz.executeServiceCall(call, resultHandler, faultHandler, eventArgs);
    }

//================================close=====================================
    public static function getLastSafetyCloseDate(branchId:int, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getLastSafetyCloseDate(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          closeDate = e.result as Date;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getCloseList(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getCloseList(branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function closeAcc(close:SafetyClose, user:String, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.closeAcc(close, user), function(e:ResultEvent, arg:Object=null):void
        {
          closeDate=e.result as Date;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function decloseAcc(close:SafetyClose, user:String, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.decloseAcc(close, user), function(e:ResultEvent, arg:Object=null):void
        {
          closeDate=e.result as Date;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
//================================== accNo =================================
    public static function getSafetyAccNo(branchId:int, typeNo:String, accHead:String, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getSafetyAccNo(branchId, typeNo, accHead),function(e:ResultEvent, arg:Object=null):void
        {
          accNo = e.result as String;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getGuaranteeNo(branchId:int, date:Date, successHandler:Function = null, faultHandler:Function = null):void
    {
      exec(service.getGuaranteeNo(branchId, date),function(e:ResultEvent, arg:Object=null):void
        {
          accNo = e.result as String;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//=====================================
    //删除前比较后台数据是否有脏数据
    public static function compareDirty(opos:Array, params:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.compareDirty(opos, params), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//============================== now ==================================
    public static function getNowDate(successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getNowDate(),function(e:ResultEvent, arg:Object=null):void
        {
          now = e.result as Date;
          if(arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
  }
}