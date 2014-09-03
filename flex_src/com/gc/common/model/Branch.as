package com.gc.common.model
{
  import com.gc.Constants;

  /**
   * RemoteClass用于与远程方法返回或调用的对象映射
   * dynamic的类可以动态定义属性branch["children"]
   **/
  [RemoteClass(alias="com.gc.common.po.Branch")]
  [Bindable]
  public dynamic class Branch
  {
    /**
     * int/Number均可以与PO的Integer/Long/int/long属性映射.
     * int/Number没有null值, 所以0/NaN ==> 0, 0 <== null
     * 在Flex Object与Java ASObject(Map)之间映射时, NaN <==> NaN(Double);
     * 在Flex PO与Java PO之间映射时, NaN ==> 0(Integer) NaN <==> NaN(Double).
     * TODO: 研究BlazeDS, 如果能实现NaN <==> null(Integer), 就将Flex中所有的int改为Number.
     **/
    public var id:int;
    public var useId:String;
    public var name:String;
    public var level:int;
    public var superId:int;
    public var onDate:Date;
    public var status:int;
    public var downDate:Date;
    public var national:String;
    public var state:String;
    public var city:String;
    public var zip:String;
    public var address:String;
    public var contact:String;
    public var telephone:String;
    public var email:String;
    public var comment:String;
    public var manDepart:Department;

    public function Branch(id:int=0, useId:String=null, name:String=null)
    {
      this.id=id;
      this.useId=useId;
      this.name=name;
    }

    /**
     * 定义只读属性data, 对应于ComboBox的value
       public function get data():String
       {
       return useId;
       }
     **/

    /**
     * 定义只读属性label, 对应于一般控件(Tree)的labelField(显示文本)
     **/
    public function get label():String
    {
      return name ? name : Constants.NULL_LABEL;
    }

    /**
     * 定义只读属性value, 标识对象, 用于在Array/ArrayCollection中查找.
     **/
    public function get value():Object
    {
      return id;
    }

    [Embed(source="assets/icons/16x16/branch.png")]
    public static const ICON:Class;

    /**
     * 定义只读属性icon, 对应于一般控件(Tree)的iconField(显示图标)
     **/
    public function get icon():Class
    {
      return ICON;
    }

    /**
     * 一般为map(Object, map[branch]=value)的键值
     **/
    public function toString():String
    {
      return "Branch{id=" + id + ", useId=" + useId + ", name=" + name + "}";
    }
  }
}