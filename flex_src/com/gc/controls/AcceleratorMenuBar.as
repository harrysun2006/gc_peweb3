package com.gc.controls
{
  import com.gc.controls.menuClasses.AcceleratorMenuItemRenderer;

  import flash.events.KeyboardEvent;

  import mx.collections.ICollectionView;
  import mx.controls.Label;
  import mx.controls.Menu;
  import mx.controls.MenuBar;
  import mx.controls.listClasses.IListItemRenderer;
  import mx.core.ClassFactory;

  /**
   * 	The AcceleratorMenuBar is a MenuBar which displays windows/mac style
   * 	accelerators next to the menu labels.
   *
   * 	The Menu's 'icon' is used to display the accelerator.
   *
   * 	@see com.rphelan.controls.menuClasses.AcceleratorMenuItemRenderer
   * 	@see mx.controls.MenuBar
   */
  public class AcceleratorMenuBar extends MenuBar
  {
    /**
     * 	Constructor.
     */
    public function AcceleratorMenuBar()
    {
      super();
    }

    /**
     * 	By overriding getMenuAt, we can ensure that each new Menu
     * 	uses AcceleratorMenuItemRenderer for its itemRenderer
     * 	and Label for all of its icons.
     */
    public override function getMenuAt(index:int):Menu
    {
      var menu:Menu = super.getMenuAt(index);
      menu.itemRenderer = new ClassFactory(AcceleratorMenuItemRenderer);
      menu.iconFunction = getIcon;
      return menu;
    }

    /**
     * 	@private
     * 	this is an iconFunction for a Menu
     *
     * 	@return a reference to the Label class
     */
    private function getIcon(item:Object):Class
    {
      return Label;
    }
  }
}