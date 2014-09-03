package com.gc
{
  import flash.events.Event;
  import flash.events.FocusEvent;
  import flash.events.MouseEvent;

  import mx.events.CloseEvent;
  import mx.events.ListEvent;
  import mx.rpc.events.ResultEvent;

  public dynamic class CommonEvent extends Event
  {
    public static const APPLY:String="apply";
    public static const CLOSING:String="closing";
    public static const CLOSED:String="closed";
    public static const EXIT:String="exit";
    public static const LOGOUT:String="logout"
    public static const RESET:String="reset";
    public static const REFRESH:String="refresh";
    public static const RETURN:String="return";
    public static const EXPORT:String="export";
    public static const IMPORT:String="import";
    public static const EXPAND:String="expand";
    public static const EXPANDED:String="expanded";
    public static const EXPAND_ALL:String="expandAll";
    public static const COLLAPSE:String="collapse";
    public static const COLLAPSED:String="collapsed";
    public static const COLLAPSE_ALL:String="collapseAll";
    public static const CREATED:String="created";
    public static const HIDED:String="hided";
    public static const CHANGED:String="changed";
    public static const CLICKED:String="clicked";
    public static const DBLCLICKED:String="dblClicked";
    public static const READY:String="ready";
    public static const PRINT:String="print";
    public static const CLEAR:String="clear";

    public static const GET:String="get";
    public static const GET_SUCCESS:String="getSuccess";
    public static const GET_FAULT:String="getFault";
    public static const LIST:String="list";
    public static const LIST_SUCCESS:String="listSuccess";
    public static const LIST_FAULT:String="listFault";
    public static const ADD:String="add";
    public static const ADD_SUCCESS:String="addSuccess";
    public static const ADD_FAULT:String="addFault";
    public static const DELETE:String="delete";
    public static const DELETE_SUCCESS:String="deleteSuccess";
    public static const DELETE_FAULT:String="deleteFault";
    public static const MERGE:String="merge";
    public static const MERGE_SUCCESS:String="mergeSuccess";
    public static const MERGE_FAULT:String="mergeFault";
    public static const SAVE:String="save";
    public static const SAVE_SUCCESS:String="saveSuccess";
    public static const SAVE_FAULT:String="saveFault";
    public static const SELECT:String="select";
    public static const SELECT_SUCCESS:String="selectSuccess";
    public static const SELECT_FAULT:String="selectFault";
    public static const UPDATE:String="update";
    public static const UPDATE_SUCCESS:String="updateSuccess";
    public static const UPDATE_FAULT:String="updateFault";

    public static const ADD_MENU:String="addMenu";
    public static const DELETE_MENU:String="deleteMenu";
    public static const UPDATE_MENU:String="updateMenu";

    public static const CHANGE_VIEW:String="changeView";
    public static const LOAD:String="load";
    public static const UNLOAD:String="unload";
    public static const DOWNLOADED:String="downloaded";
    public static const UPLOADED:String="uploaded"

    public static const APPLY_EVENT:CommonEvent=new CommonEvent(APPLY);
    public static const EXIT_EVENT:CommonEvent=new CommonEvent(EXIT, true);
    public static const LOGOUT_EVENT:CommonEvent=new CommonEvent(LOGOUT);
    public static const RESET_EVENT:CommonEvent=new CommonEvent(RESET);
    public static const REFRESH_EVENT:CommonEvent=new CommonEvent(REFRESH);
    public static const RETURN_EVENT:CommonEvent=new CommonEvent(RETURN);
    public static const EXPORT_EVENT:CommonEvent=new CommonEvent(EXPORT);
    public static const EXPAND_EVENT:CommonEvent=new CommonEvent(EXPAND);
    public static const EXPAND_ALL_EVENT:CommonEvent=new CommonEvent(EXPAND_ALL);
    public static const COLLAPSE_EVENT:CommonEvent=new CommonEvent(COLLAPSE);
    public static const COLLAPSE_ALL_EVENT:CommonEvent=new CommonEvent(COLLAPSE_ALL);
    public static const CHANGED_EVENT:CommonEvent=new CommonEvent(CHANGED);
    public static const READY_EVENT:CommonEvent=new CommonEvent(READY);
    public static const CLEAR_EVENT:CommonEvent=new CommonEvent(CLEAR);

    // 定义事件
    public static const MOUSE_CLICK_EVENT:MouseEvent=new MouseEvent(MouseEvent.CLICK);
    public static const CLOSE_EVENT:CloseEvent=new CloseEvent(CloseEvent.CLOSE, true);
    public static const FOCUS_OUT_EVENT:FocusEvent=new FocusEvent(FocusEvent.FOCUS_OUT);
    public static const LIST_CHANGE_EVENT:ListEvent=new ListEvent(ListEvent.CHANGE);

    public static const DEFAULT_RESULT_EVENT_HANDLER:Function=function(e:ResultEvent, arg:Object = null):void
      {
        if (arg is Function)
          arg(e);
      };

    public var data:Object;
    public var args:Array;

    public function CommonEvent(type:String, data:Object=null, args:Array=null, bubbles:Boolean=true, cancelable:Boolean=true)
    {
      this.data=data;
      this.args=args;
      super(type, bubbles, cancelable);
    }

    override public function clone():Event
    {
      return new CommonEvent(type, data, args);
    }

    override public function toString():String
    {
      // return super.toString();
      return "CommonEvent{type=" + type + ", data=" + data + ", args=" + args + ", bubbles=" + bubbles + ", cancelable=" + cancelable + ", phase=" + super.eventPhase + "}";
    }
  }
}