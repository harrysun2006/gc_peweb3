package events
{
  import flash.events.Event;
  import mx.collections.ArrayCollection;

  [Exclude]
  public class ResponseEvent extends Event
  {

    public var data:ArrayCollection;
    public var selection:Object;

    public function ResponseEvent(type:String, data:ArrayCollection=null, selection:Object=null)
    {
      super(type, true, true);
      this.data=data;
      this.selection=selection;
    }


  }
}