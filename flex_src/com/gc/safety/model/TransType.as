package com.gc.safety.model
{
	import com.gc.Constants;
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.TransType")]
	[Bindable]
	public dynamic class TransType
	{
		public var id:int;
		public var branch:Branch;
		public var name:String;
		public var desc:String;
		
		public function TransType(id:int = 0) {
			this.id = id;
		}
		
		public function get label():String {
			return (name == null) ? Constants.NULL_LABEL : name;
		}
		
		public function get value():Object {
			return id;
		}
		
		public function get icon():Class {
			return null;
		}
		
	}
}