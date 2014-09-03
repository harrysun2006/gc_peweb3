package com.gc.safety.model
{
	import com.gc.Constants;
	import com.gc.common.model.Branch;

  	[RemoteClass(alias="com.gc.safety.po.GuarInfoPK")]
  	[Bindable]
	public dynamic class GuarInfoPK
	{
		public var branch:Branch;
		public var accNo:String;
		public var no:int;
		public function GuarInfoPK(branch:Branch = null, accNo:String = null, no:int = 0)
		{
			this.branch = branch;
			this.accNo = accNo;
			this.no = no;
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
	      return "(branch=" + branch + ", accNo=" + accNo + ", no=" + no + ")";
	    }

	}
}