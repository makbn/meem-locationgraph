package makbn;

import org.jgrapht.WeightedGraph;

import java.util.ArrayList;
import java.util.HashMap;


public class LocationGraph<V extends LocationVertex, E extends PathEdge<V>> {
    private ArrayList<V> vertices;
    private ArrayList<PathEdge<V>> edges;
    private HashMap<String,Integer> stateInputTraffic;
    private HashMap<String,Integer> stateOutputTraffic;
    private int maxWeight=0;
    private int maxTraffic=0;
    private String verticesCSV =null;
    private String edgeCSV =null;

    public LocationGraph(Class<? extends E> edgeClass) {
        //super(edgeClass);
        this.vertices=new ArrayList<V>();
        this.edges=new ArrayList<PathEdge<V>>();
        this.stateInputTraffic=new HashMap<String, Integer>();
        this.stateOutputTraffic=new HashMap<String, Integer>();
    }

    public LocationGraph(WeightedGraph<V, E> base) {
        //super(base);
        this.vertices=new ArrayList<V>();
        this.edges=new ArrayList<PathEdge<V>>();
    }


    //@Override
    public boolean addVertex(V v) {
        for (int i=0;i<vertices.size();i++){
            if(vertices.get(i).getCityId()==v.getCityId()) {
               return false;
            }
        }
        vertices.add(v);
        return true;
    }

    public LocationVertex findByPName(String name){
        for (int i=0;i<vertices.size();i++){
            String pc=vertices.get(i).getpCity();
            if(pc.equals(name)) {
                return vertices.get(i);
            }else if(pc.trim().equals(name.trim())){
                return vertices.get(i);
            }
            if(name.contains("ی")){
                pc=pc.replaceAll("ي","ی");
                name=name.replaceAll("ي","ی");
                if(pc.equals(name)){
                    return vertices.get(i);
                }
            }
            if(name.contains("ک")){
                pc=pc.replaceAll("ك","ک");
                name=name.replaceAll("ك","ک");
                if(pc.equals(name)){
                    return vertices.get(i);
                }
            }
        }
        return null;
    }

    public void addToStateInputTraffic(String name,int value){
        if(stateInputTraffic.containsKey(name)){
            int currentValue=stateInputTraffic.get(name);
            stateInputTraffic.put(name,value+currentValue);
        }else {
            stateInputTraffic.put(name,value);
        }
    }

    public void addToStateoutputTraffic(String name,int value){
        if(stateOutputTraffic.containsKey(name)){
            int currentValue=stateOutputTraffic.get(name);
            stateOutputTraffic.put(name,value+currentValue);
        }else {
            stateOutputTraffic.put(name,value);
        }
    }
    public HashMap<String, Integer> getStateInputTraffic() {
        return stateInputTraffic;
    }

    public void setStateInputTraffic(HashMap<String, Integer> stateInputTraffic) {
        this.stateInputTraffic = stateInputTraffic;
    }

    public HashMap<String, Integer> getStateOutputTraffic() {
        return stateOutputTraffic;
    }

    public void setStateOutputTraffic(HashMap<String, Integer> stateOutputTraffic) {
        this.stateOutputTraffic = stateOutputTraffic;
    }

    public LocationVertex findById(int id){
        for (int i=0;i<vertices.size();i++){
            if(vertices.get(i).getCityId()==id) {
                return vertices.get(i);
            }
        }
        return null;
    }

    public PathEdge<V> createEdge(V src,V dst){
        if(vertices.contains(src) && vertices.contains(dst)){
            src=vertices.get(vertices.indexOf(src));
            dst=vertices.get(vertices.indexOf(dst));
            PathEdge<V> pe=new PathEdge<V>(src,dst);
            if(edges.contains(pe)){
                pe=edges.get(edges.indexOf(pe));
                pe.setWeight(pe.getWeight()+1);
                if(pe.getWeight()>maxWeight)
                    maxWeight=pe.getWeight();
            }else {
                edges.add(pe);
            }
            return pe;
        }
        return null;
    }


    public ArrayList<V> getVertices() {
        return vertices;
    }

    public ArrayList<PathEdge<V>> getEdges() {
        return edges;
    }


    public void print(){
        for(PathEdge p:edges){
            p.print();
        }
    }

    public String getVerticesCSV() {
        if(verticesCSV ==null)
            createVerticesCSV();
        return verticesCSV;
    }

    public String getEdgeCSV(){
        if(edgeCSV==null)
            createEdgeCSV();
        return edgeCSV;
    }

    private void createEdgeCSV() {
        edgeCSV="";
        for (PathEdge p:edges){
            if(p!=null)
                edgeCSV=edgeCSV.concat(p.getCSV()+"\n");
        }
    }

    private void createVerticesCSV() {
        verticesCSV ="";
        for(LocationVertex vertex:vertices){
            if(vertex!=null)
                verticesCSV = verticesCSV.concat(vertex.getCSV()+"\n");
        }
    }


    public int getMaxWeight() {
        return maxWeight;
    }

    public int getMaxTraffic(){
        return maxTraffic;
    }

    public void updateMaxTraffic(){
        for(LocationVertex v:vertices){
            if(v.getInputTraffic()+v.getInputTraffic()>maxTraffic)
                maxTraffic=v.getInputTraffic()+v.getInputTraffic();
        }
    }
}
