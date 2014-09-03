package com.gc.hr.controller
{
  import com.gc.Beans;
  import com.gc.CommonEvent;
  import com.gc.FlexController;
  import com.gc.hr.model.SalFact;
  import com.gc.hr.model.SalTemplate;
  import com.gc.util.FaultUtil;

  import mx.collections.ArrayCollection;
  import mx.rpc.AsyncToken;
  import mx.rpc.events.ResultEvent;
  import mx.rpc.remoting.RemoteObject;

  import org.swizframework.Swiz;

  public class SalaryController
  {

    private static var _service:RemoteObject;

    [Bindable]
    public static var salItemList:ArrayCollection;

    public static const ALL_LISTNAMES:Array=["salItemList"];

    public function SalaryController()
    {
    }

    protected static function get service():RemoteObject
    {
      if (_service == null)
      {
        _service=Swiz.getBean(Beans.SERVICE_HR_SALARY) as RemoteObject;
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

    public static function preloadLists(branchId:int, names:Array=null, force:Boolean=false):void
    {
      if (names == null)
        names = ALL_LISTNAMES;
      if ((salItemList == null || force) && names.indexOf("salItemList") >= 0)
        getAllItems(branchId);
    }

//==================================== SalItem ====================================

    public static function getAllItems(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getAllItems(branchId), function(e:ResultEvent, arg:Object=null):void
        {
          salItemList = e.result as ArrayCollection;
          if (arg is Function)
            arg(e);
        }, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== SalDeptPsn & SalFixOnline ====================================

    public static function getDeptPsns(branchId:int, departId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDeptPsns(branchId, departId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getDeptPsnListA(qo:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDeptPsnListA(qo), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getDeptPsnListB(qo:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDeptPsnListB(qo), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getDeptPsnListC(qo:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDeptPsnListC(qo), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getDeptPsnListD(qo:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getDeptPsnListD(qo), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function changeDeptPsns(osdps:Array, nsdps:Array, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.changeDeptPsns(osdps, nsdps), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function deleteDeptPsns(sdps:Array, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.deleteDeptPsns(sdps), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getFixOnlines(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getFixOnlines(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function addFixOnlines(sfos:Array, user:String, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.addFixOnlines(sfos, user), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function terminateFixOnlines(sfos:Array, user:String, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.terminateFixOnlines(sfos, user), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function changeFixOnlines(sfos:Array, user:String, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.changeFixOnlines(sfos, user), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

//==================================== SalTemplate ====================================

    public static function getTemplates(branchId:int, departId:int, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getTemplates(branchId, departId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getTemplateItems(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getTemplateItems(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    /**
     * 对象格式为: {persons:persons, items:items, head:head, data:[{person:p1, stds:[std11, ..., std1n], sfos:[sfo11, ..., sfo1n]}, ..., {person:pn, stds:[stdn1, ..., stdnn], sfos:[sfon1, ..., sfonn]}]}
     * persons: 部门发薪人员列表
     * items: 所有模板明细中的项目(SalTemplateD.item)合集
     * head: SalTemplate(fetched: depart)
     * data:
     * 	- person: Person
     * 	- stds: SalTemplateD(fetched: id.template, person, item)
     *  - sfos: SalFixOnline(fetched: person, item), 使用depart, persons, items, downDate=9999-12-31条件过滤
     **/
    public static function createTemplatePersonsAndItems(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      // exec(service.createTemplatePersonsAndItems(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
      FlexController.exec({s:Beans.SERVICE_HR_SALARY, m:"createTemplatePersonsAndItems", p:[obj]}, successHandler, faultHandler);
    }

    /**
     * 对象格式为: {persons:persons, items:items, head:head, data:[{person:p1, stds:[std11, ..., std1n], sfos:[sfo11, ..., sfo1n]}, ..., {person:pn, stds:[stdn1, ..., stdnn], sfos:[sfon1, ..., sfonn]}]}
     * persons: 部门发薪人员列表
     * items: 所有模板明细中的项目(SalTemplateD.item)合集
     * head: SalTemplate(fetched: depart)
     * data:
     * 	- person: Person
     * 	- stds: SalTemplateD(fetched: id.template, person, item)
     *  - sfos: SalFixOnline(fetched: person, item), 使用depart, persons, items, downDate=9999-12-31条件过滤
     **/
    public static function getTemplatePersonsAndItems(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      // exec(service.getTemplatePersonsAndItems(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
      FlexController.exec({s:Beans.SERVICE_HR_SALARY, m:"getTemplatePersonsAndItems", p:[obj]}, successHandler, faultHandler);
    }

    public static function deleteTemplate(st:SalTemplate, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.deleteTemplate(st), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveTemplate(st:SalTemplate, oix:Array, ostds:Array, nst:Object, nix:Array, nstds:Array, successHandler:Function=null, faultHandler:Function=null):void
    {
      // exec(service.saveFact(sf, osfds, nsf, nsfds), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
      FlexController.exec({s:Beans.SERVICE_HR_SALARY, m:"saveTemplate", p:[st, oix, ostds, nst, nix, nstds]}, successHandler, faultHandler);
    }

//==================================== SalFact ====================================

    public static function getFacts(qo:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getFacts(qo), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    /**
     * 对象格式为: {persons:persons, items:items, head:head, data:[{person:p1, dts:[d11, ..., d1n], sfos:[sfo11, ..., sfo1n]}, ..., {person:pn, dts:[dn1, ..., dnn], sfos:[sfon1, ..., sfonn]}]}
     * persons: 部门发薪人员列表
     * items: 所有工资凭证明细中的项目(SalFactD.item)合集
     * head: SalFact(fetched: depart, issuer)
     * data:
     * 	- person: Person
     * 	- dts: SalFactD(fetched: id.fact, person, item)
     *  - sfos: SalFixOnline(fetched: person, item), 注意depart, onDate条件
     * template: SalTemplate, 发放使用的发薪模板
     **/
    public static function createFactPersonsAndItems(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      // exec(service.createFactPersonsAndItems(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
      FlexController.exec({s:Beans.SERVICE_HR_SALARY, m:"createFactPersonsAndItems", p:[obj]}, successHandler, faultHandler);
    }

    /**
     * 对象格式为: {persons:persons, items:items, head:head, data:[{person:p1, dts:[d11, ..., d1n], sfos:[sfo11, ..., sfo1n]}, ..., {person:pn, dts:[dn1, ..., dnn], sfos:[sfon1, ..., sfonn]}]}
     * persons: 部门发薪人员列表
     * items: 所有工资凭证明细中的项目(SalFactD.item)合集
     * head: SalFact(fetched: depart, issuer)
     * data:
     * 	- person: Person
     * 	- dts: SalFactD(fetched: id.fact, person, item)
     *  - sfos: SalFixOnline(fetched: person, item), 注意depart, onDate条件
     **/
    public static function getFactPersonsAndItems(obj:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      // exec(service.getFactPersonsAndItems(obj), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
      FlexController.exec({s:Beans.SERVICE_HR_SALARY, m:"getFactPersonsAndItems", p:[obj]}, successHandler, faultHandler);
    }

    public static function deleteFact(sf:SalFact, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.deleteFact(sf), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function saveFact(osf:SalFact, oix:Array, osfds:Array, nsf:Object, nix:Array, nsfds:Array, successHandler:Function=null, faultHandler:Function=null):void
    {
      // exec(service.saveFact(sf, osfds, nsf, nsfds), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
      FlexController.exec({s:Beans.SERVICE_HR_SALARY, m:"saveFact", p:[osf, oix, osfds, nsf, nix, nsfds]}, successHandler, faultHandler);
    }

    /**
     * 返回工资实发明细及凭证头
     * @param arr [branchId, id.no, depart, month, issueDate, issueTypeValue, issuer.workerId, summary, comment]
     * @return {facts: facts, factds: [salfact: items, ... salfact: items], list: [SalFactD, ... SalFactD]}<br/>
     * 		facts: 实际发放凭证列表
     * 		list : List<{@link SalFactD}><br/>
     */
    public static function getFactDs(qo:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getFactDs(qo), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }

    public static function getFactDs2(qo:Object, successHandler:Function=null, faultHandler:Function=null):void
    {
      exec(service.getFactDs2(qo), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
    }
  }
}