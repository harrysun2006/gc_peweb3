package com.gc.common.model
{
  import com.gc.Constants;

  [RemoteClass(alias="com.gc.common.po.EventLogD")]
  [Bindable]

  public dynamic class EventLogD
  {
    public var id:int;
    public var log:EventLog;
    public var strRep:String;

    public function EventLogD(id:int=0, log:EventLog=null, strRep:String=null)
    {
      this.id=id;
      this.log=log;
      this.strRep=strRep;
    }

    public function get label():String
    {
      return strRep ? strRep : Constants.NULL_LABEL;
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
      return "EventLogD{id=" + id + ", strRep=" + strRep + "}";
    }

  }
}