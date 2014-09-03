package com.gc.hr.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Department;
  import com.gc.common.model.Person;
  import com.gc.util.CommonUtil;
  import com.gc.util.LangUtil;

  [RemoteClass(alias="com.gc.hr.po.SalFact")]
  [Bindable]
  public dynamic class SalFact
  {
    public var id:SalFactPK
    public var depart:Department;
    public var date:Date;
    public var issueDate:Date;
    public var issueType:String;
    public var summary:String;
    public var issuer:Person;
    public var comment:String;

    public static const ITYPE_CASH:Object={value:"C", name:"cash", label:LangUtil.getString("gcc_hr", "salFact.itype.cash")};
    public static const ITYPE_TRANS:Object={value:"T", name:"trans", label:LangUtil.getString("gcc_hr", "salFact.itype.trans")};
    public static const ITYPE_GOODS:Object={value:"G", name:"goods", label:LangUtil.getString("gcc_hr", "salFact.itype.goods")};
    public static const ITYPE_LIST:Array=[ITYPE_CASH, ITYPE_TRANS, ITYPE_GOODS];

    public function SalFact(branch:Branch=null, no:String=null)
    {
      this.id=new SalFactPK(branch, no);
      this.issueType=ITYPE_CASH.value;
    }

    public function get uid():String
    {
      return "sf#" + no;
    }

    public function get branch():Branch
    {
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get no():String
    {
      return id ? id.no : null;
    }

    public function get departId():int
    {
      return depart ? depart.id : 0;
    }

    public function get departName():String
    {
      return depart ? depart.name : null;
    }

    public function get issuerId():int
    {
      return issuer ? issuer.id : 0;
    }

    public function get issuerName():String
    {
      return issuer ? issuer.name : null;
    }

    public function get itype$():Object
    {
      return CommonUtil.findObject(ITYPE_LIST, issueType);
    }

    public function set itype$(value:Object):void
    {
      issueType=value && value.value ? value.value : issueType;
    }

    public function toString():String
    {
      return "SalFact{belong=" + branchId + ", no=" + no + ", depart=" + departId + "}";
    }

  }

}