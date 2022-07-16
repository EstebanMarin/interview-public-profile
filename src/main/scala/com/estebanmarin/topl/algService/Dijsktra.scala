package com.estebanmarin.topl.algService

import com.estebanmarin.topl.Domain.*
import zio.*

import javax.management.RuntimeErrorException
import scala.annotation.tailrec
import scala.collection.mutable

/** Algorithm is based on Java version implemented by Princeton University https://algs4.cs.princeton.edu/44sp/
  */

case class ShortestPath()
object ShortestPath:
  def live = ZLayer.succeed(ShortestPath())

  /** Effect tries to find a shortest path from source vertex to all other vertices in the graph
    *
    * @param g       EdgeWeightedDigraph to find a shortest path
    * @param sourceV source vertex to find a shortest path from
    * @return return either error as string when input parameters are invalid or return shortest path result
    */

  def dijkstraPathAndTime(
      source: Node,
      to: Node,
      edgeWeightedDigraphs: List[WGraphPerTimeStamp],
//    ): IO[Throwable, List[OptimalPerTimeStamp]] =
    ): IO[Throwable, OptimalPerTimeStamp] =
    def getString(sp: Either[String, ShortestPathCalc]) =
      sp match
        case Right(spCalc) => spCalc
        case Left(value)   => value
    val calculateOptimaTuple  =
      for
        graphToOptimize: WGraphPerTimeStamp <- edgeWeightedDigraphs
        sp: Either[String, ShortestPathCalc] = ShortestPath.runAlgorithm(graphToOptimize.wGraphNode.adj, graphToOptimize.wGraphNode.nodeMap.getOrElse(source, 0))
      yield (graphToOptimize.time, sp)

    val sp = ShortestPath.runAlgorithm(edgeWeightedDigraphs.head.wGraphNode.adj, edgeWeightedDigraphs.head.wGraphNode.nodeMap.getOrElse(source, 0))
    val either: Either[Throwable, ShortestPathCalc] = sp match
      case Right(value) => Right(value)
      case Left(value) => Left(new RuntimeException(s"${value}"))
    val composableEffect: IO[Throwable, ShortestPathCalc] = ZIO.fromEither(either)
    for
      sp: ShortestPathCalc <- composableEffect
      actualPath: String = sp.pathTo(edgeWeightedDigraphs.head.wGraphNode.nodeMap.getOrElse(to, 1)).toString
      timeToGet: Double = sp.distToV(edgeWeightedDigraphs.head.wGraphNode.nodeMap.getOrElse(to, 1)).getOrElse(Double.PositiveInfinity)
    yield (edgeWeightedDigraphs.head.time, actualPath, timeToGet)

  def runAlgorithm(g: MapDirectedEdges, sourceV: Int): Either[String, ShortestPathCalc] =
    val size = g.size

    if (sourceV >= size) Left(s"Source vertex must in range [0, $size)")
    else
      val edgeTo = mutable.ArrayBuffer.fill[Option[DirectedEdge]](size)(None)
      val distTo = mutable.ArrayBuffer.fill(size)(Double.PositiveInfinity)

      // init source distance and add to the queue
      distTo(sourceV) = 0.0
      val sourceDist = (sourceV, distTo(sourceV))
      val sortByWeight: Ordering[(Int, Double)] = (a, b) => a._2.compareTo(b._2)
      val queue = mutable.PriorityQueue[(Int, Double)](sourceDist)(sortByWeight)

      while (queue.nonEmpty)
        val (minDestV, _) = queue.dequeue()
        val edges = g.getOrElse(minDestV, List.empty)

        edges.foreach { e =>
          if (distTo(e.to) > distTo(e.from) + e.weight)
            distTo(e.to) = distTo(e.from) + e.weight
            edgeTo(e.to) = Some(e)
            if (!queue.exists(_._1 == e.to)) queue.enqueue((e.to, distTo(e.to)))
        }

      Right(ShortestPathCalc(edgeTo.toSeq, distTo.toSeq))

  def run(g: EdgeWeightedGraph, sourceV: Int): IO[String, ShortestPathCalc] =
    val size = g.adj.size

    if (sourceV >= size) ZIO.fromEither(Left(s"Source vertex must in range [0, $size)"))
    else
      val edgeTo = mutable.ArrayBuffer.fill[Option[DirectedEdge]](size)(None)
      val distTo = mutable.ArrayBuffer.fill(size)(Double.PositiveInfinity)

      // init source distance and add to the queue
      distTo(sourceV) = 0.0
      val sourceDist = (sourceV, distTo(sourceV))
      val sortByWeight: Ordering[(Int, Double)] = (a, b) => a._2.compareTo(b._2)
      val queue = mutable.PriorityQueue[(Int, Double)](sourceDist)(sortByWeight)

      while (queue.nonEmpty)
        val (minDestV, _) = queue.dequeue()
        val edges = g.adj.getOrElse(minDestV, List.empty)

        edges.foreach { e =>
          if (distTo(e.to) > distTo(e.from) + e.weight)
            distTo(e.to) = distTo(e.from) + e.weight
            edgeTo(e.to) = Some(e)
            if (!queue.exists(_._1 == e.to)) queue.enqueue((e.to, distTo(e.to)))
        }

      ZIO.fromEither(Right(ShortestPathCalc(edgeTo.toSeq, distTo.toSeq)))

/** @param edgeTo a sequence which represents the last edge on the shortest path from 'sourceV' to vertex i.
  *               None means there is no path to vertex i
  * @param distTo a sequence of distances from source vertex to a specific i vertex
  */
class ShortestPathCalc(edgeTo: Seq[Option[DirectedEdge]], distTo: Seq[Double]):
  /** @param v vertex to get the path for
    * @return returns error when v is invalid or sequence of edges which form the path from source vertex to v vertex
    */
  def pathTo(v: Int): Either[String, Seq[DirectedEdge]] =

    @tailrec
    def go(list: List[DirectedEdge], vv: Int): List[DirectedEdge] =
      edgeTo(vv) match
        case Some(e) => go(e +: list, e.from)
        case None => list

    hasPath(v).map(b => if (!b) Seq() else go(List(), v))

  /** @param v vertex to check whether path from source vertex to v vertex exists
    * @return returns error when v is invalid or Boolean when some path from source vertex to vertex v exists
    */
  def hasPath(v: Int): Either[String, Boolean] =
    distTo.lift(v).map(_ < Double.PositiveInfinity).toRight(s"Vertex $v does not exist")

  /** @param v vertex to get the distance for
    * @return returns error when v is invalid or the Double distance which is a sum of weights
    */
  def distToV(v: Int): Either[String, Double] =
    distTo.lift(v).toRight(s"Vertex $v does not exist")
