package com.gc.common.model
{
  import com.gc.util.LangUtil;

  [Bindable]
  public dynamic class Print
  {
    public var mode:String; // 模式: print-打印; preview-预览; excel-导出Excel; 其它-取消
    public var orientation:uint; // 纸张方向: 1-横向; 2-纵向
    public var size:uint; // 纸张大小: A4-9; A3-8
    public var pages:String; // 打印的页码: all-全部; "1,3-6"-1和3至6页
    public var copies:uint; // 打印的份数
    public var onebyone:Boolean; // 是否逐份打印: copies>1时有效
    public var control:String; // 控制选项, 暂时不用
    public var options:Object; // 其他选项

    public static const MO_UNKNOWN:String = "";
    public static const MO_PRINT:String = "print";
    public static const MO_PREVIEW:String = "preview";
    public static const MO_EXCEL:String = "excel";

    public static const OR_LANDSCAPE:Object = {value:0x01, name:"landscape", label:LangUtil.getString("gcc", "print.orientation.landscape")};
    public static const OR_PORTRAIT:Object = {value:0x02, name:"portrait", label:LangUtil.getString("gcc", "print.orientation.portrait")};
    public static const OR_LIST:Array = [OR_LANDSCAPE, OR_PORTRAIT];

    public static const SI_16KA4:Object = {value:0x09, name:"A4", label:LangUtil.getString("gcc", "print.size.16KA4")};
    public static const SI_8KA3:Object = {value:0x08, name:"A3", label:LangUtil.getString("gcc", "print.size.8KA3")};
    public static const SI_LIST:Array = [SI_16KA4, SI_8KA3];

    public function Print(mode:String = "print", orientation:uint = 1, size:uint = 8)
    {
      this.mode = mode;
      this.orientation = orientation;
      this.size = size;
      pages = "all";
      copies = 1;
      onebyone = false;
    }

    public function get isPrint():Boolean
    {
      return mode == MO_PRINT;
    }

    public function get isPreview():Boolean
    {
      return mode == MO_PREVIEW;
    }

    public function get isExcel():Boolean
    {
      return mode == MO_EXCEL;
    }

    public function get isAll():Boolean
    {
      return pages == "all";
    }

    public function get isLandscape():Boolean
    {
      return orientation == 1;
    }

    public function get isPortrait():Boolean
    {
      return orientation == 2;
    }

    public function get size$():Object
    {
      var d:Object, r:Object = null;
      for each (d in SI_LIST)
      {
        if (d.value == size)
        {
          r = d;
          break;
        }
      }
      return r;
    }
  }
}