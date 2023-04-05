package com.reseed.objects;

import java.util.ArrayList;

public class TaskObj {
    private String name, description, tarea, estatus, tecnico, admin;
    private Long fecha_culminacion, fecha_creacion, fecha_asignacion;

    private TaskLocation taskLocation;

    private ArrayList<TaskComment> taskComments;

    /**
     * Todo Acabar de rellenar y comprovar los campos, segun los datos de la DB.
     * Objeto de tarea, contiene toda la informacion de la tarea.
     * @param name nombre de la tarea.
     * @param description contenido descriptivo de la tarea.
     * @param tarea tipo de tarea (analisis, limpieza y replantacion).
     * @param fecha_culminacion fecha de ejecucion de la tarea.
     * @param fecha_creacion fecha de creación de la tarea.
     * @param fecha_asignacion fecha de asignacion de la tarea.
     * @param estatus Estado de la tarea.
     * @param tecnico Tecnico asignado a la tarea. ******ID?******  <-------------------------
     * @param admin Administrador que ha asignado la tarea. ******ID?******  <-------------------------
     * @param taskLocation Localizacion central del mapa.
     * @param taskComments Comentarios de la tarea en un ArrayList.
     */
    public TaskObj(String name, String description, String tarea, Long fecha_culminacion, Long fecha_creacion,
                   Long fecha_asignacion, String estatus, String tecnico, String admin,
                   TaskLocation taskLocation, ArrayList<TaskComment> taskComments){

        setName(name);
        setDescription(description);
        setTarea(tarea);
        setFecha_creacion(fecha_creacion);
        setFecha_culminacion(fecha_culminacion);
        setFecha_asignacion(fecha_asignacion);
        setEstatus(estatus);
        setTecnico(tecnico);
        setAdmin(admin);
        setTaskLocation(taskLocation);
        setTaskComments(taskComments);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public TaskObj setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTarea() {
        return tarea;
    }

    public TaskObj setTarea(String tarea) {
        this.tarea = tarea;
        return this;
    }

    public String getEstatus() {
        return estatus;
    }

    public TaskObj setEstatus(String estatus) {
        this.estatus = estatus;
        return this;
    }

    public String getTecnico() {
        return tecnico;
    }

    public TaskObj setTecnico(String tecnico) {
        this.tecnico = tecnico;
        return this;
    }

    public String getAdmin() {
        return admin;
    }

    public TaskObj setAdmin(String admin) {
        this.admin = admin;
        return this;
    }

    public Long getFecha_culminacion() {
        return fecha_culminacion;
    }

    public TaskObj setFecha_culminacion(Long fecha_culminacion) {
        this.fecha_culminacion = fecha_culminacion;
        return this;
    }

    public Long getFecha_creacion() {
        return fecha_creacion;
    }

    public TaskObj setFecha_creacion(Long fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
        return this;
    }

    public Long getFecha_asignacion() {
        return fecha_asignacion;
    }

    public TaskObj setFecha_asignacion(Long fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
        return this;
    }

    public TaskLocation getTaskLocation() {
        return taskLocation;
    }

    public TaskObj setTaskLocation(TaskLocation taskLocation) {
        this.taskLocation = taskLocation;
        return this;
    }

    public ArrayList<TaskComment> getTaskComments() {
        return taskComments;
    }

    public TaskObj setTaskComments(ArrayList<TaskComment> taskComments) {
        this.taskComments = taskComments;
        return this;
    }
}
