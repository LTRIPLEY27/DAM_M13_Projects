package com.reseed.objects;

import java.util.ArrayList;

public class TaskLocation {

    private String id, latitud, longitud;
    private String zoom;
    private ArrayList<MapPoint> mapPoints;

    /**
     * Constructor de la clase TaskLocation, que se encarga de localizar
     * el centro del mapa donde aparecera la zona de trabajo.
     * @param latitud Posición del centro del mapa.
     * @param longitud Posición del centro del mapa.
     * @param zoom Zoom inicial del mapa por defecto.
     * @param mapPoints Puntos de geometria del mapa relacionado.
     */
    public  TaskLocation (String id,String latitud, String longitud, String zoom, ArrayList<MapPoint> mapPoints){
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
    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    public ArrayList<MapPoint> getMapPoints() {
        return mapPoints;
    }

    public TaskLocation setMapPoints(ArrayList<MapPoint> mapPoints) {
        this.mapPoints = mapPoints;
        return this;
    }
}
