package com.gc.safety.model
{
	import com.gc.Constants;
	import com.gc.common.model.Branch;

	[RemoteClass(alias="com.gc.safety.po.AccObject")]
	[Bindable]
	public dynamic class AccObject
	{

		public var id:int;
		public var branch:Branch;
		public var name:String;
		public var desc:String;

		public function AccObject()
		{
		}
		
		public function get label():String
		{
			return (name == null) ? Constants.NULL_LABEL : name;
		}
		
		public function get value():Object
		{
			return (name == null) ? Constants.NULL_VALUE : name;
		}
		
		public function toString():String {
			return "AccObject{id=" + id + ", name=" + name + ", desc=" + desc + "}";
		}

	}
}