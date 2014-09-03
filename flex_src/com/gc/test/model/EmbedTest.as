package com.gc.test.model
{
  import flash.utils.ByteArray;

  // [Embed(source="test/test.mxml",mimeType="application/octet-stream")]
  [Embed(source="logo.jpg",mimeType="application/octet-stream")]
  [ExcludeClass]
  public class EmbedTest extends ByteArray
  {
  }
}