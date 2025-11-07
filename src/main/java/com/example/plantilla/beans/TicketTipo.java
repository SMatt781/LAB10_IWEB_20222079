package com.example.plantilla.beans;

public class TicketTipo {
    private int id_ticket_tipo;
    private int id_evento;
    private String nombre;
    private double precio;
    private int cupo_total;
    private int cupo_disponible;

    public int getId_ticket_tipo() {
        return id_ticket_tipo;
    }

    public void setId_ticket_tipo(int id_ticket_tipo) {
        this.id_ticket_tipo = id_ticket_tipo;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCupo_total() {
        return cupo_total;
    }

    public void setCupo_total(int cupo_total) {
        this.cupo_total = cupo_total;
    }

    public int getCupo_disponible() {
        return cupo_disponible;
    }

    public void setCupo_disponible(int cupo_disponible) {
        this.cupo_disponible = cupo_disponible;
    }
}
