package com.gc.controls
{
  import com.gc.util.CommonUtil;
  import com.gc.util.DateUtil;

  import flash.events.Event;
  import flash.events.KeyboardEvent;
  import flash.ui.Keyboard;

  import mx.controls.TextInput;
  import mx.controls.dataGridClasses.DataGridListData;
  import mx.events.FlexEvent;

  [Event(name="timeChange", type="mx.events.FlexEvent")]
//  [IconFile("TimeInput.png")]
  public class TimeInput extends TextInput
  {
    public function TimeInput()
    {
      super();
//			this.restrict = "0-9\\ \[AaPp][Mm]{1}\\:";
      this.restrict = "0-9\\:";
//      this.maxChars = 12;
      this.maxChars = 8;
      this.width = 80;
      this.text = '00:00:00';
      this.addEventListener(KeyboardEvent.KEY_DOWN, keyPressHandler, false, 100);
      this.addEventListener(FlexEvent.VALUE_COMMIT, parseTextInput);
    }

    /*
     * Instructs time parser how to deal with ambiguity when no indication of AM/PM is given.
     * Values below will be interpreted as PM and values above as AM.
     * For example, given the default value of 6,
     * parsing '5' as time will return 5:00 PM while '6' will return 6:00 AM.
     * Setting to 0 forces all values to be interpreted as AM while 12 forces PM.
     * Values between 13 and 24 are processed as military time.
     */
    public var guessPMBelow:int = 6;

    // max seconds allowed
    public var max:Number = 86400;
    // min seconds allowed 
    public var min:Number = 0;
    // granularity, if set to 60 values will be rounded to nearest minute
    // 300 would round values to nearest 5 minutes
    public var stepSize:int = 60;
    // how much to +- when up/down arrows are pressed
    public var jumpSize:int = 15 * 60;

    [Inspectable(defaultValue="L:NN A", category="General")]
    /**
     * Sets the dateFormatter's formatString property
     * Common values are:
     * L - Hour in am/pm (1-12)
     * H - Hour in day (1-24)
     * J - Hour in day (0-23)
     * NN - Minutes (00-59)
     * SS - Seconds (00-59)
     * A - am/pm indicator
     *
     * @default 'L:NN A'
     */
    public var formatString:String = 'L:NN A';


    private var _seconds:Number = -1;
    private var _dt:Date = new Date(2000, 0, 1);
    private var _isSet:Boolean = false;
    private var _data:Object = {};

    [Bindable("dataChange")]
    override public function get data():Object
    {
      return _data;
    }
    override public function set data(value:Object):void
    {
      var newText:String;
      var newDt:Date;
      var dgld:DataGridListData;

      _data = value;
      if (listData)
      {
        if (listData is DataGridListData)
        {
          dgld = listData as DataGridListData;
          if (dgld.dataField)
          {
            newText = String(value[dgld.dataField]);
          }
          else
          {
            newText = String(listData.label);
          }
        }
        else
        {
          newText = listData.label;
        }
      }
      else if (value != null)
      {
        newText = String(value);
      }
      else
      {
        newText = '';
      }
      newDt = DateUtil.parseTime(newText, guessPMBelow);
      if (newDt)
      {
        this.seconds = DateUtil.toSeconds(newDt);
      }
      else
      {
        this.seconds = -1;
      }

      dispatchEvent(new FlexEvent(FlexEvent.DATA_CHANGE));

    }
    [Bindable('timeChange')]
    public function get seconds():Number
    {
      return (_seconds >= 0) ? _seconds : Number(null);
    }
    public function set seconds(value:Number):void
    {
      if (value == -1)
      {
        _seconds = value;
        _isSet = false;
        this.textField.text = this.text = '00:00:00';
        CommonUtil.showError(this,2000,"请按照正确时间格式输入");
        return;
      }
      else
      {
        if (value >= min && value <= max)
        {
          //value is in valid range
          if (stepSize > 0)
          {
            _seconds = Math.round(value / stepSize) * stepSize;
          }
          else
          {
            _seconds = value;
          }
        }
        else if (value < min)
        {
          _seconds = max;
        }
        else
        {
          _seconds = min;
        }
        _dt.setHours(0,0, _seconds);
        //set using textField.text because setting this.text fires valueCommit.
        var str:String = DateUtil.formatDateTime(_dt, formatString);
        this.textField.text = this.text = str;
      }
      if (listData)
      {
        //update _data object
        if (listData is DataGridListData)
        {
          var ld:DataGridListData = listData as DataGridListData;
          if (ld.dataField)
          {
            _data[ld.dataField] = this.textField.text;
          }
          else
          {
            _data = this.textField.text;
          }
        }
        else
        {
          _data = this.textField.text;
        }
      }

      dispatchEvent(new FlexEvent('timeChange'));
    }

    [Bindable('timeChange')]
    public function get minutes():Number
    {
      return seconds / 60;
    }
    public function set minutes(value:Number):void
    {
      seconds = value * 60;
    }
    [Bindable('timeChange')]
    public function get hours():Number
    {
      return seconds / 3600;
    }
    public function set hours(value:Number):void
    {
      seconds = value * 3600;
    }

    private function keyPressHandler(event:KeyboardEvent):void
    {
      if (this.editable)
      {
        switch (event.keyCode)
        {
          case Keyboard.UP:
            seconds = Math.round(_seconds / jumpSize) * jumpSize + jumpSize;
            break;
          case Keyboard.DOWN:
            seconds = Math.round(_seconds / jumpSize) * jumpSize - jumpSize;
            break;
          case Keyboard.RIGHT:
            //add hour
            seconds = Math.round(_seconds / jumpSize) * jumpSize + 3600;
            this.textField.setSelection(0,0);
            break;
          case Keyboard.LEFT:
            seconds = Math.round(_seconds / jumpSize) * jumpSize - 3600;
            this.textField.setSelection(0,0);
            break;
          case Keyboard.HOME:
            seconds = min;
            break;
          case Keyboard.END:
            seconds = max;
            break;
        }
      }
    }
    private function parseTextInput(event:Event = null):void
    {
//      if (event)
//      {
//        trace('parseTextInput() ' + event.type + ' ' + event.target);
//        trace('text:' + this.text);
//      }
      var timeStr:String = (this.textField) ? this.textField.text : this.text;
      var dt:Date = DateUtil.parseTime(timeStr, guessPMBelow);
      if (dt)
      {
        var value:Number = DateUtil.toSeconds(dt);
        if (this._seconds != value)
        {
          this.seconds = value;
        }
      }
      else
      {
        this.seconds = -1;
      }
    }
  }
}