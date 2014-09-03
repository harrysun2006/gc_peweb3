package com.gc.safety.controller
{
	import com.gc.CommonEvent;
	import com.gc.safety.model.Insurer;
	import com.gc.util.FaultUtil;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.AsyncToken;
	import mx.rpc.remoting.RemoteObject;
	
	import org.swizframework.Swiz;

	public class ClaimsController
	{
		public static var _service:RemoteObject;

		[Bindable]
		public var claimsList:ArrayCollection;

		public function ClaimsController()
		{
		}
		
		protected static function get service():RemoteObject
		{
			if (_service == null)
			{
				_service = Swiz.getBean("safetyClaimsService") as RemoteObject;
			}
			return _service;
		}
		
		protected static function exec(call:AsyncToken, resultHandler:Function=null, faultHandler:Function=null, eventArgs:Array=null):void
		{
			Swiz.executeServiceCall(call, resultHandler, faultHandler, eventArgs);
		}
		
		public static function getOutAndInGuaByAccId(branchId:int,id:int,successHandler:Function = null, faultHandler:Function = null):void {
			exec(service.getOutAndInGuaByAccId(branchId,id),CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}
		
//================================ Accident Gua======================================
		
		public static function saveAccOutGua(oldAC:ArrayCollection, newAC:ArrayCollection, branchId:int, successHandler:Function=null, faultHandler:Function=null):void
		{
			exec(service.saveAccOutGua(oldAC, newAC, branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}
		
		public static function saveAccInPsnGua(oldAC:ArrayCollection, newAC:ArrayCollection, branchId:int, successHandler:Function=null, faultHandler:Function=null):void
		{
			exec(service.saveAccInPsnGua(oldAC, newAC, branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}
		
//=============================== Get AccGua ============================
		public static function getAccGuaForModify(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
		{
			exec(service.getAccGuaForModify(branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}
		
//=============================== Query AccGua ====================================
		public static function getAccGuaForQuery(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
		{
			exec(service.getAccGuaForQuery(branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}
		
// ============================== Get AccGua =======================================
		public static function getAccInPsnGua(branchId:int, insurer:Insurer, successHandler:Function=null, faultHandler:Function=null):void
		{
			exec(service.getAccInPsnGua(branchId, insurer), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}
		
		public static function getAccOutGua(branchId:int, insurer:Insurer, successHandler:Function=null, faultHandler:Function=null):void
		{
			exec(service.getAccOutGua(branchId, insurer), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}

//=============================== Save AccGuaPay ==================================
		public static function saveAccOutGuaPay(oldAC:ArrayCollection, newAC:ArrayCollection, branchId:int, successHandler:Function=null, faultHandler:Function=null):void
		{
			exec(service.saveAccOutGuaPay(oldAC, newAC, branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}
		
		public static function saveAccInPsnGuaPay(oldAC:ArrayCollection, newAC:ArrayCollection, branchId:int, successHandler:Function=null, faultHandler:Function=null):void
		{
			exec(service.saveAccInPsnGuaPay(oldAC, newAC, branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}

//=============================== Get AccGuaPay ===================================
		public static function getAccGuaPayForModify(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
		{
			exec(service.getAccGuaPayForModify(branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}
		
		public static function getAccPayForQuery(branchId:int, successHandler:Function=null, faultHandler:Function=null):void
		{
			exec(service.getAccPayForQuery(branchId), CommonEvent.DEFAULT_RESULT_EVENT_HANDLER, FaultUtil.getGenericFaultHandler(service, faultHandler), [successHandler]);
		}
	}
}