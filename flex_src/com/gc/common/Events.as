package com.gc.common
{
	import com.gc.common.model.*;

	import flash.events.Event;

	public class Events extends Event
	{

//==================================== Base ====================================

		public static const ADD_WEATHER:String="addWeather@commonBaseController";

//==================================== User ====================================

		public var data:Object;
		public var args:Array;

		public function Events(type:String, data:Object=null, args:Array=null, bubbles:Boolean=true, cancelable:Boolean=false)
		{
			this.data=data;
			this.args=args;
			super(type, bubbles, cancelable);
		}

		override public function clone():Event
		{
			return new Events(type, data, args);
		}

		override public function toString():String
		{
			return "Common Event{type=" + type + ", data=" + data + ", args=" + args + ", bubbles=" + bubbles + ", cancelable=" + cancelable + ", phase=" + super.eventPhase + "}";
		}
	}

}
