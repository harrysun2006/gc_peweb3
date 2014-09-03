package com.gc.validators
{
  import com.gc.util.CommonUtil;

  import mx.validators.ValidationResult;
  import mx.validators.Validator;

  public class FormulaValidator extends Validator
  {
    private var _rv:Object;
    private var _error:Object;

    public function FormulaValidator(rv:Object)
    {
      _rv=rv;
      _error=rv.error ? rv.error : new Object();
    }

    override protected function doValidation(value:Object):Array
    {
      var results:Array=[];
      var result:ValidationResult=validateFormula(value);
      if (result)
        results.push(result);
      return results;
    }

    private function validateFormula(value:Object):ValidationResult
    {
      var d:Object=source;
      if (d && d.no && _error.hasOwnProperty(d.no))
      {
        return new ValidationResult(true, d.no, "errorFormula", _error[d.no]);
      }
      return null;
    }
  }
}