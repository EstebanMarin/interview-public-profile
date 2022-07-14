package com.estebanmarin.topl.domain

type Avenue = String
type Street = Int

final case class Node(avenue: Avenue, street: Street, ID: Int)

final case class DirectedEdge(from: Int, to: Int, weight: Double)

final case class EdgeWeightedDigraph(adj: Map[Int, List[DirectedEdge]] = Map.empty)
