package com.example.plantilla.beans;

public class Usuario {
    private int id_usuario;
    private String nombres;
    private String apellidos;
    private String email;
    private String creado_en;
    private String actualizado_en;

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreado_en() {
        return creado_en;
    }

    public void setCreado_en(String creado_en) {
        this.creado_en = creado_en;
    }

    public String getActualizado_en() {
        return actualizado_en;
    }

    public void setActualizado_en(String actualizado_en) {
        this.actualizado_en = actualizado_en;
    }
}
