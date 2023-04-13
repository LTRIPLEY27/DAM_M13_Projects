package com.reseed.objects;

public class MapPoint {

    private String latitude, longitude, id;

    /**
     * Constructor del objeto MapPoint, donde se guardan los puntos del mapa.
     * @param latitude parametro en forma de String con la latitud.
     * @param longitude parametro en forma de String con la longitud.
     */
    public MapPoint(String id, String latitude, String longitude){
        setId(id);
        setLatitude(latitude);
        setLongitude(longitude);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
