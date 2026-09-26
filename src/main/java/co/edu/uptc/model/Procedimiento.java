package co.edu.uptc.model;

public class Procedimiento {
    private String nombreProcedimiento;
    private String tipoProcedimiento;
    private double costo;

    public Procedimiento(String nombreProcedimiento, String tipoProcedimiento, double costo) {
        this.nombreProcedimiento = nombreProcedimiento;
        this.tipoProcedimiento = tipoProcedimiento;
        this.costo = costo;
    }

    public Procedimiento(){

    }

    public String getNombreProcedimiento() {
        return nombreProcedimiento;
    }

    public void setNombreProcedimiento(String nombreProcedimiento) {
        this.nombreProcedimiento = nombreProcedimiento;
    }

    public String getTipoProcedimiento() {
        return tipoProcedimiento;
    }

    public void setTipoProcedimiento(String tipoProcedimiento) {
        this.tipoProcedimiento = tipoProcedimiento;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Procedimiento [nombreProcedimiento=" + nombreProcedimiento + ", tipoProcedimiento=" + tipoProcedimiento
                + ", costo=" + costo + "]";
    }

    
    
}
