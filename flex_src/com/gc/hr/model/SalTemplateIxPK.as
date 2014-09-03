package com.gc.hr.model
{
  import com.gc.common.model.Branch;

  [RemoteClass(alias="com.gc.hr.po.SalTemplateIxPK")]
  [Bindable]
  public dynamic class SalTemplateIxPK
  {
    public var branch:Branch;
    public var template:SalTemplate;
    public var item:SalItem;

    public function SalTemplateIxPK()
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
      return "(belong=" + branchId + ", template=" + templateId + ", item=" + itemId + ")";
    }
  }
}