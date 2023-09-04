
package com.alura.models;

import java.time.LocalDate;

public class Reserva {
    
    Integer id;
    Integer huespedId;
    LocalDate fechaIngreso;
    LocalDate fechaEgreso;
    Double valorTotal;
    String metodoPago;
    
    Reserva() {}

    public Reserva(Integer huespedId, LocalDate fechaIngreso, LocalDate fechaEgreso, String metodoPago) {
        
        this.huespedId = huespedId;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.valorTotal = Double.valueOf((fechaEgreso.getDayOfYear() - fechaIngreso.getDayOfYear()) * getPrecioNoche());
        this.metodoPago = metodoPago;
        
    }

    public Reserva(Integer id, Integer huespedId, LocalDate fechaIngreso, LocalDate fechaEgreso, Double valorTotal, String metodoPago) {
        
        this.id = id;
        this.huespedId = huespedId;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.valorTotal = valorTotal;
        this.metodoPago = metodoPago;
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHuespedId() {
        return huespedId;
    }

    public void setHuespedId(Integer huespedId) {
        this.huespedId = huespedId;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(LocalDate fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }
    
    public double getPrecioNoche(){
        return 1500.0;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

}
