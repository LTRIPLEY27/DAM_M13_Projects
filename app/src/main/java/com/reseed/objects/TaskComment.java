package com.reseed.objects;

public class TaskComment {



    /**
     *  "descripcion": "REFORESTACION DE ROBLES",
     *  "fecha": "2023-03-22",
     *  "tarea": "REFORESTACION DE ROBLES",
     *  "tecnico": "davidf"
     */

    private String descripcion, fecha, tarea, tecnico;

    /**
     * Constructor de la clase TaskComment, son los comentarios dentro de las tareas.
     * @param descripcion
     * @param fecha
     * @param tarea
     * @param user
     */
    public TaskComment(String descripcion, String fecha, String tarea, String user) {
        setDescripcion(descripcion);
        setFecha(fecha);
        setTarea(tarea);
        setUser(user);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TaskComment setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public String getFecha() {
        return fecha;
    }

    public TaskComment setFecha(String fecha) {
        this.fecha = fecha;
        return this;
    }

    public String getTarea() {
        return tarea;
    }

    public TaskComment setTarea(String tarea) {
        this.tarea = tarea;
        return this;
    }

    public String getUser() {
        return tecnico;
    }

    public TaskComment setUser(String tecnico) {
        this.tecnico = tecnico;
        return this;
    }
}