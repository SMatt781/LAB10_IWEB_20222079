package com.example.plantilla.dtos;

import com.example.plantilla.beans.Usuario;

public class ReservaListadoDTO {

    private String TituloEvento;
    private String fechaEvento;
    private Usuario usuario;
    private String email;
    private String nombreTicket;
    private int cantidadReservas;
    private int IdItem;


    public String getTituloEvento() {
        return TituloEvento;
    }

    public void setTituloEvento(String tituloEvento) {
        TituloEvento = tituloEvento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreTicket() {
        return nombreTicket;
    }

    public void setNombreTicket(String nombreTicket) {
        this.nombreTicket = nombreTicket;
    }

    public int getCantidadReservas() {
        return cantidadReservas;
    }

    public void setCantidadReservas(int cantidadReservas) {
        this.cantidadReservas = cantidadReservas;
    }

    public int getIdItem() {
        return IdItem;
    }

    public void setIdItem(int IdItem) {
        this.IdItem = IdItem;
    }
}
