package com.gc.hr.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.ChkDisp")]
  [Bindable]
  public dynamic class ChkDisp
  {
    public var id:int;
    public var branch:Branch;
    public var no:String;
    public var name:String;
    public var comment:String;

    public function ChkDisp(branch:Branch=null, id:int=0, no:String=null, name:String=null, comment:String=null)
    {
      this.branch=branch;
      this.id=id;
      this.no=no;
      this.name=name;
      this.comment=comment;
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
      return no ? no : Constants.NULL_VALUE;
    }

    public static const color:uint=Constants.Black;

    public function get color():uint
    {
      return ChkDisp.color;
    }

    public static const bgColor:uint=0xFFFFCC;

    public function get bgColor():uint
    {
      return ChkDisp.bgColor;
    }

    public static function get ICON():Class
    {
      return $Icon;
    }

    [Exclude(name="icon", kind="property")]
    public function get icon():Class
    {
      return $Icon;
    }

    public function toString():String
    {
      return "ChkDisp{id=" + id + ", belong=" + branchId + ", name=" + name + "}";
    }

  }
}

import com.gc.controls.RectangleIcon;
import com.gc.hr.model.ChkDisp;

class $Icon extends com.gc.controls.RectangleIcon
{
  public function $Icon():void
  {
    color=ChkDisp.bgColor;
  }
}