package com.gc.common.model
{
  import com.gc.Constants;

  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.common.po.EventLog")]
  [Bindable]
  public dynamic class EventLog
  {
    public var id:int;
    public var branch:Branch;
    public var person:Person;
    public var type:String;
    public var table:String;
    public var key:String;
    public var level:String;
    public var className:String;
    public var fileName:String;
    public var lineNumber:String;
    public var loggerName:String;
    public var methodName:String;
    public var threadName:String;
    public var message:String;
    public var logDate:Date;
    public var startDate:Date;
    public var strReps:ArrayCollection;

    public function EventLog(id:int=0)
    {
      this.id=id;
    }

    public function get label():String
    {
      return message ? message : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return id;
    }

    public function get icon():Class
    {
      return null;
    }

    public function toString():String
    {
      return "EventLog{id=" + id + ", type=" + type + ", message=" + message + "}";
    }

  }
}