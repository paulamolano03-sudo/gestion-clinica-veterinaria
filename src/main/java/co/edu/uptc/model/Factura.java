package co.edu.uptc.model;

import java.time.LocalDateTime;

public class Factura {
    private String id;
    private String  fechaEmision;
    private Consulta consulta;
    private double impuesto;
    private double total;

    public Factura(String id, String fechaEmision, Consulta consulta, double impuesto, double total) {
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

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
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
