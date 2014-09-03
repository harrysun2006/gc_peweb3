package com.gc.safety.model
{
  import com.gc.Constants;
  import com.gc.common.model.Branch;
  import com.gc.common.model.Equipment;

  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.safety.po.GuarInfo")]
  [Bindable]
  public dynamic class GuarInfo
  {
    public var id:GuarInfoPK;
    public var bus:Equipment;
    public var lineNo:String;
    public var useId:String;
    public var type:String;
    public var sits:int;
    public var archNo:String;
    public var powerNo:String;
    public var guaNo:String;
    public var guaDesc:String;
    public var guaCost:Number;
    public var fee:Number;
    public var fkGuarantee:Guarantee;
    //保单列表
    public var gis:ArrayCollection;
    //理赔列表
    public var ags:ArrayCollection;
    //赔付列表
    public var apgs:ArrayCollection;

    //保单树叶子节点显示
    public var guaCounts:int;
    public var guaPayFees:Number;

    public var reportNo:String;

    public function GuarInfo(branch:Branch = null, accNo:String = null, no:int = 0)
    {
      this.id = new GuarInfoPK(branch,accNo,no);
    }

    public function get label():String
    {
      return (bus == null) ? Constants.NULL_LABEL : bus.authNo;
    }

    public function get value():Object
    {
      return (bus == null) ? Constants.NULL_VALUE : bus.authNo;
    }

    public function get icon():Class
    {
      return bus ? bus.icon : null;
    }

    public function toString():String
    {
      return "Guarantee{id=" + id + ", guaNo=" + guaNo + "}";
    }

  }
}