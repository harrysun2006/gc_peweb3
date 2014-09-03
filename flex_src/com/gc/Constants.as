package com.gc
{
  import com.gc.util.LangUtil;
  import com.gc.validators.IdCardValidator;

  import flash.display.Stage;
  import flash.system.Capabilities;
  import flash.system.fscommand;

  import mx.controls.Spacer;
  import mx.controls.advancedDataGridClasses.AdvancedDataGridHeaderRenderer;
  import mx.core.Application;
  import mx.core.ClassFactory;
  import mx.formatters.NumberBaseRoundType;
  import mx.formatters.NumberFormatter;
  import mx.managers.ISystemManager;
  import mx.resources.IResourceManager;
  import mx.resources.ResourceManager;
  import mx.utils.URLUtil;
  import mx.validators.Validator;

  [Bindable]
  public class Constants
  {
    public static var SETTINGS:Object={};

    public static function get VERSION():String
    {
      return SETTINGS.hasOwnProperty("VERSION") ? SETTINGS["VERSION"] : "";
    }

    // 定义枚举量及其他常量
    public static const NULL_LABEL:String="N/A";
    public static const NULL_NAME:String="";
    public static const NULL_VALUE:String="";
    public static const ITEM_UNDEF:Object=new Object();
    public static const ITEM_ALL:Object=new Object();
    public static const PROP_NAME_COUNT:String="_count";
    public static const PROP_NAME_COUNT_1:String="_count_1";
    public static const PROP_NAME_ERROR:String="error";
    public static const PROP_NAME_FIELD:String="field";
    public static const PROP_NAME_FILTER:String="filter";
    public static const PROP_NAME_ICON:String="icon";
    public static const PROP_NAME_ITEM:String="item";
    public static const PROP_NAME_LABEL:String="label";
    public static const PROP_NAME_NAME:String="name";
    public static const PROP_NAME_OBJECT:String="obj";
    public static const PROP_NAME_SERIAL:String="serial";
    public static const PROP_NAME_SHOW:String="_show";
    public static const PROP_NAME_TYPE:String="_type";
    public static const PROP_NAME_VALIDATOR:String="validator";
    public static const PROP_NAME_VALUE:String="value";
    public static const TYPE_ROOT:String="root";
    public static const TYPE_UNKNOWN:String="unknown";
    public static const INTERVAL_YEARS:int=10;
    public static const OP_ADD:String="add";
    public static const OP_DELETE:String="delete";
    public static const OP_EDIT:String="edit";
    public static const OP_VIEW:String="view";

    // 定义日期/时间格式
    public static const DATE_FORMAT:String="YYYY-MM-DD";
    public static const DATETIME_FORMAT:String="YYYY-MM-DD JJ:NN:SS";
    public static const TIME_FORMAT:String="JJ:NN:SS";
    public static const NUMBER_FORMATTER_N2:NumberFormatter=new NumberFormatter();
    public static const NUMBER_FORMATTER_N0:NumberFormatter=new NumberFormatter();

    private static const _MIN_DATE:Date=new Date(1900, 0, 1, 0, 0, 0, 0);
    private static const _MAX_DATE:Date=new Date(9999, 11, 31, 0, 0, 0, 0);

    public static function get MIN_DATE():Date
    {
      return new Date(1900, 0, 1, 0, 0, 0, 0);
    }

    public static function get MAX_DATE():Date
    {
      return new Date(9999, 11, 31, 0, 0, 0, 0);
    }

    public static const MS_PER_SEC:int=1000;
    public static const SEC_PER_MIN:int=60;
    public static const MIN_PER_HOUR:int=60;
    public static const HOUR_PER_DAY:int=24;
    public static const DAY_PER_WEEK:int=7;
    public static const FIRST_DAY_OF_WEEK:int=1;
    public static const MS_PER_MIN:int=MS_PER_SEC * SEC_PER_MIN;
    public static const SEC_PER_HOUR:int=SEC_PER_MIN * MIN_PER_HOUR;
    public static const MIN_PER_DAY:int=MIN_PER_HOUR * HOUR_PER_DAY;
    public static const HOUR_PER_WEEK:int=HOUR_PER_DAY * DAY_PER_WEEK;
    public static const MS_PER_HOUR:int=MS_PER_SEC * SEC_PER_HOUR;
    public static const SEC_PER_DAY:int=SEC_PER_HOUR * HOUR_PER_DAY;
    public static const MIN_PER_WEEK:int=MIN_PER_DAY * DAY_PER_WEEK;
    public static const MS_PER_DAY:int=MS_PER_SEC * SEC_PER_DAY;
    public static const SEC_PER_WEEK:int=SEC_PER_DAY * DAY_PER_WEEK;
    public static const MS_PER_WEEK:int=MS_PER_DAY * DAY_PER_WEEK;
    public static const DEFAULT_DATAGRID_COLUMN_WIDTH:int=100;
    public static const VALIDATOR_NOEMPTY:Validator=new Validator();
    public static const VALIDATOR_IDCARD:Validator=new IdCardValidator();
    public static const ZERO_RENDERER:ClassFactory=new ClassFactory(Spacer);
    public static const ZERO_ADG_HEADER_RENDERER:ClassFactory=new ClassFactory(AdvancedDataGridHeaderRenderer);

    // 凭证类型
    public static var CLAIMS_TYPE_OUT:String;
    public static var CLAIMS_TYPE_IN:String;

    public static var APP_NAME:String;
    public static var GENDERS:Array;
    public static var MAIMS:Array;
    public static var MAIMSS:Array;
    public static var ACCSTATUS:Array;
    public static var REPORT:Array;
    public static var CONFIRMOBJ:Array;
    public static var APPLICATION_URL:String;
    public static var SERVER_PROTOCOL:String;
    public static var SERVER_NAME:String;
    public static var SERVER_PORT:int;
    public static const SERVER_CONTEXT:String="";
    public static const FLEX_SERVLET_URL:String="flex";
    public static var DEBUG:Boolean=false;

    public static function init():void
    {
      ZERO_RENDERER.properties={includeInLayout:false, visible:false};
      ZERO_ADG_HEADER_RENDERER.properties={sortItemRenderer: ZERO_RENDERER};
      NUMBER_FORMATTER_N2.rounding=NumberBaseRoundType.NEAREST;
      NUMBER_FORMATTER_N2.precision=2;
      NUMBER_FORMATTER_N2.useThousandsSeparator=false;
      NUMBER_FORMATTER_N0.rounding=NumberBaseRoundType.NEAREST;
      NUMBER_FORMATTER_N0.precision=0;
      NUMBER_FORMATTER_N0.useThousandsSeparator=false;
      APP_NAME=LangUtil.getString("gcc", "app.name");
      GENDERS=[{value: "男", label: LangUtil.getString("gcc", "male")},
        {value: "女", label: LangUtil.getString("gcc", "female")}];
      MAIMS=[{value: 0, label: LangUtil.getString("gcc", "maim.dead")},
        {value: 1, label: LangUtil.getString("gcc", "maim.hurt")}];
      MAIMSS=[{value: 0, label: LangUtil.getString("gcc", "maim.dead")},
        {value: 1, label: LangUtil.getString("gcc", "maim.hurt")},
        {value: 2, label: LangUtil.getString("gcc", "maim.nodeadandhurt")}];
      ACCSTATUS=[{value: 0, label: LangUtil.getString("gcc", "accstatus.new")},
        {value: 1, label: LangUtil.getString("gcc", "accstatus.process")},
        {value: 2, label: LangUtil.getString("gcc", "accstatus.audit")},
        {value: 3, label: LangUtil.getString("gcc", "accstatus.archive")}];
      REPORT=[{value: 0, label: LangUtil.getString("gcc", "report.down")},
        {value: 1, label: LangUtil.getString("gcc", "report.up")}];
      CONFIRMOBJ=[{value: "0", label: LangUtil.getString("gcc", "report.down")},
        {value: "1", label: LangUtil.getString("gcc", "report.up")}];
      ITEM_UNDEF[PROP_NAME_LABEL]=LangUtil.getString("gcc", "item.undef");
      ITEM_ALL[PROP_NAME_LABEL]=LangUtil.getString("gcc", "item.all");

      CLAIMS_TYPE_IN=LangUtil.getString("gcc", "claims.type.in");
      CLAIMS_TYPE_OUT=LangUtil.getString("gcc", "claims.type.out");

      var sm:ISystemManager=Application.application.systemManager;
      var player:String=Capabilities.playerType;
      var stage:Stage=sm.stage;
      var parameters:Object=sm.loaderInfo.parameters;

      APPLICATION_URL=sm.loaderInfo.url;
      SERVER_PROTOCOL=URLUtil.getProtocol(APPLICATION_URL);
      SERVER_NAME=URLUtil.getServerName(APPLICATION_URL);
      SERVER_PORT=URLUtil.getPort(APPLICATION_URL) == 0 ? 80 : URLUtil.getPort(APPLICATION_URL);

      // stage.showDefaultContextMenu=false;
      // stage.contextMenu=null;
      if (player == "StandAlone")
      {
        // stage.displayState=StageDisplayState.FULL_SCREEN;
        fscommand("trapallkeys", "true");
      }
      fscommand("showmenu", "false");
      var debug:String=parameters["debug"];
      Constants.DEBUG = (debug != null && debug.indexOf("true") == 0);
      stage.frameRate=24;
    }

    // 定义颜色
    public static const Transparent:uint=0x00000000;
    public static const Red:uint=0xFF0000;
    public static const Green:uint=0x00FF00;
    public static const Blue:uint=0x0000FF;
    public static const Yellow:uint=0xFFFF00;
    public static const Magenta:uint=0xFF00FF;
    public static const Cyan:uint=0x00FFFF;
    public static const Black:uint=0x000000;
    public static const White:uint=0xFFFFFF;
    public static const Pink:uint=0xFFC0CB;
    public static const Crimson:uint=0xDC143C;
    public static const DeepPink:uint=0xFF1493;
    public static const Violet:uint=0xEE82EE;
    public static const Purple:uint=0x800080;
    public static const Indigo:uint=0x4B0082;
    public static const GhostWhite:uint=0xF8F8FF;
    public static const Navy:uint=0x000080;
    public static const RoyalBlue:uint=0x4169E1;
    public static const CornflowerBlue:uint=0x6495ED;
    public static const DodgerBlue:uint=0x1E90FF;
    public static const SteelBlue:uint=0x4682B4;
    public static const Olive:uint=0x808000;
    public static const Gold:uint=0xFFD700;
    public static const Orange:uint=0xFFA500;
    public static const Chocolate:uint=0xD2691E;
    public static const Coral:uint=0xFF7F50;
    public static const Tomato:uint=0xFF6347;
    public static const Brown:uint=0xA52A2A;
    public static const DarkRed:uint=0x8B0000;
    public static const Maroon:uint=0x800000;
    public static const WhiteSmoke:uint=0xF5F5F5;
    public static const LightGray:uint=0xD3D3D3;
    public static const Silver:uint=0xC0C0C0;
    public static const DarkGray:uint=0xA9A9A9;
    public static const Gray:uint=0x808080;
    public static const DimGray:uint=0x696969;

    // 定义图标资源
    [Embed(source="logo.jpg")]
    public static const LOGO:Class;

    [Embed(source="assets/icons/16x16/down_down.png")]
    public static const ICON16_DOWN_DOWN:Class;

    [Embed(source="assets/icons/16x16/down1.png")]
    public static const ICON16_DOWN1:Class;

    [Embed(source="assets/icons/16x16/erase.png")]
    public static const ICON16_ERASE:Class;

    [Embed(source="assets/icons/16x16/up_up.png")]
    public static const ICON16_UP_UP:Class;

    [Embed(source="assets/icons/16x16/up1.png")]
    public static const ICON16_UP1:Class;

    [Embed(source="assets/icons/16x16/apply.png")]
    public static const ICON16_APPLY:Class;

    [Embed(source="assets/icons/16x16/down2.png")]
    public static const ICON16_DOWN2:Class;

    [Embed(source="assets/icons/16x16/up2.png")]
    public static const ICON16_UP2:Class;

    [Embed(source="assets/icons/16x16/refresh.png")]
    public static const ICON16_REFRESH:Class;

    [Embed(source="assets/icons/16x16/reset.png")]
    public static const ICON16_RESET:Class;

    [Embed(source="assets/icons/16x16/expand.gif")]
    public static const ICON16_TREE_EXPAND:Class;

    [Embed(source="assets/icons/16x16/expand_all.gif")]
    public static const ICON16_TREE_EXPANDALL:Class;

    [Embed(source="assets/icons/16x16/collapse.gif")]
    public static const ICON16_TREE_COLLAPSE:Class;

    [Embed(source="assets/icons/16x16/collapse_all.gif")]
    public static const ICON16_TREE_COLLAPSEALL:Class;

    [Embed(source="assets/icons/16x16/tree.gif")]
    public static const ICON16_TREE:Class;

    [Embed(source="assets/icons/16x16/map.png")]
    public static const ICON16_TREE_ORGANIZE:Class;

    [Embed(source="assets/icons/16x16/filter.gif")]
    public static const ICON16_TREE_FILTER:Class;

    [Embed(source="assets/icons/16x16/return.png")]
    public static const ICON16_RETURN:Class;

    [Embed(source="assets/icons/16x16/plus.gif")]
    public static const ICON16_TREEPLUS:Class;

    [Embed(source="assets/icons/16x16/minus.gif")]
    public static const ICON16_TREEMINUS:Class;

    [Embed(source="assets/icons/32x32/error.png")]
    public static const ICON32_ERROR:Class;

    [Embed(source="assets/icons/32x32/info.png")]
    public static const ICON32_INFO:Class;

    [Embed(source="assets/icons/32x32/warning.png")]
    public static const ICON32_WARNING:Class;

    [Embed(source="assets/icons/32x32/question.png")]
    public static const ICON32_QUESTION:Class;

    [Embed(source="assets/icons/16x16/branch.png")]
    public static const ICON16_ACCIDENT:Class;

    public static const ICON_NULL:Class=Class(null);

    // FlexReport Print ICONs
    [Embed(source="assets/icons/16x16/print_next.png")]
    public static const ICON16_PRINT_NEXT:Class;

    [Embed(source="assets/icons/16x16/print_previous.png")]
    public static const ICON16_PRINT_PREVIOUS:Class;

    [Embed(source="assets/icons/16x16/print_zoom_in.png")]
    public static const ICON16_PRINT_ZOOM_IN:Class;

    [Embed(source="assets/icons/16x16/print_zoom_out.png")]
    public static const ICON16_PRINT_ZOOM_OUT:Class;

    [Embed(source="assets/icons/16x16/printer.png")]
    public static const ICON16_PRINTER:Class;

    [Embed(source="assets/icons/16x16/page_white_acrobat.png")]
    public static const ICON16_PDF:Class;

    [Embed(source="assets/icons/16x16/page_white_magnify.png")]
    public static const ICON16_FITPAGE:Class;

    [Embed(source="assets/icons/16x16/page_white_width.png")]
    public static const ICON16_FITWIDTH:Class;

    [Embed(source="assets/icons/16x16/page_white.png")]
    public static const ICON16_FITACTUAL:Class;

    [Embed(source="assets/icons/16x16/page_white_stack.png")]
    public static const ICON16_PAGESTACK:Class;
  }
}