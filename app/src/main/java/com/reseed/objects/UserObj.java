package com.reseed.objects;

import java.util.ArrayList;
import java.util.List;

public class UserObj {

    private String id;
    private String user;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    //private String tokenUsuario;
    private String tipoUsuario;

    // Lista de tareas del usuario.
    private ArrayList<TaskObj> taskObjs;

    /**
     * Todo Acabar de rellenar y comprovar los campos, segun los datos de la DB.
     * Objeto de usuario, contiene toda la informacion del usuario.
     * @param id id del usuario
     * @param user user del usuario
     * @param name nombre del usuario
     * @param surname apellido del usuario
     * @param email correo electronico del usuario
     * @param phone telefono del usuario
     * @param userType tipo de usuario
     */
    public UserObj(String id, String user,String name, String surname, String email, String phone, String userType){

        setId(id);
        setUser(user);
        setNombre(name);
        setApellido(surname);
        setEmail(email);
        setTelefono(phone);
        //setTokenUsuario(userToken);
        setTipoUsuario(userType);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /*public String getTokenUsuario() {
        return tokenUsuario;
    }*/

    /*public void setTokenUsuario(String tokenUsuario) {
        this.tokenUsuario = tokenUsuario;
    }*/

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public ArrayList<TaskObj> getTaskObjs() {
        return taskObjs;
    }

    public void setTaskObjs(ArrayList<TaskObj> taskObjs) {
        this.taskObjs = taskObjs;
    }
}
