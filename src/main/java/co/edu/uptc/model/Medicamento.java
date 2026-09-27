package co.edu.uptc.model;

public class Medicamento {
    private String id;
    private String nombreMedicamento;
    private String dosisAplicada;
    private int cantidad;
    private double precioUnitario;

    

    public Medicamento(String id, String nombreMedicamento, String dosisAplicada, int cantidad, double precioUnitario) {
        this.id =  id;
        this.nombreMedicamento = nombreMedicamento;
        this.dosisAplicada = dosisAplicada;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }
    
    public Medicamento(){

    }
    

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getDosisAplicada() {
        return dosisAplicada;
    }

    public void setDosisAplicada(String dosisAplicada) {
        this.dosisAplicada = dosisAplicada;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double CalcularCostoMedicamento(){//falta corregir este metodo
        return 0;
    }

    @Override
    public String toString() {
        return "Medicamento [nombreMedicamento=" + nombreMedicamento + ", dosisAplicada=" + dosisAplicada
                + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + "]";
    }

    public double calcularTotalMedicamentos(double porcentajeImpuesto) {
        double subtotal = this.cantidad * this.precioUnitario;
        double montoImpuesto = subtotal * porcentajeImpuesto;
        return subtotal + montoImpuesto;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    

    
}
