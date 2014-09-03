package com.gc.controls
{
  import flash.display.DisplayObject;
  import flash.display.Loader;
  import flash.events.Event;
  import flash.system.LoaderContext;
  import flash.utils.ByteArray;
  import flash.utils.setTimeout;

  import mx.controls.Image;

  public class ByteArrayImage extends Image
  {
    private var _loader:Loader = new Loader();

    public function ByteArrayImage():void
    {
    }

    override protected function createChildren():void
    {
      super.createChildren();
      addChild(_loader);
    }

    private var _bytes:ByteArray;

    public function get bytes():ByteArray
    {
      return _bytes;
    }

    public function set bytes(value:ByteArray):void
    {
      _bytes=value;
      loadBytes(_bytes);
    }

    public function loadBytes(bytes:ByteArray,context:LoaderContext=null):void
    {
      if (bytes == null || bytes.length <= 0)
      {
        dispatchEvent(new Event(Event.COMPLETE));
        return;
      }
      bytes.position = 0;
      _loader.loadBytes(bytes, context);
      _loader.contentLoaderInfo.addEventListener(Event.COMPLETE, function(e:Event):void
        {
          var r:Number = Math.min(width / _loader.width, height / _loader.height);
          _loader.width = _loader.width * r;
          _loader.height = _loader.height * r;
          dispatchEvent(new Event(Event.COMPLETE));
        });
    }
  }

}