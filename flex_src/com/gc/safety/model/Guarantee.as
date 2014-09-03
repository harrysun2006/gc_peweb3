package com.gc.safety.model
{
	import com.gc.Constants;
	import com.gc.common.model.Branch;
	import com.gc.common.model.Person;
	
	import mx.collections.ArrayCollection;
 	
 	[RemoteClass(alias="com.gc.safety.po.Guarantee")]
  	[Bindable]
	public dynamic class Guarantee
	{
		public var id:GuaranteePK;
		public var inputDate:Date;
		public var type:GuaType;
		public var insurer:Insurer;
		public var onDate:Date;
		public var downDate:Date; 
		public var doPsn:Person;
		public var insurerPsn:String;
		public var desc:String;
		public var guarInfos:ArrayCollection;
		
		public function Guarantee(branch:Branch = null, accNo:String = null)
		{
			this.id = new GuaranteePK(branch,accNo);
		}
		
		public function get label():String
	    {
	      return (id == null) ? Constants.NULL_LABEL : id.label;
	    }
	
	    public function get value():Object
	    {
	      return (id == null) ? Constants.NULL_VALUE : id.value;
	    }
	
	    public function get icon():Class
	    {
	      return null;
	    }
	
	    public function toString():String
	    {
	      return "Guarantee{id=" + id + ", insurer=" + insurer + "}";
	    }

	}
}