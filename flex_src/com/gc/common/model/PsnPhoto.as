package com.gc.common.model
{
  import flash.utils.ByteArray;

  [RemoteClass(alias="com.gc.common.po.PsnPhoto")]
  [Bindable]
  public dynamic class PsnPhoto
  {
    public var id:int;
    public var person:Person;
    public var photo:ByteArray;
    public var photoDate:Date;
    public var uploader:Person;

    public function PsnPhoto()
    {
    }

  }
}