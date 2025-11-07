package com.example.plantilla.dtos;

public class TicketListadoDTO {

    private String tituloEvento;
    private String descripcionEvento;
    private String fechaEvento;
    private String lugarEvento;

    private String nombreTicket;
    private double precio;
    private int cupoTotal;
    private int cupoDisponible;
    private int idTicketTipo;

    public String getTituloEvento() {
        return tituloEvento;
    }

    public void setTituloEvento(String tituloEvento) {
        this.tituloEvento = tituloEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

    public String getNombreTicket() {
        return nombreTicket;
    }

    public void setNombreTicket(String nombreTicket) {
        this.nombreTicket = nombreTicket;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(int cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public int getIdTicketTipo() {
        return idTicketTipo;
    }

    public void setIdTicketTipo(int idTicketTipo) {
        this.idTicketTipo = idTicketTipo;
    }

    public int getCupoTotal() {
        return cupoTotal;
    }

    public void setCupoTotal(int cupoTotal) {
        this.cupoTotal = cupoTotal;
    }
}
