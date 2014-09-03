package com.gc.hr.view.personal
{
  import com.gc.CommonEvent;
  import com.gc.Constants;
  import com.gc.common.model.Person;
  import com.gc.util.CommonUtil;

  import flash.events.Event;
  import flash.events.FocusEvent;
  import flash.events.KeyboardEvent;

  import mx.collections.IList;
  import mx.containers.Canvas;
  import mx.containers.Form;
  import mx.containers.FormItem;
  import mx.controls.ComboBox;
  import mx.controls.Label;
  import mx.controls.PopUpButton;
  import mx.controls.TextInput;
  import mx.core.ClassFactory;
  import mx.core.IFactory;
  import mx.events.CollectionEvent;
  import mx.events.ListEvent;

  [ExcludeClass]
  public class PersonTreeFilter extends Canvas
  {
    [Bindable]
    protected var qo:Object=Person.qo;

    private var itemWidth:int=80;
    private var labelItemRenderer:IFactory=new ClassFactory(Label);

    protected function onCreationComplete(form:Form, text:TextInput):void
    {
      itemWidth=text.getVisibleRect(form).width;
      CommonUtil.visit(form, setItem);
    }

    protected function updateSearchText(item:Object):void
    {
      if (item == null)
        return;
      var name:String=item["name"];
      item=CommonUtil.getItem(item);
      // var prop:Object={name: name, value: CommonUtil.getItemValue(item), label: CommonUtil.getItemLabel(item)};
      var prop:Object=new Object();
      prop[Constants.PROP_NAME_NAME]=name;
      prop[Constants.PROP_NAME_VALUE]=CommonUtil.getItemValue(item);
      prop[Constants.PROP_NAME_LABEL]=CommonUtil.getItemLabel(item);
      prop[Constants.PROP_NAME_SHOW]=true;
      dispatchEvent(new CommonEvent(CommonEvent.CHANGED, null, [prop]));
    }

    protected function onFocusOut(event:Event):void
    {
      updateSearchText(event.currentTarget);
    }

    protected function onChange(event:Event):void
    {
      var obj:Object=event.currentTarget;
      updateSearchText(obj);
      if (obj is ComboBox)
      {
        var cbox:ComboBox=obj as ComboBox;
        valueChanged(cbox);
      }
    }

    protected function valueChanged(cbox:ComboBox):void
    {
      // trace(cbox.name + ": value changed to " + cbox.selectedLabel);
    }

    protected function setItem(comp:Object):void
    {
      if (comp is FormItem)
      {
        var fi:FormItem=comp as FormItem;
        fi.styleName="filterLabel";
      }
      else if (comp is TextInput)
      {
        comp.addEventListener(FocusEvent.FOCUS_OUT, onFocusOut);
      }
      else if (comp is ComboBox)
      {
        var cbox:ComboBox=comp as ComboBox;
        if (cbox.editable)
          cbox.addEventListener(FocusEvent.FOCUS_OUT, onFocusOut);
        else
          cbox.addEventListener(ListEvent.CHANGE, onChange);
        cbox.width=itemWidth;
        cbox.itemRenderer=labelItemRenderer;
        cbox.addEventListener(KeyboardEvent.KEY_DOWN, CommonUtil.keyDown);
        CommonUtil.selectDefaultItem(cbox, CommonUtil.getAccessorValue(qo, cbox.name));
      }
      else if (comp is PopUpButton)
      {
        var pub:PopUpButton=comp as PopUpButton;
        pub.width=itemWidth;
      }
    }

  }
}