package com.gc.controls.advancedDataGridClasses
{
  import com.gc.CommonEvent;

  import flash.display.DisplayObject;
  import flash.events.MouseEvent;

  import mx.controls.AdvancedDataGrid;
  import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
  import mx.controls.advancedDataGridClasses.AdvancedDataGridColumnGroup;
  import mx.controls.advancedDataGridClasses.AdvancedDataGridHeaderRenderer;
  import mx.controls.listClasses.BaseListData;
  import mx.core.IFlexDisplayObject;
  import mx.core.mx_internal;
  import mx.styles.ISimpleStyleClient;

  use namespace mx_internal;

  [Style(name="icon",type="Class",inherit="no")]

  public class SuperDataGridHeaderRenderer extends AdvancedDataGridHeaderRenderer
  {

    public function SuperDataGridHeaderRenderer()
    {
      super();
      addEventListener(MouseEvent.CLICK, clickHandler);
    }

    //--------------------------------------------------------------------------
    //
    //  Variables
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    protected var grid:AdvancedDataGrid;

    override public function set listData(value:BaseListData):void
    {
      super.listData=value;
      this.grid=AdvancedDataGrid(value.owner);
    }

    /**
     *  @private
     *  A reference to the current icon.
     *  Set by viewIcon().
     */
    protected var icon:IFlexDisplayObject;

    protected var openIcon:IFlexDisplayObject;

    /**
     *  @private
     *  Icon names.
     *  Allows subclasses to re-define the icon property names.
     */
    protected var iconName:String = "icon";

    [Embed(source="assets/icons/16x16/minus.gif")]
    private static const OPENED_ICON:Class;

    [Embed(source="assets/icons/16x16/plus.gif")]
    private static const CLOSED_ICON:Class;

    override protected function createChildren():void
    {
      super.createChildren();
      if (!icon)
      {
        var clazz:Class = Class(getStyle(iconName));
        if (clazz)
        {
          icon = new clazz();
          icon.name = iconName;
          if (icon is ISimpleStyleClient)
            ISimpleStyleClient(icon).styleName = this;
          addChild(DisplayObject(icon));
        }
      }
      openSubColumns(_opened);
    }

    /**
     *  @private
     *  Is the header currently opened?
     */
    private var _opened:Boolean=false;

    public function get opened():Boolean
    {
      return _opened;
    }

    public function set opened(value:Boolean):void
    {
      if (_opened != value)
      {
        _opened=value;
        openSubColumns(_opened);
      }
    }

    protected function clickHandler(event:MouseEvent):void
    {
      opened=!_opened;
    }

    /**
     *  @private
     *  Apply the data and listData.
     *  Create an instance of the sort item renderer if specified,
     *  and set the text into the text field.
     */
    override protected function commitProperties():void
    {
      super.commitProperties();
      var clazz:Class = _opened ? OPENED_ICON : CLOSED_ICON;
      if (openIcon)
      {
        removeChild(DisplayObject(openIcon));
      }
      openIcon = new clazz();
      addChild(DisplayObject(openIcon));
    }

    override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
    {
      super.updateDisplayList(unscaledWidth, unscaledHeight);

      // It seems strange to get a zero-width display,
      // is there a need to handle this case?
      if (unscaledWidth == 0)
        return;

      // Cache padding values
      var paddingLeft:int   = getStyle("paddingLeft");
      var paddingRight:int  = getStyle("paddingRight");
      var paddingTop:int    = getStyle("paddingTop");
      var paddingBottom:int = getStyle("paddingBottom");
      var verticalAlign:String = getStyle("verticalAlign");
      var horizontalGap:Number = getStyle("horizontalGap");

      // Size of openIcon
      // Assumption that iconWidth < unscaledWidth
      // and iconHeight < unscaledHeight
      var iconWidth:Number  = openIcon ? openIcon.width  : 0;
      var iconHeight:Number = openIcon ? openIcon.height : 0;

      // Calculate position of icon
      if (openIcon)
      {
        // var iconX:Number = unscaledWidth - iconWidth - paddingRight;
        var iconX:Number = label.x + label.width + horizontalGap;
        var iconY:Number;
        if (verticalAlign == "top")
        {
          iconY = 0;
        }
        else if (verticalAlign == "bottom")
        {
          iconY = unscaledHeight - icon.height;
        }
        else
        {
          iconY = (unscaledHeight - iconHeight - paddingTop - paddingBottom) / 2 + paddingTop;
        }
        openIcon.x = Math.round(iconX);
        openIcon.y = Math.round(iconY);
        openIcon.setActualSize(openIcon.measuredWidth, openIcon.measuredHeight);
      }
    }

    /**
     *  @private
     */
    private function openSubColumns(value:Boolean):void
    {
      if (!(data is AdvancedDataGridColumnGroup))
        return;
      var columnGroup:AdvancedDataGridColumnGroup=data as AdvancedDataGridColumnGroup;
      var columns:Array=columnGroup.children;

      /**
       * grid.validateProperties()非常重要,可以解决异常情况,如:
       * 1. _opened=false,但AdvancedDataGridColumnGroup没有收起来
       * 2. 重复调用此函数
       * ------3. 在grid中间展开收缩时出现空白, 原先通过在clickHandler加入以下代码解决:
       **/
      if (!value)
      {
        grid.horizontalScrollPosition=0;
        grid.maxHorizontalScrollPosition=0;
      }
      columnGroup.dispatchEvent(new CommonEvent(CommonEvent.EXPANDED, value, [data, this]));
      grid.callLater(function():void
        {
          grid.invalidateProperties();
          for (var i:int=1; i < columns.length; i++)
          {
            columns[i].visible=value;
          }
          grid.validateProperties();
        });
    }

    mx_internal function expand(event:CommonEvent):void
    {
      event.stopImmediatePropagation();
      opened=(event.data == null || event.data == true);
    }

    override public function set data(value:Object):void
    {
      super.data=value;
      if (value is AdvancedDataGridColumnGroup)
      {
        var columnGroup:AdvancedDataGridColumnGroup=value as AdvancedDataGridColumnGroup;
        // 使render所属的ColumnGroup及ColumnGroup的所有Columns监听CommonEvent.EXPAND事件
        // 此ColumnGroup将根据接收到的事件的data属性展开(true/null)或收拢(false)
        if (!columnGroup.hasEventListener(CommonEvent.EXPAND))
        {
          columnGroup.addEventListener(CommonEvent.EXPAND, expand);
          var columns:Array=columnGroup.children;
          for each (var column:AdvancedDataGridColumn in columns)
          {
            if (!column.hasEventListener(CommonEvent.EXPAND))
              column.addEventListener(CommonEvent.EXPAND, expand);
          }
        }
      }
    }
  }
}