package com.gc
{
  import com.gc.util.FaultUtil;

  import flash.utils.ByteArray;

  import mx.rpc.AsyncToken;
  import mx.rpc.events.FaultEvent;
  import mx.rpc.events.ResultEvent;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class FlexController
  {
    private static var _service:RemoteObject;

    public function FlexController()
    {
    }

    protected static function get service():RemoteObject
    {
      if (_service == null)
      {
        _service=Swiz.getBean(Beans.SERVICE_FLEX) as RemoteObject;
      }
      return _service;
    }

    /**
     * obj:{s:s, m:m, p:p}
     *   s - service name, string
     *   m - method name, string
     *   p - parameters
     **/
    public static function exec(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      var r:Object={s:obj.s, m:obj.m};
      var b:ByteArray;
      if (obj.hasOwnProperty("p"))
      {
        b=new ByteArray();
        b.writeObject(obj.p);
        b.compress();
        r.p=b;
      }
      var call:AsyncToken=service.exec(r);
      var sh:Function=function(e1:ResultEvent):void
        {
          if (successHandler is Function)
          {
            var r1:Object=e1.result;
            var b1:ByteArray=r1.r as ByteArray;
            b1.uncompress();
            var o1:Object=b1.readObject();
            var re1:ResultEvent=new ResultEvent(ResultEvent.RESULT, false, true, o1);
            successHandler(re1);
          }
        };
      Swiz.executeServiceCall(call, CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [sh]);
    }

    public static function getFaultHandler(handler:Function):Function
    {
      return FaultUtil.getGenericFaultHandler(service, handler);
    }

  }
}