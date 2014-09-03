package com.gc.common.model
{
  import com.gc.Constants;

  [RemoteClass(alias="com.gc.common.po.Weather")]
  [Bindable]
  public dynamic class Weather
  {
    public var id:int;
    public var branch:Branch;
    public var name:String;
    public var desc:String;

    public function Weather()
    {
    }

    public function get label():String
    {
      return name ? name : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return name ? name : Constants.NULL_VALUE;
    }

    public function toString():String
    {
      return "Weather{id=" + id + ", name=" + name + ", desc=" + desc + "}";
    }

  }
}