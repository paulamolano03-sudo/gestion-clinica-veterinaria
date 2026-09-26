package co.edu.uptc.model;


import java.time.LocalTime;

public class Veterinario {
    
    private String nombre;
    private Especialidad especialidad;
    private LocalTime horariosAtencion; // revision 

    public Veterinario() {
    }

    public Veterinario(String nombre, Especialidad especialidad, LocalTime horariosAtencion) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.horariosAtencion = horariosAtencion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public LocalTime getHorariosAtencion() {
        return horariosAtencion;
    }

    public void setHorariosAtencion(LocalTime horariosAtencion) {
        this.horariosAtencion = horariosAtencion;
    }

    @Override
    public String toString() {
        return "Veterinario{" +
                "nombre='" + nombre + '\'' +
                ", especialidad=" + especialidad +
                ", horariosAtencion=" + horariosAtencion +
                '}';
    }
}
