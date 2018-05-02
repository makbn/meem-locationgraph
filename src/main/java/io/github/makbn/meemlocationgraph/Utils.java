package io.github.makbn.meemlocationgraph;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static HashMap<String, double[]> geos;
    private static HashMap<String,double[]> stateGeos;
    private static LocationGraph lg;
    private static String runningPath;
    private static final String CITY_FILE_PATH ="/data/city.json";
    private static final String STATE_FILE_PATH ="/data/states.json";
    public static void init(LocationGraph locationGraph,String runningPath){
        Utils.lg=locationGraph;
        Utils.runningPath=runningPath;

    }

    public static double[] getStateGeo(String eState){
        if(stateGeos==null)
            stateGeos=getGeo(runningPath,STATE_FILE_PATH,0);

        return stateGeos.get(eState);
    }

    public static double[] getGeo(String pc){
        String pCity=pc.replaceAll("ي", "ی");
        pCity=pCity.replaceAll("ك", "ک").trim();
        if(geos==null){
            geos=getGeo(runningPath, CITY_FILE_PATH,1);
        }
        for(Map.Entry<String,double[]> entry:geos.entrySet()){

            if(entry.getKey().equals(pCity)){
                return entry.getValue();
            }else if(entry.getKey().trim().equals(pCity.trim())) {
                geos.put(pCity.trim(), entry.getValue());
                return entry.getValue();
            }
        }

        for(Map.Entry<String,double[]> entry:geos.entrySet()){
            if(pCity.contains(entry.getKey())){
                geos.put(pCity,entry.getValue());
                return entry.getValue();
            }
        }
        return null;
    }

    @SuppressWarnings("Duplicates")
    private static HashMap<String,double[]> getGeo(String runningPth,String file,int start) {

        JSONParser parser = new JSONParser();
        HashMap<String,double[]> geos=new HashMap<String, double[]>();
        try
        {

            Object obj = parser.parse(new FileReader(runningPth+file));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray records= (JSONArray) jsonObject.get("RECORDS");

            for(int i=0;i<records.size();i++){
                JSONObject city= (JSONObject) records.get(i);
                String name= (String) city.get("name");
                name=name.substring(start,name.length());
                double[] latlon=new double[2];
                latlon[0]= (Double) city.get("latitude");
                latlon[1]= (Double) city.get("longitude");
                geos.put(name,latlon);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return geos;
    }

    public static LocationVertex findLocationVertexByName(String name){
        LocationVertex vertex=lg.findByPName(name);
        if(vertex==null){
            double[] geo=getGeo(name);
            if(geo==null)
                return null;
            double lat=geo[0];
            double lon=geo[1];
            vertex=new LocationVertex(-1,lat,lon,null,null,-1,name,false);
        }
        return vertex;
    }
}
