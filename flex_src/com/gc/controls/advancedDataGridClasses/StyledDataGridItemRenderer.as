package com.gc.controls.advancedDataGridClasses
{
  import mx.controls.AdvancedDataGrid;
  import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
  import mx.controls.advancedDataGridClasses.AdvancedDataGridItemRenderer;
  import mx.controls.advancedDataGridClasses.AdvancedDataGridListData;
  import mx.controls.listClasses.BaseListData;
  import mx.core.mx_internal;
  import mx.events.FlexEvent;

  use namespace mx_internal;

  /**
   *  Dispatched when the <code>data</code> property changes.
   *
   *  <p>When you use a component as an item renderer,
   *  the <code>data</code> property contains the data to display.
   *  You can listen for this event and update the component
   *  when the <code>data</code> property changes.</p>
   *
   *  @eventType mx.events.FlexEvent.DATA_CHANGE
   */
  [Event(name="dataChange", type="mx.events.FlexEvent")]

  public class StyledDataGridItemRenderer extends AdvancedDataGridItemRenderer
  {
    public function StyledDataGridItemRenderer()
    {
      super();
    }

    protected var _listData:AdvancedDataGridListData;

    override public function set listData(value:BaseListData):void
    {
      super.listData = value;
      _listData = AdvancedDataGridListData(value);
    }

    override public function validateNow():void
    {
      if (data && parent)
      {
        var grid:AdvancedDataGrid=_listData.owner as AdvancedDataGrid;
        var columnIndex:int=_listData.columnIndex;
        var rowIndex:int=_listData.rowIndex;
        var cell:Object=_listData.item[grid.columns[columnIndex].dataField];
        if (cell && cell.hasOwnProperty("style"))
        {
          var style:Object=cell.style;
          for (var prop:String in style)
          {
            setStyle(prop, style[prop]);
            /**
             * 在render中使用bgColor, 控件的上下两边会留空白
               if (prop == "bgColor")
               {
               background=true;
               backgroundColor=style[prop];
               }
             **/
          }
        }
      }
      super.validateNow();
    }

    /**
     * 当设置单元格的数据时(cell.item=data;), 调用的方法如下:
     * ==> AdvancedDataGrid::updateDisplayList(6007)
     * ==> AdvancedListBase::updateDisplayList(3501)
     * ==> AdvancedListBase::makeRowsAndColumns(1231)
     * ==> AdvancedDataGrid::makeRowsAndColumns(7168)
     * ==> AdvancedDataGridBaseEx::makeRowsAndColumns(1917)
     * ==> AdvancedDataGridBase::makeRowsAndColumns(892)
     * ==> AdvancedDataGridBase::createRow(1640)
     * ==> AdvancedDataGridBase::setupRenderer(1923)
     * ==> AdvancedDataGridItemRenderer::set data(171)
     * 从而对每个单元格都会派发DATA_CHANGE事件
     **/
    override public function validateProperties():void
    {
      super.validateProperties();
      if (_listData && _listData.item)
      {
        var grid:AdvancedDataGrid=_listData.owner as AdvancedDataGrid;
        var columnIndex:int=_listData.columnIndex;
        var column:AdvancedDataGridColumn=grid.columns[columnIndex];
        var cell:Object=_listData.item[column.dataField];
        var dataTip:String=_listData.item[column.dataTipField];
        if (cell && cell.hasOwnProperty("toolTip"))
        {
          if (cell.toolTip is String)
            toolTip=cell.toolTip;
          else if (cell.toolTip is Function)
            toolTip=cell.toolTip(cell);
          else
            toolTip=null;
        }
        else if (dataTip)
          toolTip=dataTip;
        else if (_listData && _listData.label && _listData.label != "")
          toolTip=_listData.label;
        else
          toolTip=null;
      }
    }
  }
}