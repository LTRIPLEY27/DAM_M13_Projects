package com.reseed.objects;

public class TaskLocation {

    private Long latitud, longitud;
    private Float zoom;
    private Integer idMapa;

    /**
     * Constructor de la clase TaskLocation, que se encarga de localizar
     * el centro del mapa donde aparecera la zona de trabajo.
     * @param latitud Posición del centro del mapa.
     * @param longitud Posición del centro del mapa.
     * @param zoom Zoom inicial del mapa por defecto.
     * @param idMapa Id del mapa relacionado.
     */
    public  TaskLocation (Long latitud, Long longitud, Float zoom, Integer idMapa){
        setLatitud(latitud);
        setLongitud(longitud);
        setZoom(zoom);
        setIdMapa(idMapa);
    }

    public Long getLatitud() {
        return latitud;
    }

    public void setLatitud(Long latitud) {
        this.latitud = latitud;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }

    public Float getZoom() {
        return zoom;
    }

    public void setZoom(Float zoom) {
        this.zoom = zoom;
    }

    public Integer getIdMapa() {
        return idMapa;
    }

    public void setIdMapa(Integer idMapa) {
        this.idMapa = idMapa;
    }
}
