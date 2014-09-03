package com.gc.controls
{
  import com.gc.CommonEvent;
  import com.gc.Constants;
  import com.gc.util.CommonUtil;
  import com.gc.util.DateUtil;

  import flash.display.DisplayObjectContainer;
  import flash.events.Event;

  import mx.binding.utils.ChangeWatcher;
  import mx.collections.ArrayCollection;
  import mx.containers.GridItem;
  import mx.containers.TitleWindow;
  import mx.controls.DateField;
  import mx.controls.Label;
  import mx.controls.PopUpButton;
  import mx.controls.RadioButton;
  import mx.controls.RadioButtonGroup;
  import mx.core.ContainerLayout;
  import mx.core.mx_internal;
  import mx.events.FlexEvent;
  import mx.events.PropertyChangeEvent;
  import mx.events.ValidationResultEvent;
  import mx.validators.DateValidator;
  import mx.validators.Validator;

  use namespace mx_internal;

  /**
   * 日期选取控件, 参考com.gc.hr.view.check.LongPlanManage
   * params="{[qo, 'checkDate_from', 'checkDate_to']}"
   * 为了支持ChangeWatcher, qo必须是ObjectProxy对象
   * from/to必须是XXX_from, XXX_to的形式
   **/
  public class DateSelector extends PopUpButton
  {
    public static const NOLIMIT:int=0;
    public static const EQUAL:int=1;
    public static const BETWEEN:int=2;
    public static const LESS:int=3;
    public static const GREATER:int=4;
    public static const FULL:Array=[NOLIMIT, EQUAL, BETWEEN, LESS, GREATER];

    private var _popUp:TitleWindow;
    private var _radioButtonGroup:RadioButtonGroup;

    public function DateSelector()
    {
      // trace(name + ": new");
      super();
      this.popUp=_popUp=new TitleWindow();
      _popUp.layout=ContainerLayout.VERTICAL;
      _popUp.isPopUp=false;
      _popUp.title=resourceManager.getString("gcc", "select.date.filter");
      _popUp.minWidth=400;
      _radioButtonGroup=new RadioButtonGroup();
    }

    private var _showError:Boolean;

    [Inspectable(category="General",enumeration="true,false",defaultValue="false")]
    public function get showError():Boolean
    {
      return _showError;
    }

    public function set showError(value:Boolean):void
    {
      if (_showError != value)
      {
        this._showError=value;
        lblError.includeInLayout=lblError.visible=_showError;
        _popUp.validateDisplayList();
      }
    }

    private var lblError:Label;

    private function createErrorLabel():void
    {
      lblError=new Label();
      lblError.setStyle("color", "red");
      lblError.includeInLayout=lblError.visible=_showError;
      _popUp.addChild(lblError);
    }

    private var _options:Object=FULL;

    [Inspectable(category="General", enumeration="full", defaultValue="full")]
    public function get options():Object
    {
      return _options.slice(0);
    }

    public function set options(value:Object):void
    {
      if (this._options != value)
      {
        this._options=value == "full" ? FULL : value;
        invalidateDisplayList();
      }
    }

    private var _po:Object;
    private var _from:String;
    private var _to:String;
    private var _other:Function;

    public function get output():Object
    {
      return _po;
    }

    public function set output(value:Object):void
    {
      _po=value;
    }

    public function set params(value:Array):void
    {
      _po=value[0];
      _from=value[1];
      _to=value[2];
      if (value.length > 3 && value[3] is Function)
        _other=value[3];
      // trace(name + ": set params");
    }

    private var _formatString:String=Constants.DATE_FORMAT;

    public function get formatString():String
    {
      return this._formatString;
    }

    public function set formatString(value:String):void
    {
      this._formatString=value;
    }

    private var _yearNavigationEnabled:Boolean=true;

    public function get yearNavigationEnabled():Boolean
    {
      return this._yearNavigationEnabled;
    }

    public function set yearNavigationEnabled(value:Boolean):void
    {
      this._yearNavigationEnabled=value;
    }

    private function option():Object
    {
      return (_po == null || _from == null || _to == null) ? null
        : (_po[_from] == null) ? (_po[_to] == null) ? NOLIMIT : LESS
        : (_po[_to] == null) ? GREATER : (DateUtil.same(_po[_from], _po[_to], false) ? EQUAL : BETWEEN);
    }

    public function get value():Object
    {
      return (_radioButtonGroup == null) ? null : _radioButtonGroup.selectedValue;
    }

    private function get item():DisplayObjectContainer
    {
      var rb:RadioButton=_radioButtonGroup.selection;
      return (rb == null) ? null : rb.parent;
    }

    private function bind():void
    {
      var f1:Function=function(e:PropertyChangeEvent):void
        {
          if (_po == null || _from == null || DateUtil.same(e.newValue, e.oldValue))
            return;
          f(_from);
        };
      var f2:Function=function(e:PropertyChangeEvent):void
        {
          if (_po == null || _to == null || DateUtil.same(e.newValue, e.oldValue))
            return;
          f(_to);
        };
      var f:Function=function(prop:String=null):void
        {
          if (_po == null || _from == null || _to == null)
            return;
          var value:Object=option();
          switch(value) {
            case LESS:
              label=resourceManager.getString("gcc", "select.date.filter.less2", [DateUtil.formatDate(_po[_to])]);
              break;
            case GREATER:
              label=resourceManager.getString("gcc", "select.date.filter.greater2", [DateUtil.formatDate(_po[_from])]);
              break;
            case EQUAL:
              label=resourceManager.getString("gcc", "select.date.filter.equal2", [DateUtil.formatDate(_po[_from])]);
              break;
            case BETWEEN:
              label=resourceManager.getString("gcc", "select.date.filter.between2", [DateUtil.formatDate(_po[_from]), DateUtil.formatDate(_po[_to])]);
              break;
            case NOLIMIT:
            default:
              label=resourceManager.getString("gcc", "select.date.filter.none2");
              break;
          }
          toolTip=label;
          selectOption();
          // trace(name + "." + prop + ": " + _from + "=" + DateUtil.formatDate(_po[_from]) + ", " + _to + "=" + DateUtil.formatDate(_po[_to]) + ", watchable=" + ChangeWatcher.canWatch(_po, name) + ", label=" + label);
          // var p:Object={name: name, value: null, label: pub.label, show: event.args[2]};
          var p:Object=new Object();
          p[Constants.PROP_NAME_NAME]=name;
          p[Constants.PROP_NAME_VALUE]=null;
          p[Constants.PROP_NAME_LABEL]=label;
          p[Constants.PROP_NAME_SHOW]=(_po[_from] != null || _po[_to] != null);
          // dispatchEvent(new CommonEvent(CommonEvent.CHANGED, this, [_po, label, true]));
          if (prop != null) dispatchEvent(new CommonEvent(CommonEvent.CHANGED, null, [p]));
        };
      ChangeWatcher.watch(_po, _from, f1);
      ChangeWatcher.watch(_po, _to, f2);
      f();
    }

    private function addListeners():void
    {
      _popUp.addEventListener(FlexEvent.PREINITIALIZE, function(event:Event):void
        {
          // trace(name + ": PopUp.Preinitialize");
          createGridItems();
          createErrorLabel();    		
        });
      _popUp.addEventListener(FlexEvent.CREATION_COMPLETE, function(event:Event):void
        {
          // trace(name + ": PopUp.CreationComplete");
          selectOption();
        });
      _popUp.addEventListener(FlexEvent.HIDE, function(event:Event):void
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
            obj.hide(args);
        });
    }

    override protected function createChildren():void
    {
      // trace(name + ": createChildren");
      super.createChildren();
      addListeners();
    }

    private var _committed:Boolean=false;

    override protected function commitProperties():void
    {
      // trace(name + ": commitProperties");
      if (!_committed)
      {
        if (!descriptor.properties.hasOwnProperty("openAlways"))
          this.openAlways=true;
        if (isNaN(explicitWidth) && isNaN(percentWidth))
          this.width=100;
        if (_from == null)
          _from=name+"_from";
        if (_to == null)
          _to=name+"_to";
        super.commitProperties();
        bind();
        _committed=true;
      }
    }

    private function createGridItems():void
    {
      var DEFAULT_ITEMS:Array=[
        {obj: new GridItem(), 
          children: [
          {obj: new RadioButton(), label: resourceManager.getString("gcc", "select.date.filter.none"), value: NOLIMIT, selected: true, hide: select0}
          ]},
        {obj: new GridItem(),
          children: [
          {obj: new RadioButton(), label: resourceManager.getString("gcc", "select.date.filter.equal"), value: EQUAL, hide: select1}, 
            {obj: new DateField(), name: "from"}]},
        {obj: new GridItem(),
          children: [
          {obj: new RadioButton(), label: resourceManager.getString("gcc", "select.date.filter.between"), value: BETWEEN, hide: select2}, 
            {obj: new DateField(), name: "from"},
            {obj: new Label(), text: resourceManager.getString("gcc", "to")},
            {obj: new DateField(), name: "to"},
            {obj: new Label(), text: resourceManager.getString("gcc", "between")}]},
        {obj: new GridItem(),
          children: [
          {obj: new RadioButton(), label: resourceManager.getString("gcc", "select.date.filter.less"), value: LESS, hide: select3}, 
            {obj: new DateField(), name: "to"}]},
        {obj: new GridItem(),
          children: [
          {obj: new RadioButton(), label: resourceManager.getString("gcc", "select.date.filter.greater"), value: GREATER, hide: select4}, 
            {obj: new DateField(), name: "from"}]},
        ];
      var items:Array=[];
      for each (var i:int in _options)
      {
        items.push(DEFAULT_ITEMS[i]);
      }
      if (_other is Function)
      {
        items=items.concat(_other());
      }
      _popUp.removeAllChildren();
      CommonUtil.createObjects(items, _popUp, function(comp:Object, obj:Object):void
        {
          if (comp is RadioButton)
          {
            var rb:RadioButton=comp as RadioButton;
            rb.addEventListener(Event.CHANGE, onClickRadioButton);
            rb.group=_radioButtonGroup;
            rb.data=obj;
            rb.width=80;
          }
          else if (comp is DateField)
          {
            var df:DateField=comp as DateField;
            df.formatString=formatString ? formatString : Constants.DATE_FORMAT;
            df.yearNavigationEnabled=yearNavigationEnabled;
            df.enabled=false;
          }
        });
    }

    private function onClickRadioButton(event:Event):void
    {
      if (event == null || !(event.currentTarget is RadioButton))
        return;
      var rb:RadioButton=event.currentTarget as RadioButton;
      dateValidators.removeAll();
      setDateFields(_popUp, [rb.parent]);
    }

    private function selectOption():void
    {
      var _value:Object=option();
      CommonUtil.visit(_popUp, function(obj:Object):void
        {
          if (obj is RadioButton) {
            var rb:RadioButton=obj as RadioButton;
            if (rb.value == _value) {
              rb.selected=true;
              _radioButtonGroup.selection=rb;
            }
          }
        });
      setDateFields();
    }

    private function setDateFields(container:Object=null, args:Array=null):void
    {
      if (container == null)
        container = item;
      if (args == null && container != null)
        args = [container];
      if (container == null || args == null || args.length <= 0)
        return;
      CommonUtil.visit(_popUp, function(obj: Object):void
        {
          if (obj is DateField)
          {
            obj.enabled=(obj.parent == args[0]);
            obj.errorString=null;
            if (obj.enabled)
            {
              var dv:DateValidator=new DateValidator();
              dv.source=obj;
              dv.required=true;
              dv.property="text";
              dv.requiredFieldError=resourceManager.getString("gcc", "date.validate.notnull");
              dateValidators.addItem(dv);
              if (obj.name == "from" && obj.text == "" && _po != null && _po[_from] != null)
                obj.text=DateUtil.formatDateTime(_po[_from], obj.formatString);
              if (obj.name == "to" && obj.text == "" && _po != null && _po[_to] != null)
                obj.text=DateUtil.formatDateTime(_po[_to], obj.formatString);
            }
          }
        });
    }

    private function select0(dates:Array):void
    {
      _po[_from]=null;
      _po[_to]=null;
    }

    private function select1(dates:Array):void
    {
      _po[_from]=DateUtil.parseDate(dates[0], DateUtil.DATE_BEGIN);
      _po[_to]=DateUtil.parseDate(dates[0], DateUtil.DATE_END);
    }

    private function select2(dates:Array):void
    {
      // CommonUtil.setValue(_po, _from, fromDate);
      // CommonUtil.setValue(_po, _to, toDate);
      _po[_from]=DateUtil.parseDate(dates[0], DateUtil.DATE_BEGIN);
      _po[_to]=DateUtil.parseDate(dates[1], DateUtil.DATE_END);
    }

    private function select3(dates:Array):void
    {
      _po[_from]=null;
      _po[_to]=DateUtil.parseDate(dates[0], DateUtil.DATE_END);
    }

    private function select4(dates:Array):void
    {
      _po[_from]=DateUtil.parseDate(dates[0], DateUtil.DATE_BEGIN);
      _po[_to]=null;
    }

    private var dateValidators:ArrayCollection=new ArrayCollection();

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
