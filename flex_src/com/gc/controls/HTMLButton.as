package com.gc.controls
{
  import flash.events.Event;
  import flash.text.TextLineMetrics;

  import mx.controls.Button;
  import mx.core.UITextField;
  import mx.core.mx_internal;

  use namespace mx_internal;

  /**
   * The HTMLButton class have additional property is htmlLabel.
   * You cas specify HTML text in htmlLabel property.
   */
  public class HTMLButton extends Button
  {
    /**
     *  @private
     *  Storage for the htmlText property.
     */
    private var _htmlLabel:String;

    /**
     *  @private
     *  The value of the unscaledWidth parameter during the most recent
     *  call to updateDisplayList
     */
    private var oldUnscaledWidth:Number;

    /**
     *  @private
     *  This flag indicate htmlText changed for this component
     */
    private var htmlLabelChanged:Boolean;

    /**
     *  @private
     *  This flag indicate style changed for this component
     */
    private var styleChangedFlag:Boolean = true;

    /**
     *  @private
     *  This flag indicate tooltip set for this component
     */
    private var toolTipSet:Boolean = false;

    /**
     *  @private
     *  This label setter override for update htmlLabel property with null
     */
    override public function set label(value:String):void
    {
      super.label = value;

      if (super.label != value)
        _htmlLabel = null;
    }

    override public function get label():String
    {
      if (isHTML)
        return _htmlLabel;
      return super.label;
    }

    [Bindable("htmlLabelChanged")]
    [CollapseWhiteSpace]
    [Inspectable(category="General",defaultValue="")]
    /**
     *  Specifies the text displayed by the Button control, including HTML markup that
     *  expresses the styles of that text.
     *  When you specify HTML text in this property, you can use the subset of HTML
     *  tags that is supported by the Flash TextField control.
     */
    public function get htmlLabel():String
    {
      return _htmlLabel;
    }


    /**
     * @private
     */
    public function set htmlLabel(value:String):void
    {
      if (_htmlLabel != value)
      {
        _htmlLabel = value;

        label = null;

        htmlLabelChanged = true;

        invalidateSize();
        invalidateDisplayList();

        dispatchEvent(new Event("htmlLabelChanged"));
      }
    }

    /**
     *  @private
     *  This label setter override for update toolTipSet property for this component
     */
    override public function set toolTip(value:String):void
    {
      super.toolTip = value;

      if (value)
        toolTipSet = true;
      else
        toolTipSet = false;
    }

    /**
     *  @private
     */
    private function get isHTML():Boolean
    {
      return _htmlLabel != null;
    }

    /**
     *  @private
     */
    override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
    {
      super.updateDisplayList(unscaledWidth, unscaledHeight);

      // If our width changed, reset the label text to get it to fit.
      if (isHTML && 
        (oldUnscaledWidth > unscaledWidth ||
        textField.htmlText != htmlLabel ||
        htmlLabelChanged||
        styleChangedFlag))
      {
        textField.htmlText = _htmlLabel;
        var lineMetrics:TextLineMetrics= measureHTMLText(_htmlLabel);
        var truncated:Boolean = (lineMetrics.width + UITextField.TEXT_WIDTH_PADDING) > textField.width;

        if (!toolTipSet)
        {
          if (truncated)
            super.toolTip = textField.text;
          else
            super.toolTip = null;
        }
      }

      oldUnscaledWidth = unscaledWidth;
      htmlLabelChanged = false;
      styleChangedFlag = false;
    }

    /**
     *  @private
     *  This function overrited only for update styleChangedFlag
     */
    override public function styleChanged(styleProp:String):void
    {
      styleChangedFlag = true;

      super.styleChanged(styleProp);
    }

    /**
     * This overrited function return TextLineMetrics based on htmlLabel property.
     * If htmlLabel is not null, its return TextLineMetrics for htmlLabel.
     * Otherwiset its return TextLineMetrics for label.
     */
    override public function measureText(text:String):TextLineMetrics
    {
      if (isHTML)
        return super.measureHTMLText(text);

      return super.measureText(text);
    }
  }
}