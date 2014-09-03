package com.gc.hr.view
{
  import com.gc.CommonEvent;

  import mx.containers.TitleWindow;
  import mx.core.ContainerLayout;

  public class PersonsWindow extends TitleWindow
  {
    public function PersonsWindow()
    {
      super();
      layout=ContainerLayout.VERTICAL;
      width=800;
      height=600;
    }

    override protected function createChildren():void
    {
      super.createChildren();
      addEventListener(CommonEvent.CREATED, onCreated);
      this.setFocus();
    }

    private function onCreated(event:CommonEvent):void
    {
      if (event.args == null)
        return;
      trace(event.args);
    }
  }
}