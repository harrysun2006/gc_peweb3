package com.gc.controls
{
  import com.gc.CommonEvent;
  import com.gc.Constants;
  import com.gc.common.controller.BaseController;
  import com.gc.common.controller.UserController;
  import com.gc.util.CommonUtil;
  import com.gc.util.PrintUtil;

  import flash.events.Event;
  import flash.events.KeyboardEvent;
  import flash.ui.Keyboard;

  import mx.binding.utils.ChangeWatcher;
  import mx.collections.ArrayCollection;
  import mx.collections.IList;
  import mx.containers.HBox;
  import mx.containers.VBox;
  import mx.controls.AdvancedDataGrid;
  import mx.controls.Alert;
  import mx.controls.Button;
  import mx.controls.ComboBox;
  import mx.controls.Label;
  import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
  import mx.controls.dataGridClasses.DataGridColumn;
  import mx.core.ClassFactory;
  import mx.core.UIComponent;
  import mx.events.AdvancedDataGridEvent;
  import mx.events.AdvancedDataGridEventReason;
  import mx.events.CloseEvent;
  import mx.events.CollectionEvent;
  import mx.events.PropertyChangeEvent;
  import mx.rpc.events.ResultEvent;
  import mx.skins.ProgrammaticSkin;
  import mx.utils.ObjectProxy;
  import mx.utils.ObjectUtil;

  public class Voucher extends VBox
  {
    /**
     * 支持3类业务方式:
     * 1. 维护代码表, 主键一般为assign或sequence-identity, 参考com/gc/hr/view/check/GroupManage, ItemManage
     * 2. 维护不带凭证明细的凭证表, 主键(凭证号)通过自定义函数生成, 参考com/gc/hr/view/check/LongPlanManage.mxml
     * 3. 维护带凭证明细的凭证表, 参考com/gc/hr/view/check/PlanXXX.mxml
     * Button组件根据buttons参数可以选择, 如下:
     * <gc:Voucher buttons="{[Voucher.SAVE, Voucher.CLOSE]}">
     * 缺省为LITE中的所有按钮
     * 可以改进为类似于AdvancedDataGrid+columns+AdvancedDataGridColumn的方式:
     * Voucher+VoucherButton
     **/
    public static const PARAM_CLASS:String="clazz";
    public static const PARAM_BUNDLE:String="bundle";
    public static const PARAM_CODE:String="code";
    public static const PARAM_FLIST:String="flist";
    public static const PARAM_VFIELDS:String="vfields";
    public static const PARAM_SERIAL:String="serial";
    public static const PARAM_LLIST:String="list";

    public static const INSERT:int=0;
    public static const APPEND:int=1;
    public static const DELETE:int=2;
    public static const CLEAR:int=3;
    public static const EXPORT:int=4;
    public static const IMPORT:int=5;
    public static const PRINT:int=6;
    public static const SAVE:int=7;
    public static const CLOSE:int=8;
    public static const FULL:Array=[INSERT,APPEND,DELETE,CLEAR,EXPORT,IMPORT,PRINT,SAVE,CLOSE];
    public static const LITE:Array=[INSERT,APPEND,DELETE,CLEAR,SAVE,CLOSE];
    public static const NONE:Array=[];

    private var list0:IList;
    private var list1:IList;

    public function Voucher()
    {
      super();
      _proxy["access"]=false;
      _proxy["list"]=false;
    }

    private var _grid:AdvancedDataGrid;

    public function get grid():AdvancedDataGrid
    {
      return _grid;
    }

    public function set grid(g:AdvancedDataGrid):void
    {
      if (g == null)
        return;
      _grid=g;
    }

    public function get params():Object
    {
      return {clazz:_clazz, bundle:_bundle, code:_code, flist:_flist, vfields:_vfields, serial:_serial};
    }

    public function set params(p:Object):void
    {
      if (p is Array)
        setArrayParams(p as Array);
      else if (p != null)
      {
        if (p.hasOwnProperty(PARAM_CLASS))
          clazz=p[PARAM_CLASS] as Class;
        if (p.hasOwnProperty(PARAM_BUNDLE))
          bundle=p[PARAM_BUNDLE] as String;
        if (p.hasOwnProperty(PARAM_CODE))
          code=p[PARAM_CODE] as String;
        if (p.hasOwnProperty(PARAM_FLIST))
          flist=p[PARAM_FLIST] as Function;
        if (p.hasOwnProperty(PARAM_VFIELDS))
          vfields=p[PARAM_VFIELDS] as Array;
        if (p.hasOwnProperty(PARAM_SERIAL))
          serial=p[PARAM_SERIAL] as String;
        if (p.hasOwnProperty(PARAM_LLIST))
          list=p[PARAM_LLIST] as IList;
      }
    }

    private function setArrayParams(args:Array):void
    {
      if (args != null)
      {
        if (args.length > 0)
          clazz=args[0] as Class;
        if (args.length > 1)
          bundle=args[1] as String;
        if (args.length > 2)
          code=args[2] as String;
        if (args.length > 3)
          flist=args[3] as Function;
        if (args.length > 4)
          vfields=args[4] as Array;
        if (args.length > 5)
          serial=args[5] as String;
      }
    }

    private var _clazz:Class;

    public function get clazz():Class
    {
      return _clazz;
    }

    public function set clazz(value:Class):void
    {
      _clazz=value;
    }

    private var _bundle:String;

    [Inspectable(category="General")]
    public function get bundle():String
    {
      return _bundle;
    }

    [Inspectable(category="General", defaultValue="gcc")]
    public function set bundle(value:String):void
    {
      if (_bundle != value)
      {
        _bundle=value;
      }
    }

    private var _code:String;

    public function get code():String
    {
      return _code;
    }

    public function set code(value:String):void
    {
      if (_code != value)
      {
        _code=value;
      }
    }

    private var _flist:Function;

    public function get flist():Function
    {
      return _flist;
    }

    public function set flist(value:Function):void
    {
      if (_flist != value)
      {
        _flist=value;
      }
    }

    private var _llist:IList;

    public function get list():Object
    {
      return _llist;
    }

    public function set list(value:Object):void
    {
      if (value is IList)
      {
        _llist=value as IList;
      }
      else if (value is Array)
      {
        _llist=new ArrayCollection(value as Array);
      }
      loadGridList();
    }

    private var _proxy:ObjectProxy=new ObjectProxy();
    private var _faccess:Object;

    public function get faccess():Object
    {
      return _faccess;
    }

    public function set faccess(value:Object):void
    {
      if (_faccess != value)
      {
        _faccess=value;
        if (_faccess is Function)
          _proxy["access"]=_faccess();
        else if (_faccess is Boolean)
          _proxy["access"]=_faccess as Boolean;
        else
          _proxy["access"]=false;
      }
    }

    public function get writable():Boolean
    {
      return _proxy["access"] && _proxy["list"];
    }

    private var _vfields:Array;

    public function get vfields():Array
    {
      return _vfields;
    }

    public function set vfields(value:Array):void
    {
      _vfields=value;
    }

    private var _serial:String;

    public function get serail():String
    {
      return _serial;
    }

    public function set serial(value:String):void
    {
      if (_serial != value)
        _serial=value;
    }

    private var _buttons:Object=LITE;

    [Inspectable(category="General", enumeration="none,lite,full", defaultValue="lite")]
    public function get buttons():Object
    {
      return _buttons;
    }

    public function set buttons(value:Object):void
    {
      if (this._buttons != value)
      {
        this._buttons=(value == "none" ? NONE : value == "lite" ? LITE : value == "full" ? FULL : value);
        invalidateDisplayList();
      }
    }

    private var _fhead:Function;

    public function get fhead():Function
    {
      return _fhead;
    }

    public function set fhead(value:Function):void
    {
      if (_fhead != value)
      {
        _fhead=value;
      }
    }

    private function createButtons():void
    {
      var DEFAULT_BUTTONS:Array=[
        {obj:new Button(), label:resourceManager.getString("gcc", "append"), click:append, enabled:writable},
        {obj:new Button(), label:resourceManager.getString("gcc", "insert"), click:insert, enabled:writable},
        {obj:new Button(), label:resourceManager.getString("gcc", "delete"), click:_delete, enabled:writable},
        {obj:new Button(), label:resourceManager.getString("gcc", "clear"), click:clear, enabled:writable},
        {obj:new Button(), label:resourceManager.getString("gcc", "export"), click:export},
        {obj:new Button(), label:resourceManager.getString("gcc", "import"), click:_import, enabled:writable},
        {obj:new Button(), label:resourceManager.getString("gcc", "print"), click:print},
        {obj:new Button(), label:resourceManager.getString("gcc", "save"), click:save, enabled:writable},
        {obj:new Button(), label:resourceManager.getString("gcc", "close"), click:close},
        ];
      var hbox:Object={obj:new HBox(), horizontalAlign:"center", 
          percentWidth:100, paddingTop:10, paddingBottom:10, paddingLeft:10, children:[]};
      for each (var i:Number in _buttons)
      {
        if (i >= 0 && i < DEFAULT_BUTTONS.length)
          hbox.children.push(DEFAULT_BUTTONS[i]);
      }
      if (hbox.children.length > 0)
        addChild(CommonUtil.createObject(hbox, function(comp:Object, obj:Object):void
          {
            var f:Function=function(e:PropertyChangeEvent):void
              {
                comp.enabled=writable;
              }
            if (obj.hasOwnProperty("enabled")) {
              ChangeWatcher.watch(_proxy, "access", f);
              ChangeWatcher.watch(_proxy, "list", f);
            }
          }));
    }

    private function addListeners():void
    {
      if (owner != null && !owner.hasEventListener(CommonEvent.CREATED))
      {
        /**
         * 如果在owner中addEventListener,请使用callLater避免调用时v1=null以及重复调用
         * 可以兼顾TitleWindow, Module (auto/all)
         **/ /*
           owner.addEventListener(CommonEvent.CREATED, function(e:CommonEvent):void
           {
           callLater(function():void
           {
           params=(e.args == null) ? e.data : e.args;
           });
           });
         */
        owner.addEventListener(CommonEvent.CREATED, function(e:CommonEvent):void
          {
            params=(e.args == null) ? e.data : e.args;
          });
        owner.removeEventListener(KeyboardEvent.KEY_UP, CommonUtil.keyUp);
        owner.addEventListener(KeyboardEvent.KEY_UP, function(e:KeyboardEvent):void
          {
            var obj:Object=e.target;
            switch (e.keyCode)
            {
              case Keyboard.UP:
              case Keyboard.DOWN:
              case Keyboard.LEFT:
              case Keyboard.RIGHT:
                break;
              case Keyboard.DELETE:
                if (obj is ComboBox)
                {
                  var cbox:ComboBox=obj as ComboBox;
                  if (!cbox.editable)
                  {
                    cbox.selectedIndex=-1;
                    cbox.dispatchEvent(CommonEvent.LIST_CHANGE_EVENT);
                  }
                }
                break;
              case Keyboard.ESCAPE:
                close(e);
                break;
            }
          });
      }
    }

    override protected function commitProperties():void
    {
      super.commitProperties();
    }

    override protected function createChildren():void
    {
      addListeners();
      super.createChildren();
      var g:AdvancedDataGrid, b:UIComponent;
      CommonUtil.visit(this, function(obj:Object):void
        {
          if (obj is AdvancedDataGrid) g=obj as AdvancedDataGrid;
          else if (obj is UIComponent && (obj.id == "buttons" || obj.name == "buttons")) b=obj as UIComponent;
        });
      if (_grid == null)
        grid=g;
      setGrid();
      callLater(function():void
        {
          setGridColumns();
          if (_buttons is Array && b == null)
            createButtons();
          loadGridList();
          firstEditableColumn=getFirstEditableColumn(_grid);
        });
    }

    protected function loadGridList(list:ArrayCollection=null):void
    {
      var loader:Function=function(l:IList):void
        {
          list0=l;
          list1=ObjectUtil.copy(list0) as IList;
          dispatchEvent(new CommonEvent(CommonEvent.LIST_SUCCESS, list1));
          var idx:int=_grid ? _grid.selectedIndex : 0;
          if (idx >= list1.length) idx = list1.length - 1;
          _grid.dataProvider=list1;
          _grid.validateNow();
          if (list1.length > 0) _grid.selectedIndex=idx;
        }
      if (list is IList)
      {
        loader(list);
      }
      else if (_llist is IList)
      {
        loader(_llist);
      }
      else if (flist is Function)
      {
        var f:Function=function(e:ResultEvent):void
          {
            loader(e.result as IList);
          };
        var branchId:int=UserController.branchId;

        if (fhead != null)
          flist(fhead, f);
        else
          flist(branchId, f);
      }
    }

    private var firstEditableColumn:int;

    private function getFirstEditableColumn(g:AdvancedDataGrid):int
    {
      if (g==null)
        return -1;
      for (var i:int=0; i<g.columns.length; i++)
      {
        if (g.columns[i].editable)
          return i;
      }
      return -1;
    }

    private function setGrid():void
    {
      if (_grid == null)
        return;
      if (isNaN(_grid.explicitWidth) && isNaN(_grid.percentWidth))
        _grid.percentWidth=100;
      if (isNaN(_grid.explicitHeight) && isNaN(_grid.percentHeight))
        _grid.percentHeight=100;
      if (isNaN(_grid.minWidth))
        _grid.minWidth=100;
      _grid.sortableColumns=false;
      _grid.sortExpertMode=false;
      if (!_grid.descriptor.properties.hasOwnProperty("horizontalScrollPolicy"))
        _grid.horizontalScrollPolicy="auto";
      if (!_grid.descriptor.properties.hasOwnProperty("selectionMode"))
        _grid.selectionMode="singleRow";
      if (!_grid.descriptor.properties.hasOwnProperty("itemRenderer"))
        _grid.itemRenderer=new ClassFactory(Label);
      if (!_grid.descriptor.properties.hasOwnProperty("tabEnabled"))
        _grid.tabEnabled=false;
      if (!_grid.descriptor.properties.hasOwnProperty("headerSortSeparatorSkin"))
        _grid.setStyle("headerSortSeparatorSkin", ProgrammaticSkin);
      if (!_grid.descriptor.properties.hasOwnProperty("editable"))
      {
        _grid.editable=writable.toString();
        var f:Function=function(e:PropertyChangeEvent):void
          {
            _grid.editable=writable.toString();
          };
        ChangeWatcher.watch(_proxy, "access", f);
        ChangeWatcher.watch(_proxy, "list", f);
      }
      if (!_grid.descriptor.properties.hasOwnProperty("keyDown"))
      {
        _grid.addEventListener(KeyboardEvent.KEY_DOWN, CommonUtil.gridKeyDown);
      }
      _grid.sortItemRenderer=Constants.ZERO_RENDERER;
      if (_grid.labelFunction == null)
        _grid.labelFunction=CommonUtil.gridLabelFunction;
      _grid.setFocus();
      // _grid.addEventListener(MouseEvent.CLICK, onClick);
      _grid.addEventListener(AdvancedDataGridEvent.ITEM_EDIT_END, onGridItemEditEnd);
      _grid.addEventListener(CollectionEvent.COLLECTION_CHANGE, function(e:Event):void
        {
          _proxy["list"]=_grid.dataProvider != null;
        });
    }

    private function setGridColumns():void
    {
      if (_grid == null || _bundle == null || _code == null)
        return;
      // trace("set grid columns: " + _bundle + ", " + code);
      CommonUtil.setDataGridColumns(_grid, _bundle, _code, function(obj:Object):void
        {
          if (obj is DataGridColumn || obj is AdvancedDataGridColumn) {
            if (obj.dataField == Constants.PROP_NAME_SERIAL)
              CommonUtil.copyProperties({width:64, editable:false, 
                  styleFunction:CommonUtil.serialStyleFunction, 
                  labelFunction:CommonUtil.serialLabelFunction}, obj);
          }
        });
    }

    private function onGridItemEditEnd(event:AdvancedDataGridEvent):void
    {
      if (event.itemRenderer && event.reason != AdvancedDataGridEventReason.CANCELLED)
      {
        var adg:AdvancedDataGrid=event.currentTarget as AdvancedDataGrid;
        var column:AdvancedDataGridColumn=adg.columns[event.columnIndex];
        var newData:Object=adg.itemEditorInstance[column.editorDataField];
        var property:String=column.dataField;
        var data:Object=event.itemRenderer.data;
        if (newData == "" && CommonUtil.getAccessorValue(data, property) == null)
          event.reason=AdvancedDataGridEventReason.CANCELLED;
      }
    }

    private function onClick(event:Event):void
    {
      var cells:Array=_grid.selectedCells;
      for each (var cell:Object in cells)
      {
        if (cell.columnIndex == 0)
        {
          _grid.selectionMode="singleRow";
          _grid.selectedItem=list1.getItemAt(cell.rowIndex, 0);
          _grid.validateNow();
          return;
        }
      }
      trace(event.target);
    }

    private function add(i:int):void
    {
      var obj:Object=new _clazz();
      if (obj.hasOwnProperty("branch"))
        obj.branch=UserController.branch;
      if (i >= 0 && i < list1.length)
        list1.addItemAt(obj, i);
      else
        list1.addItem(obj);
      _grid.selectedItem=obj;
      if (firstEditableColumn >= 0)
        _grid.editedItemPosition={rowIndex: _grid.selectedIndex, columnIndex: firstEditableColumn};
    }

    public function insert(event:Event):void
    {
      if (_grid.selectedItem == null)
      {
        Alert.show(resourceManager.getString("gcc", "insert.not.null"), Constants.APP_NAME, Alert.OK, 
          null, null, Constants.ICON32_WARNING);
        return;
      }
      add(_grid.selectedIndex);
    }

    public function append(event:Event):void
    {
      add(list1.length);
    }

    public function _delete(event:Event):void
    {
      var po:Object=_grid.selectedItem;
      if (po == null)
      {
        Alert.show(resourceManager.getString("gcc", "delete.not.null"), Constants.APP_NAME, Alert.OK, 
          null, null, Constants.ICON32_WARNING);
      }
      else
      {
        var k:int=list1.getItemIndex(po);
        if (k >= 0)
        {
          list1.removeItemAt(k);
          callLater(function():void{_grid.selectedIndex=(k < list1.length) ? k : list1.length - 1;});
        }
      }
    }

    public function clear(event:Event):void
    {
      if (list1.length > 0)
      {
        Alert.show(resourceManager.getString("gcc", "clear.confirm"), Constants.APP_NAME, Alert.YES | Alert.NO,
          null, function(e:CloseEvent):void
          {
            if (e.detail == Alert.YES)
              list1.removeAll();
          }, Constants.ICON32_QUESTION);
      }
    }

    public function export(event:Event):void
    {
      var headers:Object=CommonUtil.getGridHeaders(_grid);
      var data:Array=CommonUtil.getGridData(_grid);
      CommonUtil.export(data, headers);
    }

    public function _import(event:Event):void
    {
      /**
       * 1. upload, get response data
       * 2. confirm if remove all current items in list
       * 3. add items using response data
       **/
      CommonUtil._import(function():void{});
    }

    public function print(event:Event):void
    {
      PrintUtil.print(_grid);
    }

    /**
     * 定义保存前的验证回调函数, 接口为function(ArrayCollection, AdvancedDataGrid, Array):Boolean
     * 通过指定此属性用户可以自定义验证函数
     **/
    private var _validator:Function=validate;

    public function get validator():Function
    {
      return _validator;
    }

    public function set validator(value:Function):void
    {
      _validator=value;
    }

    protected function validate(list:IList, grid:AdvancedDataGrid, fields:Array):Boolean
    {
      return CommonUtil.validateGrid(_grid, vfields).length <= 0;
    }

    public function save(event:Event, close:Boolean=false):void
    {
      if (_validator is Function && !_validator(list1, _grid, vfields))
        return;
      if (ObjectUtil.compare(list0, list1, 1) == 0)
      {
        Alert.show(resourceManager.getString("gcc", "save.no.change"), Constants.APP_NAME, 
          Alert.OK, null, null, Constants.ICON32_WARNING);
        return;
      }
      BaseController.saveObjects(list0.toArray(), list1.toArray(), 
        {"@class": CommonUtil.getAlias(_clazz), "@order": "id", "branch.id": UserController.branchId}, 
        function(e1:ResultEvent):void
        {
          var f:Function=close ? function(e2:Event):void
            {
              _close();
            } : function(e2:Event):void
            {
              loadGridList(e1.result as ArrayCollection);
            };
          dispatchEvent(new CommonEvent(CommonEvent.SAVE_SUCCESS, e1.result));
          Alert.show(resourceManager.getString("gcc", "save.success"), Constants.APP_NAME, 
            Alert.OK, null, f, Constants.ICON32_INFO);
        });
    }

    private function _close():void
    {
      if (owner != null)
        owner.dispatchEvent(CommonEvent.CLOSE_EVENT);
    }

    public function close(event:Event=null):void
    {
      if (ObjectUtil.compare(list0, list1, 1) != 0)
      {
        Alert.show(resourceManager.getString("gcc", "save.change"), Constants.APP_NAME, Alert.YES | Alert.NO,
          null, function(e:CloseEvent):void
          {
            if (e.detail == Alert.YES)
              save(event, true);
            else
              _close();
          }, Constants.ICON32_QUESTION);
      }
      else
      {
        _close();
      }
    }
  }
}