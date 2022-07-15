package com.estebanmarin.topl.algService

final case class DirectedEdge(
    from: Int,
    to: Int,
    weight: Double,
  )

final case class EdgeWeightedGraph(adj: Map[Int, List[DirectedEdge]] = Map.empty)
object EdgeWeightedDigraphOps:
  implicit class EdgeWeightedDigraphOps(g: EdgeWeightedGraph):
    /** Adds directed edge 'e' to EdgeWeightedDigraph by creating a full-copy adjacency list, i.e. this operation
     * leaves current graph 'g' immutable
     *
     * @param e - DirectedEdge to add into the graph 'g'
     * @return returns new graph with edge 'e' added to it
     */
    def addEdge(e: DirectedEdge): EdgeWeightedGraph =
      val list = g.adj.getOrElse(e.from, List.empty)
      val adj = g.adj + (e.from -> (list :+ e))
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

