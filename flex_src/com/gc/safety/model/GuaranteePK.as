package com.gc.safety.model
{
	import com.gc.Constants;
	import com.gc.common.model.Branch;

  	[RemoteClass(alias="com.gc.safety.po.GuaranteePK")]
  	[Bindable]
	public dynamic class GuaranteePK
	{
		public var branch:Branch;
		public var accNo:String;
		
		public function GuaranteePK(branch:Branch = null, accNo:String = null)
		{
			this.branch = branch;
			this.accNo = accNo;
		}
		
		public function get label():String
	    {
	      return (accNo == null) ? Constants.NULL_LABEL : accNo;
	    }
	
	    public function get value():Object
	    {
	      return (accNo == null) ? Constants.NULL_VALUE : accNo;
	    }
	
	    public function get icon():Class
	    {
	      return null;
	    }
	
	    public function toString():String
	    {
	      return "(branch=" + branch + ", accNo=" + accNo + ")";
	    }

	}
}