package com.gc.test.model
{

  [RemoteClass(alias="com.test.TestA")]
  [ExcludeClass]
  public dynamic class TestA
  {
    public var id:int;
    public var name:String;

    public function TestA(id:int=0, name:String=null)
    {
      this.id=id;
      this.name=name;
    }

    public function get uid():String
    {
      return "A_"+id;
    }

    public function toString():String
    {
      return "A{id=" + id + ", name=" + name + "}";
    }
  }
}