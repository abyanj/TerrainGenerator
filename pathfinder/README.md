# Pathfinder Library

Author: Abyan Jaigirdar

## Rationale

The Pathfinder Library is a Java library designed to solve the shortest path problem in a graph. It is built with extensibility in mind, allowing developers to implement and use different pathfinding algorithms according to their needs. The library currently implements Dijkstra's algorithm, a widely used and efficient algorithm for finding the shortest path between nodes in a graph.

### Project Structure
The Pathfinder project consists of the following components:
- `Connection.java`: Represents a connection between two nodes in the graph, including the source, destination, and weight of the connection.
- `Node.java`: Represents a node in the graph, containing an ID and an optional index.
- `Graph.java`: Represents a graph, containing nodes and edges, and provides methods to add edges, get edges, and get the node list.
- `Pathfinder.java:` Defines the interface for finding a path between two nodes, with a single method findPath(Node startNode, Node endNode).
- `DijkstraPathfinder.java`: An implementation of the Pathfinder interface, using Dijkstra's algorithm to find the shortest path between two nodes. 

In addition, the project includes a test suite and a README file:

- `DijkstraPathfinderTest.java`: A JUnit test suite for the DijkstraPathfinder class, testing the functionality of the findPath method.
- `README.md`: Provides an overview of the project, its purpose, author information, and guidance for extending the library with new algorithms.
## Extending the Library

To implement a new pathfinding algorithm, follow these steps:

1. Create a new class that implements the `Pathfinder` interface.
2. Implement the `findPath` method, which takes two `Node` objects as input (the start and end nodes) and returns a `List<Node>` representing the shortest path between them. If no path exists, return `null`.
3. Instantiate the new pathfinding class with an instance of the `Graph` class, and use the `findPath` method to find the shortest path between nodes.

### Example Usage

```java
Graph graph = new Graph();
Node A = new Node("A");
Node B = new Node("B");
Node C = new Node("C");
// Add nodes and edges to the graph
graph.addEdge(A, B, 1);
graph.addEdge(A, C, 2);

DijkstraPathfinder pathfinder = new DijkstraPathfinder(graph);
List<Node> path = pathfinder.findPath(A, C);