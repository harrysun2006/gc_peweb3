package com.gc.test.controller
{
  import com.gc.Beans;
  import com.gc.CommonEvent;
  import com.gc.hr.model.*;
  import com.gc.util.FaultUtil;

  import flash.utils.getQualifiedClassName;

  import mx.collections.ArrayCollection;
  import mx.controls.Alert;
  import mx.rpc.AbstractOperation;
  import mx.rpc.AsyncToken;
  import mx.rpc.events.ResultEvent;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class TestController
  {

    private static var _services:Object=new Object();

    [Bindable]
    public static var personList:ArrayCollection;

    public function TestController()
    {
    }

    protected static function service(name:String):RemoteObject
    {
      if (!_services.hasOwnProperty(name))
      {
        _services[name]=Swiz.getBean(name) as RemoteObject;
      }
      return _services[name];
    }

    public static function getFaultHandler(name:String, handler:Function):Function
    {
      return FaultUtil.getGenericFaultHandler(service(name), handler);
    }

    protected static function exec(call:AsyncToken, resultHandler:Function, faultHandler:Function=null, eventArgs:Array=null):void
    {
      Swiz.executeServiceCall(call, resultHandler, faultHandler, eventArgs);
    }

    /**
     * 测试发现e.result是否封装成com.gc.model.Person类型的ArrayCollection对性能没有影响
     */
    public static function getAllPersons(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      var beginDate:Date;
      var endDate:Date;
      var ro:RemoteObject=service(Beans.SERVICE_HR_PERSONAL);
      beginDate=new Date();
      // commonBaseservice.getOperation("getAllPersons").addEventListener(ResultEvent.RESULT, getAllPersons);
      exec(ro.getAllPersons(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          personList=e.result as ArrayCollection;
          endDate=new Date();
          if (arg is Function)
            arg(e);
          Alert.show("getAllPersons: Object class: " + flash.utils.getQualifiedClassName(personList[0]) + "\nUsing: " + (endDate.time - beginDate.time).toString());
        // Alert.show(personList[0]["branch"].name);
        },  FaultUtil.getGenericFaultHandler(ro, faultHandler), [successHandler]);
    }

    /**
     * 将Person类的注解[RemoteClass(alias="com.gc.common.po.Person")]删除, 则返回的ArrayCollection中的元素为Object类型, 否则为Person类型.
     * 返回 的e.result中的元素类型和此处的调用方式无关
     */
    public static function getAllPersonsRaw(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      var beginDate:Date;
      var endDate:Date;
      var ro:RemoteObject=service(Beans.SERVICE_HR_PERSONAL);
      var op:AbstractOperation=ro.getOperation("getAllPersons");
      var listener:Function=function(e:ResultEvent):void
        {
          personList=e.result as ArrayCollection;
          endDate=new Date();
          op.removeEventListener(ResultEvent.RESULT, listener);
          Alert.show("getAllPersonsRaw: Object class: " + flash.utils.getQualifiedClassName(personList[0]) + "\nUsing: " + (endDate.time - beginDate.time).toString());
        // Alert.show(personList[0]["branch"].name);
        };
      beginDate=new Date();
      op.addEventListener(ResultEvent.RESULT, listener);
      ro.getAllPersons(branchId);
    }

//==================================== TEST ====================================

    public static function testAmf(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      var ro:RemoteObject=service(Beans.SERVICE_HR_PERSONAL);
      exec(ro.testAmf(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(ro, faultHandler), [successHandler]);
    }

    public static function testPoly(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      var ro:RemoteObject=service(Beans.SERVICE_HR_PERSONAL);
      exec(ro.testPoly(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(ro, faultHandler), [successHandler]);
    }

    public static function testList(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      var ro:RemoteObject=service(Beans.SERVICE_HR_PERSONAL);
      exec(ro.testList(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(ro, faultHandler), [successHandler]);
    }

    public static function testSet(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      var ro:RemoteObject=service(Beans.SERVICE_HR_PERSONAL);
      exec(ro.testSet(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(ro, faultHandler), [successHandler]);
    }

    public static function testMap(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      var ro:RemoteObject=service(Beans.SERVICE_HR_PERSONAL);
      exec(ro.testMap(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(ro, faultHandler), [successHandler]);
    }

  }
}