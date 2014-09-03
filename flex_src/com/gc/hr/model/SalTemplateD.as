package com.gc.hr.model
{
  import com.gc.common.model.Branch;
  import com.gc.common.model.Person;

  [RemoteClass(alias="com.gc.hr.po.SalTemplateD")]
  [Bindable]
  public dynamic class SalTemplateD
  {
    public var id:SalTemplateDPK;
    public var person:Person;
    public var amount:Number;
    public var comment:String;

    public function SalTemplateD()
    {
      id=new SalTemplateDPK();
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

    public function get no():int
    {
      return id ? id.no : 0;
    }

    public function get item():SalItem
    {
      return id ? id.item : null;
    }

    public function get itemId():int
    {
      return id ? id.itemId : 0;
    }

    public function get personId():int
    {
      return person ? person.id : 0;
    }

    public function toString():String
    {
      return "SalTemplateD{belong=" + branchId + ", hd=" + hd + ", no=" + no + ", item=" + itemId + ", person=" + personId + "}";
    }

  }
}