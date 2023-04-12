package ca.mcmaster.cas.se2aa4.pathfinder;

public class Connection {
    private Node source;
    private Node destination;
    private double weight;

    public Connection(Node source, Node destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public double getWeight() {
        return weight;
    }
}
