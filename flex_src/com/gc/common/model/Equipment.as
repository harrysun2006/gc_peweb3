package com.gc.common.model
{
  import com.gc.Constants;
  import com.gc.util.LangUtil;

  [RemoteClass(alias="com.gc.common.po.Equipment")]
  [Bindable]
  public dynamic class Equipment
  {
    public var id:int;
    public var branch:Branch;
    public var useId:String;
    public var type:EquType;
    public var name:String;
    public var prodData:String;
    public var buyDate:Date;
    public var insureDate:Date;
    public var buyPrice:Number;
    public var manufacturer:int;
    public var provider:int;
    public var supporter:int;
    public var serviceProtocol:int;
    public var config:String;
    public var planUse:String;
    public var standByCheckPart:int;
    public var checkContain:String;
    public var maintainReq:int;
    public var sponsor:int;
    public var status:int;
    public var downDate:Date;
    public var comment:String;
    public var authDate:Date;
    public var authNo:String;
    public var productDate:Date;
    public var color:String;
    public var powerType:String;
    public var inPower1:Number;
    public var inPower2:Number;
    public var archNo:String;
    public var length:Number;
    public var width:Number;
    public var height:Number;
    public var weight:Number;
    public var config1:Number;
    public var config2:Number;
    public var config3:Number;
    public var config4:Number;
    public var config5:Number;
    public var outPower1:Number;
    public var outPower2:Number;
    public var spareUse1:String;
    public var spareUse2:String;
    public var spareUse3:String;
    public var spareUse4:String;
    public var spareUse5:String;
    public var acType:String;
    public var acNo:String;
    public var checkDate:Date;
    public var nextCheckDate:Date;
    public var regNo:String;
    public var regType:String;
    public var imports:String;
    public var brand:String;
    public var turnType:String;
    public var innerSize:Number;
    public var springNum:Number;
    public var netWeight:Number;
    public var loading:Number;
    public var drawing:Number;
    public var certName:String;
    public var certNo:String;
    public var impCertName:String;
    public var impCertNo:String;
    public var taxCertName:String;
    public var cmt1:String;
    public var cmt2:String;
    public var cmt3:String;
    public var cmt4:Number;
    public var cmt5:Number;
    public var cmt6:Number;
    public var lastMntDate:Date;
    public var lastMntLc:Number;
    public var totalLc:Number;
    public var equBelong:String;
    public var emission:String;
    public var roadWayNo:String;

    public var shift:String;
    private static const SHIFT_MAP:Object={};

    public function Equipment(id:int=0, branch:Branch=null, useId:String=null)
    {
      this.id=id;
      this.branch=branch;
      this.useId=useId;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get label():String
    {
      return authNo ? authNo : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return id;
    }

    public function get shift$():String
    {
      var s:String = (shift == "0" || shift == null) ? "even" : "odd";
      if (!SHIFT_MAP.hasOwnProperty(shift))
        SHIFT_MAP[shift]=LangUtil.getString("gcc", "shift."+s);
      return SHIFT_MAP[shift];
    }

    [Embed(source="assets/icons/16x16/bus.png")]
    public static const ICON:Class;

    public function get icon():Class
    {
      return ICON;
    }

    public function toString():String
    {
      return "Equipment{id=" + id + ", belong=" + branchId + ", useId=" + useId + ", name=" + name + "}";
    }

  }
}