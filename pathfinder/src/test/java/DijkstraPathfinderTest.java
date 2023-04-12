import ca.mcmaster.cas.se2aa4.pathfinder.DijkstraPathfinder;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph;
import ca.mcmaster.cas.se2aa4.pathfinder.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DijkstraPathfinderTest {

    @Test
    public void testDijkstraPathfinder() {
        // Create nodes
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");

        // Create graph and add edges
        Graph graph = new Graph();
        graph.addEdge(nodeA, nodeB, 4);
        graph.addEdge(nodeA, nodeC, 2);
        graph.addEdge(nodeB, nodeC, 3);
        graph.addEdge(nodeB, nodeD, 1);
        graph.addEdge(nodeC, nodeD, 4);
        graph.addEdge(nodeC, nodeE, 3);
        graph.addEdge(nodeD, nodeE, 2);

        // Create DijkstraPathfinder instance and find the shortest path
        DijkstraPathfinder pathfinder = new DijkstraPathfinder(graph);
        List<Node> path = pathfinder.findPath(nodeA, nodeE);

        // Check if the path is not null
        assertNotNull(path);

        // Check if the path contains the correct number of nodes
        assertEquals(3, path.size());

        // Check if the path is correct
        assertEquals(nodeA, path.get(0));
        assertEquals(nodeC, path.get(1));
        assertEquals(nodeE, path.get(2));
    }
}
