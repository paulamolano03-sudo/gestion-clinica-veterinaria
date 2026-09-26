package co.edu.uptc.model;

import java.time.LocalDateTime;

public class Cita {
    
    private String id;
    private Paciente paciente;
    private Veterinario veterinario;
    private String motivo;
    private LocalDateTime fechaHora;
    private double costo;

    public Cita() {
    }

    public Cita(String id, Paciente paciente, Veterinario veterinario, String motivo, LocalDateTime fechaHora, double costo) {
        this.id = id;
        this.paciente = paciente;
        this.veterinario = veterinario;
        this.motivo = motivo;
        this.fechaHora = fechaHora;
        this.costo = costo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id='" + id + '\'' +
                ", paciente=" + paciente +
                ", veterinario=" + veterinario +
                ", motivo='" + motivo + '\'' +
                ", fechaHora=" + fechaHora +
                ", costo=" + costo +
                '}';
    }
}
