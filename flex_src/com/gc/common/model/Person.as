package com.gc.common.model
{
  import com.gc.Constants;
  import com.gc.common.controller.UserController;
  import com.gc.hr.controller.PersonalController;
  import com.gc.hr.model.ChkGroup;
  import com.gc.util.CommonUtil;
  import com.gc.util.DateUtil;
  import com.gc.util.LangUtil;
  import com.gc.util.HanZiUtil;

  import flash.utils.ByteArray;

  import mx.collections.ArrayCollection;
  import mx.collections.Sort;
  import mx.collections.SortField;
  import mx.rpc.events.ResultEvent;
  import mx.utils.ObjectProxy;
  import mx.utils.ObjectUtil;

  [RemoteClass(alias="com.gc.common.po.Person")]
  [Bindable]
  public dynamic class Person
  {
    public var id:int;
    public var branch:Branch;
    public var workerId:String;
    public var name:String;
    public var pid:String;
    public var sex:String;
    public var people:String;
    public var nativePlace:String;
    public var regType:String;
    public var regAddress:String;
    public var birthday:Date;
    public var marryStatus:String;
    public var annuities:String;
    public var accumulation:String;
    public var onDate:Date;
    public var status:int;
    public var downDate:Date;
    public var downReason:String;
    public var upgradeDate:Date;
    public var upgradeReason:String;
    public var type:String;
    public var salaryType:String;
    // 当前岗位或工种类别, 保留编码: 1：驾驶员，2：乘务员，3：调度员，4：管理人员， 5：维修工，6：其他人员
    private var _position:String;
    private var _fkPosition:Position;
    public var regBelong:String;
    public var party:String;
    public var grade:String;
    public var schooling:String;
    public var allotDate:Date;
    public var allotReason:String;
    public var depart:Department;
    public var office:String;
    public var line:Line;
    public var bus:Equipment;
    public var cert2No:String;
    public var cert2NoHex:String;
    public var fillTableDate:Date;
    public var commend:String;
    public var workDate:Date;
    public var retireDate:Date;
    public var serviceLength:String;
    public var inDate:Date;
    public var outDate:Date;
    public var workLength:String;
    public var groupNo:String;
    public var contractNo:String;
    public var contr1From:Date;
    public var contr1End:Date;
    public var contractReason:String;
    public var contr2From:Date;
    public var contr2End:Date;
    public var workType:String;
    public var level:int;
    public var techLevel:String;
    public var responsibility:String;
    public var cert1No:String;
    public var cert1NoDate:Date;
    public var serviceNo:String;
    public var serviceNoDate:Date;
    public var frontWorkResume:String;
    public var frontTrainingResume:String;
    public var specification:String;
    public var degree:String;
    public var graduate:String;
    public var skill:String;
    public var lanCom:String;
    public var national:String;
    public var state:String;
    public var city:String;
    public var address:String;
    public var zip:String;
    public var telephone:String;
    public var email:String;
    public var officeTel:String;
    public var officeExt:String;
    public var officeFax:String;
    public var lastModifier:Person;
    public var comment:String;
    public var psnPhoto:PsnPhoto;
    public var photo:ByteArray;
    public var chkGroup:ChkGroup;
    public var chkLongPlans:ArrayCollection;
    public var chkPlanDs:ArrayCollection;
    public var chkFactDs:ArrayCollection;

    // other non-serialized properties
    public var newCert2No:String;
    public var newCert2NoHex:String;

    // other calculated properties
    public var graduateDate:Date;
    public var partyOnDate:Date;

    // 绑定DataGrid里的CheckBox,缺少该量导致点击全选后不能马上显示
    // 可能可以用其他方法处理 override等
    public var selected:Boolean;

    // 92年之前的工龄(单位为月，社保体系建立之前的工龄，系统按缴金工龄处理)
    public var paySocialInsInit:int;
    // 92年之后使用薪资系统之前的缴金工龄
    public var paySocialInsAdj:int;
    // 薪资系统中有社保项发生额的月份
    public var paySocialInsSal:int;

    // 全局公用变量
    public static const DEFAULT_SEARCH:Object=new ObjectProxy();
    public static const qo:Object=new ObjectProxy();
    public static const DOWN_STATUS:String="#downStatus";
    public static const DOWN_STATUS_YES:String="yes";
    public static const DOWN_STATUS_NO:String="no";
    public static const DOWN_STATUS_NULL:String="null";

    public static const POSITION_DRIVER:String="1";
    public static const POSITION_CONDUCTOR:String="2";
    public static const POSITION_DISPATCHER:String="3";

    public static const SORT_WORKERID:Sort=new Sort();
    // CheckBox单独用做List的itemRender, enabled无效; 嵌在HBox中OK!!!
    public static const SC_WORKERID:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.workerId : null;
        var s2:String=obj2 && obj2.person ? obj2.person.workerId : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_NAME:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? HanZiUtil.pinyin(obj1.person.name) : null;
        var s2:String=obj2 && obj2.person ? HanZiUtil.pinyin(obj2.person.name) : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_PID:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.pid : null;
        var s2:String=obj2 && obj2.person ? obj2.person.pid : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_SEX:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.sex : null;
        var s2:String=obj2 && obj2.person ? obj2.person.sex : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_DEPART_NAME:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.departName : null;
        var s2:String=obj2 && obj2.person ? obj2.person.departName : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_OFFICE:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.office : null;
        var s2:String=obj2 && obj2.person ? obj2.person.office : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_LINE_NAME:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.lineName : null;
        var s2:String=obj2 && obj2.person ? obj2.person.lineName : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_BUS_USEID:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.busUseId : null;
        var s2:String=obj2 && obj2.person ? obj2.person.busUseId : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_BUS_AUTHNO:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.busAuthNo : null;
        var s2:String=obj2 && obj2.person ? obj2.person.busAuthNo : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_BUS_SHIFT:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.busShift : null;
        var s2:String=obj2 && obj2.person ? obj2.person.busShift : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_TYPE:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.type : null;
        var s2:String=obj2 && obj2.person ? obj2.person.type : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_POSITION_NAME:Function=function(obj1:Object, obj2:Object):int
      {
        var s1:String=obj1 && obj1.person ? obj1.person.positionName : null;
        var s2:String=obj2 && obj2.person ? obj2.person.positionName : null;
        return ObjectUtil.stringCompare(s1, s2);
      };
    public static const SC_ONDATE:Function=function(obj1:Object, obj2:Object):int
      {
        var d1:Date=obj1 && obj1.person ? obj1.person.onDate : null;
        var d2:Date=obj2 && obj2.person ? obj2.person.onDate : null;
        return ObjectUtil.dateCompare(d1, d2);
      };
    public static const SC_DOWNDATE:Function=function(obj1:Object, obj2:Object):int
      {
        var d1:Date=obj1 && obj1.person ? obj1.person.downDate : null;
        var d2:Date=obj2 && obj2.person ? obj2.person.downDate : null;
        return ObjectUtil.dateCompare(d1, d2);
      };

    private static const _LINK_ITEMS:Array=[
      {no:"#L01", name:"workerId", df:"person", enabled:false, selected:true, align:"left", scf:SC_WORKERID},
      {no:"#L02", name:"name", enabled:false, selected:true, align:"left", scf:SC_NAME},
      {no:"#L03", name:"pid", scf:SC_PID},
      {no:"#L04", name:"sex", scf:SC_SEX},
      {no:"#L05", name:"depart.name", scf:SC_DEPART_NAME},
      {no:"#L06", name:"office", scf:SC_OFFICE},
      {no:"#L07", name:"line.name", scf:SC_LINE_NAME},
      {no:"#L08", name:"bus.useId", scf:SC_BUS_USEID},
      {no:"#L09", name:"bus.authNo", scf:SC_BUS_AUTHNO},
      {no:"#L10", name:"bus.shift$", scf:SC_BUS_SHIFT},
      {no:"#L11", name:"type", scf:SC_TYPE},
      {no:"#L12", name:"fkPosition.name", scf:SC_POSITION_NAME},];

    public function Person(id:int=0, workerId:String=null, name:String=null)
    {
      this.id=id;
      this.workerId=workerId;
      this.name=name;
    }

    public function get uid():String
    {
      return "p#"+id;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get departId():int
    {
      return depart ? depart.id : 0;
    }

    public function get departNo():String
    {
      return depart ? depart.no : null;
    }

    public function get departName():String
    {
      return depart ? depart.name : null;
    }

    public function get lineId():int
    {
      return line ? line.id : 0;
    }

    public function get lineNo():String
    {
      return line ? line.no : null;
    }

    public function get lineName():String
    {
      return line ? line.name : null;
    }

    public function get busId():int
    {
      return bus ? bus.id : 0;
    }

    public function get busUseId():String
    {
      return bus ? bus.useId : null;
    }

    public function get busAuthNo():String
    {
      return bus ? bus.authNo : null;
    }

    public function get busShift():String
    {
      return bus ? bus.shift$ : null;
    }

    public function get isDown():Boolean
    {
      return downDate < Constants.MAX_DATE;
    }

    public function get isOn():Boolean
    {
      return downDate >= Constants.MAX_DATE;
    }

    // 本企业工龄
    public function get thisWorkLength():String
    {
      var m:int=DateUtil.getDiff(inDate, new Date());
      return inDate && m > 0 ? DateUtil.formatDuration(m) : "";
    }

    public function set thisWorkLength(value:String):void
    {
    }

    /**
     * 定义只读属性label, 对应于一般控件(Tree)的labelField(显示文本)
     **/
    public function get label():String
    {
      return workerId + "[" + name + "]";
    }

    public function get value():Object
    {
      return id;
    }

    [Embed(source="assets/icons/16x16/person.png")]
    public static const ICON:Class;

    public function get icon():Class
    {
      return ICON;
    }

    public function toString():String
    {
      return "Person{id=" + id + ", workerId=" + workerId + ", belong=" + branchId + ", name=" + name + "}";
    }

    public function get position():String
    {
      return _position;
    }

    public function set position(s:String):void
    {
      this._position=s;
      dispatchEvent(new Event("positionChanged"));
    }

    public function get positionName():String
    {
      return _fkPosition ? _fkPosition.name : null;
    }

    public function get fkPosition():Position
    {
      return _fkPosition;
    }

    public function set fkPosition(p:Position):void
    {
      this._fkPosition=p;
      this.position=p ? p.no : null;
    }

    public function get downDate$():String
    {
      return (downDate && downDate.time >= Constants.MAX_DATE.time) ? LangUtil.getString("gcc", "person.downStatus.no") 
        : DateUtil.formatDate(downDate);
    }

    public function set downDate$(s:String):void
    {
    }

    public function get contr1End$():String
    {
      return (contr1End && contr1End.time >= Constants.MAX_DATE.time) ? LangUtil.getString("gcc", "person.contr1End.no") 
        : DateUtil.formatDate(contr1End);
    }

    public function set contr1End$(s:String):void
    {
    }

    /**
     * 总工龄及进本单位之后的工龄(用inDate计算), 逻辑如下:
     * 1. 进本单位之后的工龄:
     *    如某员工2010-4-16进入本单位，那么他的合同从2010-5-1开始算，到2010-5-17算1个月。
     *    目前使用不足一月的不算(到2010-5-17算1个月), 如果截止日期小于inDate或inDate为空, 返回0。
     * 2. 总工龄:
     *    总工龄=进本单位之前的工龄(workLength)+进本单位之后的工龄
     **/
    public function getThisWorkLength(d:Date):int
    {
      var m:int=inDate ? DateUtil.getDiff(inDate, d) : 0;
      return m > 0 ? m : 0;
    }

    public function getThisWorkLength$(d:Date):String
    {
      return DateUtil.formatDuration(getThisWorkLength(d));
    }

    public function getTotalWorkLength(d:Date):int
    {
      return Number(workLength) + getThisWorkLength(d);
    }

    public function getTotalWorkLength$(d:Date):String
    {
      return DateUtil.formatDuration(getTotalWorkLength(d));
    }

    /**
     * 缴金工龄=92前工龄(paySocialInsInit) + 92后使用薪资系统之前的缴金月数(paySocialInsAdj) + 薪资系统中缴金月数(paySocialInsSal)
     * 1. paySocialInsSal已经由后台查询返回截止到某查询日的月数。
     * 2. 如果查询截止日小于使用薪资系统日期, 则paySocialInsInit和paySocialInsAdj不扣除。
     **/
    public function get paySocialIns():int
    {
      return paySocialInsInit + paySocialInsAdj + paySocialInsSal;
    }

    public function set paySocialIns(i:int):void
    {
    }

    public function get paySocialIns$():String
    {
      return DateUtil.formatDuration(paySocialIns);
    }

    public function set paySocialIns$(s:String):void
    {
    }

    // 返回截止到d的缴金月数
    protected function getPaySocialIns(d:Date):int
    {
      return 0;
    }

    protected function getPaySocialIns$(d:Date):String
    {
      return DateUtil.formatDuration(getPaySocialIns(d));
    }

    [Bindable("positionChanged")]
    public function get driverCardNo():String
    {
      return (position == POSITION_DRIVER) ? serviceNo : "";
    }

    [Bindable("positionChanged")]
    public function get conductorCardNo():String
    {
      return (position == POSITION_CONDUCTOR) ? serviceNo : "";
    }

    [Bindable("positionChanged")]
    public function get dispatcherCardNo():String
    {
      return (position == POSITION_DISPATCHER) ? serviceNo : "";
    }

    public static function init():void
    {
      CommonUtil.empty(qo);
      qo[DOWN_STATUS]=DEFAULT_SEARCH[DOWN_STATUS]=DOWN_STATUS_NO;
      // CommonUtil.copyProperties(Person.DEFAULT_SEARCH, Person.qo);
      SORT_WORKERID.fields=[new SortField("workerId")];
      for each (var obj:Object in _LINK_ITEMS)
      {
        obj.label=LangUtil.getString("gcc", "person."+obj.name);
        obj.enabled=!obj.hasOwnProperty("enabled") || obj.enabled;
        obj.selected=obj.hasOwnProperty("selected") && obj.selected;
        obj.df=obj.hasOwnProperty("df") ? obj.df : "person#"+obj.name;
        obj.dtf=obj.hasOwnProperty("dtf") ? obj.dtf : "person."+obj.name;
        obj.align=obj.hasOwnProperty("align") ? obj.align : "right";
      }
    }

    public static function get LINK_ITEMS():ArrayCollection
    {
      var list:ArrayCollection=new ArrayCollection();
      var obj:Object, li:Object;
      for each (obj in _LINK_ITEMS)
      {
        li=new ObjectProxy(ObjectUtil.copy(obj));
        if (obj.hasOwnProperty("scf"))
          li.scf=obj.scf;
        list.addItem(li);
      }
      return list;
    }

    public static function getSearch(qo:Object=null, orders:Array=null):Object
    {
      var type:String;
      var value:Object;
      var date:Date;
      if (qo == null)
        qo=Person.qo;
      var status:Object=qo[Person.DOWN_STATUS];
      qo=CommonUtil.clear(qo);
      for each (var node:Object in orders)
      {
        if (node is Date)
          date=node as Date;
        else
          value=node[Constants.PROP_NAME_VALUE];
        type=node[Constants.PROP_NAME_TYPE];
        switch (type)
        {
          case Constants.TYPE_ROOT:
          case Constants.TYPE_UNKNOWN:
            continue;
            break;
          case "birthday":
          case "contr1End":
          case "workDate":
          case "inDate":
            if (date.fullYear == 0)
              qo[type]=new Date(0);
            else
            {
              qo[type + "_from"]=DateUtil.getBeginYear(date, Constants.INTERVAL_YEARS);
              qo[type + "_to"]=DateUtil.getEndYear(date, Constants.INTERVAL_YEARS);
            }
            break;
          case "depart.no":
            qo["depart.id"]=value;
            break;
          default:
            // CommonUtil.setValue(qo, type, value);
            qo[type]=value;
            break;
        }
      }
      if (status == null || status == "" || status.toLowerCase() == Person.DOWN_STATUS_NULL)
      {
        qo["downDate_from"]=null;
        qo["downDate_to"]=null;
      }
      else if (status.toLowerCase() == Person.DOWN_STATUS_YES)
      {
        var d:Date=Constants.MAX_DATE;
        d.setMilliseconds(d.getMilliseconds() - 1);
        qo["downDate_from"]=null;
        qo["downDate_to"]=d;
      }
      else if (status.toLowerCase() == Person.DOWN_STATUS_NO)
      {
        qo["downDate_from"]=Constants.MAX_DATE;
        qo["downDate_to"]=null;
      }
      qo.setPropertyIsEnumerable("downDate_from", qo["downDate_from"]!=null);
      qo.setPropertyIsEnumerable("downDate_to", qo["downDate_to"]!=null);
      return qo;
    }

    private static var _ALL:ArrayCollection=null;
    private static var _ALL_MAP:Object=new Object();

    public static function indexOfAll(obj:Object):int
    {
      return obj is Person && _ALL_MAP.hasOwnProperty(obj.id) ? int(_ALL_MAP[obj.id]) : -1;
    }

    public static function get ALL():ArrayCollection
    {
      if (!_ALL)
      {
        _ALL=new ArrayCollection();
        PersonalController.getPersons(UserController.limit, {}, ["workerId"], function(e:ResultEvent):void
          {
            var i:int=0;
            for each(var p:Person in e.result)
            {
              _ALL.addItem(p);
              _ALL_MAP[p.id]=i++;
            }
          });
      }
      return _ALL;
    }
  }
}