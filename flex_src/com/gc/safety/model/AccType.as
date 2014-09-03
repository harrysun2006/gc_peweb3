package com.gc.safety.model
{
	import com.gc.Constants;
	import com.gc.common.model.Branch;
	
	/**
	 * RemoteClass用于与远程方法返回或调用的对象映射 
	 * dynamic的类可以动态定义属性branch["children"]
	 **/
	[RemoteClass(alias="com.gc.safety.po.AccType")]
	[Bindable]
	public dynamic class AccType
	{
		public var id:int;
		public var branch:Branch;
		public var name:String;
		public var desc:String;
		
		public function AccType() {}
		
		public function get label():String
		{
			return (name == null) ? Constants.NULL_LABEL : name;
		}
		
		public function get value():Object
		{
			return (name == null) ? Constants.NULL_VALUE : name;
		}
		
		public function toString():String {
			return "AccType{id=" + id + ", name=" + name + ", desc=" + desc + "}";
		}

	}
}