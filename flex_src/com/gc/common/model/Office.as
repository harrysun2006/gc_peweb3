package com.gc.common.model
{
  import com.gc.Constants;

  [RemoteClass(alias="com.gc.common.po.Office")]
  [Bindable]
  public dynamic class Office
  {
    public var id:OfficePK;
    public var duty:String;
    public var leader:Person;
    public var comment:String;

    public function Office(branch:Branch=null, depart:Department=null, name:String=null)
    {
      this.id=new OfficePK(branch, depart, name);
    }

    public function get branch():Branch
    {
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get depart():Department
    {
      return id ? id.depart : null;
    }

    public function get departId():int
    {
      return id ? id.departId : 0;
    }

    public function get name():String
    {
      return id ? id.name : Constants.NULL_NAME;
    }

    public function get label():String
    {
      return id ? id.label : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return id ? id.value : Constants.NULL_VALUE;
    }

    public function get icon():Class
    {
      return null;
    }

    public function toString():String
    {
      return "Office{id=" + id + "}";
      // return (id == null) ? "" : id.name;
    }
  }
}