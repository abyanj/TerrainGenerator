package ca.mcmaster.cas.se2aa4.pathfinder;
import java.util.List;

public interface Pathfinder {
    List<Node> findPath(Node startNode, Node endNode);
}
