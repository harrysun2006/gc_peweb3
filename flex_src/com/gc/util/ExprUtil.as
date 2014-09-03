package com.gc.util
{
  import bee.eval.Ident;
  import bee.eval.ParseError;
  import bee.eval.Util;

  import mx.resources.IResourceManager;
  import mx.resources.ResourceManager;

  public class ExprUtil
  {

    private static function singleton():void
    {
    }

    public function ExprUtil(caller:Function=null)
    {
      if (caller != singleton)
        throw new Error("ExprUtil is a non-instance class!!!");
    }

    /**
     * validate if the expr list(exprs) is valid or not.
     * @exprs: Object, e.g. {a:V, b:V, c:V, d:V, abs:F, x:"a*(b+3)", y:"c/(d-7)", z:"x+y"}
     *  or    Object, e.g. {a:true, b:true, c:true, d:true, abs:true, x:"a*(b+3)", y:"c/(d-7)", z:"x+y"}
     *  or    String, e.g. "(3+3)*(7-3)/(1+2+1)"
     * @callback: used to format error message, syntax: function(obj:Object):Boolean
     *  - obj: {id:id, expr:expr, error:error, eobj:eobj, eid:eid, edata:edata, dep:dep, miss:miss, comp:comp}
     *    -- id: String, id at left of =
     *    -- expr: String, expression at right of =
     *    -- error: String, return formatted error message
     *    -- eobj: Object, the error object
     *    -- eid: int, the error id
     *    -- edata: Object, error related object
     *    -- dep: Object, {a:Ident, b:Ident, ...}, ids this expr depends on
     *    -- miss: Array, ["a", "b", ...], ids this expr required but missing
     *    -- comp: Compiler, contains parsed expression object
     *    -- sylla: Object, container for the validate session
     *  - return true, then continue to parse other exprs; false to break.
     * @return: Object {expr:{a:.., b:.., y:.., z:..}, error:{y:.., z:..}, dep:{y:{}, z:{}},
     * 		valid:true, queue:["a","b","c","d","x","y","z"], miss:{y:["abs"], z:["abs"]}
     * 		flag:singleton, comp:{y:.., z:..}, sylla:{}}
     *  - expr: Object, all exprs input from exprs
     *  - error: Object, parse errors for expressions
     *  - dep: Object, symbols for expression to depend on
     *  - valid: Boolean, if error is null then true, otherwise false
     *  - queue: Array, orders to evaluation expressions
     *  - miss: Object, missing vars or functions for expression
     *  - flag: Function, indicate to this object(r) is returned by validate, used in evaluate
     *  - comp: Object, compiler for expression for quick evaluation later
     *  - sylla: Object, container for the validate session
     **/
    public static function validate(exprs:Object, callback:Function=null):Object
    {
      var rm:IResourceManager=ResourceManager.getInstance();
      var ff:Function=function(obj:Object):Boolean
        {
          var n:String, s1:String="", s2:String="", id:Ident;
          if (callback is Function)
          {
            var b:Boolean=callback(exprs, obj);
            if (obj.error) return b;
          }
          if (obj.eobj is ParseError)
          {
            if (obj.eid == ParseError.NOT_FUNCTION) obj.error=rm.getString("gcc", "eval.error.not.function", [obj.id, obj.expr, obj.edata]);
            else if (obj.eid == ParseError.UNEXPECTED_TOKEN) obj.error=rm.getString("gcc", "eval.error.unexpected.token", [obj.id, obj.expr, obj.edata]);
            else if (obj.eid == ParseError.MISSING_TOKEN_RIGHTPAR) obj.error=rm.getString("gcc", "eval.error.missing.token.rightpar", [obj.id, obj.expr]);
            else if (obj.eid == ParseError.MISSING_TOKEN_LEFTPAR) obj.error=rm.getString("gcc", "eval.error.missing.token.leftpar", [obj.id, obj.expr]);
          }
          else if (obj.eobj is Error)
          {
            obj.error=rm.getString("gcc", "eval.error.general", [obj.id, obj.expr, obj.edata]);
          }
          else
          {
            if (obj.eid == Util.ERROR_WRONG_IDS)
            {
              for each (id in obj.edata) s1+=id.id+", ";
              if (s1.length > 0) obj.error=rm.getString("gcc", "eval.error.wrong.ids", [obj.id, obj.expr, s1.substr(0, s1.length-2)]);
            }
            else if (obj.eid == Util.ERROR_RECURSIVE) obj.error=rm.getString("gcc", "eval.error.recursive", [obj.id, obj.expr]);
          }
          if (obj.miss.length > 0)
          {
            for each (n in obj.miss) 
            {
              id=obj.dep[n];
              if (id && id.isF) s2+=n+", ";
              else s1+=n+", ";
            }
            if (s1.length > 0) obj.error=rm.getString("gcc", "eval.error.missing.vars", [obj.id, obj.expr, s1.substr(0, s1.length-2)]);
            else if (s2.length > 0) obj.error=rm.getString("gcc", "eval.error.missing.funcs", [obj.id, obj.expr, s2.substr(0, s2.length-2)]);
          }
          return false;
        };
      return Util.validate(exprs, ff);
    }

    /**
     * evaludate the expr list(exprs).
     * @exprs: Object, returned by validate function
     *  or    Object, e.g. {a:V, b:V, c:V, d:V, abs:F, x:"a*(b+3)", y:"c/(d-7)", z:"x+y"}
     *  or    Object, e.g. {a:true, b:true, c:true, d:true, abs:true, x:"a*(b+3)", y:"c/(d-7)", z:"x+y"}
     *  or    String, e.g. "(3+3)*(7-3)/(1+2+1)"
     * @context: Object, context object used in evaludation, e.g. {a:3, b:5, c:7, d:9, abs:Math.abs}
     * @callback: used to format error message, syntax: function(obj:Object):Boolean
     *  - obj: {id:id, expr:expr, error:error, eobj:eobj, eid:eid, edata:edata}
     *    -- id: String, id at left of =
     *    -- expr: String, expression at right of =
     *    -- error: String, return formatted error message
     *    -- eobj: Object, the error object
     *    -- eid: int, the error id
     *    -- edata: Object, error related object
     *    -- sylla: Object, container for the evaluate session
     *  - return true, then continue to parse other exprs; false to break.
     * @return: Object {value:{a:.., b:.., y:.., z:..}, error:{y:.., z:..}, valid:true, sylla:{}}
     *  - value: Object, all evaluated values for all expressions in exprs.expr
     *  - error: Object, evaluation errors for expressions
     *  - valid: Boolean, if error is null then true, otherwise false
     *  - sylla: Object, container for the evaluate session
     **/
    public static function evaluate(exprs:Object, context:Object=null, callback:Function=null):Object
    {
      var rm:IResourceManager=ResourceManager.getInstance();
      var ff:Function=function(obj:Object):Boolean
        {
          if (callback is Function)
          {
            var b:Boolean=callback(exprs, obj);
            if (obj.error) return b;
          }
          if (obj.eobj is Error) obj.error=rm.getString("gcc", "eval.error.general", [obj.id, obj.expr, obj.edata]);
          else
          {
            if (obj.eid == Util.ERROR_INVALID_EXPRS) obj.error=rm.getString("gcc", "eval.error.invalid");
            else if (obj.eid == Util.ERROR_NOT_DEFINED) obj.error=rm.getString("gcc", "eval.error.not.defined", [obj.id]);
            else if (obj.eid == Util.ERROR_NOT_NUMBER) obj.error=rm.getString("gcc", "eval.error.not.number", [obj.id]);
            else if (obj.eid == Util.ERROR_DIVIDE_ZERO) obj.error=rm.getString("gcc", "eval.error.divide.zero", [obj.id, obj.expr]);
          }
          return true;
        }
      return Util.evaluate(exprs, context, ff);
    }

  }
}