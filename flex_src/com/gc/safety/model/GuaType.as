package com.gc.safety.model
{
	import com.gc.Constants;
	import com.gc.common.model.Branch;

	[RemoteClass(alias="com.gc.safety.po.GuaType")]
	[Bindable]
	public dynamic class GuaType
	{
		public var id:int;
		public var branch:Branch;
		public var name:String;
		public var desc:String;

		public function GuaType()
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
			return "GuaType{id=" + id + ", name=" + name + ", desc=" + desc + "}";
		}
		

	}
}