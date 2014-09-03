package com.gc.common.controller
{
  import com.gc.Beans;
  import com.gc.CommonEvent;
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;
  import com.gc.common.model.Person;
  import com.gc.common.model.SecurityLimit;
  import com.gc.common.model.SecurityUser;
  import com.gc.util.FaultUtil;

  import mx.rpc.AsyncToken;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class UserController
  {
    private static var _service:RemoteObject;
    private static var _user:SecurityUser;

    public function UserController()
    {
    }

    protected static function get service():RemoteObject
    {
      if (_service == null)
      {
        _service=Swiz.getBean(Beans.SERVICE_USER) as RemoteObject;
      }
      return _service;
    }

    protected static function exec(call:AsyncToken, resultHandler:Function, faultHandler:Function=null, eventArgs:Array=null):void
    {
      Swiz.executeServiceCall(call, resultHandler, faultHandler, eventArgs);
    }

    public static function getFaultHandler(handler:Function):Function
    {
      return FaultUtil.getGenericFaultHandler(service, handler);
    }

    public static function get user():SecurityUser
    {
      if (_user == null)
      {
        _user=Swiz.getBean(Beans.LOGIN_USER) as SecurityUser;
      }
      return _user;
    }

    public static function set user(u:SecurityUser):void
    {
      _user=u;
      Swiz.addBean(Beans.LOGIN_USER, _user);
    }

    public static function get userUseId():String
    {
      return user ? user.useId : null;
    }

    public static function get branch():Branch
    {
      return limit ? limit.branch : null;
    }

    public static function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public static function get branchName():String
    {
      return branch ? branch.name : null;
    }

    public static function get branchUseId():String
    {
      return branch ? branch.useId : null;
    }

    public static function get person():Person
    {
      return user ? user.person : null;
    }

    public static function get personId():int
    {
      return person ? person.id : 0;
    }

    public static function get personWorkerId():String
    {
      return person ? person.workerId : null;
    }

    public static function get personName():String
    {
      return person ? person.name : null;
    }

    public static function get limit():SecurityLimit
    {
      return user ? user.limit : null;
    }

    public static function get hrLimit():int
    {
      return limit ? limit.hrLimit : 0;
    }

    public static function get hrDepart():Department
    {
      return limit ? limit.hrLimitDepart : null;
    }

    public static function get hrDepartId():int
    {
      return hrDepart ? hrDepart.id : 0;
    }

    public static function get safetyLimit():int
    {
      return limit ? limit.safetyLimit : 0;
    }

    public static function get safetyDepart():Department
    {
      return limit ? limit.safetyLimitDepart : null;
    }

    public static function get safetyDepartId():int
    {
      return safetyDepart ? safetyDepart.id : 0;
    }

    public static const HR_READ:Array=[1,2,3,4,5,6,7,8,9];
    public static const HR_WRITE:Array=[1,3,4];
    public static const HR_CHECK_READ:Array=HR_READ;
    public static const HR_CHECK_WRITE:Array=[1,3,4,7,8];
    public static const HR_SAL_READ:Array=[1,3,5,6,7,9];
    public static const HR_SAL_WRITE:Array=[1,3,5,7,9];
    public static const HR_HEAD:Array=[1,2,3,4,5];
    public static const HR_SUB:Array=[6,7,8,9];

    public static function get hrRead():Boolean
    {
      return HR_READ.indexOf(hrLimit) >= 0;
    }

    public static function get hrWrite():Boolean
    {
      return HR_WRITE.indexOf(hrLimit) >= 0;
    }

    public static function get hrCheckRead():Boolean
    {
      return HR_CHECK_READ.indexOf(hrLimit) >= 0;
    }

    public static function get hrCheckWrite():Boolean
    {
      return HR_CHECK_WRITE.indexOf(hrLimit) >= 0;
    }

    public static function get hrSalRead():Boolean
    {
      return HR_SAL_READ.indexOf(hrLimit) >= 0;
    }

    public static function get hrSalWrite():Boolean
    {
      return HR_SAL_WRITE.indexOf(hrLimit) >= 0;
    }

    public static function get hrHead():Boolean
    {
      return HR_HEAD.indexOf(hrLimit) >= 0;
    }

    public static function get hrSub():Boolean
    {
      return HR_SUB.indexOf(hrLimit) >= 0;
    }

    public static function authenticate(user:SecurityUser, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.authenticate(user), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getLimitBranches(user:SecurityUser, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getLimitBranches(user), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getProfile(user:SecurityUser, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getProfile(user), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveProfile(user:SecurityUser, profile:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.saveProfile(user, profile), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function canWritePerson(person:Person):Boolean
    {
      if (person == null)
        return true;
      if (branchId != person.branch.id)
        return false;
      if (hrSub)
        return hrDepartId == person.depart.id;
      return hrWrite;
    }

  }
}