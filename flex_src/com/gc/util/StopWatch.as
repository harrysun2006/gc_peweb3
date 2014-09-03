/**
 *  Step 1:	Create an instance of the stop watch
 * 			[Bindable] public var myStopWatch:StopWatch = new StopWatch(0.1);
 *
 *  Step 2: Display the watch results.
 *
 * <mx:NumberFormatter id="numberFormatter" decimalSeparatorFrom="." decimalSeparatorTo="." precision="6" />
 * <mx:Label text="{numberFormatter.format(myStopWatch.duration)}" />
 *
 *
 * To start the watch
 * 			myStopWatch.restartStopWatch()
 * 			myStopWatch.startStopWatch()
 *
 * To stop the watch
 *  		myStopWatch.stopStopWatch()
 *
 * Cheers!
 * -Hem
 */

package com.gc.util
{
  import flash.events.TimerEvent;
  import flash.utils.Timer;

  public class StopWatch
  {
    [Bindable]
    public var duration:Number=0;

    private var stopWatch:Timer; // every second
    private var accuracy:Number;

    public function StopWatch(accuracy:Number)
    {
      var delay:Number=1000 * Number(accuracy);
      this.accuracy=accuracy;
      stopWatch=new Timer(delay);
      stopWatch.addEventListener(TimerEvent.TIMER, handleEvents);
      //stopWatch.start();
    }

    private function handleEvents(e:TimerEvent):void
    {
      duration=stopWatch.currentCount * accuracy;
    }

    public function restart():void
    {
      stopWatch.start();
    }

    public function start():void
    {
      stopWatch.start();
    }

    public function stop():void
    {
      stopWatch.stop();
    }

    public function reset():void
    {
      stopWatch.reset();
    }
  }
}