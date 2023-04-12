package ca.mcmaster.cas.se2aa4.a3.island.adt;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.*;
import ca.mcmaster.cas.se2aa4.a3.island.adt.edge.Edge;
import ca.mcmaster.cas.se2aa4.a3.island.adt.point.Point;
import ca.mcmaster.cas.se2aa4.a3.island.adt.tile.Tile;

import ca.mcmaster.cas.se2aa4.pathfinder.DijkstraPathfinder;
import ca.mcmaster.cas.se2aa4.pathfinder.Graph;
import ca.mcmaster.cas.se2aa4.pathfinder.Node;
import ca.mcmaster.cas.se2aa4.pathfinder.Pathfinder;


import java.util.*;

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
    public Mesh addColor(Mesh inputMesh, int numOfCities) {
        Graph graph = new Graph();
        Map<Integer, Tile> cityTiles = new HashMap<>();
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
            newVertices.add(Vertex.newBuilder(v).addProperties(p.getDefaultColor()).build());

        }
        List<Node> cityNodes = new ArrayList<>();
        CityGenerator(newVertices, cityTiles, cityNodes, graph, numOfCities);

        // Connect all centroids to their neighbours
        for (Tile t1 : islandTiles){
            List<Tile> nTiles = t1.getNeighbours();
            for (int i = 0; i < nTiles.size(); i++){
                int node1Idx = nTiles.get(i).getCentroidIdx();
                int node2Idx = t1.getCentroidIdx();

                Node node1 = graph.allNode.get(node1Idx);
                Node node2 = graph.allNode.get(node2Idx);

                double xDiff = newVertices.get(node1Idx).getX() - newVertices.get(node2Idx).getX();
                double yDiff = newVertices.get(node1Idx).getY() - newVertices.get(node2Idx).getY();

                int edgeWeight = (int) Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));

                graph.addEdge(node1, node2, edgeWeight);

            }
        }
        DijkstraPathfinder dijkstraPathfinder = new DijkstraPathfinder(graph);
        Node hub = cityNodes.get(0);
        int citySize = cityNodes.size();
        // Iterate through the shortestPath of the hub to every other city and make edges
        Property color = Property.newBuilder().setKey("rgb_color").setValue("255,0,0").build();

        for (Node c2 : cityNodes) {
            if (c2 != null && !hub.equals(c2)) {
                List<Node> shortestPath = dijkstraPathfinder.findPath(hub, c2);
                if (shortestPath != null) {
                    ArrayList<Node> path = new ArrayList<>(shortestPath);

                    for (int i = 0; i < path.size() - 1; i++) {
                        Structs.Property thickness = Structs.Property.newBuilder().setKey("thickness").setValue(Integer.toString(5)).build();
                        newSegments.add(Structs.Segment.newBuilder().setV1Idx(path.get(i).getIdx()).setV2Idx(path.get(i + 1).getIdx()).addProperties(color).addProperties(thickness).build());

                        int x1 = (int)newVertices.get(path.get(i).getIdx()).getX();
                        int y1 = (int)newVertices.get(path.get(i).getIdx()).getY();
                        int x2 = (int)newVertices.get(path.get(i + 1).getIdx()).getX();
                        int y2 = (int)newVertices.get(path.get(i + 1).getIdx()).getY();

                        int deltaX = x2 - x1;
                        int deltaY = y2 - y1;

                        int squaredDistance = deltaX * deltaX + deltaY * deltaY;
                        int weight = (int) Math.sqrt(squaredDistance);

                        graph.addEdge(graph.allNode.get(path.get(i).getIdx()), graph.allNode.get(path.get(i + 1).getIdx()), weight);
                    }
                }
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
    public void CityGenerator(List<Vertex> newVertices, Map<Integer, Tile> cityTiles, List<Node> cityNodes, Graph graph, int numOfCities){
        List<Tile> islandTiles = getIslandTiles();
        int count = 1;
        for(Tile t: islandTiles){
            if(!t.getBaseType().isLake()){
                Node city = new Node("City" + count);
                graph.allNode.put(t.getCentroidIdx(), city);
                city.setIdx(t.getCentroidIdx());
                count++;
            }

        }
        if(numOfCities == 0){
            numOfCities = 10;
        }
        for(Tile t : islandTiles) {
            if(!t.getBaseType().isLake()){
                Random rand = new Random();
                int thicknessProperty = rand.nextInt(20) + 10;

                if (rand.nextInt(10) < 1 && numOfCities !=0) {
                    Property thickness = Property.newBuilder().setKey("thickness").setValue(Integer.toString(thicknessProperty)).build();
                    int red = rand.nextInt(255);
                    int green = rand.nextInt(255);
                    int blue = rand.nextInt(255);
                    String colorCode = red + "," + green + "," + blue;
                    Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
                    newVertices.add(Vertex.newBuilder(t.getCentroid().getFoundationVertex()).addProperties(color).addProperties(thickness).build());
                    cityTiles.put(newVertices.size() - 1, t);
                    Node city = new Node("City" + count);
                    cityNodes.add(city);
                    graph.allNode.put(t.getCentroidIdx(), city);
                    city.setIdx(t.getCentroidIdx());
                    count++;
                    numOfCities--;


                }
        }
            System.out.println(cityNodes.size());
        }

    }


}