package com.gc.controls.treeClasses
{
  import mx.controls.Tree;
  import mx.controls.treeClasses.TreeItemRenderer;
  import mx.core.mx_internal;
  import mx.utils.UIDUtil;

  use namespace mx_internal;

  public class StyledTreeItemRenderer extends TreeItemRenderer
  {
    public function StyledTreeItemRenderer()
    {
      super();
    }

    override protected function commitProperties():void
    {
      super.commitProperties();
    }

    override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
    {
      super.updateDisplayList(unscaledWidth, unscaledHeight);
      if (data && parent)
      {
        var style:Object=data.hasOwnProperty("style") ? data.style : null;
//        trace(label.text + ":" + UIDUtil.getUID(this) + ":" + (style ? UIDUtil.getUID(style) : ""));
        if (style)
        {
          for (var prop:String in style)
          {
            setStyle(prop, style[prop]);
            if (prop == "bgColor")
            {
              label.background=true;
              label.backgroundColor=style[prop];
            }
          }
        }
        var tree:Tree=listData.owner as Tree;
        var labelColor:Number;
        if (!enabled)
          labelColor = getStyle("disabledColor");
        else if (tree.isItemHighlighted(listData.uid))
          labelColor = getStyle("textRollOverColor");
        else if (tree.isItemSelected(listData.uid))
          labelColor = getStyle("textSelectedColor");
        else
          labelColor = getStyle("color");
        label.setColor(labelColor);
      }
    }
  }
}