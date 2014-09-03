package com.gc.controls
{
  import com.gc.util.CommonUtil;

  import mx.core.IFactory;
  import mx.core.UIComponent;

  public class AdvancedClassFactory implements IFactory
  {
    public var generator:Class;
    public var properties:Object;
    public var listeners:Array;
    public var styles:Object;

    public function AdvancedClassFactory(generator:Class=null, properties:Object=null, listeners:Array=null, styles:Object=null)
    {
      super();
      this.generator=generator;
      this.properties=properties;
      this.listeners=listeners;
      this.styles=styles;
    }

    public function newInstance():*
    {
      var instance:Object = new generator();
      CommonUtil.copyProperties(properties, instance);
      CommonUtil.addEventListeners(instance, listeners);
      if (instance is UIComponent)
      {
        var comp:UIComponent=instance as UIComponent;
        for (var name:String in styles)
          comp.setStyle(name, styles[name]);
      }
      return instance;
    }
  }
}