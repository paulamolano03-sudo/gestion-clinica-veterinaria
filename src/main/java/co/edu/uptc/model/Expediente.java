package co.edu.uptc.model;

import java.util.List;

public class Expediente {
    private String id;
    private List<Consulta> consultas;
    private List<String> historialVacunas;
    private List<String> historialCirugias;

    public Expediente(String id, List<Consulta> consultas, List<String> historialVacunas,
            List<String> historialCirugias) {
        this.id = id;
        this.consultas = consultas;
        this.historialVacunas = historialVacunas;
        this.historialCirugias = historialCirugias;
    }

    public Expediente(){
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public List<String> getHistorialVacunas() {
        return historialVacunas;
    }

    public void setHistorialVacunas(List<String> historialVacunas) {
        this.historialVacunas = historialVacunas;
    }

    public List<String> getHistorialCirugias() {//
        return historialCirugias;
    }

    public void setHistorialCirugias(List<String> historialCirugias) {
        this.historialCirugias = historialCirugias;
    }

    @Override
    public String toString() {
        return "Expediente [id=" + id + ", consultas=" + consultas + ", historialVacunas=" + historialVacunas
                + ", historialCirugias=" + historialCirugias + "]";
    }

    
}
