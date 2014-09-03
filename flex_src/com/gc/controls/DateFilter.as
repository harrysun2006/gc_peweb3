package com.gc.controls
{
  import com.gc.CommonEvent;
  import com.gc.Constants;
  import com.gc.util.CommonUtil;
  import com.gc.util.DateUtil;

  import flash.events.Event;

  import mx.collections.ArrayCollection;
  import mx.containers.GridItem;
  import mx.containers.TitleWindow;
  import mx.controls.DateField;
  import mx.controls.Label;
  import mx.controls.RadioButton;
  import mx.controls.RadioButtonGroup;
  import mx.core.ContainerLayout;
  import mx.core.UIComponent;
  import mx.events.FlexEvent;
  import mx.events.ValidationResultEvent;
  import mx.validators.DateValidator;
  import mx.validators.Validator;

  [ExcludeClass]
  public class DateFilter extends TitleWindow
  {
    /**
     * RadioButton+DateField组件根据options参数可以选择, 如下:
     * <gc:DateFilter options="{[DateFilter.LESS, DateFilter.GREATER]}"/>
     * 缺省为FULL中的所有选项
     * 如果需要增加其他选择项可以通过params的other参数调用回调函数
     * 回调函数返回一对象数组, 参见com/gc/hr/view/personal/PersonTreeFilter1.xml, 如下:
       <mx:popUp>
       <gc:DateFilter params="{[qo, 'downDateFrom', 'downDateTo', add1]}"/>
       </mx:popUp>
       ... ...

       private function get11(target:UIComponent, dates:Array):void
       {
       var label:String=Person.LABEL_NOT_DOWN;
       CommonUtil.setValue(qo, "downDateFrom", Constants.MAX_DATE);
       CommonUtil.setValue(qo, "downDateTo", null);
       target.dispatchEvent(new CommonEvent(CommonEvent.HIDED, this, [qo, label, true]));
       }

       private function get12(target:UIComponent, dates:Array):void
       {
       var label:String=Person.LABEL_DOWN;
       CommonUtil.setValue(qo, "downDateFrom", null);
       CommonUtil.setValue(qo, "downDateTo", Constants.MAX_DATE);
       target.dispatchEvent(new CommonEvent(CommonEvent.HIDED, this, [qo, label, true]));
       }

       private function add1():Array
       {
       return [[{comp: new RadioButton(), label: Person.LABEL_NOT_DOWN, value: 5, selected: true, hide: get11},
       {comp: new RadioButton(), label: Person.LABEL_DOWN, value: 6, hide: get12}]];
       }
     * 可以改进为类似于AdvancedDataGrid+columns+AdvancedDataGridColumn的方式:
     * DateFilter+DateFilterOption
     **/
    public static const NONE:int=0;
    public static const EQUAL:int=1;
    public static const BETWEEN:int=2;
    public static const LESS:int=3;
    public static const GREATER:int=4;
    public static const FULL:Array=[NONE, EQUAL, BETWEEN, LESS, GREATER];

    private var dateValidators:ArrayCollection=new ArrayCollection();
    private var lblError:Label;

    private var _showError:Boolean;

    [Inspectable(category="General",enumeration="true,false",defaultValue="true")]
    public function get showError():Boolean
    {
      return _showError;
    }

    public function set showError(value:Boolean):void
    {
      var old:Boolean=this._showError;
      this._showError=value;
      if (old != value)
      {
        this.invalidateDisplayList();
      }
    }

    private var _options:Array=FULL;

    [Inspectable(category="General")]
    public function get options():Array
    {
      return _options;
    }

    public function set options(value:Array):void
    {
      if (this._options != value)
      {
        this._options=value;
        invalidateDisplayList();
      }
    }

    private var _radioButtonGroup:RadioButtonGroup;

    public function get value():Object
    {
      return (_radioButtonGroup == null) ? 0 : _radioButtonGroup.selectedValue;
    }

    private var _po:Object;
    private var _from:String;
    private var _to:String;
    private var _other:Function;

    [Inspectable(category="General")]
    public function set params(args:Array):void
    {
      _po=args[0];
      _from=args[1];
      _to=args[2];
      if (args[3] is Function)
      {
        _other=args[3];
      }
    }

    public function DateFilter()
    {
      super();
      layout=ContainerLayout.VERTICAL;
      isPopUp=false;
      title=resourceManager.getString("gcc", "select.date.filter");
      _radioButtonGroup=new RadioButtonGroup();
    }

    private function createErrorLabel():void
    {
      lblError=new Label();
      lblError.setStyle("color", "red");
      addChild(lblError);
    }

    private function createGridItems():void
    {
      var DEFAULT_ITEMS:Array=[
        {obj: new GridItem(), 
          children: [
          {obj: new RadioButton(), label: resourceManager.getString("gcc", "select.date.filter.none"), value: NONE, selected: true, hide: select0}
          ]},
        {obj: new GridItem(),
          children: [
          {obj: new RadioButton(), label: resourceManager.getString("gcc", "select.date.filter.equal"), value: EQUAL, hide: select1}, 
            {obj: new DateField()}]},
        {obj: new GridItem(),
          children: [
          {obj: new RadioButton(), label: resourceManager.getString("gcc", "select.date.filter.between"), value: BETWEEN, hide: select2}, 
            {obj: new DateField()},
            {obj: new Label(), text: resourceManager.getString("gcc", "to")},
            {obj: new DateField()},
            {obj: new Label(), text: resourceManager.getString("gcc", "between")}]},
        {obj: new GridItem(),
          children: [
          {obj: new RadioButton(), label: resourceManager.getString("gcc", "select.date.filter.less"), value: LESS, hide: select3}, 
            {obj: new DateField()}]},
        {obj: new GridItem(),
          children: [
          {obj: new RadioButton(), label: resourceManager.getString("gcc", "select.date.filter.greater"), value: GREATER, hide: select4}, 
            {obj: new DateField()}]},
        ];
      var _items:Array=[];
      for each (var i:int in _options)
      {
        _items.push(DEFAULT_ITEMS[i]);
      }
      if (_other != null)
      {
        _items=_items.concat(_other());
      }
      CommonUtil.createObjects(_items, this, function(comp:Object, obj:Object):void
        {
          if (comp is RadioButton)
          {
            var rb:RadioButton=comp as RadioButton;
            rb.addEventListener(Event.CHANGE, onClickRadioButton);
            rb.group=_radioButtonGroup;
            rb.data=obj;
          }
          else if (comp is DateField)
          {
            var df:DateField=comp as DateField;
            df.formatString=Constants.DATE_FORMAT;
            df.yearNavigationEnabled=true;
          }
        });
    }

    override protected function commitProperties():void
    {
      super.commitProperties();
      createGridItems();
      createErrorLabel();
    }

    override protected function createChildren():void
    {
      width=(measuredWidth < 400) ? 400 : measuredWidth;
      super.createChildren();
      CommonUtil.visit(this, setChildComponent);
      addEventListener(FlexEvent.HIDE, onHide);
    }

    override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
    {
      lblError.includeInLayout=lblError.visible=_showError;
      super.updateDisplayList(unscaledWidth, unscaledHeight);
    }

    private function onClickRadioButton(event:Event):void
    {
      if (event == null || !(event.currentTarget is RadioButton))
        return;
      var rb:RadioButton=event.currentTarget as RadioButton;
      dateValidators.removeAll();
      CommonUtil.visit(this, setChildComponent, [rb.parent]);
    }

    private function setChildComponent(comp:Object, args:Array=null):void
    {
      if (comp is DateField)
      {
        var df:DateField=comp as DateField;
        df.enabled=false;
        if (args == null || args.length == 0)
          return;
        if (df.parent == args[0])
        {
          df.enabled=true;
          var dv:DateValidator=new DateValidator();
          dv.source=df;
          dv.required=true;
          dv.property="text";
          dv.requiredFieldError=resourceManager.getString("gcc", "date.validate.notnull");
          dateValidators.addItem(dv);
        }
      }
    }

    private function onHide(event:FlexEvent):void
    {
      if (!validate() || _radioButtonGroup == null || _radioButtonGroup.selection == null)
        return;
      var rb:RadioButton=_radioButtonGroup.selection;
      var obj:Object=rb.data;
      var args:Array=[]
      CommonUtil.visit(rb.parent, function(obj:Object):void
        {
          if (obj is DateField)
            args.push((obj as DateField).text);
        });
      if (obj != null && obj.hasOwnProperty("hide") && obj.hide is Function)
        obj.hide(owner, args);
    }

    private function select0(target:UIComponent, dates:Array):void
    {
      var label:String=resourceManager.getString("gcc", "select.date.filter.none2");
      CommonUtil.setValue(_po, _from, null);
      CommonUtil.setValue(_po, _to, null);
      target.dispatchEvent(new CommonEvent(CommonEvent.HIDED, this, [_po, label, false]));
    }

    private function select1(target:UIComponent, dates:Array):void
    {
      var label:String=resourceManager.getString("gcc", "select.date.filter.equal2", [dates[0]]);
      var fromDate:Date=DateUtil.parseDate(dates[0], DateUtil.DATE_BEGIN);
      var toDate:Date=DateUtil.parseDate(dates[0], DateUtil.DATE_END);
      CommonUtil.setValue(_po, _from, fromDate);
      CommonUtil.setValue(_po, _to, toDate);
      target.dispatchEvent(new CommonEvent(CommonEvent.HIDED, this, [_po, label, true]));
    }

    private function select2(target:UIComponent, dates:Array):void
    {
      var label:String=resourceManager.getString("gcc", "select.date.filter.between2", [dates[0], dates[1]]);
      var fromDate:Date=DateUtil.parseDate(dates[0], DateUtil.DATE_BEGIN);
      var toDate:Date=DateUtil.parseDate(dates[1], DateUtil.DATE_END);
      CommonUtil.setValue(_po, _from, fromDate);
      CommonUtil.setValue(_po, _to, toDate);
      target.dispatchEvent(new CommonEvent(CommonEvent.HIDED, this, [_po, label, true]));
    }

    private function select3(target:UIComponent, dates:Array):void
    {
      var label:String=resourceManager.getString("gcc", "select.date.filter.less2", [dates[0]]);
      var toDate:Date=DateUtil.parseDate(dates[0], DateUtil.DATE_END);
      CommonUtil.setValue(_po, _from, null);
      CommonUtil.setValue(_po, _to, toDate);
      target.dispatchEvent(new CommonEvent(CommonEvent.HIDED, this, [_po, label, true]));
    }

    private function select4(target:UIComponent, dates:Array):void
    {
      var label:String=resourceManager.getString("gcc", "select.date.filter.greater2", [dates[0]]);
      var fromDate:Date=DateUtil.parseDate(dates[0], DateUtil.DATE_BEGIN);
      CommonUtil.setValue(_po, _from, fromDate);
      CommonUtil.setValue(_po, _to, null);
      target.dispatchEvent(new CommonEvent(CommonEvent.HIDED, this, [_po, label, true]));
    }

    private function validate():Boolean
    {
      var errors:Array=Validator.validateAll(dateValidators.toArray());
      if (errors.length > 0)
      {
        if (lblError)
          lblError.text=(errors[0] as ValidationResultEvent).message;
        return false;
      }
      else
      {
        if (lblError)
          lblError.text="";
        return true;
      }
    }

  }
}