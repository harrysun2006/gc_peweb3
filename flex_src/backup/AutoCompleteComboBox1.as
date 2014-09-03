package backup
{
  import flash.events.Event;

  import mx.controls.ComboBox;
  import mx.controls.listClasses.IDropInListItemRenderer;
  import mx.controls.listClasses.IListItemRenderer;
  import mx.core.IDataRenderer;
  import mx.core.mx_internal;

  use namespace mx_internal;

  [ExcludeClass]
  public class AutoCompleteComboBox1 extends ComboBox implements IDataRenderer, IDropInListItemRenderer, IListItemRenderer
  {
    public function AutoCompleteComboBox1()
    {
    }

    override protected function textInput_changeHandler(event:Event):void
    {
      super.textInput_changeHandler(event);
      collection.filterFunction=filter;
      var savedText:String=this.text;
      if (collection.refresh())
      {
        this.dropdown.selectedIndex=-1;
        this.dropdown.verticalScrollPosition=0;
        this.text=savedText;
        this.textInput.setFocus();
      }
    }

    override public function set selectedIndex(value:int):void
    {
      super.selectedIndex = value;
      this.selectionChanged = false;
      this.invalidateDisplayList();
    }

    override public function set dataProvider(value:Object):void
    {
      this.selectionChanged = true;
      super.dataProvider = value;
    }

    private function filter(item:Object):Boolean
    {
      return item["label"].toString().indexOf(this.text)>=0;
    }

  }
}