package makbn;

public class PathEdge<V extends LocationVertex> {

    public static final int DEFAULT_WEIGHT=1;

    private static final boolean DEFAULT_DIRECTED=true;

    protected int id;

    protected int weight=DEFAULT_WEIGHT;

    protected boolean isDirected=DEFAULT_DIRECTED;

    protected V vertexSrc;

    protected V vertexDst;


    public PathEdge(V src,V dst,int w,boolean isDirected){
        this.vertexSrc=src;
        this.vertexDst=dst;
        this.weight=w;
        this.isDirected=isDirected;
    }

    public PathEdge(V src,V dst){
        this(src,dst,DEFAULT_WEIGHT,DEFAULT_DIRECTED);
    }

    public PathEdge(V src,V dst,int w){
        this(src,dst,w,DEFAULT_DIRECTED);
    }

    public PathEdge(V src,V dst,boolean isDirected){
        this(src,dst,DEFAULT_WEIGHT,isDirected);
    }

    public V getVertexSrc()
    {
        return vertexSrc;
    }

    public V getVertexDst()
    {
        return vertexDst;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof PathEdge){
            if(this.vertexDst.equals(((PathEdge) obj).vertexDst) && this.vertexSrc.equals(((PathEdge) obj).vertexSrc))
                return true;
            if(this.vertexSrc.getLat()==((PathEdge) obj).getVertexSrc().getLat() &&
                this.vertexSrc.getLon()==((PathEdge) obj).getVertexSrc().getLon() &&
                this.vertexDst.getLat()==((PathEdge) obj).getVertexDst().getLat() &&
                this.vertexDst.getLon()== ((PathEdge) obj).getVertexDst().getLon()){
                return true;
            }
        }
        return false;
    }

    public void print()
    {
        System.out.println("SRC: "+vertexSrc.toString() + "\t DST: "+vertexDst.toString()+"\t W: "+weight);
    }

    public String getCSV() {
        return  this.vertexSrc.getCityId()+","+
        this.vertexDst.getCityId()+","+
        this.weight+","+
        this.isDirected+",";
    }
}
