package com.reseed.objects;

import java.util.ArrayList;

public class TaskLocation {

    private String id;
    private float latitud, longitud;
    private int zoom;
    private ArrayList<MapPoint> mapPoints;

    /**
     * Constructor de la clase TaskLocation, que se encarga de localizar
     * el centro del mapa donde aparecera la zona de trabajo.
     * @param latitud Posición del centro del mapa.
     * @param longitud Posición del centro del mapa.
     * @param zoom Zoom inicial del mapa por defecto.
     * @param mapPoints Puntos de geometria del mapa relacionado.
     */
    public  TaskLocation (String id,Float latitud, Float longitud, int zoom, ArrayList<MapPoint> mapPoints){
        setId(id);
        setLatitud(latitud);
        setLongitud(longitud);
        setZoom(zoom);
        setMapPoints(mapPoints);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public ArrayList<MapPoint> getMapPoints() {
        return mapPoints;
    }

    public void setMapPoints(ArrayList<MapPoint> mapPoints) {
        this.mapPoints = mapPoints;
    }
}
