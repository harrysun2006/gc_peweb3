package com.gc.controls.menuClasses
{
  import com.gc.util.KeyUtil;

  import flash.events.KeyboardEvent;
  import flash.ui.Keyboard;

  /**
   * 	The Accelerator class represents an accelerator key combination
   * 	which is usually used as a keyboard short cut in an application.
   */
  public class Accelerator
  {
    /**
     * 	Indicates if the accelerator uses the alt key modifier
     */
    private var _altKey:Boolean;

    /**
     * 	Array of keyCodes that (along with alt/ctrl/shift modifiers)
     * 	can trigger this accelerator
     */
    private var _keyCodes:Array;

    /**
     * 	Indicates if the accelerator uses the control key modifier
     */
    private var _ctrlKey:Boolean;

    /**
     * 	Optional data payload to associate with the accelerator
     */
    private var _data:Object;


    /**
     * 	Indicates if the accelerator uses the shift key modifier
     */
    private var _shiftKey:Boolean;

    /**
     * 	Constructor
     */
    public function Accelerator(keyCodes:Array=null, ctrlKey:Boolean=false, shiftKey:Boolean=false, altKey:Boolean=false, data:Object=null)
    {
      _keyCodes = keyCodes;
      _ctrlKey = ctrlKey;
      _shiftKey = shiftKey;
      _altKey = altKey;
      _data = data;
    }

    /**
     * 	Checks to see if a given KeyboardEvent matches this accelerator
     *
     * 	@param event A KeyboardEvent such as KEY_UP or KEY_DOWN
     * 	@return whether the event matches this accelerator
     */
    public function test(event:KeyboardEvent):Boolean
    {
      if (event.ctrlKey == _ctrlKey 
        && event.altKey == _altKey 
        && event.shiftKey == _shiftKey)
      {
        for each (var code:uint in _keyCodes)
        {
          if (code == event.keyCode)
            return true;
        }
      }

      return false;
    }

    /**
     * 	Creates an accelerator from a string (e.g. Ctrl+Shift+S)
     *
     * 	@param str The accelerator string
     * 	@return an instance of the Accelerator class
     */
    public static function fromString(str:String):Accelerator
    {
      var rVal:Accelerator = new Accelerator();

      // before splitting the string by '+' we have to
      // distinguish + keys from + delimiters
      str = str.replace("++", "+[PLUS]");

      // break the string into its component parts
      var components:Array = str.split("+");
      for each (var component:String in components)
      {
        if (component == "[PLUS]") // special case
          rVal.keyCodes = KeyUtil.keyCodesFromString("+");
        else if (component == KeyUtil.ALT)
          rVal.altKey = true;
        else if (component == KeyUtil.BACKSPACE)
          rVal.keyCode = Keyboard.BACKSPACE;
        else if (component == KeyUtil.CAPS_LOCK)
          rVal.keyCode = Keyboard.CAPS_LOCK;
        else if (component == KeyUtil.CONTROL)
          rVal.ctrlKey = true;
        else if (component == KeyUtil.DELETE)
          rVal.keyCode = Keyboard.DELETE;
        else if (component == KeyUtil.DOWN)
          rVal.keyCode = Keyboard.DOWN;
        else if (component == KeyUtil.END)
          rVal.keyCode = Keyboard.END;
        else if (component == KeyUtil.ENTER)
          rVal.keyCode = Keyboard.ENTER;
        else if (component == KeyUtil.ESCAPE)
          rVal.keyCode = Keyboard.ESCAPE;
        else if (component == KeyUtil.F1)
          rVal.keyCode = Keyboard.F1;
        else if (component == KeyUtil.F10)
          rVal.keyCode = Keyboard.F10;
        else if (component == KeyUtil.F11)
          rVal.keyCode = Keyboard.F11;
        else if (component == KeyUtil.F12)
          rVal.keyCode = Keyboard.F12;
        else if (component == KeyUtil.F2)
          rVal.keyCode = Keyboard.F2;
        else if (component == KeyUtil.F3)
          rVal.keyCode = Keyboard.F3;
        else if (component == KeyUtil.F4)
          rVal.keyCode = Keyboard.F4;
        else if (component == KeyUtil.F5)
          rVal.keyCode = Keyboard.F5;
        else if (component == KeyUtil.F6)
          rVal.keyCode = Keyboard.F6;
        else if (component == KeyUtil.F7)
          rVal.keyCode = Keyboard.F7;
        else if (component == KeyUtil.F8)
          rVal.keyCode = Keyboard.F8;
        else if (component == KeyUtil.F9)
          rVal.keyCode = Keyboard.F9;
        else if (component == KeyUtil.HOME)
          rVal.keyCode = Keyboard.HOME;
        else if (component == KeyUtil.INSERT)
          rVal.keyCode = Keyboard.INSERT;
        else if (component == KeyUtil.LEFT)
          rVal.keyCode = Keyboard.LEFT;
        else if (component == KeyUtil.PAGE_DOWN)
          rVal.keyCode = Keyboard.PAGE_DOWN;
        else if (component == KeyUtil.PAGE_UP)
          rVal.keyCode = Keyboard.PAGE_UP;
        else if (component == KeyUtil.RIGHT)
          rVal.keyCode = Keyboard.RIGHT;
        else if (component == KeyUtil.SHIFT)
          rVal.shiftKey = true;
        else if (component == KeyUtil.TAB)
          rVal.keyCode = Keyboard.TAB;
        else if (component == KeyUtil.UP)
          rVal.keyCode = Keyboard.UP;
        else
        {
          rVal.keyCodes = KeyUtil.keyCodesFromString(component);
        }
      }

      return rVal;
    }

    /**
     * 	@return a string representing this accelerator
     */
    public function toString():String
    {
      var rVal:String = "";

      // add in modifiers first
      if (_altKey)
        rVal = appendKey(rVal, KeyUtil.ALT);
      if (_ctrlKey)
        rVal = appendKey(rVal, KeyUtil.CONTROL);
      if (_shiftKey)
        rVal = appendKey(rVal, KeyUtil.SHIFT);

      // add the key char
      var keyCode:uint = _keyCodes && _keyCodes.length ? _keyCodes[0] : 0;
      if (keyCode == Keyboard.BACKSPACE)
        rVal = appendKey(rVal, KeyUtil.BACKSPACE);
      else if (keyCode == Keyboard.CAPS_LOCK)
        rVal = appendKey(rVal, KeyUtil.CAPS_LOCK);
      else if (keyCode == Keyboard.DELETE)
        rVal = appendKey(rVal, KeyUtil.DELETE);
      else if (keyCode == Keyboard.DOWN)
        rVal = appendKey(rVal, KeyUtil.DOWN);
      else if (keyCode == Keyboard.END)
        rVal = appendKey(rVal, KeyUtil.END);
      else if (keyCode == Keyboard.ENTER)
        rVal = appendKey(rVal, KeyUtil.ENTER);
      else if (keyCode == Keyboard.ESCAPE)
        rVal = appendKey(rVal, KeyUtil.ESCAPE);
      else if (keyCode == Keyboard.F1)
        rVal = appendKey(rVal, KeyUtil.F1);
      else if (keyCode == Keyboard.F10)
        rVal = appendKey(rVal, KeyUtil.F10);
      else if (keyCode == Keyboard.F11)
        rVal = appendKey(rVal, KeyUtil.F11);
      else if (keyCode == Keyboard.F12)
        rVal = appendKey(rVal, KeyUtil.F12);
      else if (keyCode == Keyboard.F2)
        rVal = appendKey(rVal, KeyUtil.F2);
      else if (keyCode == Keyboard.F3)
        rVal = appendKey(rVal, KeyUtil.F3);
      else if (keyCode == Keyboard.F4)
        rVal = appendKey(rVal, KeyUtil.F4);
      else if (keyCode == Keyboard.F5)
        rVal = appendKey(rVal, KeyUtil.F5);
      else if (keyCode == Keyboard.F6)
        rVal = appendKey(rVal, KeyUtil.F6);
      else if (keyCode == Keyboard.F7)
        rVal = appendKey(rVal, KeyUtil.F7);
      else if (keyCode == Keyboard.F8)
        rVal = appendKey(rVal, KeyUtil.F8);
      else if (keyCode == Keyboard.F9)
        rVal = appendKey(rVal, KeyUtil.F9);
      else if (keyCode == Keyboard.HOME)
        rVal = appendKey(rVal, KeyUtil.HOME);
      else if (keyCode == Keyboard.INSERT)
        rVal = appendKey(rVal, KeyUtil.INSERT);
      else if (keyCode == Keyboard.LEFT)
        rVal = appendKey(rVal, KeyUtil.LEFT);
      else if (keyCode == Keyboard.PAGE_DOWN)
        rVal = appendKey(rVal, KeyUtil.PAGE_DOWN);
      else if (keyCode == Keyboard.PAGE_UP)
        rVal = appendKey(rVal, KeyUtil.PAGE_UP);
      else if (keyCode == Keyboard.RIGHT)
        rVal = appendKey(rVal, KeyUtil.RIGHT);
      else if (keyCode == Keyboard.TAB)
        rVal = appendKey(rVal, KeyUtil.TAB);
      else if (keyCode == Keyboard.UP)
        rVal = appendKey(rVal, KeyUtil.UP);
      else
      {
        rVal  = appendKey(rVal, KeyUtil.stringFromKeyCode(keyCode));
      }

      return rVal;
    }

    /**
     * 	Used by toString() to build an accelerator string
     * 	with '+' delimiters
     */
    private function appendKey(str:String, key:String):String
    {
      return str == "" ? key : str + "+" + key;
    }

    public function get altKey():Boolean
    {
      return _altKey;
    }

    public function set altKey(b:Boolean):void
    {
      _altKey = b;
    }

    public function get keyCodes():Array
    {
      return _keyCodes;
    }

    public function set keyCodes(codes:Array):void
    {
      _keyCodes = codes;
    }

    public function set keyCode(code:uint):void
    {
      _keyCodes = [code];
    }

    public function get ctrlKey():Boolean
    {
      return _ctrlKey;
    }

    public function set ctrlKey(b:Boolean):void
    {
      _ctrlKey = b;
    }

    public function get data():Object
    {
      return _data;
    }

    public function set data(obj:Object):void
    {
      _data = obj;
    }

    public function get shiftKey():Boolean
    {
      return _shiftKey;
    }

    public function set shiftKey(b:Boolean):void
    {
      _shiftKey = b;
    }
  }
}