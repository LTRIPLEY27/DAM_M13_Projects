package com.reseed.objects;

public class MapPoint {

    private float latitude, longitude;
    private int id;

    /**
     * Constructor del objeto MapPoint, donde se guardan los puntos del mapa.
     * @param latitude parametro en forma de String con la latitud.
     * @param longitude parametro en forma de String con la longitud.
     */
    public MapPoint(int id, float latitude, float longitude){
        setId(id);
        setLatitude(latitude);
        setLongitude(longitude);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
