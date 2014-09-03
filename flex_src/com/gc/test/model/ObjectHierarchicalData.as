package com.gc.test.model
{
  import flash.events.EventDispatcher;

  import mx.collections.IHierarchicalData;

  [ExcludeClass]
  public class ObjectHierarchicalData extends EventDispatcher implements IHierarchicalData
  {
    private var source:Object;

    public function ObjectHierarchicalData(value:Object)
    {
      super();
      source = value;
    }

    /* in our simple system, only parents with their type set to
     'parent' can have children; otherwise, they can't have children */
    public function canHaveChildren(node:Object):Boolean
    {
      if (node.type == "parent")
      {
        return true;
      }
      return false;
    }

    /* for any given node, determine whether a node has any children by
     looking through all the other nodes for that node's ID as a parentTask */
    public function hasChildren(node:Object):Boolean
    {
      // trace(node.name);
      var children:Array = new Array(); // = source.parentTask == parentId);
      for each (var obj:Object in source)
      {
        if (obj.parentTask == node.id)
        {
          children.push(obj);
        }
      }
      if (children.length > 0)
        return true;

      return false;
    }

    /* for any given node, return all the nodes that are children of that
     node in an array */
    public function getChildren(node:Object):Object
    {
      var parentId:String = node.id;
      var children:Array = new Array();
      for each (var obj:Object in source)
      {
        if (obj.parentTask == parentId)
        {
          children.push(obj);
        }
      }
      return children;
    }

    public function getData(node:Object):Object

    {
      for each (var obj:Object in source)
      {
        for (var prop:Object in node)
        {
          if (obj[prop] == node[prop])
          {
            return obj;
          }
          else
          {
            break;
          }
        }
      }
      return null;
    }

    /* we want to return every obj that is a root object
       which, in this case, is going to be all nodes that have a parent node
     of '0' */
    public function getRoot():Object
    {
      var rootsArr:Array = new Array();
      for each (var obj:Object in source)
      {
        if (obj.parentTask == "0")
        {
          rootsArr.push(obj);
        }
      }
      return rootsArr;
    }

    public function getParent(node:Object):*
    {
      for each (var obj:Object in source)
      {
        if (obj.parentTask == node.parentTask)
        {
          return obj;
        }
      }
      return null;
    }

  }

}
