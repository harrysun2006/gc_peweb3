package com.gc.util
{
  import com.gc.Constants;

  import flash.events.Event;
  import flash.net.FileReference;
  import flash.net.URLLoader;
  import flash.net.URLLoaderDataFormat;
  import flash.net.URLRequest;
  import flash.utils.ByteArray;

  import fxl.xls.Workbook;
  import fxl.xls.Worksheet;

  import mx.controls.Alert;

  public class ExcelUtil
  {
    private static function singleton():void
    {
    }

    public function ExcelUtil(caller:Function=null)
    {
      if (caller != singleton)
        throw new Error("ExcelUtil is a non-instance class!!!");
    }

    /**
     * as3xls有3个版本(sigfrid1.0, djw, 1.0.1), 功能比较弱:
     * 1. 读取excel文件时会抛错(2030), djw版可以正确读取。
     * 2. 不支持图片, 单元格合并, 格式及样式设置, sheet名称设置无效，调用Sheet.resize方法会清空当前内容。
     * 3. 没有addRow, insertRow, deleteRow等方法。
     * 4. 原版的as3xls保存excel文件中文为乱码，djw版保存的excel文件每个单元格会丢失2字节内容。
     * 目前的as3xls版本调用Sheet.resize将清空之前写入的内容, 只能一次性写入!!!
     **/
    public static function export(data:Array, headers:Array, formats:Object=null, name:String="test.xls", row:uint=0, col:uint=0):void
    {
      var book:Workbook=new Workbook();
      var sheet:Worksheet=new Worksheet(book);
      var i:int, j:int, arr:Array;
      sheet.resize(data.length+row+1, headers.length+col);
      for (i=0; i < headers.length; i++)
        writeCell(sheet, row, i+col, headers[i]);
      for (i=0; i < data.length; i++)
      {
        if (data[i] is Array)
        {
          arr=data[i] as Array;
          for (j=0; j < arr.length; j++)
            writeCell(sheet, i+row+1, j+col, arr[j]);
        }
        else
        {
          writeCell(sheet, i+row+1, col, data[i]);
        }
      }
      book.sheets.addItem(sheet);

      var bytes:ByteArray=book.save();
      var fr:FileReference=new FileReference();
      fr.addEventListener(Event.COMPLETE, function(e1:Event):void
        {
          Alert.show(LangUtil.getString("gcc", "download.complete"), 
            Constants.APP_NAME, Alert.OK, null, null, Constants.ICON32_INFO);
        });
      fr.save(bytes, name);
    }

    private static function writeCell(sheet:Worksheet, row:uint=0, col:uint=0, value:Object=null):void
    {
      if (value is String)
        sheet.setCell(row, col, value);
      else if (value is Date)
        sheet.setCell(row, col, DateUtil.formatDate(value));
      else if (value is Number)
        sheet.setCell(row, col, Constants.NUMBER_FORMATTER_N2.format(value));
    }

    public static function load(name:String, callback:Function=null):void
    {
      var book:Workbook=new Workbook();
      var loader:URLLoader=new URLLoader();
      var req:URLRequest=new URLRequest("../template/"+name);
      loader.addEventListener(Event.COMPLETE, function(e1:Event):void
        {
          var bytes:ByteArray=loader.data as ByteArray;
          book.load(bytes);
          if (callback != null) callback(book);
        });
      loader.dataFormat=URLLoaderDataFormat.BINARY;
      loader.load(req);
    }

    public static function save(book:Workbook, name:String="test.xls"):void
    {
      var fr:FileReference=new FileReference();
      var bytes:ByteArray=book.save();
      fr.addEventListener(Event.COMPLETE, function(e1:Event):void
        {
          Alert.show(LangUtil.getString("gcc", "download.complete"), 
            Constants.APP_NAME, Alert.OK, null, null, Constants.ICON32_INFO);
        });
      fr.save(bytes, name);
    }

    public static function readRow(sheet:Worksheet, row:uint):Array
    {
      var cc:Array=[];
      var c:uint;
      for (c = 0; c < sheet.cols; c++)
        cc.push(sheet.cell(row, c).value);
      return cc;
    }

    public static function readCol(sheet:Worksheet, col:uint):Array
    {
      var rr:Array=[];
      var r:uint;
      for (r = 0; r < sheet.rows; r++)
        rr.push(sheet.cell(r, col).value);
      return rr;
    }

  }
}