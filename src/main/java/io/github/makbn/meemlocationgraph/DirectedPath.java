package io.github.makbn.meemlocationgraph;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class DirectedPath {

    public enum PathType{
        airline,railway,road;
    }

    private LinkedList<LocationVertex> vertices;
    private Color color;
    private Stroke stroke;
    private PathType type;
    private String additionalName;


    public DirectedPath(LinkedList<LocationVertex> vertices) {
        this.vertices = vertices;
    }

    public DirectedPath(ArrayList<LocationVertex> vertices){
        this.vertices=new LinkedList<LocationVertex>();
        for(LocationVertex vertex:vertices){
            this.vertices.addLast(vertex);
        }
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public PathType getType() {
        return type;
    }

    public void setType(PathType type) {
        this.type = type;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public LinkedList<LocationVertex> getVertices() {
        return vertices;
    }

    public void setVertices(LinkedList<LocationVertex> vertices) {
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        String s="";
        for(LocationVertex locationVertex:vertices){
            s=s.concat(locationVertex.getpCity()+"-");
        }
        if(additionalName!=null){
            s=s.substring(0,s.length()-1).concat(" ("+additionalName+")");
        }
        return s;
    }
}
