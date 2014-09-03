package com.gc.common.model
{

  import com.gc.Constants;

  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.common.po.EquOnline")]
  [Bindable]
  public dynamic class EquOnline
  {
    public var branch:Branch;
    public var id:int;
    public var equipment:Equipment;
    public var installer:Person;
    public var responsor:Person;
    public var config:String;
    public var depart:Department;
    public var address:String;
    public var useLimits:String;
    public var configChange:String;
    public var isMainEqu:int;
    public var soft:int;
    public var onDate:Date;
    public var downDate:Date;
    public var onReason:String;
    public var downReason:String;
    public var checkPart:int;
    public var checkContain:String;
    public var lastCheckDate:Date;
    public var comment:String;
    public var fromStatus:int;
    public var toStatus:int;
    public var downDepart:Department;
    public var installor:Person;
    public var whither:String;
    public var downComment:String;
    public var team:String;
    public var line:Line;
    public var park:int;
    public var monthMNT:String;
    public var weekMNT:String;
    public var fuel:Number;
    public var lube:Number;
    public var type:String;
    public var planUse:String;

    //事故树用
    public var accs:ArrayCollection;
    public var trans:ArrayCollection=new ArrayCollection();

    public var mileage:Number;

    public function EquOnline(id:int=0, equipment:Equipment=null, depart:Department=null)
    {
      this.id=id;
      this.equipment=equipment;
      this.depart=depart;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get equipmentId():int
    {
      return equipment ? equipment.id : 0;
    }

    public function get departId():int
    {
      return depart ? depart.id : 0;
    }

    public function get label():String
    {
      return equipment ? equipment.label : Constants.NULL_LABEL;
    }

    public function get value():Object
    {
      return id;
    }

    public function get icon():Class
    {
      return equipment ? equipment.icon : null;
    }

    public function toString():String
    {
      return "EquOnline{id=" + id + ", equipmentId=" + equipmentId + ", departId=" + departId + "}";
    }
  }
}