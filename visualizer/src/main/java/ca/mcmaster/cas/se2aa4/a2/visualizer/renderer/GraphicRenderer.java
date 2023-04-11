package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.sql.Struct;
import java.util.Iterator;
import java.util.Optional;

public class GraphicRenderer implements Renderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.2f);
        canvas.setStroke(stroke);
        drawPolygons(aMesh,canvas);
        drawSegments(aMesh, canvas);
        drawVertexs(aMesh, canvas);
    }

    private void drawPolygons(Mesh aMesh, Graphics2D canvas) {
        for(Structs.Polygon p: aMesh.getPolygonsList()){
            drawAPolygon(p, aMesh, canvas);
        }
    }

    private void drawAPolygon(Structs.Polygon p, Mesh aMesh, Graphics2D canvas) {
        Hull hull = new Hull();
        for(Integer segmentIdx: p.getSegmentIdxsList()) {
            hull.add(aMesh.getSegments(segmentIdx), aMesh);
        }
        Path2D path = new Path2D.Float();
        Iterator<Vertex> vertices = hull.iterator();
        Vertex current = vertices.next();
        path.moveTo(current.getX(), current.getY());
        while (vertices.hasNext()) {
            current = vertices.next();
            path.lineTo(current.getX(), current.getY());
        }
        path.closePath();
        canvas.draw(path);
        Optional<Color> fill = new ColorProperty().extract(p.getPropertiesList());
        if(fill.isPresent()) {
            Color old = canvas.getColor();
            canvas.setColor(fill.get());
            canvas.fill(path);
            canvas.setColor(old);
        }
    }

    private void drawSegments(Mesh aMesh, Graphics2D canvas){
        for(Structs.Segment s: aMesh.getSegmentsList()){
            drawASegment(s, aMesh, canvas);
        }
    }
    private void drawVertexs(Mesh aMesh, Graphics2D canvas){
        for(Structs.Vertex v: aMesh.getVerticesList()){
            drawVertex(v, aMesh, canvas);
        }
    }
    private void drawVertex(Structs.Vertex v, Mesh aMesh, Graphics2D canvas){
            for(Structs.Property prop: v.getPropertiesList()){
                if(prop.getKey().equals("thickness")){
                    int THICKNESS = extractThickness(v);
                    double centre_x = v.getX() - (THICKNESS/2.0d);
                    double centre_y = v.getY() - (THICKNESS/2.0d);
                    canvas.setColor(extractColor(v));
                    Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
                    canvas.fill(point);
                }
            }
    }


    private void drawASegment(Structs.Segment s, Mesh aMesh, Graphics2D canvas){
        Vertex v1 = aMesh.getVertices(s.getV1Idx());
        Vertex v2 = aMesh.getVertices(s.getV2Idx());
        for (Structs.Property prop : s.getPropertiesList()){
            if (prop.getKey().equals("thickness")){
                Stroke stroke = new BasicStroke(Float.parseFloat(prop.getValue()));
                canvas.setStroke(stroke);
            }
        }

        Path2D path = new Path2D.Float();
        path.moveTo(v1.getX(), v1.getY());
        path.lineTo(v2.getX(), v2.getY());
        path.closePath();
        Optional<Color> color = new ColorProperty().extract(s.getPropertiesList());
        if(color.isPresent()){
            Color old = canvas.getColor();
            canvas.setColor(color.get());
            canvas.draw(path);
            canvas.setColor(old);
        }

        Stroke stroke = new BasicStroke(0.3f);
        canvas.setStroke(stroke);
    }
    private int extractThickness(Structs.Vertex v){
        int value = 0;
        for (Structs.Property p: v.getPropertiesList()){
            if(p.getKey().equals("thickness")){
                value = Integer.parseInt(p.getValue());
                return value;

            }
        }

        return value;

    }
    private Color extractColor(Structs.Vertex v){
        String value = "";
        for (Structs.Property p: v.getPropertiesList()){
            if(p.getKey().equals("rgb_color")){
                value = p.getValue();
            }
        }
        String[] raw = value.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }



}
