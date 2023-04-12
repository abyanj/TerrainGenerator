package ca.mcmaster.cas.se2aa4.pathfinder;

import java.util.*;

public class DijkstraPathfinder implements Pathfinder {
    private Graph graph;

    public DijkstraPathfinder(Graph graph) {
        this.graph = graph;
    }

    @Override
    public List<Node> findPath(Node source, Node destination) {
        Map<Node, Double> distances = initializeDistances(source);
        Map<Node, Node> previousNodes = new HashMap<>();
        PriorityQueue<Node> unvisitedNodes = initializeUnvisitedNodes(distances);

        while (!unvisitedNodes.isEmpty()) {
            Node currentNode = unvisitedNodes.poll();

            if (currentNode.equals(destination)) {
                break;
            }

            updateNeighborDistances(currentNode, distances, previousNodes, unvisitedNodes);
        }

        return buildPath(source, destination, previousNodes);
    }

    private Map<Node, Double> initializeDistances(Node source) {
        Map<Node, Double> distances = new HashMap<>();
        for (Node node : graph.adjacencyList.keySet()) {
            if (node == null) {
                continue;
            }
            double initialDistance = (node.equals(source)) ? 0.0 : Double.MAX_VALUE;
            distances.put(node, initialDistance);
        }
        return distances;
    }

    private PriorityQueue<Node> initializeUnvisitedNodes(Map<Node, Double> distances) {
        Comparator<Node> nodeComparator = Comparator.comparingDouble(n -> distances.getOrDefault(n, Double.MAX_VALUE));
        PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>(nodeComparator);
        unvisitedNodes.addAll(distances.keySet());
        return unvisitedNodes;
    }

    private void updateNeighborDistances(Node currentNode, Map<Node, Double> distances, Map<Node, Node> previousNodes, PriorityQueue<Node> unvisitedNodes) {
        for (Connection edge : graph.getEdges(currentNode)) {
            Node neighbor = edge.getDestination();
            if (!unvisitedNodes.contains(neighbor)) {
                continue;
            }

            double newDistance = distances.get(currentNode) + edge.getWeight();
            if (newDistance < distances.get(neighbor)) {
                distances.put(neighbor, newDistance);
                previousNodes.put(neighbor, currentNode);
                unvisitedNodes.remove(neighbor);
                unvisitedNodes.add(neighbor);
            }
        }
    }

    private List<Node> buildPath(Node source, Node destination, Map<Node, Node> previousNodes) {
        LinkedList<Node> path = new LinkedList<>();
        Node currentNode = destination;
        while (currentNode != null) {
            path.addFirst(currentNode);
            currentNode = previousNodes.get(currentNode);
        }
        return path.isEmpty() || !path.getFirst().equals(source) ? null : path;
    }
}
