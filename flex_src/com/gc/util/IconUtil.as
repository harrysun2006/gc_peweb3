package com.gc.util
{
  import flash.display.BitmapData;
  import flash.display.Graphics;
  import flash.display.IBitmapDrawable;
  import flash.display.Loader;
  import flash.display.LoaderInfo;
  import flash.display.Sprite;
  import flash.events.Event;
  import flash.geom.Matrix;
  import flash.net.URLRequest;
  import flash.system.LoaderContext;
  import flash.utils.Dictionary;

  import mx.containers.accordionClasses.AccordionHeader;
  import mx.controls.tabBarClasses.Tab;
  import mx.core.BitmapAsset;
  import mx.core.SpriteAsset;
  import mx.core.UIComponent;
  import mx.events.FlexEvent;

  /**
   * Provides a workaround for using run-time loaded graphics in styles and properties which require a Class reference
   */
  public class IconUtil extends BitmapAsset
  {

    private static var dictionary:Dictionary;

    public static const DEFAULT_WIDTH:int=16;
    public static const DEFAULT_HEIGHT:int=16;

    /**
     * Used to associate run-time graphics with a target
     * @target: a reference to the component associated with this icon
     * @source: can be either of
     * 	1. an url to a JPG, PNG or GIF file you wish to be loaded and displayed
     * 	2. a function with BitmapData as its args
     *  3. a IBitmapDrawable instance (all DisplayObject is)
     * @params: define params such as width, height, color, backgroundColor, ...
     * @width: defines the width of the graphic when displayed
     * @height: defines the height of the graphic when displayed
     * return a reference to the IconUtility class which may be treated as a BitmapAsset
     * @example &lt;mx:Button id="button" icon="{IconUtility.getClass(button, 'http://www.yourdomain.com/images/test.jpg')}" /&gt;
     */
    public static function getClass(target:UIComponent, source:Object, params:Object):Class
    {
      if (!dictionary)
      {
        dictionary=new Dictionary();
      }
      if (source is String)
      {
        var loader:Loader=new Loader();
        loader.load(new URLRequest(source as String), new LoaderContext(true));
        source=loader;
      }
      dictionary[target]={source:source, params:params};
      return IconUtil;
    }

    public static function getRectangleIcon(target:UIComponent, color:uint=0xFF0000FF, width:int=16, height:int=16):Class
    {
      return getClass(target, rectangle, {width:width, height:height, color:color});
    }

    /**
     * @private
     */
    public function IconUtil():void
    {
      addEventListener(Event.ADDED, addedHandler, false, 0, true)
    }

    private function addedHandler(event:Event):void
    {
      if (parent)
      {
        if (parent is AccordionHeader)
        {
          var header:AccordionHeader=parent as AccordionHeader;
          loadData(header.data);
        }
        else if (parent is Tab)
        {
          var tab:Tab=parent as Tab;
          loadData(tab.data);
        }
        else
        {
          loadData(parent);
        }
      }
    }

    private function loadData(object:Object):void
    {
      var data:Object=dictionary[object];
      if (data)
      {
        var source:Object=data.source;
        var params:Object=getDefaultParams(data.params);
        bitmapData=new BitmapData(params.width, params.height, true, 0xFFFFFFFF);
        if (source is Loader)
        {
          var loader:Loader=source as Loader;
          if (!loader.content)
          {
            loader.contentLoaderInfo.addEventListener(Event.COMPLETE, function(event:Event):void
              {
                if (event && event.target && event.target is LoaderInfo)
                {
                  display(event.target.loader, params);
                }}, false, 0, true);
          }
          else
          {
            display(loader, params);
          }
        }
        else if (source is IBitmapDrawable || source is Function)
        {
          display(source, params);
        }
      }
    }

    private function getDefaultParams(params:Object):Object
    {
      var r:Object=params ? params : new Object();
      if (!r.hasOwnProperty("width") || r.width <= 0)
        r.width=DEFAULT_WIDTH;
      if (!r.hasOwnProperty("height") || r.height <= 0)
        r.height=DEFAULT_HEIGHT;
      return r;
    }

    private function display(obj:Object, params:Object):void
    {
      if (!bitmapData)
      {
        params=getDefaultParams(params);
        bitmapData=new BitmapData(params.width, params.height, true, 0xFFFFFFFF);
      }
      if (obj is Loader)
      {
        var loader:Loader=obj as Loader;
        bitmapData.draw(loader, new Matrix(bitmapData.width/loader.width, 0, 0, bitmapData.height/loader.height, 0, 0));
      }
      else if (obj is IBitmapDrawable)
      {
        var bitmap:IBitmapDrawable=obj as IBitmapDrawable;
        bitmapData.draw(bitmap);
      }
      else if (obj is Function)
      {
        var f:Function=obj as Function;
        f(bitmapData, params);
      }
      if (parent is UIComponent)
      {
        var component:UIComponent=parent as UIComponent;
        component.invalidateSize();
      }
    }

    private static function rectangle(bitmapData:BitmapData, params:Object):void
    {
      var o:Sprite=new SpriteAsset();
      var g:Graphics=o.graphics;
      var color:uint=(params && params.hasOwnProperty("color")) ? params.color : 0;
      g.clear();
      g.beginFill(color);
      g.drawRect(0, 0, params.width, params.height);
      g.endFill();
      bitmapData.draw(o, new Matrix(bitmapData.width/o.width, 0, 0, bitmapData.height/o.height, 0, 0));
    }

  }
}