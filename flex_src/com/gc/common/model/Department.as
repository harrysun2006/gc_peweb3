package com.gc.common.model
{
  import com.gc.Constants;

  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.common.po.Department")]
  [Bindable]
  public dynamic class Department
  {
    public var id:int;
    public var branch:Branch;
    public var name:String;
    public var duty:String;
    public var onDate:Date;
    public var status:int;
    public var downDate:Date;
    public var manager:Person;
    public var national:String;
    public var state:String;
    public var city:String;
    public var zip:String;
    public var address:String;
    public var telephone:String;
    public var email:String;
    public var comment:String;
    public var officeTel:String;
    public var officeExt:String;
    public var officeFax:String;
    public var no:String;
    public var offices:ArrayCollection;
    public var lines:ArrayCollection;
    public var equOnlines:ArrayCollection;
    public var equipments:ArrayCollection;
    public var checkGroups:ArrayCollection;

    public function Department(id:int=0, branch:Branch=null, name:String=null)
    {
      this.id=id;
      this.branch=branch;
      this.name=name;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get label():String
    {
      return name ? name : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return id;
    }

    [Embed(source="assets/icons/16x16/depart.png")]
    public static const ICON:Class;

    public function get icon():Class
    {
      return ICON;
    }

    public function toString():String
    {
      return "Department{id=" + id + ", belong=" + branchId + ", name=" + name + "}";
    }
  }
}