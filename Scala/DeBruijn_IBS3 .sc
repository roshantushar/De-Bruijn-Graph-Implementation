import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import scala.collection.mutable.Set
import util.control.Breaks._


object DeBruijn_IBS3 {

  class Graph(vlist:ArrayBuffer[String])
 {
    var vert:Map[Int,String] = Map()
    var ind:Map[String,Int] = Map()
   
    for(i <- 0 to vlist.size-1)
    {   vert = vert + (i -> vlist(i))
        ind = ind + (vlist(i) -> i)
    }
     
    var edge = ArrayBuffer[(Int,Int)]()
    var edgelabel = ArrayBuffer[String]()
    
    def addEdge(obj:Graph, vsrc:String, vdst:String, Label:String, repeats:Boolean = true):Unit =
   // Edge label is optional
   // "Unit" is analogous to "void"
    {
       var e = (obj.ind(vsrc), obj.ind(vdst))
     
       if((repeats) || (obj.edge contains e))
       {
          obj.edge.append(e)
          obj.edgelabel.append(Label)
       }
    }
   
  }
  
  //---------------------------------------------------------------------------------------------
  
  class Graph(vlist:ArrayBuffer[String])
 {
    var vert:Map[Int,String] = Map() // why not mutable.Map ?
    var ind:Map[String,Int] = Map()
   
    for(i <- 0 to vlist.size-1)
    {   vert = vert + (i -> vlist(i))
        ind = ind + (vlist(i) -> i)
    }
     
    var edge = ArrayBuffer[(Int,Int)]()
    var edgelabel = ArrayBuffer[String]()
   
    def addVertex(obj:Graph, label:String)
    {
       var new_vert_index = (obj.ind).size
       obj.vert += (new_vert_index -> label)
       obj.ind += (label -> new_vert_index)
    }
   
    def addEdge(obj:Graph, vsrc:String, vdst:String, Label:String, repeats:Boolean = true):Unit =
   // Edge label is optional
   // 'Unit' is analogous to 'void'
    {
       var e = (obj.ind(vsrc), obj.ind(vdst))
     
       if((repeats) || (obj.edge contains e))
       {
          obj.edge.append(e)
          obj.edgelabel.append(Label)
       }
    }
    
    def degrees(obj:Graph): (Map[Int,Int], Map[Int,Int]) =
    // error when simply (Map,Map) is given
    {
       var inDegrees:Map[Int,Int] = Map()
       var outDegrees:Map[Int,Int] = Map()
       
       for (i <- obj.edge)
       {
          var src = i._1
          var dst = i._2
          // obj.edge(i) is a tuple
          
          if(outDegrees.contains(src)) outDegrees(src) = outDegrees(src) + 1
          else outDegrees(src) = 1
          
          if(inDegrees.contains(dst)) inDegrees(dst) = inDegrees(dst) + 1
          else inDegrees(dst) = 1
       }
       
       return (outDegrees, inDegrees)
    }
    //////////////////////////////////////////////////////////////////////
    def verifyAndGetStart(obj:Graph) : Int =
    {
       var (inDegrees, outDegrees) = degrees(obj)
       var start = 0
       var end = 0
       
       obj.vert.keys foreach
       {
         key =>
         var ins = 0
         var outs = 0
         if (inDegrees.contains(key)) {var ins = inDegrees(key)}
         if(outDegrees.contains(key)) {var outs = outDegrees(key)}
                  
         if (ins == outs) {}
         else if (ins - outs == 1) {end = key}
         else if (outs - ins == 1) {start = key}
         else
         {  start = -1; end = -1; break }
          
       }
       
       if (start >=0 && end >= 0) { return start }
       else {return -1}
    }
    
    def eulerianPath(obj:Graph) : ArrayBuffer[Int] =
    {
       var graph = ArrayBuffer[(Int,Int)]()
       graph = obj.edge
       
       var current_vertex = verifyAndGetStart(obj)
          
       var path = ArrayBuffer[Int]()
       path.append(current_vertex)
          
       var next = 1
          
       while(graph.size > 0)
       {
          for (edge <- graph)
          {
             if (edge._1 == current_vertex) {
                current_vertex = edge._2
                graph = graph - edge
                path.insert(next, current_vertex)
                next += 1
                break
             }
             
             else
             {
                for (i <- graph)
                {
                   try
                   {
                      next = path(i._1) + 1
                      current_vertex = i._1
                      break
                   }
                   catch
                   {
                      case x: IllegalArgumentException =>
                      {}
                   }
                
                   //else part
                   println("There's no path")
                   //return false
                   
                }  // for loop
             } // else
          } // for loop
       } // while loop
          
       return path
    } // eulerian path
    
    def eulerEdges (obj:Graph, path:ArrayBuffer[Int]): ArrayBuffer[String] =
    {
       var edgeID:Map[(Int,Int),List[Int]] = Map()
       
       for (i <- 0 to obj.edge.size-1)
       {
          if(edgeID contains obj.edge(i))
          { edgeID(obj.edge(i)) = edgeID(obj.edge(i)) + List(i)}
          else
          { edgeID(obj.edge(i) = List() + List(i))}
       }
       
       var edgeList = ArrayBuffer[String]()
       
       for (i <- 0 to path.size-1)
       {
          edgeList.append(obj.edgelabel( edgeID(path(i),path(i+1)).dropRight(1) ))
       }
       
       return edgeList
    }
 }
 
 //------------------------------------------------------------------------------------------------------------
  
  val k_val = 5
  
  var labeled_READS = ArrayBuffer[String]()
  
  labeled_READS.append("ACGGC_1", "ACGGC_2", "CACGG", "CGCAA", "CGCAC", "CGGCG_1", "CGGCG_2", "CGGCG_3", "GACGG", "GCACG", "GCGCA_1", "GCGCA_2", "GCGGC", "GGCGC_1", "GGCGC_2", "GGCGG")
  var ordered_nodes = ArrayBuffer[String]()
  ordered_nodes.append("ACGG", "CACG", "CGCA", "CGGC", "GACG", "GCAA", "GCAC", "GCGC", "GCGG", "GGCG")
  
  val G1 = new Graph(ordered_nodes)
  
  for (code <- labeled_READS)
  { G1.addEdge(G1, code.substring(0,k_val-1), code.substring(1,k_val), code, true) }
  
  
  println("Welcome all!!!")

}