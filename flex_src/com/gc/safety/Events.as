package com.gc.safety
{
  import com.gc.safety.model.*;

  import flash.events.Event;
  import flash.events.MouseEvent;

  public class Events extends Event
  {
    public static const ADD:String = "add";
    public static const DELETE:String = "delete";
    public static const UPDATE:String = "update";
    public static const ADD_PO:String = "addpo";
    public static const CLOSE:String = "close";
    public static const GET:String = "get";
    public static const DBCLICKED:String = "doubleClick";
    public static const CLICKED:String = "clicked";
    public static const SELECTED:String = "selected";
    public static const ADD_REPORTNO:String = "addreportno";
    public static const ADD_OUTPSNFEE:String = "addoutpsnfee";
    public static const ADD_OUTOBJFEE:String = "addoutobjfee";
    public static const ADD_INPSNFEE:String = "addinpsnfee";
    public static const SAVE_SUCCESS:String = "savesuccess";
    public static const DEL_SUCCESS:String = "delsuccess";
    public static const UPDATE_SUCCESS:String = "updatesuccess";
    public static const REFRESH:String = "refresh";
    public static const ACC_REFILTERING:String = "accrefiltering";

    //定义事件
    public static const MOUSE_DBCLICKED_EVENT:MouseEvent = new MouseEvent(MouseEvent.DOUBLE_CLICK);

//==================================== Accident ====================================

    public static const GET_ACCTYPES:String="getAccTypes@safetyAccidentController";
    public static const ADD_ACCTYPE:String="addAccType@safetyAccidentController";
    public static const DEL_ACCTYPE:String="delAccType@safetyAccidentController";
    public static const SEL_ACCTYPE:String="selAccType@safetyAccidentController";

    public static const GET_ACCLEVELS:String="getAccLevels@safetyAccidentController";
    public static const ADD_ACCLEVEL:String="addAccLevel@safetyAccidentController";
    public static const DEL_ACCLEVEL:String="delAccLevel@safetyAccidentController";
    public static const SAVE_ACCLEVEL:String="saveAccLevel@safetyAccidentController";

    public static const GET_ACCDUTIES:String="getAccDuties@safetyAccidentController";
    public static const ADD_ACCDUTY:String="addAccDuty@safetyAccidentController";
    public static const DEL_ACCDUTY:String="delAccDuty@safetyAccidentController";
    public static const SAVE_ACCDUTY:String="saveAccDuty@safetyAccidentController";

    public static const GET_ACCEXTENTS:String="getAccExtents@safetyAccidentController";
    public static const ADD_ACCEXTENT:String="addAccExtent@safetyAccidentController";
    public static const DEL_ACCEXTENT:String="delAccExtent@safetyAccidentController";
    public static const SAVE_ACCEXTENT:String="saveAccExtent@safetyAccidentController"

    public static const GET_ACCPSNS:String="getAccPsns@safetyAccidentController";
    public static const ADD_ACCPSN:String="addAccPsn@safetyAccidentController";
    public static const DEL_ACCPSN:String="delAccPsn@safetyAccidentController";
    public static const SAVE_ACCPSN:String="saveAccPsn@safetyAccidentController"

    public static const GET_ACCPAYPSNS:String="getAccPayPsns@safetyAccidentController";
    public static const ADD_ACCPAYPSN:String="addAccPayPsn@safetyAccidentController";
    public static const DEL_ACCPAYPSN:String="delAccPayPsn@safetyAccidentController";
    public static const SAVE_ACCPAYPSN:String="saveAccPayPsn@safetyAccidentController"

    public static const GET_ACCOBJECT:String="getCurrentAccObject@safetyAccidentController";
    public static const ADD_ACCOBJECT:String="addAccObject@safetyAccidentController";
    public static const DEL_ACCOBJECT:String="delAccObject@safetyAccidentController";
    public static const SAVE_ACCOBJECT:String="saveAccObject@safetyAccidentController";

    public static const GET_ACCPAYOBJECTS:String="getAccPayObjects@safetyAccidentController";
    public static const ADD_ACCPAYOBJECT:String="addAccPayObject@safetyAccidentController";
    public static const DEL_ACCPAYOBJECT:String="delAccPayObject@safetyAccidentController";
    public static const SAVE_ACCPAYOBJECT:String="saveAccPayObject@safetyAccidentController";

    public static const GET_ACCPROCESSOR:String="getCurrentAccProcessor@safetyAccidentController";
    public static const ADD_ACCPROCESSOR:String="addAccProcessor@safetyAccidentController";
    public static const DEL_ACCPROCESSOR:String="delAccProcessor@safetyAccidentController";
    public static const SAVE_ACCPROCESSOR:String="saveAccProcessor@safetyAccidentController";

//==================================== Guarantee ====================================

    public static const GET_GUARANTEES:String="getGuarantees@safetyGuaranteeController";
    public static const ADD_GUARANTEE:String="addGuarantee@safetyGuaranteeController";
    public static const DEL_GUARANTEE:String="delGuarantee@safetyGuaranteeController";
    public static const SEL_GAURANTEE:String="selGuarantee@safetyGuaranteeController";

    public static const GET_GUARINFOS:String="getGuarInfos@safetyGuaranteeController";
    public static const GET_GUARINFOS1:String="getGuarInfos1@safetyGuaranteeController";
    public static const ADD_GUARINFO:String="addGuarInfo@safetyGuaranteeController";
    public static const DEL_GUARINFO:String="delGuarInfo@safetyGuaranteeController";
    public static const SEL_GAURINFO:String="selGuarInfo@safetyGuaranteeController";

    public static const GET_GUARANTEETYPES:String="getCurrentGuaranteeTypes@safetyGuaranteeController";
    public static const ADD_GUARANTEETYPE:String="addGuaranteeType@safetyGuaranteeController";
    public static const DEL_GUARANTEETYPE:String="delGuaranteeType@safetyGuaranteeController";
    public static const SAVE_GUARANTEETYPE:String="saveGuaranteeType@safetyGuaranteeController";

    public static const GET_INSURERS:String="getCurrentInsurers@safetyGuaranteeController";
    public static const ADD_INSURER:String="addInsurer@safetyGuaranteeController";
    public static const DEL_INSURER:String="delInsurer@safetyGuaranteeController";
    public static const SAVE_INSURER:String="saveInsurer@safetyGuaranteeController";

//==================================== Trans ====================================

    public static const GET_TRANSINFOS:String="getTransInfos@safetyTransController";
    public static const ADD_TRANSINFO:String="addTransInfo@safetyTransController";
    public static const DEL_TRANSINFO:String="delTransInfo@safetyTransController";
    public static const SEL_TRANSINFO:String="selTransInfo@safetyTransController";

    public static const GET_TRANSTYPES:String="getTransTypes@safetyTransController";
    public static const ADD_TRANSTYPE:String="addTransType@safetyTransController";
    public static const DEL_TRANSTYPE:String="delTransType@safetyTransController";
    public static const SEL_TRANSTYPE:String="selTransType@safetyTransController";

    public var data:Object;
    public var args:Array;

    public function Events(type:String, data:Object=null, args:Array=null, bubbles:Boolean=true, cancelable:Boolean=false)
    {
      this.data=data;
      this.args=args;
      super(type, bubbles, cancelable);
    }

    override public function clone():Event
    {
      return new Events(type, data, args);
    }

    override public function toString():String
    {
      return "Safety Event{type=" + type + ", data=" + data + ", args=" + args + ", bubbles=" + bubbles + ", cancelable=" + cancelable + ", phase=" + super.eventPhase + "}";
    }
  }

}
