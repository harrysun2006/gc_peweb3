package com.gc
{
  import flash.events.EventDispatcher;

  import mx.collections.ArrayCollection;
  import mx.collections.IHierarchicalData;

  public class ArrayHierarchy extends EventDispatcher implements IHierarchicalData
  {
    private var source:Object;

    public function ArrayHierarchy(value:Object)
    {
      super();
      source = value;
    }

    public function canHaveChildren(node:Object):Boolean
    {
      return node.hasOwnProperty("children");
    }

    public function hasChildren(node:Object):Boolean
    {
      var r:Boolean=false;
      if (node.hasOwnProperty("children"))
      {
        if (node.children is Array || node.children is ArrayCollection)
        {
          r=node.children.length>0;
        }
      }
      return r;
    }

    public function getChildren(node:Object):Object
    {
      return node.hasOwnProperty("children") ? node["children"] : null;
    }

    public function getData(node:Object):Object
    {
      return node;
    }

    public function getRoot():Object
    {
      return source;
    }
  }
}