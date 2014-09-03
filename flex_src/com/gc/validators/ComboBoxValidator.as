package com.gc.validators
{
  import mx.controls.ComboBox;
  import mx.validators.ValidationResult;
  import mx.validators.Validator;

  import com.gc.util.CommonUtil;

  public class ComboBoxValidator extends Validator
  {
    public function ComboBoxValidator()
    {
    }

    public static function validateItem(validator:ComboBoxValidator, value:Object):Array
    {
      var results:Array = [];
      var combo:ComboBox=validator.combo;
      var list:Object=combo.dataProvider;
      var key:String=validator.key;
      var find:int=CommonUtil.indexOfKey(list, value, key);
      if (find < 0)
      {
        results.push(new ValidationResult(true, null, "notInList", validator.notInListError));
      }
      else
      {
        combo.selectedItem=list.getItemAt(find);
      }
      return results;
    }

    private var _notInListError:String;

    public function get notInListError():String
    {
      return _notInListError;
    }

    public function set notInListError(value:String):void
    {
      _notInListError = value != null ? value : resourceManager.getString("validators", "notInListError");
    }

    private var _combo:ComboBox;

    public function get combo():ComboBox
    {
      return _combo;
    }

    public function set combo(value:ComboBox):void
    {
      _combo=value;
    }

    private var _key:String="label";

    public function get key():String
    {
      return _key;
    }

    public function set key(value:String):void
    {
      _key=value;
    }

    override protected function doValidation(value:Object):Array
    {
      var results:Array = super.doValidation(value);

      // Return if there are errors
      // or if the required property is set to false and length is 0.
      var val:String = value ? String(value) : "";
      if (results.length > 0 || ((val.length == 0) && !required))
        return results;
      else
        return validateItem(this, value);
    }
  }
}