package com.gc.util
{
  import flash.display.Sprite;

  public class PrintUtil
  {
    import com.gc.CommonEvent;

    import flash.events.Event;
    import flash.events.EventDispatcher;
    import flash.printing.PrintJob;
    import flash.printing.PrintJobOptions;
    import flash.printing.PrintJobOrientation;
    import flash.utils.setTimeout;

    import mx.collections.IList;
    import mx.controls.AdvancedDataGrid;
    import mx.controls.DataGrid;
    import mx.core.Application;
    import mx.core.Container;
    import mx.core.UIComponent;
    import mx.printing.FlexPrintJob;
    import mx.printing.PrintAdvancedDataGrid;
    import mx.printing.PrintDataGrid;

    private static function singleton():void
    {
    }

    public function PrintUtil(caller:Function=null)
    {
      if (caller != singleton)
        throw new Error("PrintUtil is a non-instance class!!!");
    }

    /**
     * 打印, 参考hr/index.mxml的printCards方法
     * obj: 表格, 对象数组或列表
     * template: 页面渲染模板, 必须是Container的子类
     **/
    public static function print(obj:Object, template:Class=null, f:Function=null):void
    {
      if (obj is DataGrid)
        printDataGrid(obj as DataGrid);
      else if (obj is AdvancedDataGrid)
        printAdvancedDataGrid(obj as AdvancedDataGrid);
      else if (obj is Array)
        printPages(obj as Array, template, f);
      else if (obj is IList)
        printPages((obj as IList).toArray(), template, f);
      else if (obj is Sprite)
        printSprite(obj as Sprite);
    }

    private static function printDataGrid(dg:DataGrid):void
    {
      var fpj:FlexPrintJob=new FlexPrintJob();
      if (fpj.start())
      {
        var pdg:PrintDataGrid=new PrintDataGrid();
        pdg.visible=false;
        pdg.width=fpj.pageWidth;
        pdg.height=fpj.pageHeight;
        pdg.columns=dg.columns;
        pdg.dataProvider=dg.dataProvider;
        pdg.labelFunction=dg.labelFunction;
        Application.application.addChild(pdg);
        fpj.addObject(pdg);
        while (pdg.validNextPage)
        {
          pdg.nextPage();
          fpj.addObject(pdg);
        }
        fpj.send();
        Application.application.removeChild(pdg);
      }
    }

    private static function printAdvancedDataGrid(adg:AdvancedDataGrid):void
    {
      var fpj:FlexPrintJob=new FlexPrintJob();
      if (fpj.start())
      {
        var padg:PrintAdvancedDataGrid=new PrintAdvancedDataGrid();
        padg.visible=false;
        padg.width=fpj.pageWidth;
        padg.height=fpj.pageHeight;
        padg.columns=adg.columns;
        padg.dataProvider=adg.dataProvider;
        padg.labelFunction=adg.labelFunction;
        padg.headerRenderer=adg.headerRenderer;
        padg.itemRenderer=adg.itemRenderer;
        padg.setStyle("headerSortSeparatorSkin", adg.getStyle("headerSortSeparatorSkin"));
        Application.application.addChild(padg);
        fpj.addObject(padg);
        while (padg.validNextPage)
        {
          padg.nextPage();
          fpj.addObject(padg);
        }
        fpj.send();
        Application.application.removeChild(padg);
      }
    }

    private static function printPages(objs:Array, template:Class, f:Function=null):void
    {
      printPages2(objs, template, f);
    }

    /**
     * objs: 页面数组
     **/
    private static function printPages1(objs:Array, template:Class):void
    {
      if (objs == null || objs.length <= 0)
        return;
      var fpj:FlexPrintJob=new FlexPrintJob();
      fpj.printAsBitmap=false;
      if (fpj.start())
      {
        for each (var page:UIComponent in objs)
        {
          fpj.addObject(page);
          Application.application.removeChild(page);
        }
        fpj.send();
      }
    }
    /*
       PersonalController.getPersonsCard(ids, function(e1:ResultEvent):void
       {
       var coll:ArrayCollection=e1.result as ArrayCollection;
       var page:Container;
       var pages:Array=[];
       var count:int=0;
       var ed:EventDispatcher=new EventDispatcher();
       var f:Function=function(e2:Event):void
       {
       CommonUtil.print(pages);
       ed.removeEventListener(CommonEvent.READY, f);
       };
       ed.addEventListener(CommonEvent.READY, f);
       for each (var obj:Object in coll)
       {
       page=new PersonCard1();
       page.visible=page.includeInLayout=false;
       Application.application.addChild(page);
       page.addEventListener(Event.COMPLETE, function(e:Event):void
       {
       count++;
       if (count == coll.length) ed.dispatchEvent(CommonEvent.READY_EVENT);
       }, true);
       page.data=obj;
       pages.push(page);
       }
       Application.application.validateNow();
       });
     */

    /**
     * objs: 数据对象数组
     **/
    private static function printPages2(objs:Array, template:Class, f:Function=null):void
    {
      if (objs == null || objs.length <= 0)
        return;
      var pages:Array=[]
      var page:Container;

      var ed:EventDispatcher=new EventDispatcher();
      var f1:Function=function(e:CommonEvent=null):void
        {
          var fpj:FlexPrintJob=new FlexPrintJob();
          fpj.printAsBitmap=false;
          if (fpj.start())
          {
            for each (var page:UIComponent in pages)
            {
              fpj.addObject(page);
              Application.application.removeChild(page);
            }
            fpj.send();
          }
          ed.removeEventListener(CommonEvent.READY, f1);
        };
      ed.addEventListener(CommonEvent.READY, f1);

      for each (var obj:Object in objs)
      {
        page=template is Class ? new template() : obj as Container;
        page.visible=page.includeInLayout=false;
        Application.application.addChild(page);
        page.addEventListener(Event.COMPLETE, function(e:Event):void
          {
            if (f != null && f(page, objs)) ed.dispatchEvent(CommonEvent.READY_EVENT);
          }, true);
        pages.push(page);
        page.data=obj;
      }
      Application.application.validateNow();
      if (f == null)
        setTimeout(f1, 200);
    }

    /**
     * http://help.adobe.com/zh_CN/AS3LCR/Flex_4.0/flash/printing/PrintJob.html#orientation
     * 注意版本之间的区别:
     * orientation: Flash Player 9 - read only, AIR 1.0 - read only, AIR 2 - read-write
     * 其他很多方法及PaperSize等类都只有AIR 2支持
     **/
    private static function printSprite(sprite:Sprite):void
    {
      var pj:PrintJob=new PrintJob();
      var pjop:PrintJobOptions=new PrintJobOptions();
      var pjor:PrintJobOrientation=new PrintJobOrientation();
      pj.start();
      pj.addPage(sprite);
      pj.send();
    }
  }
}