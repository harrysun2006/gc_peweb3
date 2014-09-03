package com.gc.safety.model
{
	import com.gc.Constants;
	import com.gc.common.model.Branch;
	
	[RemoteClass(alias="com.gc.safety.po.AccLevel")]
  	[Bindable]
	public dynamic class AccLevel
	{
		
		public var id:int;
   		public var branch:Branch;
    	public var name:String;
    	public var desc:String;

		public function AccLevel()
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
			return "AccLevel{id=" + id + ", name=" + name + ", desc=" + desc + "}";
		}

	}
}