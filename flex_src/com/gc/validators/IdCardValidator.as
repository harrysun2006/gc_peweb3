package com.gc.validators
{

  import mx.controls.DateField;
  import mx.utils.StringUtil;
  import mx.validators.ValidationResult;
  import mx.validators.Validator;

  import com.gc.util.LangUtil;

  public class IdCardValidator extends Validator
  {
    public function IdCardValidator()
    {
    }

    override protected function doValidation(value:Object):Array
    {
      var results:Array=[];
      var result:ValidationResult=validateId(value);
      if (result)
        results.push(result);
      return results;
    }

    /**
     * 15位身份证号码编码较为简单，从左到右其15位码依次为:
     * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
     * A A A A A A Y Y M M  D  D  N  N  S
     * 前六位AAAAAA是身份证编码对象的出生地编码，该号码可由国家统计局公布的相关标准中得到。
     * YY表示出生年的后两位，MM和DD表示出生月和日，不足两位的高位补0，NNS为顺序号，无法确定。
     * S为性别识别码，男性为奇数，女性为偶数。例如，一位于1965年2月16日出生在湖北省巴东县的女性的15位身份证号码为：
     * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
     * A A A A A A Y Y M M  D  D  N  N  S
     * 4 2 2 8 2 3 6 5 0 2  1  6  0  2  2
     * 目前程序验证:
     * 1. 15位是否全是数字
     * 2. 生日是否为合法日期
     **/
    private function validateId15(id:String):ValidationResult
    {
      var i:int;
      var c:String;
      for (i=0; i<15; i++)
      {
        c=id.charAt(i);
        if (c < "0" || c > "9")
          return new ValidationResult(true, null, "icv.error.format", LangUtil.getString("gcc", "icv.error.format"));
      }
      var d:Date=DateField.stringToDate(id.substr(6, 6), "YYMMDD");
      if (d == null)
        return new ValidationResult(true, null, "icv.error.birthday", LangUtil.getString("gcc", "icv.error.birthday"));
      return null;
    }

    /**
     * 18位身份证号码比15位身份证号码多出的3位中的两位用来补充2位的年份为4位，另外1位作为校验码存在。
     * 一项由国家质量技术监督局发布的标准对身份证号码的编码规则做了具体的规定。
     * 该标准为名《公民身份号码》，编号GB11643-1999，为国家强制标准。
     * 它代替了1989年公布的名为《社会保障号码》GB11643-1989国家强制标准，从1999年7月1日开始强制实施。
     * 该标准的内容介绍如下:
     * 1．标准适用范围：规定了公民身份号码的编码对象、号码的具体结构组成和表现形式，并规定每个编码对象将获得一个唯一的、终身不变的号码。
     * 2．编码对象：具有中国人民共和国国籍的公民。
     * 3．号码的结构组成和表现形式。
     * 该标准中对号码的构造组成和表现形式进行了如下的阐述：
     * 号码由17位本体码和1位校验码组成，这18位号码从左到右依次为：
     * 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18
     * A A A A A A Y Y Y Y  M  M  D  D  N  N  S  C
     * 前六位AAAAAA同样是地区编码，按照国家推荐标准GB/T2260的规定进行编码。
     * YYYY是扩充后的四位出生年码，MM和DD为出生月和日码，不足两位的仍然在高位补0。
     * NNS为无法确定的顺序码，其中S为性别识别码，同样将奇数分配给男性，偶数分配给女性。
     * 第18位校验位是对前17位进行如下的计算得到的。
     * 使用数组B[I]表示从左到右第I个数字，W[I]表示第I位上的加权因子，需要注意的是，为了方便讲述，这里的数组第一个是B[1]而不是计算机中的B[0]，W[I]也是同样。加权因子依次为：
     * 1 2 3  4 5 6 7 8 9 10 11 12 13 14 15 16 17
     * 7 9 10 5 8 4 2 1 6 3  7  9  10 5  8  4  2
     * 得到第18位的值C首先要利用这个加权因子计算出 的值，计算公式为：
     * 即，将前17位的数字与其对应的加权因子相乘，再将积相加，最后的和再除以11，取其余。
     * 除以11最后的余有11种可能，从0到10。通过余再做一个转换，即可得到相应的第18位上的校验码，余数和校验码的转换如下：
     * 余数                     : 0 1 2 3 4 5 6 7 8 9 10
     * 对应的校验码 : 1 0 X 9 8 7 6 5 4 3 2
     * 下面我们来计算一下一位湖北省襄樊市襄城区在1986年11月9日出生的男性的18位身份证号码，其顺序号假定为321（男性为奇数）。
     * 该身份证号前17位为：
     * 42068219861109321
     * 先计算前17位与加权因子的积的和除以11的余：余数为1则其对应的第18位校验码为0，该男性的18位身份证号码为:
     * 420682198611093210
     * 目前程序验证:
     * 1. 前17位是否全是数字
     * 2. 校验码是否正确
     * 3. 生日是否为合法日期
     **/
    private function validateId18(id:String):ValidationResult
    {
      var wi:Array=[7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1];
      var ai:Array=["1","0","X","9","8","7","6","5","4","3","2"];
      var i:int, sum:int=0;
      var c:String;
      for (i=0; i < id.length-1; i++)
      {
        c=id.charAt(i);
        if (c < "0" || c > "9")
          return new ValidationResult(true, null, "icv.error.format", LangUtil.getString("gcc", "icv.error.format"));
        sum+=Number(c)*wi[i];
      }
      if (id.charAt(17) != ai[sum%11])
        return new ValidationResult(true, null, "icv.error.checksum", LangUtil.getString("gcc", "icv.error.checksum"));
      var d:Date=DateField.stringToDate(id.substr(6, 8), "YYYYMMDD");
      if (d == null)
        return new ValidationResult(true, null, "icv.error.birthday", LangUtil.getString("gcc", "icv.error.birthday"));
      return null;
    }

    /**
     * 早期身份证由15位数字构成，这主要是在1980年以前发放的身份证，后来考虑到千年虫问题，因为15位的身份证号码只能
     * 为1900年1月1日到1999年12月31日出生的人编号，所以又增加了18位身份证号码编号规则。
     **/
    private function validateId(value:Object):ValidationResult
    {
      var id:String=value ? value.toString() : "";
      id=StringUtil.trim(id);
      if (id.length == 15)
        return validateId15(id);
      else if (id.length == 18)
        return validateId18(id);
      else
        return new ValidationResult(true, null, "icv.error.wrong.length", LangUtil.getString("gcc", "icv.error.wrong.length"));
    }
  }
}