package com.gc.common.model
{
  import com.gc.Constants;

  [RemoteClass(alias="com.gc.common.po.EquType")]
  [Bindable]
  public dynamic class EquType
  {
    public var id:int;
    public var name:String;
    public var comment:String;


    public function EquType(id:int = 0)
    {
      this.id = id;
    }

    public function get label():String
    {
      return name ? name : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return name ? name : Constants.NULL_VALUE;
    }

    public function get icon():Class
    {
      return null;
    }

    public function toString():String
    {
      return "EquType{id=" + id + ", name=" + name + "}";
    }

  }
}