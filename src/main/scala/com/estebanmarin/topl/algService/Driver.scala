package com.estebanmarin.topl.algService

import com.estebanmarin.topl.Domain.*

final case class DirectedEdge(
    from: Int,
    to: Int,
    weight: Double,
  )

final case class DirectedEdgeNode(
    from: Node,
    to: Node,
    weight: Double,
  )

final case class EdgeWeightedGraphNodetrace(adj: Map[Int, List[DirectedEdge]] = Map.empty, nodeMap: Map[Node, Int] = Map.empty)
object EdgeWeightedGraphNodetrace:
  extension (g: EdgeWeightedGraphNodetrace)
    def addEdge(e: DirectedEdgeNode): EdgeWeightedGraphNodetrace =
      // node Map to Int process
      def curryUpdateNodeMap(node: Node)(gnodeMap: Map[Node, Int]): Map[Node, Int] = gnodeMap.get(node) match
        case Some(value) => gnodeMap + (node -> value)
        case None => gnodeMap + (node -> (gnodeMap.size + 1))
      def updateMapFromDirected(directedEdgeNode: DirectedEdgeNode): Map[Node, Int] =
        val firstNodeCheckUpdate = curryUpdateNodeMap(directedEdgeNode.from)(g.nodeMap)
        curryUpdateNodeMap(directedEdgeNode.to)(firstNodeCheckUpdate)
      val updateNodeMap: Map[Node, Int] = updateMapFromDirected(e)
      // EdgeWeightedGraph calculation
      val NodeFromID: Int = updateNodeMap.get(e.from).getOrElse(Int.MaxValue)
      val NodetoID: Int = updateNodeMap.get(e.to).getOrElse(Int.MaxValue)
      def nodeToEdge(e: DirectedEdgeNode): DirectedEdge =
        DirectedEdge(from = NodeFromID, to = NodetoID, weight = e.weight)

      val list: List[DirectedEdge] = g.adj.getOrElse(NodeFromID, List.empty)
      val adj: Map[Int, List[DirectedEdge]] = g.adj + (NodeFromID -> (list :+ nodeToEdge(e)))

      EdgeWeightedGraphNodetrace(adj, updateNodeMap)

final case class EdgeWeightedGraph(adj: Map[Int, List[DirectedEdge]] = Map.empty)

object EdgeWeightedDigraphOps:
  implicit class EdgeWeightedDigraphOps(g: EdgeWeightedGraph):
    def addEdge(e: DirectedEdge): EdgeWeightedGraph =
      val list: List[DirectedEdge] = g.adj.getOrElse(e.from, List.empty)
      val adj: Map[Int, List[DirectedEdge]] = g.adj + (e.from -> (list :+ e))
      EdgeWeightedGraph(adj)

import com.estebanmarin.topl.algService.EdgeWeightedDigraphOps.*

val edgeWDiagram = EdgeWeightedGraph()
  .addEdge(DirectedEdge(4, 5, 0.35))
  .addEdge(DirectedEdge(5, 4, 0.35))
  .addEdge(DirectedEdge(4, 7, 0.37))
  .addEdge(DirectedEdge(5, 7, 0.28))
  .addEdge(DirectedEdge(7, 5, 0.28))
  .addEdge(DirectedEdge(5, 1, 0.32))
  .addEdge(DirectedEdge(0, 4, 0.38))
  .addEdge(DirectedEdge(0, 2, 0.26))
  .addEdge(DirectedEdge(7, 3, 0.39))
  .addEdge(DirectedEdge(1, 3, 0.29))
  .addEdge(DirectedEdge(2, 7, 0.34))
  .addEdge(DirectedEdge(6, 2, 0.40))
  .addEdge(DirectedEdge(3, 6, 0.52))
  .addEdge(DirectedEdge(6, 0, 0.58))
  .addEdge(DirectedEdge(6, 4, 0.93))

val edgeWDiagram2 = EdgeWeightedGraph()
  .addEdge(DirectedEdge(4, 5, 0.35))
  .addEdge(DirectedEdge(5, 4, 0.35))
  .addEdge(DirectedEdge(4, 7, 0.37))
  .addEdge(DirectedEdge(5, 7, 0.28))
  .addEdge(DirectedEdge(7, 5, 0.28))
  .addEdge(DirectedEdge(5, 1, 0.32))
  .addEdge(DirectedEdge(0, 4, 0.38))
  .addEdge(DirectedEdge(0, 2, 0.26))
  .addEdge(DirectedEdge(7, 3, 0.39))
  .addEdge(DirectedEdge(1, 3, 0.29))
  .addEdge(DirectedEdge(2, 7, 0.34))
  .addEdge(DirectedEdge(6, 2, 0.40))
  .addEdge(DirectedEdge(3, 6, 0.52))
  .addEdge(DirectedEdge(6, 0, 0.58))
  .addEdge(DirectedEdge(6, 4, 0.93))

val edgeWNodeDiagram = EdgeWeightedGraphNodetrace()
  .addEdge(DirectedEdgeNode(from = Node("D", 1), to = Node("E", 1), weight = 0.35))
  .addEdge(DirectedEdgeNode(from = Node("E", 1), to = Node("D", 1), weight = 0.35))
  .addEdge(DirectedEdgeNode(from = Node("D", 1), to = Node("E", 2), weight = 0.37))
