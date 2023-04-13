package com.reseed.objects;

public class TaskComment {



    /**
     *  "descripcion": "REFORESTACION DE ROBLES",
     *  "fecha": "2023-03-22",
     *  "tarea": "REFORESTACION DE ROBLES",
     *  "tecnico": "davidf"
     */

    private String id, descripcion, fecha, tarea, tecnico, admin;

    /**
     * Constructor de la clase TaskComment, son los comentarios dentro de las tareas.
     * @param descripcion
     * @param fecha
     * @param tarea
     * @param tecnico
     * @param admin
     */
    public TaskComment(String id, String descripcion, String fecha, String tarea, String tecnico, String admin) {
        setId(id);
        setDescripcion(descripcion);
        setFecha(fecha);
        setTarea(tarea);
        setTecnico(tecnico);
        setAdmin(admin);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
