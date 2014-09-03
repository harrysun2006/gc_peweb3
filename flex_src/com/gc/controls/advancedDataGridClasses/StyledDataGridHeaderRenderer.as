package com.gc.controls.advancedDataGridClasses
{
  import mx.controls.advancedDataGridClasses.AdvancedDataGridHeaderRenderer;
  import mx.styles.CSSStyleDeclaration;

  public class StyledDataGridHeaderRenderer extends AdvancedDataGridHeaderRenderer
  {
    public function StyledDataGridHeaderRenderer()
    {
      super();
    }

    private var _labelStyle:String;

    public function get labelStyle():String
    {
      return _labelStyle;
    }

    public function set labelStyle(value:String):void
    {
      _labelStyle = value;
    }

    override protected function commitProperties():void
    {
      super.commitProperties();
      label.styleName = _labelStyle;
    }

  }
}