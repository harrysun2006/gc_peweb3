package com.gc.controls
{
  import flash.display.Graphics;
  import flash.display.Sprite;
  import flash.geom.Matrix;

  import mx.core.SpriteAsset;

  public class RectangleIcon extends Icon
  {
    public function RectangleIcon()
    {
      super();
    }

    override protected function draw():void
    {
      var o:Sprite=new SpriteAsset();
      var g:Graphics=o.graphics;
      g.clear();
      g.beginFill(color);
      g.drawRect(0, 0, width, height);
      g.endFill();
      bitmapData.draw(o, new Matrix(bitmapData.width/o.width, 0, 0, bitmapData.height/o.height, 0, 0));
    }

  }
}