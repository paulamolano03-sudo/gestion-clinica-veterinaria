package co.edu.uptc.model;

import java.time.LocalDateTime;

public class Factura {
    private String id;
    private LocalDateTime fechaEmision;
    private Consulta consulta;
    private int impuesto;
    private double total;

    public Factura(String id, LocalDateTime fechaEmision, Consulta consulta, int impuesto, double total) {
        this.id = id;
        this.fechaEmision = fechaEmision;
        this.consulta = consulta;
        this.impuesto = impuesto;
        this.total = total;
    }

    public Factura(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public int getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(int impuesto) {
        this.impuesto = impuesto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Factura [id=" + id + ", fechaEmision=" + fechaEmision + ", consulta=" + consulta + ", impuesto="
                + impuesto + ", total=" + total + "]";
    }
    
    


    
}
