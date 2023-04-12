package ca.mcmaster.cas.se2aa4.pathfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
    Map<Node, List<Connection>> adjacencyList;
    List<Node> nodeList = new ArrayList<>();
    public HashMap<Integer, Node> allNode = new HashMap<>();

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(Node firstNode, Node finalNode, double weight) {
        Connection edge = new Connection(firstNode, finalNode, weight);
        adjacencyList.putIfAbsent(firstNode, new LinkedList<>());
        adjacencyList.get(firstNode).add(edge);

        // if not already in list, add node
        if (!nodeList.contains(firstNode)) {
            nodeList.add(firstNode);
        }
        if (!nodeList.contains(finalNode)) {
            nodeList.add(finalNode);
        }

        Connection reverseEdge = new Connection(finalNode, firstNode, weight);
        adjacencyList.putIfAbsent(finalNode, new LinkedList<>());
        adjacencyList.get(finalNode).add(reverseEdge);
    }


    public List<Connection> getEdges(Node node) {
        return adjacencyList.get(node);
    }


}
