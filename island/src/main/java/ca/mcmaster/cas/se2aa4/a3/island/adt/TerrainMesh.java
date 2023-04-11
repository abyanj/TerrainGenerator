package ca.mcmaster.cas.se2aa4.a3.island.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TerrainMesh {
    List<Tile> tiles = new ArrayList<>();
    List<Tile> islandTiles = new ArrayList<>();
    List<Edge> edges = new ArrayList<>();
    List<Point> points = new ArrayList<>();


    public TerrainMesh(Mesh inputMesh){
        List<Polygon> sourcePolygons = inputMesh.getPolygonsList();
        List<Segment> sourceSegments = inputMesh.getSegmentsList();
        List<Vertex> sourceVertices = inputMesh.getVerticesList();
        for (Vertex v : sourceVertices){
            points.add(new Point(v));

        }
        for (Segment s : sourceSegments){
            edges.add(new Edge(s, points));
        }
        for (Polygon p : sourcePolygons){
            tiles.add(new Tile(p, edges, points));
        }

        //Adding neighbour tiles to each tile
        for (Tile t : tiles){
            t.addNeighbours(tiles);
        }

        //Adding outgoing edges to each point
        for (Point p : points){
            p.addOutgoingEdges(edges);
        }
    }

    // *** TILES ***

    //Note: Indices will no longer match with the list of all tiles
    //Use the pointers a tile already has
    public void addIslandTiles() {
        for (Tile t : tiles){
            if (t.getBaseType().isLand()){
                islandTiles.add(t);
            }
        }
    }

    public void calculateBiome(String diagram){
        for (Tile t : tiles){
            t.calculateBiome(diagram);
        }
    }

    public void calculateAltitude(){
        for (Tile t : tiles){
            t.calculateAltitude();
        }
    }
    public void stabilizeAltitude(){
        for (Tile t : tiles){
            if (t.getBaseType().isOcean()){
                for (Point p : t.getPointsOfTile()){
                    p.setElevation(0);
                }
            }
        }
    }
    public void calculateAbsorption(){
        for (Tile t : tiles){
            t.calculateAbsorption();
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Tile> getIslandTiles() {
        return islandTiles;
    }

    // *** EDGES ***

    public List<Edge> getEdges() {
        return edges;
    }

    // *** POINTS ***

    public List<Point> getPoints() {
        return points;
    }

    //Paint mesh
    public Mesh addColor(Mesh inputMesh) {
        List<Polygon> newPolygons = new ArrayList<>();
        for (Tile t : tiles){
            Polygon p = t.getFoundationPolygon();
            newPolygons.add(Polygon.newBuilder(p).addProperties(t.getDefaultColor()).build());
        }

        List<Segment> newSegments = new ArrayList<>();
        for (Edge e : edges){
            Segment s = e.getFoundationSegment();
            newSegments.add(Segment.newBuilder(s).addProperties(e.getDefaultColor()).addProperties(e.getThickness()).build());
        }

        List<Vertex> newVertices = new ArrayList<>();
        for (Point p : points){
            Vertex v = p.getFoundationVertex();
            Random rand = new Random();
            int thicknessProperty = rand.nextInt(5)+1;
            if (rand.nextInt(10) < 1) {
                Property thickness = Property.newBuilder().setKey("thickness").setValue(Integer.toString(thicknessProperty)).build();
                int red = rand.nextInt(255);
                int green = rand.nextInt(255);
                int blue = rand.nextInt(255);
                String colorCode = red + "," + green + "," + blue;
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

                newVertices.add(Vertex.newBuilder(v).addProperties(color).addProperties(thickness).build());
            } else {
                newVertices.add(Vertex.newBuilder(v).addProperties(p.getDefaultColor()).build());
            }

        }

        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(newPolygons)
                .clearSegments()
                .addAllSegments(newSegments)
                .clearVertices()
                .addAllVertices(newVertices)
                .build();
    }

    public Mesh addAltitudeColor(Mesh inputMesh){
        List<Polygon> newPolygons = new ArrayList<>();
        for (Tile t : tiles){
            Polygon p = t.getFoundationPolygon();
            newPolygons.add(Polygon.newBuilder(p).addProperties(t.getAltitudeColor()).build());
        }

        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(newPolygons)
                .build();
    }
    public Mesh addAbsorptionColor(Mesh inputMesh){
        List<Polygon> newPolygons = new ArrayList<>();
        for (Tile t : tiles){
            Polygon p = t.getFoundationPolygon();
            newPolygons.add(Polygon.newBuilder(p).addProperties(t.getAbsorptionColor()).build());
        }

        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(newPolygons)
                .build();
    }

    public Mesh addAquiferColor(Mesh inputMesh){
        List<Polygon> newPolygons = new ArrayList<>();
        for (Tile t : tiles){
            Polygon p = t.getFoundationPolygon();
            newPolygons.add(Polygon.newBuilder(p).addProperties(t.getAquiferColor()).build());
        }

        return Mesh.newBuilder(inputMesh)
                .clearPolygons()
                .addAllPolygons(newPolygons)
                .build();
    }
}