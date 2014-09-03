package com.gc.controls
{
  import flexlib.containers.SuperTabNavigator;

  import mx.collections.ArrayCollection;
  import mx.controls.richTextEditorClasses.ToolBar;
  import mx.core.EdgeMetrics;
  import mx.events.IndexChangedEvent;

  public class ToolBarNavigator extends SuperTabNavigator
  {

    protected var _toolBar:ToolBar;

    protected var _toolBars:Array;

    public function ToolBarNavigator()
    {
    }

    public function get toolBars():Object
    {
      return _toolBars;
    }

    /**
     *  @private
     */
    public function set toolBars(value:Object):void
    {
      if (value is Array)
      {
        _toolBars=value as Array;
      }
      else if (value is ArrayCollection)
      {
        _toolBars=(value as ArrayCollection).source;
      }
      setToolBar((_toolBars != null && _toolBars.length > selectedIndex) ? _toolBars[selectedIndex] : null);
    }

    private function setToolBar(value:Object):void
    {
      if (value == null || value is ToolBar)
      {
        if (_toolBar)
          holder.removeChild(_toolBar);
        _toolBar=value as ToolBar;
        if (_toolBar)
        {
          _toolBar.setStyle("borderStyle", "none");
          _toolBar.setStyle("paddingTop", 0);
          _toolBar.setStyle("paddingBottom", 0);
          holder.addChildAt(_toolBar, 1);
        }
      }
    }

    override protected function createChildren():void
    {
      super.createChildren();
      if (_toolBars != null && _toolBars.length > 0)
      {
        setToolBar(_toolBars[0]);
      }
      this.addEventListener(IndexChangedEvent.CHANGE, tabChangedEvent);
    }

    private function tabChangedEvent(event:IndexChangedEvent):void
    {
      setToolBar((toolBars != null && event.newIndex < toolBars.length) ? toolBars[event.newIndex] : null);
    }

    override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
    {
      super.updateDisplayList(unscaledWidth, unscaledHeight);
      holder.setStyle("verticalAlign", "middle");

      if (_toolBar && _toolBar.includeInLayout)
      {
        canvas.width-=_toolBar.width;
      }
    }

  }
}