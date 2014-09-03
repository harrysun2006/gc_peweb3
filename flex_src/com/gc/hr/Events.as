package com.gc.hr
{
  import flash.events.Event;

  public class Events extends Event
  {

//==================================== Personal ====================================

    public static const GET_PERSONS:String="getPersons@hrPersonalController";
    public static const ADD_PERSON:String="addPerson@hrPersonalController";
    public static const DEL_PERSON:String="delPerson@hrPersonalController";
    public static const SEL_PERSON:String="selPerson@hrPersonalController";

    public static const PERSON_TREE_REORGANIZING:String="reorganizingPersonTree";
    public static const PERSON_TREE_REFILTERING:String="refilteringPersonTree";

    public static const GET_HIRETYPES:String="getHireTypes@hrPersonalController";
    public static const ADD_HIRETYPE:String="addHireType@hrPersonalController";
    public static const DEL_HIRETYPE:String="delHireType@hrPersonalController";
    public static const SEL_HIRETYPE:String="selHireType@hrPersonalController";

    public static const GET_JOBGRADES:String="getJobGrades@hrPersonalController";
    public static const ADD_JOBGRADE:String="addJobGrade@hrPersonalController";
    public static const DEL_JOBGRADE:String="delJobGrade@hrPersonalController";
    public static const SEL_JOBGRADE:String="selJobGrade@hrPersonalController";

    public static const GET_JOBSPECS:String="getJobSpecs@hrPersonalController";
    public static const ADD_JOBSPEC:String="addJobSpec@hrPersonalController";
    public static const DEL_JOBSPEC:String="delJobSpec@hrPersonalController";
    public static const SEL_JOBSPEC:String="selJobSpec@hrPersonalController";

    public static const GET_MARRYSTATUSLIST:String="getMarryStatusList@hrPersonalController";
    public static const ADD_MARRYSTATUS:String="addMarryStatus@hrPersonalController";
    public static const DEL_MARRYSTATUS:String="delMarryStatus@hrPersonalController";
    public static const SEL_MARRYSTATUS:String="selMarryStatus@hrPersonalController";

    public static const GET_NATIVEPLACES:String="getNativePlaces@hrPersonalController";
    public static const ADD_NATIVEPLACE:String="addNativePlace@hrPersonalController";
    public static const DEL_NATIVEPLACE:String="delNativePlace@hrPersonalController";
    public static const SEL_NATIVEPLACE:String="selNativePlace@hrPersonalController";

    public static const GET_PEOPLES:String="getPeoples@hrPersonalController";
    public static const ADD_PEOPLE:String="addPeople@hrPersonalController";
    public static const DEL_PEOPLE:String="delPeople@hrPersonalController";
    public static const SEL_PEOPLE:String="selPeople@hrPersonalController";

    public static const GET_POLPARTIES:String="getPolParties@hrPersonalController";
    public static const ADD_POLPARTY:String="addPolParty@hrPersonalController";
    public static const DEL_POLPARTY:String="selPolParty@hrPersonalController";
    public static const SEL_POLPARTY:String="selPolParty@hrPersonalController";

    public static const GET_REGBRANCHES:String="getRegBranches@hrPersonalController";
    public static const ADD_REGBRANCH:String="addRegBranch@hrPersonalController";
    public static const DEL_REGBRANCH:String="delRegBranch@hrPersonalController";
    public static const SEL_REGBRANCH:String="selRegBranch@hrPersonalController";

    public static const GET_SCHDEGREES:String="getSchDegrees@hrPersonalController";
    public static const ADD_SCHDEGREE:String="addSchDegree@hrPersonalController";
    public static const DEL_SCHDEGREE:String="delSchDegree@hrPersonalController";
    public static const SEL_SCHDEGREE:String="selSchDegree@hrPersonalController";

    public static const GET_SCHGRADUATES:String="getGraduates@hrPersonalController";
    public static const ADD_SCHGRADUATE:String="addGraduate@hrPersonalController";
    public static const DEL_SCHGRADUATE:String="delGraduate@hrPersonalController";
    public static const SEL_SCHGRADUATE:String="selGraduate@hrPersonalController";

    public static const GET_SCHOOLINGS:String="getSchoolings@hrPersonalController";
    public static const ADD_SCHOOLING:String="addSchooling@hrPersonalController";
    public static const DEL_SCHOOLING:String="delSchooling@hrPersonalController";
    public static const SEL_SCHOOLING:String="selSchooling@hrPersonalController";

//==================================== Check ====================================

//==================================== Salary ====================================

    public static const GET_SALITEMS:String="getSalItems@hrSalaryController";
    public static const ADD_SALITEM:String="addSalItems@hrSalaryController";
    public static const DEL_SALITEM:String="delSalItems@hrSalaryController";
    public static const SEL_SALITEM:String="selSalItems@hrSalaryController";

    public static const ADD_SALDEPTPSN:String="addSalDeptPsn@hrSalaryController";
    public static const DEL_SALDEPTPSN:String="delSalDeptPsn@hrSalaryController";
    public static const EDIT_SALDEPTPSN:String="editSalDeptPsn@hrSalaryController";
    public static const SEL_SALDEPTPSN:String="selSalDeptPsn@hrSalaryController";

    public static const ADD_SALFIXONLINE:String="addSalFixOnline@hrSalaryController";
    public static const DEL_SALFIXONLINE:String="delSalFixOnline@hrSalaryController";
    public static const EDIT_SALFIXONLINE:String="editSalFixOnline@hrSalaryController";
    public static const SEL_SALFIXONLINE:String="selSalFixOnline@hrSalaryController";

    public static const ADD_SALTEMPLATE:String="addSalTemplate@hrSalaryController";
    public static const DEL_SALTEMPLATE:String="delSalTemplate@hrSalaryController";
    public static const EDIT_SALTEMPLATE:String="editSalTemplate@hrSalaryController";
    public static const SEL_SALTEMPLATE:String="selSalTemplate@hrSalaryController";
    
    public static const ADD_SALFACT:String="addSalFact@hrSalaryController";
    public static const DEL_SALFACT:String="delSalFact@hrSalaryController";
    public static const EDIT_SALFACT:String="editSalFact@hrSalaryController"; 
    public static const SEL_SALFACT:String="selSalFact@hrSalaryController";

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
      return "HR Event{type=" + type + ", data=" + data + ", args=" + args + ", bubbles=" + bubbles + ", cancelable=" + cancelable + ", phase=" + eventPhase + "}";
    }
  }

}
