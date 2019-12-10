package com.example.loginsegurity;

public class Usuario {

    private String id, email, nombre;

    public Usuario (String id, String email, String nombre){
        this.id = id;
        this.email = email;
        this.nombre = nombre;
    }
    public Usuario(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public  String toStrinig(){
        return email;
    }
}
