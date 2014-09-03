package com.gc.controls
{
  import flash.display.BitmapData;
  import flash.events.Event;

  import mx.core.BitmapAsset;

  public class Icon extends BitmapAsset
  {

    public static const DEFAULT_WIDTH:Number = 16;
    public static const DEFAULT_HEIGHT:Number = 16;

    protected var iconWidth:int = DEFAULT_WIDTH;
    protected var iconHeight:int = DEFAULT_HEIGHT;
    protected var color:uint = 0xFFFFFF;

    public function Icon():void
    {
      addEventListener(Event.ADDED, addedHandler, false, 0, true)
    }

    protected function addedHandler(event:Event):void
    {
      iconWidth = (iconWidth <= 0) ? DEFAULT_WIDTH : iconWidth;
      iconHeight = (iconHeight <= 0) ? DEFAULT_HEIGHT : iconHeight;
      bitmapData = new BitmapData(iconWidth, iconHeight, true, 0xFFFFFFFF);
      draw();
    }

    protected function draw():void
    {
    }

  }
}