package com.gc.validators
{
  import mx.validators.ValidationResult;
  import mx.validators.Validator;

  public class ValidatorEx extends Validator
  {
    public var validator:Function;

    public function ValidatorEx(v:Function=null)
    {
      this.validator=v;
    }

    override protected function doValidation(value:Object):Array
    {
      var results:Array = super.doValidation(value);
      if (results.length <= 0)
      {
        var result:ValidationResult = (validator is Function) ? validator(value, source, property, subFields) : null;
        if (result)
        {
          results.push(result);
        }
      }
      return results;
    }
  }
}