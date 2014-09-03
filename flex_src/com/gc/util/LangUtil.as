package com.gc.util
{
  import mx.resources.IResourceManager;
  import mx.resources.ResourceManager;

  public class LangUtil
  {
    private static function singleton():void
    {
    }

    public function LangUtil(caller:Function=null)
    {
      if (caller != singleton)
        throw new Error("LangUtil is a non-instance class!!!");
    }

    private static var RESOURCE_MANAGER:IResourceManager=null;

    public static function getString(bundle:String, code:String, params:Array=null):String
    {
      if (RESOURCE_MANAGER == null)
      {
        RESOURCE_MANAGER=ResourceManager.getInstance();
        RESOURCE_MANAGER.localeChain=["zh_CN"];
      }
      return RESOURCE_MANAGER.getString(bundle, code, params);
    }

  }
}