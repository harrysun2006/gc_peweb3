package com.gc.controls
{
  import flash.display.DisplayObject;
  import flash.display.DisplayObjectContainer;
  import flash.events.Event;
  import flash.events.FocusEvent;
  import flash.events.KeyboardEvent;
  import flash.events.MouseEvent;
  import flash.geom.Point;
  import flash.geom.Rectangle;
  import flash.ui.Keyboard;

  import mx.containers.Box;
  import mx.controls.Label;
  import mx.controls.TextArea;
  import mx.controls.dataGridClasses.DataGridListData;
  import mx.controls.listClasses.BaseListData;
  import mx.controls.listClasses.IDropInListItemRenderer;
  import mx.core.Container;
  import mx.core.FlexVersion;
  import mx.core.IUIComponent;
  import mx.core.UIComponent;
  import mx.core.UIComponentGlobals;
  import mx.core.mx_internal;
  import mx.effects.Tween;
  import mx.events.DropdownEvent;
  import mx.events.FlexMouseEvent;
  import mx.events.InterManagerRequest;
  import mx.events.SandboxMouseEvent;
  import mx.managers.IFocusManagerComponent;
  import mx.managers.ISystemManager;
  import mx.managers.PopUpManager;
  import mx.styles.ISimpleStyleClient;

  use namespace mx_internal;

//--------------------------------------
//  Events
//-------------------------------------- 

  /**
   *  Dispatched when the specified UIComponent closes.
   *
   *  @eventType mx.events.DropdownEvent.CLOSE
   */
  [Event(name="close",type="mx.events.DropdownEvent")]

  /**
   *  Dispatched when the specified UIComponent opens.
   *
   *  @eventType mx.events.DropdownEvent.OPEN
   */
  [Event(name="open",type="mx.events.DropdownEvent")]

//--------------------------------------
//  Styles
//--------------------------------------

  /**
   *  Length of a close transition, in milliseconds.
   *  The default value is 250.
   */
  [Style(name="closeDuration",type="Number",format="Time",inherit="no")]

  /**
   *  Easing function to control component closing tween.
   */
  [Style(name="closeEasingFunction",type="Function",inherit="no")]

  /**
   *  Length of an open transition, in milliseconds.
   *  The default value is 250.
   */
  [Style(name="openDuration",type="Number",format="Time",inherit="no")]

  /**
   *  Easing function to control component opening tween.
   */
  [Style(name="openEasingFunction",type="Function",inherit="no")]

  /**
   *  The name of a CSS style declaration used by the control.
   *  This style allows you to control the appearance of the
   *  UIComponent object popped up by this control.
   *
   *  @default undefined
   */
  [Style(name="popUpStyleName",type="String",inherit="no")]

  /**
   *  Skin class for the popUpDown state (when arrowButton is in down
   *  state) of the background and border.
   *  @default mx.skins.halo.PopUpButtonSkin
   */
  [Style(name="popUpDownSkin",type="Class",inherit="no")]

  /**
   *  Number of vertical pixels between the PopUpButton and the
   *  specified popup UIComponent.
   *  The default value is 0.
   */
  [Style(name="popUpGap",type="Number",format="Length",inherit="no")]

  /**
   *  The icon used for the right button of PopUpButton.
   *  Supported classes are mx.skins.halo.PopUpIcon
   *  and mx.skins.halo.PopUpMenuIcon.
   *  @default mx.skins.halo.PopUpIcon
   */
  [Style(name="popUpIcon",type="Class",inherit="no")]

  /**
   *  Skin class for the popUpOver state (over arrowButton) of
   *  the background and border.
   *  @default mx.skins.halo.PopUpButtonSkin
   */
  [Style(name="popUpOverSkin",type="Class",inherit="no")]

  [RequiresDataBinding(true)]

  public class PopUpTextArea extends Label implements IDropInListItemRenderer
  {
    public function PopUpTextArea()
    {
      super();
      addEventListener(MouseEvent.CLICK, clickHandler);
      addEventListener(Event.REMOVED_FROM_STAGE, removedFromStageHandler);
    }

    //--------------------------------------------------------------------------
    //
    //  Variables
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    private var inTween:Boolean = false;

    /**
     *  @private
     *  Is the popUp list currently shown?
     */
    private var showingPopUp:Boolean = false;

    /**
     *  @private
     *  The tween used for showing/hiding the popUp.
     */
    private var tween:Tween = null;

    /**
     *  @private
     */
    private var popUpChanged:Boolean = false;


    //--------------------------------------------------------------------------
    //
    //  Properties
    //
    //--------------------------------------------------------------------------


    //----------------------------------
    //  closeOnActivity
    //----------------------------------

    /**
     *  @private
     *  Storage for the closeOnActivity property.
     */
    private var _closeOnActivity:Boolean = true;

    /**
     *  @private
     *  Specifies popUp would close on click/enter activity.
     *  In popUps like Menu/List/TileList etc, one need not change
     *  this as they should close on activity. However for multiple
     *  selection, and other popUp, this can be set to false, to
     *  prevent the popUp from closing on activity.
     *
     *  @default true
     */
    private function get closeOnActivity():Boolean
    {
      // We are not exposing this property for now, until the need arises.
      return _closeOnActivity;
    }

    /**
     *  @private
     */
    private function set closeOnActivity(value:Boolean):void
    {
      _closeOnActivity = value;
    }

    //----------------------------------
    //  openAlways
    //----------------------------------

    /**
     *  @private
     *  Storage for the openAlways property.
     */
    private var _openAlways:Boolean = true;

    [Inspectable(category="General",defaultValue="false")]

    /**
     *  If <code>true</code>, specifies to pop up the
     *  <code>popUp</code> when you click the main button.
     *  The <code>popUp</code> always appears when you press the Spacebar,
     *  or when you click the pop-up button, regardless of the setting of
     *  the <code>openAlways</code> property.
     *
     *  @default false
     */
    public function get openAlways():Boolean
    {
      // We are not exposing this property for now, until the need arises.
      return _openAlways;
    }

    /**
     *  @private
     */
    public function set openAlways(value:Boolean):void
    {
      _openAlways = value;
    }

    //----------------------------------
    //  popUp
    //----------------------------------

    /**
     *  @private
     *  Storage for popUp property.
     */
    private var _popUp:Container = null;

    [Bindable(event='popUpChanged')]
    [Inspectable(category="General",defaultValue="null")]

    /**
     *  Specifies the UIComponent object, or object defined by a subclass
     *  of UIComponent, to pop up.
     *  For example, you can specify a Menu, TileList, or Tree control.
     *
     *  @default null
     */
    public function get popUp():Container
    {
      return _popUp;
    }

    /**
     *  @private
     */
    public function set popUp(value:Container):void
    {
      _popUp = value;
      popUpChanged = true;

      invalidateProperties();
    }

    //--------------------------------------------------------------------------
    //
    //  Overridden methods: UIComponent
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    override protected function commitProperties():void
    {
      super.commitProperties();

      if (popUpChanged)
      {
        if (_popUp is IFocusManagerComponent)
          IFocusManagerComponent(_popUp).focusEnabled = false;

        _popUp.cacheAsBitmap = true;
        _popUp.scrollRect = new Rectangle(0, 0, 0, 0);
        _popUp.addEventListener(FlexMouseEvent.MOUSE_DOWN_OUTSIDE, popMouseDownOutsideHandler);

        _popUp.addEventListener(FlexMouseEvent.MOUSE_WHEEL_OUTSIDE, popMouseDownOutsideHandler);
        _popUp.addEventListener(SandboxMouseEvent.MOUSE_DOWN_SOMEWHERE, popMouseDownOutsideHandler);
        _popUp.addEventListener(SandboxMouseEvent.MOUSE_WHEEL_SOMEWHERE, popMouseDownOutsideHandler);

        _popUp.owner = this;

        if (FlexVersion.compatibilityVersion >= FlexVersion.VERSION_3_0 && _popUp is ISimpleStyleClient)
          ISimpleStyleClient(_popUp).styleName = getStyle("popUpStyleName");

        popUpChanged = false;
      }

      // Close if we're disabled and we happen to still be showing our popup.
      if (showingPopUp && !enabled)
        close();
    }

    /**
     *  @private
     */
    override public function styleChanged(styleProp:String):void
    {
      if (styleProp == "popUpStyleName" && _popUp 
        && FlexVersion.compatibilityVersion >= FlexVersion.VERSION_3_0
        && _popUp is ISimpleStyleClient)
        ISimpleStyleClient(_popUp).styleName = getStyle("popUpStyleName");

      super.styleChanged(styleProp);
    }


    //--------------------------------------------------------------------------
    //
    //  Methods
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     *  Used by PopUpMenuButton
     */
    mx_internal function getPopUp():IUIComponent
    {
      return _popUp ? _popUp : null;
    }

    /**
     *  Opens the UIComponent object specified by the <code>popUp</code> property.
     */
    public function open():void
    {
      openWithEvent(null);
    }

    /**
     *  @private
     */
    private function openWithEvent(trigger:Event=null):void
    {
      if (!showingPopUp && enabled)
      {
        displayPopUp(true);

        var cbde:DropdownEvent = new DropdownEvent(DropdownEvent.OPEN);
        cbde.triggerEvent = trigger;
        dispatchEvent(cbde);
      }
    }

    /**
     *  Closes the UIComponent object opened by the PopUpButton control.
     */
    public function close():void
    {
      closeWithEvent(null);
    }

    /**
     *  @private
     */
    private function closeWithEvent(trigger:Event=null):void
    {
      if (showingPopUp)
      {
        displayPopUp(false);

        var cbde:DropdownEvent = new DropdownEvent(DropdownEvent.CLOSE);
        cbde.triggerEvent = trigger;
        dispatchEvent(cbde);
      }
    }

    /**
     *  @private
     */
    private function displayPopUp(show:Boolean):void
    {
      if (!initialized || (show == showingPopUp))
        return;
      // Subclasses may extend to do pre-processing
      // before the popUp is displayed
      // or override to implement special display behavior

      var popUpGap:Number = getStyle("popUpGap");
      var point:Point = new Point(0, unscaledHeight + popUpGap);
      point = localToGlobal(point);

      //Show or hide the popup
      var initY:Number;
      var endY:Number;
      var easingFunction:Function;
      var duration:Number;
      var sm:ISystemManager = systemManager.topLevelSystemManager;
      var sbRoot:DisplayObject = sm.getSandboxRoot();
      var screen:Rectangle;

      if (sm != sbRoot)
      {
        var request:InterManagerRequest = new InterManagerRequest(InterManagerRequest.SYSTEM_MANAGER_REQUEST, false, false, "getVisibleApplicationRect");
        sbRoot.dispatchEvent(request);
        screen = Rectangle(request.value);
      }
      else
        screen = sm.getVisibleApplicationRect();

      if (show)
      {
        if (getPopUp() == null)
          return;

        if (_popUp.parent == null)
        {
          PopUpManager.addPopUp(_popUp, this, false);
          _popUp.owner = this;
        }
        else
          PopUpManager.bringToFront(_popUp);

        if (point.y + _popUp.height > screen.bottom && 
          point.y > (screen.top + height + _popUp.height))
        {
          // PopUp will go below the bottom of the stage
          // and be clipped. Instead, have it grow up.
          point.y -= (unscaledHeight + _popUp.height + 2*popUpGap);
          initY = -_popUp.height;
        }
        else
        {
          initY = _popUp.height;
        }

        point.x = Math.min(point.x, screen.right - _popUp.getExplicitOrMeasuredWidth());
        point.x = Math.max(point.x, 0);
        point = _popUp.parent.globalToLocal(point);
        if (_popUp.x != point.x || _popUp.y != point.y)
          _popUp.move(point.x, point.y);

        _popUp.scrollRect = new Rectangle(0, initY, _popUp.width, _popUp.height);

        if (!_popUp.visible)
          _popUp.visible = true;

        endY = 0;
        showingPopUp = show;
        duration = getStyle("openDuration");
        easingFunction = getStyle("openEasingFunction") as Function;
      }
      else
      {
        showingPopUp = show;

        if (_popUp.parent == null)
          return;

        point = _popUp.parent.globalToLocal(point);

        endY = (point.y + _popUp.height > screen.bottom && 
          point.y > (screen.top + height + _popUp.height)
          ? -_popUp.height - 2
          : _popUp.height + 2);
        initY = 0;
        duration = getStyle("closeDuration")
        easingFunction = getStyle("closeEasingFunction") as Function;
      }

      inTween = true;
      UIComponentGlobals.layoutManager.validateNow();

      // Block all layout, responses from web service, and other background
      // processing until the tween finishes executing.
      UIComponent.suspendBackgroundProcessing();

      tween = new Tween(this, initY, endY, duration);
      if (easingFunction != null)
        tween.easingFunction = easingFunction;
    }


    /**
     *  @private
     */
    mx_internal function onTweenUpdate(value:Number):void
    {
      _popUp.scrollRect =
        new Rectangle(0, value, _popUp.width, _popUp.height);
    }

    /**
     *  @private
     */
    mx_internal function onTweenEnd(value:Number):void
    {
      _popUp.scrollRect =
        new Rectangle(0, value, _popUp.width, _popUp.height);

      inTween = false;
      UIComponent.resumeBackgroundProcessing();

      if (!showingPopUp)
      {
        _popUp.visible = false;
        _popUp.scrollRect = null;
      }
    }


    //--------------------------------------------------------------------------
    //
    //  Overridden event handlers: UIComponent
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    override protected function keyDownHandler(event:KeyboardEvent):void
    {
      super.keyDownHandler(event);

      if (event.ctrlKey && event.keyCode == Keyboard.DOWN)
      {
        openWithEvent(event);
        event.stopPropagation();
      }
      else if ((event.ctrlKey && event.keyCode == Keyboard.UP) ||
        (event.keyCode == Keyboard.ESCAPE))
      {
        closeWithEvent(event);
        event.stopPropagation();
      }
      else if (event.keyCode == Keyboard.ENTER && showingPopUp)
      {
        // Redispatch the event to the popup
        // and let its keyDownHandler() handle it.
        _popUp.dispatchEvent(event);
        closeWithEvent(event);
        event.stopPropagation();
      }
      else if (showingPopUp &&
        (event.keyCode == Keyboard.UP ||
        event.keyCode == Keyboard.DOWN ||
        event.keyCode == Keyboard.LEFT ||
        event.keyCode == Keyboard.RIGHT ||
        event.keyCode == Keyboard.PAGE_UP ||
        event.keyCode == Keyboard.PAGE_DOWN))
      {
        // Redispatch the event to the popup
        // and let its keyDownHandler() handle it.
        _popUp.dispatchEvent(event);
        event.stopPropagation();
      }
    }

    /**
     *  @private
     */
    override protected function focusOutHandler(event:FocusEvent):void
    {
      // Note: event.relatedObject is the object getting focus.
      // It can be null in some cases, such as when you open
      // the popUp and then click outside the application.

      // If the dropdown is open...
      if (showingPopUp && _popUp)
      {
        // If focus is moving outside the popUp...
        if (!event.relatedObject)
        {
          close();
        }
        else if (_popUp is DisplayObjectContainer && !DisplayObjectContainer(_popUp).contains(event.relatedObject))
        {
          close();
        }
      }

      super.focusOutHandler(event);
    }

    /**
     *  @private
     */
    protected function clickHandler(event:MouseEvent):void
    {
      if (!enabled)
      {
        // Prevent the propagation of click from a disabled Button.
        // This is conceptually a higher-level event and
        // developers will expect their click handlers not to fire
        // if the Button is disabled.
        event.stopImmediatePropagation();
        return;
      }
      if (openAlways)
      {
        if (showingPopUp)
          closeWithEvent(event);
        else
          openWithEvent(event);
      }
    }

    //--------------------------------------------------------------------------
    //
    //  Event handlers
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     *  Close popUp for IListItemRenderer's like List/Menu.
     */
    private function popUpItemClickHandler(event:Event):void
    {
      if (_closeOnActivity)
        close();
    }

    /**
     *  @private
     */
    private function popMouseDownOutsideHandler(event:Event):void
    {
      if (event is MouseEvent)
      {
        // for automated testing, since we're generating this event and
        // can only set localX and localY, transpose those coordinates
        // and use them for the test point.
        var mouseEvent:MouseEvent = MouseEvent(event);
        var p:Point = event.target.localToGlobal(new Point(mouseEvent.localX, mouseEvent.localY));
        if (hitTestPoint(p.x, p.y, true))
        {
          // do nothing
        }
        else
        {
          close();
        }
      }
      else if (event is SandboxMouseEvent)
        close();
    }

    /**
     *  @private
     */
    private function removedFromStageHandler(event:Event):void
    {
      // Ensure we've unregistered ourselves from PopupManager, else
      // we'll be leaked.
      if (_popUp)
      {
        PopUpManager.removePopUp(_popUp);
        showingPopUp = false;
      }
    }

    /**
     *  @private
     */
    mx_internal function get isShowingPopUp():Boolean
    {
      return showingPopUp;
    }

    override protected function createChildren():void
    {
      super.createChildren();
      _popUp=new Box();
      _text=new TextArea();
      _popUp.addChild(_text);
    }

    override public function set data(value:Object):void
    {
      super.data=value;
      _text.text=data[_listData.dataField];
    }

    override public function get data():Object
    {
      return super.data;
    }

    private var _text:TextArea;

    override public function get text():String
    {
      return _text.text;
    }

    override public function set text(value:String):void
    {
      _text.text=value;
    }

    private var _listData:DataGridListData;

    override public function get listData():BaseListData
    {
      return _listData;
    }

    override public function set listData(value:BaseListData):void
    {
      _listData = DataGridListData(value);
    }

  }
}