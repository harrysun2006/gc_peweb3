package com.gc.hr.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.hr.po.SalVarOnline")]
  [ExcludeClass]
  [Bindable]
  public dynamic class SalVarOnline
  {
    public var id:int;
    public var branch:Branch;
    public var depart:Department;
    public var person:Person;
    public var item:SalItem;
    public var onDate:Date;
    public var downDate:Date;
    public var rate:Number;
    public var comment:String;

    public function SalVarOnline()
    {
    }

  }
}