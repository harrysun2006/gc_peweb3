package events
{
  import flash.events.Event;

  [Exclude]
  public class RequestEvent extends Event
  {

    public var data:Object;

    public function RequestEvent(type:String, data:Object=null)
    {
      super(type, true, true);
      this.data=data;
    }

  }
}