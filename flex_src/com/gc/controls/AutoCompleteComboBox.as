////////////////////////////////////////////////////////////////////////////////
//
//  Copyright (C) 2003-2006 Adobe Macromedia Software LLC and its licensors.
//  All Rights Reserved. The following is Source Code and is subject to all
//  restrictions on such code as contained in the End User License Agreement
//  accompanying this product.
//
//  Modified by Jens Krause [www.websector.de] to avoid issues using Flex 3
//	@see: http://www.websector.de/blog/2008/04/30/quick-tip-avoid-issues-using-adobes-autocomplete-input-component-using-flex-3/
//
// 	This class is for demonstration purposes only. 
//	To use this solution extend the original Autocomplete.as described at the blog entry mentioned above, please.
//
////////////////////////////////////////////////////////////////////////////////

package com.gc.controls
{
  import flash.events.Event;
  import flash.events.FocusEvent;
  import flash.events.KeyboardEvent;
  import flash.net.SharedObject;
  import flash.ui.Keyboard;

  import mx.collections.IList;
  import mx.controls.ComboBox;
  import mx.core.UIComponent;


//--------------------------------------
//  Events
//--------------------------------------

  /**
   *  Dispatched when the <code>filterFunction</code> property changes.
   *
   *  You can listen for this event and update the component
   *  when the <code>filterFunction</code> property changes.</p>
   *
   *  @eventType flash.events.Event
   */
  [Event(name="filterFunctionChange",type="flash.events.Event")]

  /**
   *  Dispatched when the <code>typedText</code> property changes.
   *
   *  You can listen for this event and update the component
   *  when the <code>typedText</code> property changes.</p>
   *
   *  @eventType flash.events.Event
   */
  [Event(name="typedTextChange",type="flash.events.Event")]

//--------------------------------------
//  Excluded APIs
//--------------------------------------

  [Exclude(name="editable",kind="property")]

  /**
   *  The AutoComplete control is an enhanced
   *  TextInput control which pops up a list of suggestions
   *  based on characters entered by the user. These suggestions
   *  are to be provided by setting the <code>dataProvider
   *  </code> property of the control.
   *  @mxml
   *
   *  <p>The <code>&lt;fc:AutoComplete&gt;</code> tag inherits all the tag attributes
   *  of its superclass, and adds the following tag attributes:</p>
   *
   *  <pre>
   *  &lt;fc:AutoComplete
   *    <b>Properties</b>
   *    lookAhead="false"
   *    typedText=""
   *    filterFunction="<i>Internal filter function</i>"
   *
   *    <b>Events</b>
   *    filterFunctionChange="<i>No default</i>"
   *    typedTextChange="<i>No default</i>"
   *  /&gt;
   *  </pre>
   *
   *  @includeExample ../../../../../../docs/com/adobe/flex/extras/controls/example/AutoCompleteCountriesData/AutoCompleteCountriesData.mxml
   *
   *  @see mx.controls.ComboBox
   *
   */
  public class AutoCompleteComboBox extends ComboBox
  {

    //--------------------------------------------------------------------------
    //
    //  Constructor
    //
    //--------------------------------------------------------------------------

    /**
     *  Constructor.
     */
    public function AutoCompleteComboBox()
    {
      super();

      //Make ComboBox look like a normal text field
      editable = true;

      // setStyle("arrowButtonWidth", 0);
      setStyle("fontWeight", "normal");
      setStyle("cornerRadius", 0);
      setStyle("paddingLeft", 0);
      setStyle("paddingRight", 0);
      rowCount = 7;
    }

    //--------------------------------------------------------------------------
    //
    //  Variables
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    private var cursorPosition:Number=0;

    /**
     *  @private
     */
    private var prevIndex:Number = -1;

    /**
     *  @private
     */
    private var removeHighlight:Boolean = false;

    /**
     *  @private
     */
    private var showDropdown:Boolean=false;

    /**
     *  @private
     */
    private var showingDropdown:Boolean=false;

    /**
     *  @private
     */
    private var tempCollection:Object;

    /**
     *  @private
     */
    private var dropdownClosed:Boolean=true;

    //--------------------------------------------------------------------------
    //
    //  Overridden Properties
    //
    //--------------------------------------------------------------------------

    //----------------------------------
    //  editable
    //----------------------------------
    /**
     *  @private
     */
    override public function set editable(value:Boolean):void
    {
      //This is done to prevent user from resetting the value to false
      super.editable = value;
    }

    /**
     *  @private
     */
    override public function set dataProvider(value:Object):void
    {
      super.dataProvider = value;
      tempCollection = value;
    }

    //----------------------------------
    //  labelField
    //----------------------------------
    /**
     *  @private
     */
    override public function set labelField(value:String):void
    {
      super.labelField = value;

      invalidateProperties();
      invalidateDisplayList();
    }


    //--------------------------------------------------------------------------
    //
    //  Properties
    //
    //--------------------------------------------------------------------------


    //----------------------------------
    //  filterFunction
    //----------------------------------

    /**
     *  @private
     *  Storage for the filterFunction property.
     */
    private var _filterFunction:Function = defaultFilterFunction;

    /**
     *  @private
     */
    private var filterFunctionChanged:Boolean = true;

    [Bindable("filterFunctionChange")]
    [Inspectable(category="General")]

    /**
     *  A function that is used to select items that match the
     *  function's criteria.
     *  A filterFunction is expected to have the following signature:
     *
     *  <pre>f(item:~~, text:String):Boolean</pre>
     *
     *  where the return value is <code>true</code> if the specified item
     *  should displayed as a suggestion.
     *  Whenever there is a change in text in the AutoComplete control, this
     *  filterFunction is run on each item in the <code>dataProvider</code>.
     *
     *  <p>The default implementation for filterFunction works as follows:<br>
     *  If "AB" has been typed, it will display all the items matching
     *  "AB~~" (ABaa, ABcc, abAc etc.).</p>
     *
     *  <p>An example usage of a customized filterFunction is when text typed
     *  is a regular expression and we want to display all the
     *  items which come in the set.</p>
     *
     *  @example
     *  <pre>
     *  public function myFilterFunction(item:~~, text:String):Boolean
     *  {
     *     public var regExp:RegExp = new RegExp(text,"");
     *     return regExp.test(item);
     *  }
     *  </pre>
     *
     */
    public function get filterFunction():Function
    {
      return _filterFunction;
    }

    /**
     *  @private
     */
    public function set filterFunction(value:Function):void
    {
      //An empty filterFunction is allowed but not a null filterFunction
      if (value!=null)
      {
        _filterFunction = value;
        filterFunctionChanged = true;

        invalidateProperties();
        invalidateDisplayList();

        dispatchEvent(new Event("filterFunctionChange"));
      }
      else
        _filterFunction = defaultFilterFunction;
    }

    //----------------------------------
    //  filterFunction
    //----------------------------------

    [Inspectable(category="General")]

    //----------------------------------
    //  lookAhead
    //----------------------------------

    /**
     *  @private
     *  Storage for the lookAhead property.
     */
    private var _lookAhead:Boolean=false;

    /**
     *  @private
     */
    private var lookAheadChanged:Boolean;

    [Bindable("lookAheadChange")]
    [Inspectable(category="Data")]

    /**
     *  lookAhead decides whether to auto complete the text in the text field
     *  with the first item in the drop down list or not.
     *
     *  @default "false"
     */
    public function get lookAhead():Boolean
    {
      return _lookAhead;
    }

    /**
     *  @private
     */
    public function set lookAhead(value:Boolean):void
    {
      _lookAhead = value;
      lookAheadChanged = true;
    }

    //----------------------------------
    //  typedText
    //----------------------------------

    /**
     *  @private
     *  Storage for the typedText property.
     */
    private var _typedText:String="";
    /**
     *  @private
     */
    private var typedTextChanged:Boolean;

    [Bindable("typedTextChange")]
    [Inspectable(category="Data")]

    /**
     *  A String to keep track of the text changed as
     *  a result of user interaction.
     */
    public function get typedText():String
    {
      return _typedText;
    }

    /**
     *  @private
     */
    public function set typedText(input:String):void
    {
      _typedText = input;
      typedTextChanged = true;

      invalidateProperties();
      invalidateDisplayList();
      dispatchEvent(new Event("typedTextChange"));
    }

    //--------------------------------------------------------------------------
    //
    //  Overridden methods
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    override protected function commitProperties():void
    {
      super.commitProperties();

      if (!dropdown)
        selectedIndex=-1;

      if (dropdown)
      {
        // updateDataProvider();
        // selectedIndex=(collection as IList).getItemIndex(selectedItem);
        if (typedTextChanged)
        {
          cursorPosition = textInput.selectionBeginIndex;
          updateDataProvider();
          //In case there are no suggestions there is no need to show the dropdown
          if (collection.length==0 || typedText==""|| typedText==null)
          {
            dropdownClosed=true;
            showDropdown=false;
          }
          else
          {
            showDropdown = true;
            //这句话之前被harry 注释掉的，具体问题还不知;
            selectedIndex = 0;
          }
        }
      }
    }

    /**
     *  @private
     */
    override public function getStyle(styleProp:String):*
    {
      if (styleProp != "openDuration")
        return super.getStyle(styleProp);
      else
      {
        if (dropdownClosed)
          return super.getStyle(styleProp);
        else
          return 0;
      }
    }

    /**
     *  @private
     */
    override protected function keyDownHandler(event:KeyboardEvent):void
    {
      super.keyDownHandler(event);
      if (!event.ctrlKey)
      {
        //An UP "keydown" event on the top-most item in the drop-down
        //or an ESCAPE "keydown" event should change the text in text
        // field to original text
        if (event.keyCode == Keyboard.UP && prevIndex==0)
        {
          textInput.text = _typedText;
          textInput.setSelection(textInput.text.length, textInput.text.length);
          selectedIndex = -1;
        }
        else if (event.keyCode==Keyboard.ESCAPE && showingDropdown)
        {
          textInput.text = _typedText;
          textInput.setSelection(textInput.text.length, textInput.text.length);
          showingDropdown = false;
          dropdownClosed=true;
        }
        else if (event.keyCode == Keyboard.ENTER)
        {
          /*
             textInput.text = selectedLabel;
             textInput.setSelection(cursorPosition, textInput.text.length);
             textInput.setSelection(textInput.text.length,_typedText.length);
           */
        }
        else if (lookAhead && event.keyCode ==  Keyboard.BACKSPACE 
          || event.keyCode == Keyboard.DELETE)
          removeHighlight = true;
      }
      else if (event.ctrlKey && event.keyCode == Keyboard.UP)
        dropdownClosed=true;
      prevIndex = selectedIndex;
    }

    /**
     *  @private
     */
    override protected function measure():void
    {
      super.measure();
      measuredWidth = mx.core.UIComponent.DEFAULT_MEASURED_WIDTH;
    }

    /**
     *  @private
     */
    override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
    {

      super.updateDisplayList(unscaledWidth, unscaledHeight);
      //An UP "keydown" event on the top-most item in the drop 
      //down list otherwise changes the text in the text field to ""
      if (selectedIndex == -1)
        textInput.text = typedText;

      if (dropdown)
      {
        if (typedTextChanged)
        {
          //This is needed because a call to super.updateDisplayList() set the text
          // in the textInput to "" and thus the value 
          //typed by the user losts
          if (lookAhead && showDropdown && typedText!="" && !removeHighlight)
          {
            var label:String = itemToLabel(collection[0]);
            var index:Number =  label.toLowerCase().indexOf(_typedText.toLowerCase());
            if (index==0)
            {
              textInput.text = _typedText+label.substr(_typedText.length);
              textInput.setSelection(textInput.text.length, _typedText.length);
            }
            else
            {
              textInput.text = _typedText;
              textInput.setSelection(cursorPosition, cursorPosition);
              removeHighlight = false;
            }

          }
          else
          {
            textInput.text = _typedText;
            textInput.setSelection(cursorPosition, cursorPosition);
            removeHighlight = false;
          }

          typedTextChanged= false;
        }
        else if (typedText)
          //Sets the selection when user navigates the suggestion list through
          //arrows keys.
          textInput.setSelection(_typedText.length, textInput.text.length);
        // 清空textInput 里面填写的内容，影响不定
        _typedText = "";
      }
      if (showDropdown && !dropdown.visible)
      {
        //This is needed to control the open duration of the dropdown
        super.open();
        showDropdown = false;
        showingDropdown = true;

        if (dropdownClosed)
          dropdownClosed=false;
      }
    }


    /**
     *  @private
     */
    override protected function textInput_changeHandler(event:Event):void
    {
      super.textInput_changeHandler(event);
      //Stores the text typed by the user in a variable
      typedText=text;
    }

    //--------------------------------------------------------------------------
    //
    //  Methods
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    private function defaultFilterFunction(element:*, text:String):Boolean
    {
      var label:String = itemToLabel(element);
      return (label.toLowerCase().indexOf(text.toLowerCase()) >= 0);
    }

    /**
     *  @private
     */

    private function templateFilterFunction(element:*):Boolean
    {
      var flag:Boolean=false;
      if (filterFunction!=null)
        flag=filterFunction(element, typedText);
      return flag;
    }

    /**
     *  @private
     *  Updates the dataProvider used for showing suggestions
     */
    private function updateDataProvider():void
    {
      dataProvider = tempCollection;
      collection.filterFunction = templateFilterFunction;
      collection.refresh();
    }

    //--------------------------------------------------------------------------
    //
    //  modified source
    //
    //--------------------------------------------------------------------------

    /**
     *  Closes the combox and set the selection which is lost using Flex 3
     *
     *  @event	Event	Trigger event to close the combobox
     */
    override public function close(event:Event=null):void
    {
      super.close(event);
      if (selectedIndex == 0)
      {
        // set the text using the selected label
        textInput.text = selectedLabel;
        // select the text from typed text position to texts length
        textInput.setSelection(cursorPosition, textInput.text.length);
      }
    }
  }

}