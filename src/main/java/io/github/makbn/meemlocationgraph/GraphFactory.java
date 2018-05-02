package io.github.makbn.meemlocationgraph;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GraphFactory {

    private static HashMap<String,double[]> geos;
    private static HashSet<String> notFound;

    public static LocationGraph createEmpty(){
        return new LocationGraph(PathEdge.class);
    }
    public static LocationGraph createFromSQL(String host,String dbName,String username,String password,String cityId,String postmanId,String date,String orderTableName,String runningPath) throws SQLException, ClassNotFoundException {
        SqlHelper.init(SqlHelper.DBRM.MS_SQL_SERVER,host,dbName,username,password);
        // String query="SELECT LE_DATEDELIVERED, LE_TIMEDELIVERED, LE_LAT, LE_LON, LE_LETTER_ID, PCity FROM TBL_Letter WHERE LE_POSTMAN_ID={POSTMAN_ID} AND LE_DATEDELIVERED ='{DATA_DELIVERED}' AND CityID={CITY_ID} ORDER BY LE_DATEDELIVERED , LE_TIMEDELIVERED";
        String query="select * from Full_Exchange2 order by ParcelCode , EventDate ";

        //query=query.replace("{POSTMAN_ID}",postmanId).replace("{CITY_ID}",cityId).replace("{ORDER_TABLE}",orderTableName).replace("{DATA_DELIVERED}",date);

        if(SqlHelper.isConnected()){
            System.out.println("connected to db...");
            Statement statement=SqlHelper.getStatement();
            ResultSet resultSet=statement.executeQuery(query);
            System.out.println("query executed...");
            return createModel(resultSet,runningPath);
        }
        return null;
    }

    private static LocationGraph createModel(ResultSet resultSet,String runningPath) throws SQLException {
        String csv="";
        notFound=new HashSet<String>();
        ArrayList<LocationVertex> vertices=new ArrayList<LocationVertex>();
        ArrayList<PathEdge> pathEdges=new ArrayList<PathEdge>();
        LocationGraph<LocationVertex,PathEdge<LocationVertex>> lg=new LocationGraph(PathEdge.class);
        LocationVertex lastVertex=null;
        String lastParcelCode=null;
        int lastPostNodeId=-1;
        int lastCityId=-1;
        int id=1;
        while (resultSet.next()){
            String parcelCode=resultSet.getString("ParcelCode");
            int postNodeId=resultSet.getInt("PostnodeID");
            String pCity=resultSet.getString("PCity");
            int cityId=resultSet.getInt("CityID");
            int event=resultSet.getInt("EventID");
           // int stateId=resultSet.getInt("stateID");
            int stateId=-1;
            String pState=resultSet.getString("pState");
            String eState=resultSet.getString("eState");
            double[] geo=Utils.getGeo(pCity);
            if(geo==null) {
                notFound.add(pCity);
                continue;
            }
            double lat=geo[0];
            double lon=geo[1];
            String date=resultSet.getString("EventDate");

            LocationVertex currentVertex=new LocationVertex(id++,lat,lon,date,null,-1,pCity,false,cityId,pState,eState,stateId);
            currentVertex.setEventId(event);
            currentVertex.setPostNodeId(postNodeId);
            lg.addVertex(currentVertex);
            if(lastVertex!=null){
                if(parcelCode.equals(lastParcelCode)) {
                    if(postNodeId!=lastPostNodeId
                        & lastCityId!=cityId){
                        PathEdge pe=lg.createEdge(lastVertex,currentVertex);
                    }
                }

            }
            lastVertex=currentVertex;
            lastParcelCode=parcelCode;
            lastPostNodeId=postNodeId;
            lastCityId=cityId;
        }

        System.out.println("Done!");
        for(String s:notFound)
            System.err.println(s);
        resultSet.close();
        return lg;
    }


}
