package com.gc.hr.model
{
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.SalTemplateIx")]
  [Bindable]
  public dynamic class SalTemplateIx
  {
    public var id:SalTemplateIxPK;
    public var no:int;

    public function SalTemplateIx()
    {
      id=new SalTemplateIxPK();
    }

    public function get branch():Branch
    {
      return id ? id.branch : null;
    }

    public function get branchId():int
    {
      return id ? id.branchId : 0;
    }

    public function get template():SalTemplate
    {
      return id ? id.template : null;
    }

    public function get hd():int
    {
      return id ? id.templateId : 0;
    }

    public function get item():SalItem
    {
      return id ? id.item : null;
    }

    public function get itemId():int
    {
      return id ? id.itemId : 0;
    }

    public function toString():String
    {
      return "SalTemplateIx{belong=" + branchId + ", hd=" + hd + ", item=" + itemId + ", no=" + no + "}";
    }

  }
}