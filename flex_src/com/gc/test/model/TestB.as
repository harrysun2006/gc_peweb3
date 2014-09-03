package com.gc.test.model
{
  import mx.core.IUID;

  [RemoteClass(alias="com.test.TestB")]
  [ExcludeClass]
  [Bindable]
  public dynamic class TestB
  {
    public var id:int;
    public var name:String;

    public function TestB(id:int=0, name:String=null)
    {
      this.id=id;
      this.name=name;
    }

    public function get alias():String
    {
      return "B_"+id;
    }

    public function set alias(value:String):void
    {
    }

    public function toString():String
    {
      return "B{id=" + id + ", name=" + name + "}";
    }
  }
}