package com.reseed.objects;

public class MapPoint {

    private String latitude;
    private String longitude;

    /**
     * Constructor del objeto MapPoint, donde se guardan los puntos del mapa.
     * @param latitude parametro en forma de String con la latitud.
     * @param longitude parametro en forma de String con la longitud.
     */
    public MapPoint(String latitude, String longitude){
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public String getLatitude() {
        return latitude;
    }

    public MapPoint setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public MapPoint setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }
}
