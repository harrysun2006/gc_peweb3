package com.gc.hr.model
{
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.SalTemplateDPK")]
  [Bindable]
  public dynamic class SalTemplateDPK
  {
    public var branch:Branch;
    public var template:SalTemplate;
    public var no:int;
    public var item:SalItem;

    public function SalTemplateDPK()
    {
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get templateId():int
    {
      return template ? template.id : 0;
    }

    public function get itemId():int
    {
      return item ? item.id : 0;
    }

    public function toString():String
    {
      return "(belong=" + branchId + ", template=" + templateId + ", no=" + no + ", item=" + itemId + ")";
    }
  }
}