package com.gc.common.model
{
  import com.gc.Constants;

  [RemoteClass(alias="com.gc.common.po.Line")]
  [Bindable]
  public dynamic class Line
  {
    public var id:int;
    public var branch:Branch;
    public var no:String;
    public var name:String;
    public var desNo:String;
    public var authNo:String;
    public var dealType:String;
    public var lineType:String;
    public var busModel:String;
    public var onReason:String;
    public var onDate:Date;
    public var downDate:Date;
    public var downReason:String;
    public var mustBus:int;
    public var local:String;
    public var lineDirect:String;
    public var lc:Number;
    public var workTime:String;
    public var intCrowd:int;
    public var intQuiet:int;
    public var ticketMode1:String;
    public var ticketMode2:String;
    public var ticketMode3:String;
    public var lowPrice:Number;
    public var highPrice:Number;
    public var depart:Department;
    public var team:String;
    public var responsor:int;
    public var attemper:int;
    public var userDef1:String;
    public var userDef2:String;
    public var userDef3:String;
    public var length:Number;
    public var cycleTime:Number;
    public var startTime:String;
    public var endTime:String;
    public var emptyLc:Number;

    public function Line(id:int=0, branch:Branch=null, no:String=null, name:String=null)
    {
      this.id=id;
      this.branch=branch;
      this.no=no;
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

    public function get icon():Class
    {
      return null;
    }

    public function toString():String
    {
      return "Line{id=" + id + ", belong=" + branchId + ", no=" + no + ", name=" + name + "}";
    }
  }
}