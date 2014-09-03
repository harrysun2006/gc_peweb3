package com.gc.util
{

  public class MathUtils
  {
    public static function random(min:Number, max:Number):Number
    {
      return Number(Math.floor(Math.random() * (max - min + 1)) + min);
    }
  }
}