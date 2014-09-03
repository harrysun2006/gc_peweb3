package com.gc.controls
{
  import flash.display.DisplayObject;
  import flash.display.Graphics;
  import flash.display.Shape;
  import flash.display.Sprite;

  import mx.collections.IHierarchicalData;
  import mx.controls.AdvancedDataGrid;
  import mx.controls.advancedDataGridClasses.AdvancedDataGridHeaderInfo;
  import mx.controls.listClasses.BaseListData;
  import mx.controls.listClasses.IListItemRenderer;
  import mx.core.FlexShape;
  import mx.core.FlexSprite;
  import mx.core.SpriteAsset;
  import mx.core.mx_internal;
  import mx.styles.StyleManager;

  use namespace mx_internal;

  public class StyledDataGrid extends AdvancedDataGrid
  {
    public function StyledDataGrid()
    {
      super();
    }

    override protected function drawRowBackgrounds():void
    {
      var colors:Array = getStyle("depthColors");
      if (_rootModel is IHierarchicalData && colors)
      {
        super.drawRowBackgrounds();
        return;
      }
      var rowBGs:Sprite = Sprite(listContent.getChildByName("rowBGs"));
      if (!rowBGs)
      {
        rowBGs = new FlexSprite();
        rowBGs.mouseEnabled = false;
        rowBGs.name = "rowBGs";
        listContent.addChildAt(rowBGs, 0);
      }
      colors = getStyle("alternatingItemColors");
      if (!colors || colors.length == 0)
        return;
      StyleManager.getColorNames(colors);
      var curRow:int = 0;
      var i:int = 0;
      var actualRow:int = verticalScrollPosition;
      var actualLockedRow:int = 0;
      var n:int = dataProvider ? dataProvider.length : 0;
      n = n < listItems.length ? n : listItems.length;

      // for Locked rows
      while (curRow < lockedRowCount && curRow < n)
      {
        drawRowBackground(rowBGs, i++, rowInfo[curRow].y, rowInfo[curRow].height, colors[actualLockedRow % colors.length], actualLockedRow);
        curRow++;
        actualLockedRow++;
        actualRow++;
      }

      var numLockCols:int = lockedColumnCount > 0 ? lockedColumnCount : 0;
      var x:int;
      if (numLockCols > 0 && numLockCols < visibleColumns.length)
      {
        for (i = 0; i < numLockCols; i++)
          x += displayableColumns[i].width;
      }
      // for unlocked rows
      while (curRow < n)
      {
        // drawRowBackground(rowBGs, i++, rowInfo[curRow].y, rowInfo[curRow].height, colors[actualRow % colors.length], actualRow);
        drawRowBackground2(rowBGs, i++, x, rowInfo[curRow].y, rowInfo[curRow].height, colors[actualRow % colors.length], actualRow);
        curRow++;
        actualRow++;
      }

      while (rowBGs.numChildren > i)
      {
        rowBGs.removeChildAt(rowBGs.numChildren - 1);
      }
    }

    protected function drawRowBackground2(s:Sprite, rowIndex:int, x:Number, y:Number, 
      height:Number, color:uint, dataIndex:int):void
    {
      var background:Shape;
      var displayWidth:int = unscaledWidth - viewMetrics.right - viewMetrics.left;
      if (rowIndex < s.numChildren)
      {
        background = Shape(s.getChildAt(rowIndex));
      }
      else
      {
        background = new FlexShape();
        background.name = "background";
        s.addChild(background);
      }

      background.x = x;
      background.y = y;

      // Height is usually as tall is the items in the row, but not if
      // it would extend below the bottom of listContent
      var height:Number = Math.min(height,
        listContent.height -
        y);

      var g:Graphics = background.graphics;
      g.clear();
      g.beginFill(color, getStyle("backgroundAlpha"));
      g.drawRect(0, 0, displayWidth - x, height);
      g.endFill();
    }

    override public function isItemHighlighted(data:Object):Boolean
    {
      return data != null && itemToUID(data) == highlightUID && data.hasOwnProperty("columnIndex") && highlightColumnIndex == data.columnIndex;
    }

    override public function isItemSelected(data:Object):Boolean
    {
      if (selectionMode == "singleCell" || selectionMode == "multipleCells")
      {
        var selection:Object=cellSelectionData[itemToUID(data)];
        return selection && data.hasOwnProperty("columnIndex") && selection[data.columnIndex];
      }
      else
      {
        return super.isItemSelected(data);
      }
    }

    override protected function drawCellItem(item:IListItemRenderer, selected:Boolean = false, highlighted:Boolean = false, caret:Boolean = false, transition:Boolean = false):void
    {
      if (!selected && !highlighted && !caret && item)
      {
        var rowData:BaseListData = rowMap[item.name];
        var columnIndex:int=rowData.columnIndex;
        var rowIndex:int=rowData.rowIndex;
        var cell:Object=rowData["item"][columns[columnIndex].dataField];
        var style:Object=(cell && cell.hasOwnProperty("style")) ? cell.style : null;
        if (style && style.hasOwnProperty("bgColor"))
        {
          var o:Sprite = new SpriteAsset();
          o.mouseEnabled = false;
          /**
           * TODO: 增加对应的removeChild, 参考AdvancedListBase的drawItem函数
           * selectionLayer.removeChild(selectionIndicators[rowData.uid]);
           * delete selectionIndicators[rowData.uid]
           * TODO: 滚动时的处理, 需要研究AdvancedDataGrid.moveIndicators和AdvancedListBase.scrollVertically方法
           **/
          selectionLayer.addChild(DisplayObject(o));
          drawCellIndicator(o, item.x, rowInfo[rowData.rowIndex].y, item.width, rowInfo[rowData.rowIndex].height, style.bgColor, item);
        }
      }
      super.drawCellItem(item, selected, highlighted, caret, transition);
    }

    protected function drawCellIndicator(indicator:Sprite, x:Number, y:Number, width:Number, height:Number, color:uint, itemRenderer:IListItemRenderer):void
    {
      var g:Graphics = Sprite(indicator).graphics;
      g.clear();
      g.beginFill(color);
      g.drawRect(0, 0, width, height);
      g.endFill();
      indicator.x = x;
      indicator.y = y;
    }

    override protected function setScrollBarProperties(totalColumns:int, visibleColumns:int,
      totalRows:int, visibleRows:int):void
    {
    }

  }
}