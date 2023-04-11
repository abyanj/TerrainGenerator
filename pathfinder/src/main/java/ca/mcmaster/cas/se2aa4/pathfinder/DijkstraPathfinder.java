package ca.mcmaster.cas.se2aa4.pathfinder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;

public class DijkstraPathfinder implements Pathfinder {
    private Graph graph;

    public DijkstraPathfinder(Graph graph) {
        this.graph = graph;
    }

    @Override
    public List<Node> findPath(Node startNode, Node endNode) {
        Map<String, Double> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<Node> unvisitedNodes = new PriorityQueue<>(Comparator.comparing(distances::get));

        for (String nodeId : graph.getNodes().keySet()) {
            distances.put(nodeId, Double.MAX_VALUE);
            previousNodes.put(nodeId, null);
        }

        distances.put(startNode.getId(), 0.0);
        unvisitedNodes.add(startNode);

        while (!unvisitedNodes.isEmpty()) {
            Node currentNode = unvisitedNodes.poll();

            if (currentNode.getId().equals(endNode.getId())) {
                break;
            }

            for (String neighborId : graph.getNeighbors(currentNode.getId())) {
                Node neighbor = graph.getNode(neighborId);
                double tentativeDistance = distances.get(currentNode.getId()) + 1;

                if (tentativeDistance < distances.get(neighborId)) {
                    unvisitedNodes.remove(neighbor);
                    distances.put(neighborId, tentativeDistance);
                    previousNodes.put(neighborId, currentNode.getId());
                    unvisitedNodes.add(neighbor);
                }
            }
        }

        LinkedList<Node> path = new LinkedList<>();
        String currentNodeId = endNode.getId();

        while (currentNodeId != null) {
            path.addFirst(graph.getNode(currentNodeId));
            currentNodeId = previousNodes.get(currentNodeId);
        }

        return path;
    }
}
