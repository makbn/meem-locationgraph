package makbn;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import java.util.ArrayList;

public  class GraphModel {

    protected String rawData;
    protected Any jsonData;
    protected LocationGraph<LocationVertex,PathEdge<LocationVertex>>  graph;

    public GraphModel(String rawData){
        this.rawData=rawData;
        this.jsonData= JsonIterator.deserialize(rawData);
    }

    public  LocationGraph getGraph(){
        return graph;
    }

    public  ArrayList<LocationVertex> getVertex(){
        return graph.getVertices();
    }

    public ArrayList<PathEdge<LocationVertex>> getEdge(){
        return graph.getEdges();

    }


    private void jsonToModel(Any any){

    }




}
