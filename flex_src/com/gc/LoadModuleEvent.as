package com.gc
{
  import flash.events.Event;
  import flash.utils.ByteArray;

  public class LoadModuleEvent extends Event
  {
    public static const LOAD_MODULE:String="loadModule";
    public static const UNLOAD_MODULE:String="unloadModule";

    public static const UNLOAD_MODULE_EVENT:LoadModuleEvent=new LoadModuleEvent(UNLOAD_MODULE);

    public var url:String;
    public var arg:Object;
    public var bytes:ByteArray;

    public function LoadModuleEvent(type:String, url:String=null, arg:Object=null, bytes:ByteArray=null)
    {
      super(type, true, false);
      this.url=url;
      this.arg=arg;
      this.bytes=bytes;
    }

  }
}