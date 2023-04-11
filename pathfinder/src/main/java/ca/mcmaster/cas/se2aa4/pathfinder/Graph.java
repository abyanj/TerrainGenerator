package ca.mcmaster.cas.se2aa4.pathfinder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<String, Node> nodes;
    private Map<String, Set<String>> adjacencyList;

    public Graph() {
        nodes = new HashMap<>();
        adjacencyList = new HashMap<>();
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        adjacencyList.put(node.getId(), new HashSet<>());
    }

    public void addEdge(String nodeId1, String nodeId2) {
        adjacencyList.get(nodeId1).add(nodeId2);
        adjacencyList.get(nodeId2).add(nodeId1);
    }

    public Node getNode(String nodeId) {
        return nodes.get(nodeId);
    }

    public Map<String, Node> getNodes() {
        return nodes;
    }



    public Set<String> getNeighbors(String nodeId) {
        return adjacencyList.get(nodeId);
    }
}
