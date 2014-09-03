package com.gc.util
{
  import mx.collections.ArrayCollection;

  public class ArrayUtil
  {

    private static function singleton():void
    {
    }

    public function ArrayUtil(caller:Function=null)
    {
      if (caller != singleton)
        throw new Error("ArrayUtil is a non-instance class!!!");
    }

    public static function getValues(arr:Array, key:String, sep:String=","):String
    {
      var arrKeys:Array=new Array();
      for (var i:uint=0; i < arr.length; i++)
      {
        if (arr[i].hasOwnProperty(key))
          arrKeys.push(arr[i][key]);
      }
      var str:String=arrKeys.join(sep);
      return str;
    }

    /**
     * will loop throught the contents of an array and attempt to compare the value in a key within the array
     * to the value provided
     */
    public static function contains(arr:Array, key:String, value:String, partial:Boolean=false, caseSensitive:Boolean=true):Boolean
    {
      var tempValue:String;
      var o:Object;
      if (!caseSensitive)
        value=value.toLowerCase();
      for (var i:int=0; i < arr.length; i++)
      {
        o=arr[i];
        var keys:Array = key.split(".");
        var obj:Object = o;
        for (var j:int = 0; j <keys.length; j++)
        {
          if (!obj.hasOwnProperty(keys[j]) || obj[keys[j]] == null)
            return false;
          obj = obj[keys[j]];
        }
        tempValue = obj as String;

        if (!caseSensitive)
          tempValue=tempValue.toLowerCase();
        /* temp is the same as string */
        if (tempValue == value)
          return true;
        /* partial search is allowed */
        if (partial && tempValue.search(value) >= 0)
          return true;
      }
      return false;
    }

    /**
     * function designed to find an object with a matching key and return the position within the array
     */
    public static function index(arr:Array, key:String, value:String, caseSensitive:Boolean=true, startIndex:int=0):int
    {
      var o:Object;
      var tempValue:String;
      /** If this is not case sensitive than will toggle the casing to lowerCase */
      if (!caseSensitive)
        value=value.toLowerCase();
      for (var i:int=startIndex; i < arr.length; i++)
      {
        o=arr[i];
        tempValue=String((o.hasOwnProperty(key) ? o[key] : ""));
        if (!caseSensitive)
          tempValue=tempValue.toLowerCase();
        /* temp is the same as string */
        if (tempValue == value)
          return i;
      }
      return -1;
    }

    public static function match(arr:Array, key:String, value:String, partial:Boolean=false):Array
    {
      var tempValue:String;
      var o:Object;
      var retArray:Array=new Array();
      for (var i:int=0; i < arr.length; i++)
      {
        o=arr[i];
        tempValue=String((o.hasOwnProperty(key) ? o[key] : ""));
        /* temp is the same as string */
        if (tempValue == value)
        {
          retArray.push(o);
        }
        else if (partial && tempValue.search(value) >= 0)
        {
          retArray.push(o);
        }
      }
      return retArray;
    }
  }
}