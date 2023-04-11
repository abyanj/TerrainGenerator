package ca.mcmaster.cas.se2aa4.a3.island.adt.point;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.river.RiverType;

import java.util.ArrayList;
import java.util.List;

public class Point {
    private final Vertex foundation;
    private final double x;
    private final double y;
    private double elevation;
    private RiverType riverType = RiverType.FALSE;
    List<Edge> outgoingEdges = new ArrayList<>();

    public Point(Vertex foundation){
        this.foundation = foundation;
        this.x = foundation.getX();
        this.y = foundation.getY();
    }

    public void addOutgoingEdges(List<Edge> edges){
        for (Edge e : edges){
            for (Point p : e.getPointsOfEdge()){
                if (this.equals(p)){
                    outgoingEdges.add(e);
                }
            }
        }
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public List<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    public double getElevation() {
        return elevation;
    }

    public boolean isRiver() {
        return riverType.isRiver();
    }

    public Vertex getFoundationVertex() {
        return foundation;
    }

    public Property getDefaultColor(){
        return Property.newBuilder().setKey("rgb_color").setValue(riverType.getValue()).build();
    }
    public Property getCityColor(){
        return Property.newBuilder().setKey("rgb_color").setValue(riverType.getValue()).build();
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public void setRiverType(RiverType riverType) {
        this.riverType = riverType;
    }
}
