package com.gc.util
{
  import flash.events.Event;
  import flash.events.IOErrorEvent;
  import flash.events.ProgressEvent;
  import flash.events.SecurityErrorEvent;
  import flash.net.Socket;
  import flash.system.fscommand;
  import flash.utils.ByteArray;
  import flash.utils.setTimeout;

  import mx.formatters.NumberBaseRoundType;
  import mx.formatters.NumberFormatter;

  public class AideUtil
  {

    protected static const DEFAULT_CHARSET:String="utf8";
    protected static const AIDE_PORT:uint=843;
    protected static const AIDE_TEST:String="TT";
    protected static const AIDE_EXIT:String="ET";
    protected static const AIDE_OPENCOM:String="OC1";
    protected static const AIDE_CLOSECOM:String="CC";
    protected static const AIDE_EXECUTE:String="EX";

    protected static const AIDEP_PORT:uint=16601;
    protected static const RS:int=0x1E; // ASCII码表中的记录分隔符
    protected static const US:int=0x1F; // ASCII码表中的单元分隔符
    protected static const COMMA:int=0x2C;

    private static var sockAide:Socket=null;
    private static var aideDataHandler:Function=null;
    private static var aideErrorHandler:Function=null;
    private static var sockAidep:Socket=null;

    private static function singleton():void
    {
    }

    public function AideUtil(caller:Function=null)
    {
      if (caller != singleton)
        throw new Error("AideUtil is a non-instance class!!!");
    }

    public static function init():void
    {
      openAide();
      setTimeout(function():void
        {
          openAidep();
        }, 2000);
    }

    public static function exit():void
    {
      closeAide();
      closeAidep();
    }

    /**
     * aide.exe相关方法:
     * 1. openAide: 启动aide.exe程序, 连接843端口
     * 2. openCardReader: 打开读卡器, 开始读卡
     * 3. closeCardReader: 关闭读卡器, 结束读卡
     * 4. closeAide: 关闭aide.exe程序, 关闭socke连接
     * 5. execute: 执行其他程序
     * 6. test: 测试信息
     **/
    public static function openAide():void
    {
      try
      {
        if (!sockAide)
          sockAide=new Socket();
        if (!sockAide.connected)
        {
          fscommand("exec", "aide.exe");
          sockAide.connect("localhost", AIDE_PORT);
        }
      }
      catch (e:Error)
      {
      }
    }

    public static function closeAide():void
    {
      if (sockAide && sockAide.connected)
      {
        sockAide.writeMultiByte(AIDE_EXIT, DEFAULT_CHARSET);
        sockAide.flush();
        sockAide.close();
      }
      sockAide = null;
    }

    private static function aideData(e:ProgressEvent):void
    {
      var ss:Array=[];
      while (sockAide.bytesAvailable)
      {
        ss.push(sockAide.readMultiByte(sockAide.bytesAvailable, DEFAULT_CHARSET));
      }
      if (aideDataHandler is Function)
        aideDataHandler(ss);
    }

    private static function aideError(e:Event):void
    {
      if (aideErrorHandler is Function)
        aideErrorHandler(e);
    }

    /**
     * 打开读卡器, 开始读卡
     *  - dataHandler: 接收到数据之后的回调函数
     *  - errorHandler: 错误处理回调函数
     * 测试卡号:
     * 142-0619-1064#00273EA2
     *   024021000B75D187200000273EA21064FFFFFFFF00002007101704010142061920101231
     * 142-0619-1111#AABBCCDD
     *   024021000B75D1872000AABBCCDD1111FFFFFFFF00002007101704010142061920101231
     * 142-0619-2222#AABBCCDD
     *   024021000B75D1872000AABBCCDD2222FFFFFFFF00002007101704010142061920101231
     * 142-0619-3333#AABBCCDD
     *   024021000B75D1872000AABBCCDD3333FFFFFFFF00002007101704010142061920101231
     * 142-0619-4444#AABBCCDD
     *   024021000B75D1872000AABBCCDD4444FFFFFFFF00002007101704010142061920101231
     * 142-0619-5555#AABBCCDD
     *   024021000B75D1872000AABBCCDD5555FFFFFFFF00002007101704010142061920101231
     **/
    public static function openCardReader(dataHandler:Function=null, errorHandler:Function=null):void
    {
      openAide();
      aideDataHandler=dataHandler;
      aideErrorHandler=errorHandler;
      sockAide.addEventListener(ProgressEvent.SOCKET_DATA, aideData);
      sockAide.addEventListener(SecurityErrorEvent.SECURITY_ERROR, aideError);
      sockAide.addEventListener(IOErrorEvent.IO_ERROR, aideError);
      if (!sockAide.connected)
      {
        aideError(null);
      }
      else
      {
        sockAide.writeMultiByte(AIDE_OPENCOM, DEFAULT_CHARSET);
        sockAide.flush();
      }
    }

    /**
     * 关闭读卡器, 结束读卡
     **/
    public static function closeCardReader():void
    {
      if (!sockAide)
        return;
      aideDataHandler=aideErrorHandler=null;
      sockAide.removeEventListener(ProgressEvent.SOCKET_DATA, aideData);
      sockAide.removeEventListener(IOErrorEvent.IO_ERROR, aideError);
      sockAide.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, aideError);
      if (sockAide.connected)
      {
        sockAide.writeMultiByte(AIDE_CLOSECOM, DEFAULT_CHARSET);
        sockAide.flush();
      }
    }

    /**
     * 执行其他程序
     **/
    public static function execute(cmd:String, show:Boolean=true):void
    {
      openAide();
      sockAide.writeMultiByte(AIDE_EXECUTE + (show ? "S" : "H") + cmd, DEFAULT_CHARSET);
      sockAide.flush();
    }

    public static function test1():void
    {
      openAide();
      sockAide.writeMultiByte(AIDE_TEST, DEFAULT_CHARSET);
      sockAide.flush();
    }

    public static function openAidep():void
    {
      try
      {
        if (!sockAidep)
          sockAidep=new Socket();
        if (!sockAidep.connected)
        {
          execute("..\\aidep.exe");
          sockAidep.connect("localhost", AIDEP_PORT);
        }
      }
      catch (e:Error)
      {
      }
    }

    // 退出aidep.exe程序
    public static function closeAidep():void
    {
      if (sockAidep && sockAidep.connected)
      {
        write(sockAidep, "EXIT");
        sockAidep.close();
      }
      sockAidep = null;
    }

    protected static function write(sock:Socket, cmd:String, data:ByteArray=null):void
    {
      var r:ByteArray=new ByteArray();
      var l2:int=data ? data.length : 0;
      r.writeInt(8+cmd.length+l2);
      r.writeInt(cmd.length);
      r.writeMultiByte(cmd, DEFAULT_CHARSET);
      r.writeInt(l2);
      if (data)
        r.writeBytes(data);
      sock.writeBytes(r);
      sock.flush();
    }

    /**
     * GB2312
     * 范围: 0xA1A1 - 0xFEFE
     * 汉字范围: 0xB0A1 - 0xF7FE
     * GB2312规 定"对任意一个图形字符都采用两个字节表示，每个字节均采用七位编码表示"，第一个字节为"高字节"，第二个字节为"低字节"。
     * GB2312- 80包含了大部分常用的一、二级汉字，和9区的符号。
     * 该字符集是几乎所有的中文系统和国际化的软件都支持的中文字符集，这也是最基本的中文字符集。
     * 其编码 范围是高位0xA1-0xFE，低位也是0xA1-0xFE；汉字从0xB0A1开始，结束于0xF7FE。
     * GBK 亦采用双字节表示，总体编码范围为 8140-FEFE 之间，首字节在 81-FE 之间，尾字节在 40-FE 之间，剔除 0x7F。
     * BIG5
     * 范围: 0xA140 - 0xF9FE, 0xA1A1 - 0xF9FE
     * BIG5是台湾的IIIT1984年发明的,CNS 11643-1992( Chinese National Standard)是扩展版本,主要大家用的还是BIG5。
     * 每个字由两个字节组 成，其第一字节编码范围为0xA1-0xF9，第二字节编码范围为0x40-0x7E与0xA1-0xFE
     * GB18030
     * 编码是变长的，其二字节部分与GBK兼容；
     * 四字节部分是扩充的字形、字位，其编码范围是首字节0x81-0xFE、二字节0x30-0x39、三字节0x81-0xFE、四字节0x30-0x39。
     * 考虑到:
     * 1. 传输的数据中包含中文, 使用分隔符不严谨
     * 2. 使用长度、类型等额外信息, 太繁琐
     * 3. 基于1和2最好使用AMF3/XML/JSON之一, 但PowerBuilder没有提供比较好用的类库支持这些格式
     * 4. 最终确定使用US(0x1F)/,(0x2C)/;(0x3B)分隔元素, 使用RS(0x1E)分隔记录
     * 数字类型格式到小数点后4位(或整型), 日期格式化为YYYY-MM-DD, Boolean型格式化为T/F
     * Sample 1:
     * [code,period]
     * [branch.id,branch.useId,branch.name]
     * [sf.no,sf.departId,sf.departName,sf.date,sf.issueDate,sf.issueType,sf.summary,sf.issuerId,sf.issuerName,sf.comment],
     * [item1, item2, ..., itemn], // id, no, name(仅打印显示的项目)
     * [n,m,d11,d12,...,d1n,...,dm1,dm2,...,dmn] // 数据
     **/
    protected static function serialize(vars:Array):ByteArray
    {
      var nf:NumberFormatter=new NumberFormatter();
      nf.rounding=NumberBaseRoundType.NEAREST;
      nf.precision=2;
      nf.useThousandsSeparator=false;
      var wi:Function=function(_b:ByteArray, _i:int):void
        {
          _b.writeMultiByte(_i+"", DEFAULT_CHARSET);
        };
      var wn:Function=function(_b:ByteArray, _n:Number):void
        {
          _b.writeMultiByte(nf.format(_n), DEFAULT_CHARSET);
        };
      var wb:Function=function(_b:ByteArray, _bb:Boolean):void
        {
          _b.writeMultiByte(_bb ? "T" : "F", DEFAULT_CHARSET);
        };
      var wd:Function=function(_b:ByteArray, _d:Date):void
        {
          _b.writeMultiByte(DateUtil.formatDate(_d), DEFAULT_CHARSET);
        };
      var wa:Function=function(_b:ByteArray, _arr:Array, _deli:uint=0):void
        {
          var _i:int, _obj:Object;
          if (_deli == 0) _deli = US;
          for (_i=0; _i < _arr.length; _i++)
          {
            _obj=_arr[_i];
            if (_obj is int) wi(_b, _obj as int);
            else if (_obj is Number) wn(_b, _obj as Number);
            else if (_obj is Boolean) wb(_b, _obj as Boolean);
            else if (_obj is Date) wd(_b, _obj as Date);
            else if (_obj is Array) wa(_b, _obj as Array, US);
            else if (_obj is String) _b.writeMultiByte(_obj as String, DEFAULT_CHARSET);
            _b.writeByte(_deli);
          }
        };
      var b:ByteArray=new ByteArray();
      wa(b, vars, RS);
      return b;
    }

    public static function print(... vars):void
    {
      openAidep();
      var arr:Array=[], i:int=0;
      for (i=0; i < vars.length; i++)
        arr.push(vars[i]);
      write(sockAidep, "PRINT", serialize(arr));
    }

    public static function preview(... vars):void
    {
      openAidep();
      var arr:Array=[], i:int=0;
      for (i=0; i < vars.length; i++)
        arr.push(vars[i]);
      write(sockAidep, "PRIVIEW", serialize(arr));
    }

    public static function excel(... vars):void
    {
      openAidep();
      var arr:Array=[], i:int=0;
      for (i=0; i < vars.length; i++)
        arr.push(vars[i]);
      write(sockAidep, "EXCEL", serialize(arr));
    }

    public static function test2():void
    {
      openAidep();
      sockAidep.writeMultiByte("Hello World!\n打印中文文本!", DEFAULT_CHARSET);
      sockAidep.flush();
    }
  }
}