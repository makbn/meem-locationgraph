package io.github.makbn.meemlocationgraph;

import java.nio.charset.Charset;

public class LocationVertex{

    private int id;

    private double lat;

    private double lon;

    private String date;

    private String time;

    private int postManId;

    private String pCity;
    private String eCity;
    private String eState;
    private String pState;

    private boolean delivered=false;

    private int postNodeId;

    private int eventId;

    private int cityId;
    private int stateId;

    private int inputTraffic;
    private int outputTraffic;

    public LocationVertex(int id, double lat, double lon, String date, String time, int postManId, String pCity, boolean delivered) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.date = date;
        this.time = time;
        this.postManId = postManId;
        this.pCity = pCity;
        this.delivered = delivered;
    }

    public LocationVertex(int id, double lat, double lon, String date, String time, int postManId, String pCity, boolean delivered,int cityID) {
        this(id,lat,lon,date,time,postManId,pCity,delivered);
        this.cityId=cityID;
    }

    public LocationVertex(int id, double lat, double lon, String date, String time, int postManId, String pCity, boolean delivered,int cityId,String pState,String eState,int stateId,String eCity ) {
        this(id,lat,lon,date,time,postManId,pCity,delivered);
        this.eState = eState;
        this.pState = pState;
        this.cityId = cityId;
        this.stateId = stateId;
        this.eCity=eCity;
    }

    public static LocationVertex getLocationVertex(String csv){
        String[] param=csv.split(",");
        return new LocationVertex(Integer.parseInt(param[0].trim()),
                Double.valueOf(param[1].trim()),
                Double.valueOf(param[2].trim()),
                param[3].trim(),
                param[4].trim(),
                Integer.parseInt(param[5].trim()),
                new String( param[6].trim().getBytes( Charset.forName("UTF-8" )), Charset.forName("UTF-8") ),
                Boolean.valueOf(param[7].trim()),
                Integer.parseInt(param[8].trim()),
                param[9].trim(),
                param[10].trim(),
                Integer.parseInt( param[11].trim()),
                param[12].trim()
                );
    }


    public int getInputTraffic() {
        return inputTraffic;
    }

    public void setInputTraffic(int inputTraffic) {
        this.inputTraffic = inputTraffic;
    }

    public int getOutputTraffic() {
        return outputTraffic;
    }

    public void setOutputTraffic(int outputTraffic) {
        this.outputTraffic = outputTraffic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPostManId() {
        return postManId;
    }

    public void setPostManId(int postManId) {
        this.postManId = postManId;
    }

    public String getpCity() {
        return pCity;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String toString()
    {
        return ("[lat:"+lat+"\tlon:"+lon);
    }

    public int getPostNodeId()
    {
        return postNodeId;
    }

    public void setPostNodeId(int postNodeId)
    {
        this.postNodeId = postNodeId;
    }

    public int getEventId()
    {
        return eventId;
    }

    public void setEventId(int eventId)
    {
        this.eventId = eventId;
    }

    public int getCityId()
    {
        return cityId;
    }

    public void setCityId(int cityId)
    {
        this.cityId = cityId;
    }


    public String getCSV(){
        String csvLine="";
        csvLine=this.id+","+
                this.lat+","+
                this.lon+","+
                this.date+","+
                this.time+","+
                this.postManId+","+
                this.pCity+","+
                this.delivered+","+
                this.cityId+","+
                this.pState+","+
                this.eState+","+
                this.stateId;
        return csvLine;
    }

    public String geteCity() {
        return eCity;
    }

    public void seteCity(String eCity) {
        this.eCity = eCity;
    }

    public String geteState() {
        return eState;
    }

    public void seteState(String eState) {
        this.eState = eState;
    }

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    @Override
    public boolean equals(Object obj)
    {

        if(obj instanceof LocationVertex){
            if(((LocationVertex) obj).getCityId()==this.getCityId())
                return  true;
            else if(((LocationVertex) obj).getLat()==this.getLat() && ((LocationVertex) obj).getLon()==this.getLon())
                return true;
        }
        return false;
    }
}
