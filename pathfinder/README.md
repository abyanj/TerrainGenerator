# Pathfinder

- Author: [Abyan Jaigirdar ; jaigia1@mcmaster.ca]

### Rationale

```
The Pathfinder project provides a simple and extensible library for working with graphs and implementing various pathfinding algorithms. 
The project aims to offer a robust foundation for solving graph traversal problems, including finding the shortest path between two nodes. 
The library is designed to be easy to understand, extend, and integrate into other projects.
```

### Project Structure

The Pathfinder project consists of the following components:


- `Node.java`: Represents a node in the graph, capable of holding attributes using a HashMap.
- `Graph.java`: Represents a graph, containing nodes and edges, and provides methods to add nodes, add edges, get nodes, and get neighbors.
- `Pathfinder.java`: Defines the interface for finding a path between two nodes, with a single method findPath(Node startNode, Node endNode).
- `DijkstraPathfinder.java`: An implementation of the Pathfinder interface, using Dijkstra's algorithm to find the shortest path between two nodes.

### Extending the Library


To extend the library with a new pathfinding algorithm, follow these steps:

1. Create a new class that implements the Pathfinder interface.
2. Implement the findPath method in the new class, using the desired pathfinding algorithm.
3. If necessary, modify the Graph and Node classes to support the new algorithm's requirements (e.g., edge weights or additional attributes).
