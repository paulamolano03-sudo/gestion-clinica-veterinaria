package co.edu.uptc.model;


import java.time.LocalTime;

public class Veterinario {
    
    private String id;
    private String nombre;
    private Especialidad especialidad;
    private LocalTime horariosAtencion; 

    public Veterinario() {
    }

    

    public Veterinario(String id, String nombre, Especialidad especialidad, LocalTime horariosAtencion) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.horariosAtencion = horariosAtencion;
    }



        public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
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

