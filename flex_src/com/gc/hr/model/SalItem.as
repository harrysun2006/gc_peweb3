package com.gc.hr.model
{
  import bee.eval.Ident;
  import bee.eval.ParseError;
  import bee.eval.Util;
  import bee.eval.ast.CallExpression;
  import bee.eval.ast.IdentExpression;

  import com.gc.Constants;
  import com.gc.common.model.Branch;
  import com.gc.hr.controller.SalaryController;
  import com.gc.util.CommonUtil;
  import com.gc.util.ExprUtil;
  import com.gc.util.LangUtil;

  import mx.collections.ArrayCollection;

  [RemoteClass(alias="com.gc.hr.po.SalItem")]
  [Bindable]
  public dynamic class SalItem
  {
    public var id:int;
    public var branch:Branch;
    public var no:String;
    public var name:String;
    public var onDate:Date;
    public var downDate:Date;
    public var accountDebit:String;
    public var accountCredit:String;
    public var flag:int;
    public var type:String;
    public var print:String;
    private var _formula:String;
    public var editType:String;
    public var comment:String;

    public var selected:Boolean;

    public static const FLAG_POS:Object={value:1, name:"pos", label:LangUtil.getString("gcc_hr", "salItem.flag.pos")};
    public static const FLAG_NEG:Object={value:-1, name:"neg", label:LangUtil.getString("gcc_hr", "salItem.flag.neg")};
    public static const FLAG_ZERO:Object={value:0, name:"zero", label:LangUtil.getString("gcc_hr", "salItem.flag.zero")};
    public static const FLAG_LIST:Array=[FLAG_POS, FLAG_NEG, FLAG_ZERO];

    public static const TYPE_WG:Object={value:"WG", name:"wg", label:LangUtil.getString("gcc_hr", "salItem.type.wg")};
    public static const TYPE_SG:Object={value:"SG", name:"sg", label:LangUtil.getString("gcc_hr", "salItem.type.sg")};
    public static const TYPE_WF:Object={value:"WF", name:"wf", label:LangUtil.getString("gcc_hr", "salItem.type.wf")};
    public static const TYPE_DK:Object={value:"DK", name:"dk", label:LangUtil.getString("gcc_hr", "salItem.type.sg")};
    public static const TYPE_PZ:Object={value:"PZ", name:"pz", label:LangUtil.getString("gcc_hr", "salItem.type.sg")};
    public static const TYPE_LIST:Array=[TYPE_WG, TYPE_SG, TYPE_WF, TYPE_DK, TYPE_PZ];

    public static const PRINT_V:Object={value:"0", label:LangUtil.getString("gcc_hr", "salItem.print.view")};
    public static const PRINT_P:Object={value:"1", label:LangUtil.getString("gcc_hr", "salItem.print.print")};
    public static const PRINT_LIST:Array=[PRINT_V, PRINT_P];

    public static const ETYPE_0:Object={value:"0", name:"0", label:LangUtil.getString("gcc_hr", "salItem.etype.0")};
    public static const ETYPE_1:Object={value:"1", name:"1", label:LangUtil.getString("gcc_hr", "salItem.etype.1")};
    public static const ETYPE_LIST:Array=[ETYPE_0, ETYPE_1];

    public static const STATUS_ENABLE:Object={value:true, label:LangUtil.getString("gcc", "enable")};
    public static const STATUS_DISABLE:Object={value:false, label:LangUtil.getString("gcc", "disable")};
    public static const STATUS_LIST:Array=[STATUS_ENABLE, STATUS_DISABLE];

    public function SalItem()
    {
      onDate=Constants.MIN_DATE;
      downDate=Constants.MAX_DATE;
      print=PRINT_P.value;
      flag=FLAG_POS.value;
      type=TYPE_WG.value;
      editType=ETYPE_1.value;
    }

    public function get uid():String
    {
      return "si#" + id;
    }

    public function get branchId():int
    {
      return branch ? branch.id : 0;
    }

    public function get status():Object
    {
      var value:Boolean=(downDate >= Constants.MAX_DATE);
      return value ? STATUS_ENABLE : STATUS_DISABLE;
    }

    public function set status(value:Object):void
    {
      if (value && value.value)
      {
        downDate=Constants.MAX_DATE;
      }
      else
      {
        var today:Date=new Date();
        downDate=new Date(today.fullYear, today.month, today.date+1);
      }
    }

    public function get flag$():Object
    {
      return CommonUtil.findObject(FLAG_LIST, flag);
    }

    public function set flag$(value:Object):void
    {
      flag=value && value.value ? value.value : flag;
    }

    public function get type$():Object
    {
      return CommonUtil.findObject(TYPE_LIST, type);
    }

    public function set type$(value:Object):void
    {
      type=value && value.value ? value.value : type;
    }

    public function get print$():Object
    {
      return CommonUtil.findObject(PRINT_LIST, print);
    }

    public function set print$(value:Object):void
    {
      print=value && value.value ? value.value : print;
    }

    public function get printable():Boolean
    {
      return print == PRINT_P.value;
    }

    public function get etype$():Object
    {
      return CommonUtil.findObject(ETYPE_LIST, editType);
    }

    public function set etype$(value:Object):void
    {
      editType=value && value.value ? value.value : editType;
    }

    public function get formula():String
    {
      return _formula;
    }

    public function set formula(value:String):void
    {
      _formula=CommonUtil.trim(value);
      // editType=_formula ? ETYPE_0.value : ETYPE_1.value;
    }

    public function get editable():Boolean
    {
      // return (editType == ETYPE_1.value && e1);
      return (editType == ETYPE_1.value);
    }

    private function get e1():Boolean
    {
      return !_formula ? true
        : _formula.indexOf("link") < 0 ? true
        : false;
    }

    public function get computable():Boolean
    {
      return _formula && _formula != "";
    }

    public function get label():String
    {
      return name + "[" + no + "]";
    }

    public function set label(s:String):void
    {
    }

    public function toString():String
    {
      return "SalItem{id=" + id + ", belong=" + branchId + ", name=" + name + "}";
    }

    private static var _map:Object=null;

    private static function get map():Object
    {
      if (!_map)
      {
        _map=new Object();
        var si:SalItem;
        for each (si in SalaryController.salItemList)
          _map[si.no]=si;
      }
      return _map;
    }

    public static function refresh():void
    {
      _map=null;
    }

    public static function getItemByNo(no:String):SalItem
    {
      return map[no];
    }

    public static function getNameByNo(no:String):String
    {
      return map[no] ? map[no].name : "";
    }

// -------------------------------- Validate & Evaluate --------------------------------

    private static const xx_avg:Function=function(obj:Object, id:Ident):void
      {
        var call:CallExpression;
        var f1:Function=function(id:Ident):Boolean{return id && xxId(id.id);};
        var f2:Function=function(id:Ident):Boolean{return id && id.isV;};
        for each (call in id.calls)
        {
          if (!f1(call.args[0].id)) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.function.args.type1", [obj.id, id.id, 1]);
          else if (!f2(call.args[1].id)) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.function.args.type2", [obj.id, id.id, 2]);
          if (obj.error) return;
        }
      };

    private static const xx_link:Function=function(obj:Object, id:Ident):void
      {
        var call:CallExpression;
        var f1:Function=function(id:Ident):Boolean{return id && xxId(id.id);};
        for each (call in id.calls)
        {
          if (!f1(call.args[0].id)) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.function.args.type1", [obj.id, id.id, 1]);
          if (obj.error) return;
        }
      };

    private static const XX_FUNCS:Object={avg:xx_avg, link:xx_link};

    public static function xxFunc(f:String):Boolean
    {
      return XX_FUNCS[f];
    }

    public static function xxId(id:String):Boolean
    {
      var reg:RegExp=/xx\d{3}/ig;
      return reg.test(id);
    }

    // 检查公式引用项目的依赖性
    protected static const EVALF_ITEMS1:Function=function(exprs:Object, obj:Object):Boolean
      {
        if (obj.miss && obj.miss.length > 0)
        {
          var n:String, s:String="", id:Ident;
          for each (n in obj.miss) 
          {
            id=obj.dep[n];
            if (id.isV) s+=n+"("+getNameByNo(n)+"), ";
          }
          if (s.length > 0) obj.error=LangUtil.getString("gcc_hr", "salTemplate.error.missing.items", [getNameByNo(obj.id), obj.expr, s.substr(0, s.length-2)]);
        }
        return false;
      };

    // 2011-1-13讨论结果: 不检查公式引用项目的依赖性, 缺失项按0处理
    protected static const EVALF_ITEMS2:Function=function(exprs:Object, obj:Object):Boolean
      {
        if (obj.miss && obj.miss.length > 0)
          for each (var n:String in obj.miss) exprs[n]=0;
        obj.miss=[];
        return false;
      };

    public static const EVALF_ITEMS:Function=EVALF_ITEMS2;

    /**
     * 1. 判断xx项目及xx函数的合法性
     * 2. 设置obj.sylla.xx的值: X27=avg(xx001,X19)+avg(xx003,X26)
     *    obj.sylla.xx={xx001:{fun:"avg", ino:"X27", args:["X19"], value:null}, xx003:{fun:"avg", ino:"X27", args:["X26"], value:null}}
     * 3. 设置obj.sylla.xxr的值:
     *    obj.sylla.xxr={X27:[xx001,xx003]}
     **/
    private static const EVALF_XX:Function=function(exprs:Object, obj:Object):Boolean
      {
        var n:String, id1:Ident, id2:Ident, call:CallExpression, ide:IdentExpression, args:Array;
        if (!obj.sylla.xx) obj.sylla.xx={};
        if (!obj.sylla.xxq) obj.sylla.xxq=[];
        if (!obj.sylla.xxr) obj.sylla.xxr={};
        for (n in obj.dep)
        {
          id1=obj.dep[n];
          if (!XX_FUNCS[n]) continue;
          for each (call in id1.calls)
          {
            args=[];
            for each (ide in call.args)
            {
              id2=ide.id;
              args.push(id2.id);
              if (xxId(id2.id))
              {
                if (id2.count > 1) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.xx1", [obj.id, id2.id]);
                else if (obj.sylla.xx[id2.id]) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.xx2", [obj.id, id2.id]);
                obj.sylla.xx[id2.id]={fun:n, ino:obj.id, args:args, value:null};
                if (!obj.sylla.xxr[obj.id]) obj.sylla.xxr[obj.id]=[];
                obj.sylla.xxr[obj.id].push(id2.id);
              }
            }
          }
          XX_FUNCS[n](obj, id1);
        }
        return false;
      }

    /**
     * 检验公式的一般错误
     **/
    private static const EVALF_GENERAL:Function=function(exprs:Object, obj:Object):Boolean
      {
        var n:String, s1:String="", s2:String="", id:Ident;
        if (obj.eobj is ParseError)
        {
          if (obj.eid == ParseError.NOT_FUNCTION) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.not.function", [obj.id, obj.edata]);
          else if (obj.eid == ParseError.UNEXPECTED_TOKEN) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.unexpected.token", [obj.id, obj.edata]);
          else if (obj.eid == ParseError.MISSING_TOKEN_RIGHTPAR) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.missing.token.rightpar", [obj.id]);
          else if (obj.eid == ParseError.MISSING_TOKEN_LEFTPAR) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.missing.token.leftpar", [obj.id]);
        }
        else if (obj.eobj is Error)
        {
          obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.general", [obj.id, obj.edata]);
        }
        else
        {
          if (obj.eid == Util.ERROR_WRONG_IDS)
          {
            for each (id in obj.edata) s1+=id.id+", ";
            if (s1.length > 0) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.wrong.ids", [obj.id, s1.substr(0, s1.length-2)]);
          }
          else if (obj.eid == Util.ERROR_RECURSIVE) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.recursive", [obj.id]);

          else if (obj.eid == Util.ERROR_INVALID_EXPRS) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.invalid");
          else if (obj.eid == Util.ERROR_NOT_DEFINED) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.not.defined", [obj.id]);
          else if (obj.eid == Util.ERROR_NOT_NUMBER) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.not.number", [obj.id]);
          else if (obj.eid == Util.ERROR_DIVIDE_ZERO) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.divide.zero", [obj.id]);
        }
        if (obj.miss && obj.miss.length > 0)
        {
          for each (n in obj.miss) 
          {
            id=obj.dep[n];
            if (id && id.isF) s2+=n+", ";
            else s1+=n+", ";
          }
          if (s1.length > 0) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.missing.vars", [obj.id, s1.substr(0, s1.length-2)]);
          else if (s2.length > 0) obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.missing.funcs", [obj.id, s2.substr(0, s2.length-2)]);
        }
        return false;
      };

    /**
     * 检验公式的函数参数个数错误
     **/
    private static const EVALF_FUN:Function=function(exprs:Object, obj:Object):Boolean
      {
        var n:String;
        var id:Ident;
        for (n in obj.dep)
        {
          id=obj.dep[n];
          if (FUN_ARG_COUNTS.hasOwnProperty(n))
          {
            if (!id.calls || id.calls.length <= 0 || !id.isF)
              obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.function.format", [obj.id, id.id]);
            else if (!id.calls[0].args || FUN_ARG_COUNTS[n] != id.calls[0].args.length)
              obj.error=LangUtil.getString("gcc_hr", "salItem.formula.error.function.args.length", [obj.id, id.id, FUN_ARG_COUNTS[n], id.calls[0].args.length]);
          }
        }
        return false;
      }

    // 定义公式中支持的常量及函数
    public static const EVAL_EXPRS:Object={
      // constants defined in Math: E=1+1/1!+1/2!+1/3!+1/4!+...+1/n!
        _E:Math.E, _LN10:Math.LN10, _LN2:Math.LN2, _LOG10E:Math.LOG10E, _LOG2E:Math.LOG2E, 
        _PI:Math.PI, _SQRT2R:Math.SQRT1_2, _SQRT2:Math.SQRT2, 
        // functions defined in Math
        _abs:Math.abs, _acos:Math.acos, _asin:Math.asin, _atan:Math.atan, _atan2:Math.atan2, 
        _ceil:Math.ceil, _cos:Math.cos, _exp:Math.exp, _floor:Math.floor, _log:Math.log,
        _max:Math.max, _min:Math.min, _pow:Math.pow, _random:Math.random, _round:Math.round, 
        _sin:Math.sin, _sqrt:Math.sqrt, _tan:Math.tan,
        // user defined functions
        sig:sig, sig2:sig2, round:round, avg:avg, link:link};

    // 定义公式支持的函数的参数个数
    private static const FUN_ARG_COUNTS:Object={
        _abs:1, _acos:1, _asin:1, _atan:1, _atan2:2, _ceil:1, _cos:1, _exp:1, _floor:1, _log:1,
        _pow:2, _random:0, _round:1, _sin:1, _sqrt:1, _tan:1, 
        sig:1, sig2:4, round:2, avg:2, link:1};

    public static function getExprs():Object
    {
      var exprs:Object=new Object();
      for (var n:String in EVAL_EXPRS)
        exprs[n]=EVAL_EXPRS[n];
      return exprs;
    }

    /**
     * 返回公式检验回调函数,额外的错误信息处理:错误提示,自定义错误
     **/
    private static function getCallback(callbacks:Array=null):Function
    {
      var f6:Function=function(exprs:Object, obj:Object):Boolean
        {
          var n:String, mm:Array=[], xx:Array=[];
          if (obj.miss && obj.miss.length > 0)
          {
            for each (n in obj.miss)
            {
              if (xxId(n)) xx.push(n);
              else mm.push(n);
            }
            obj.miss=mm;
            if (xx.length > 0)
            {
              if (!obj.sylla.xxq) obj.sylla.xxq={};
              obj.sylla.xxq[obj.id]=xx;
            }
          }
          return false;
        };
      callbacks=callbacks ? callbacks : [];
      callbacks.unshift(f6);
      callbacks.push(EVALF_GENERAL, EVALF_FUN, EVALF_XX);
      var ff:Function=function(exprs:Object, obj:Object):Boolean
        {
          var callback:Function, b:Boolean;
          for each (callback in callbacks)
          {
            b=callback(exprs, obj);
            if (obj.error) return b;
          }
          return true;
        };
      return ff;
    }

    /**
     * 验证所有项目公式的合法性
     * filter: 带公式项目过滤函数
     *   true - rr.comp中包含所有项目公式(所有公式项目将按公式计算值)
     *   false - rr.comp中不含所有项目公式(所有公式项目将取现有值)
     *   函数 - 返回Boolean型, 意义同上
     **/
    public static function validate(sis:Object, callbacks:Array=null, filter:Object=true):Object
    {
      var si:Object;
      var exprs:Object=getExprs();
      for each (si in sis)
      {
        if ((filter == true || (filter is Function && filter(si))) && si.formula)
          exprs[si.no]=si.formula;
        else
          exprs[si.no]=Ident.V;
      }
      var rr:Object=ExprUtil.validate(exprs, getCallback(callbacks));
      var n:String, xx:Array=[];
      if (!rr.sylla.xx)
        rr.sylla.xx={};
      if (!rr.sylla.xxq)
        rr.sylla.xxq=[];
      if (!rr.sylla.xxr)
        rr.sylla.xxr={};
      for each (n in rr.queue)
      {
        if (rr.sylla.xxq[n])
          xx=xx.concat(rr.sylla.xxq[n]);
      }
      rr.sylla.xxq=xx;
      return rr;
    }

    // 公式求值
    public static function evaluate(exprs:Object, context:Object, callbacks:Array=null):Object
    {
      return ExprUtil.evaluate(exprs, context, getCallback(callbacks));
    }

    private static const XX_ARGS:Object={avg:xxa_avg, link:xxa_link};

    public static function xxArg(id:Ident, rr:Object, pp:Object):void
    {
      if (XX_ARGS[id.id])
        XX_ARGS[id.id](id, rr, pp);
    }

    /**
     * return signal of x.
     * if (x > 50) y = a;
     * else if (x < 50) y = b;
     * else y = c;
     * ==>
     * x1=sig(x-50)
     * y=(x1+1)*x1/2*a+(x1-1)*x1/2*b+(1-x1*x1)*c
     **/
    public static function sig(x:Number):Number
    {
      return x > 0 ? 1 : x < 0 ? -1 : 0;
    }

    public static function sig2(x:Number, a:Number, b:Number, c:Number):Number
    {
      return x > 0 ? a : x < 0 ? b : c;
    }

    public static function round(x:Number, d:uint=2):Number
    {
      var d10:uint=1, i:uint=0;
      for (i = 0; i < d; i++)
        d10 *= 10;
      return x ? Math.round(x*d10)/d10 : 0;
    }

    private static function xxa_avg(id:Ident, rr:Object, pp:Object):void
    {
      var n:String, call:CallExpression, ide:IdentExpression, id2:Ident, args:Object;
      var tt:Object=pp["#total"]; // {si.no: total1, ...}
      for each (call in id.calls)
      {
        args={"#fun":id.id};
        for each (ide in call.args)
        {
          id2=ide.id;
          if (id2 && id2.isV)
          {
            if (rr.sylla.xx.hasOwnProperty(id2.id)) // xx001, ...的值
            {
              args["#amount"]=rr.sylla.xx[id2.id] ? rr.sylla.xx[id2.id].value : NaN; // xx项的用户输入值: 10000
              args["#xxid"]=id2.id; // xx项编号: xx001
              id2.value=rr.expr[id2.id]=args;
            }
            else // X01, ...项目依赖项的参数及值
            {
              args["#vno"]=id2.id; // 项目编号: X19
              args["#total"]=tt[id2.id]; // 项目和: 115
            }
          }
        }
      }
    }

    /**
     * avg函数求值
     *  - obj对象格式为: {#fun:"avg", #amount:10000, #xxid:"xx001", #vno:"X19", #total:115, #dd:{...}}
     *  - x为对应的X19的值, == #dd[X19]
     **/
    public static function avg(obj:Object, x:Number):Number
    {
      var amount:Number=obj && obj.hasOwnProperty("#amount") ? Number(obj["#amount"]) : NaN;
      var total:Number=obj && obj.hasOwnProperty("#total") ? Number(obj["#total"]) : NaN;
      return (isNaN(amount) || isNaN(total)) ? NaN : total == 0 ? NaN : amount*x/total;
    }

    public static function avg2(obj:Object, x:Number):Number
    {
      return 100;
    }

    private static function xxa_link(id:Ident, rr:Object, pp:Object):void
    {
      var n:String, call:CallExpression, ide:IdentExpression, id2:Ident, args:Object, xx:Object;
      var fds:ArrayCollection;
      for each (call in id.calls)
      {
        args={"#fun":id.id};
        for each (ide in call.args)
        {
          id2=ide.id;
          if (id2 && id2.isV && rr.sylla.xx.hasOwnProperty(id2.id)) // 只有xx001, ...项
          {
            xx=rr.sylla.xx[id2.id] ? rr.sylla.xx[id2.id].value : null;
            args["#ref"]=xx ? xx["#ref"] : null; // 引用的凭证明细对象: {sfd#p1:1, sfd#p2:2, ...}
            args["#xxid"]=id2.id; // xx项编号: xx002
            args["#fact"]=xx ? xx["#fact"] : null; // 凭证对象
            args["#item"]=xx ? xx["#item"] : null; // 引用项目对象
            id2.value=rr.expr[id2.id]=args;
          }
        }
      }
    }

    /**
     * link函数求值
     *  - obj对象格式为: {#fun:"avg", #ref:{...}, #xxid:"xx002", #bid:2, #no:"D10-000006", #vno:"X11", #vid:11, #dd:{...}}
     **/
    public static function link(obj:Object):Number
    {
      var ref:Object=obj ? obj["#ref"] : null;
      var dd:Object=obj ? obj["#dd"] : null;
      return ref && dd && ref.hasOwnProperty("sfd#"+dd.person.id) ? ref["sfd#"+dd.person.id] : 0;
    }
  }
}