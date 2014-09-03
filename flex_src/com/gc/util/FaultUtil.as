package com.gc.util
{
  import mx.rpc.remoting.RemoteObject;

  public class FaultUtil
  {
    private static function singleton():void
    {
    }

    public function FaultUtil(caller:Function=null)
    {
      if (caller != singleton)
        throw new Error("FaultUtil is a non-instance class!!!");
    }

    public static function getGenericFaultHandler(service:RemoteObject, faultHandler:Function):Function
    {
      var h:FaultHandler=new FaultHandler(service, faultHandler);
      return h.genericFaultHandler;
    }
  }
}

import mx.controls.Alert;
import mx.rpc.Fault;
import mx.rpc.events.FaultEvent;
import mx.rpc.remoting.RemoteObject;
import mx.utils.ObjectUtil;

import com.gc.Constants;
import com.gc.util.FaultUtil;
import com.gc.util.LangUtil;

class FaultHandler
{
  private var service:RemoteObject;
  private var faultHandler:Function;

  public var genericFaultHandler:Function;

  public function FaultHandler(service:RemoteObject, faultHandler:Function)
  {
    this.service=service;
    this.faultHandler=faultHandler;
    this.genericFaultHandler=onGenericFault;
  }

  private function onGenericFault(e:FaultEvent, args:Object=null):void
  {
    if (faultHandler != null)
    {
      faultHandler(e);
      e.preventDefault();
    }
    else
    {
      if (service.hasEventListener(FaultEvent.FAULT))
      {
      }
      else
      {
        e.preventDefault();
        var code:String=e.fault.faultCode;
        var id:int=e.fault.errorID;
        var text:String;
        // trace(ObjectUtil.toString(e));
        if (code == "ConcurrencyError" && e.fault.rootCause == null)
          return;
        if (code == "Channel.Call.Failed")
          text=LangUtil.getString("gcc", "channel.call.error");
        else if (code == "Client.Error.MessageSend")
          text=LangUtil.getString("gcc", "client.message.send.error");
        // else if (code == "Server.Processing")
        //  text=LangUtil.getString("gcc", "server.processing.error");
        else if (e.fault.rootCause.hasOwnProperty("message"))
          text=e.fault.rootCause.message;
        else if (e.fault.faultString)
          text=e.fault.faultString;
        else
          text=ObjectUtil.toString(e.fault);
        Alert.show(text, LangUtil.getString("gcc", "service.call.error", [service.destination, e.token.message["operation"]]), Alert.OK, null, null, Constants.ICON32_ERROR);
      }
    }
  }
}