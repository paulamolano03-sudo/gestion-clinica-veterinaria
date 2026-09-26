package co.edu.uptc.model;

import java.time.LocalDateTime;
import java.util.List;

public class Consulta {
    private String id;
    private LocalDateTime fecha;
    private double pesoMedido;
    private String sintomas;
    private String diagnostico;
    private Procedimiento procedimiento;
    private List<Medicamento> medicamentos;


    public Consulta(String id, LocalDateTime fecha, double pesoMedido, String sintomas, String diagnostico,
            Procedimiento procedimiento, List<Medicamento> medicamentos) {
        this.id = id;
        this.fecha = fecha;
        this.pesoMedido = pesoMedido;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.procedimiento = procedimiento;
        this.medicamentos = medicamentos;
    }

    public Consulta(){
        
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public LocalDateTime getFecha() {
        return fecha;
    }


    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }


    public double getPesoMedido() {
        return pesoMedido;
    }


    public void setPesoMedido(double pesoMedido) {
        this.pesoMedido = pesoMedido;
    }


    public String getSintomas() {
        return sintomas;
    }


    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }


    public String getDiagnostico() {
        return diagnostico;
    }


    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }


    public Procedimiento getProcedimiento() {
        return procedimiento;
    }


    public void setProcedimiento(Procedimiento procedimiento) {
        this.procedimiento = procedimiento;
    }


    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }


    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }


    
}
