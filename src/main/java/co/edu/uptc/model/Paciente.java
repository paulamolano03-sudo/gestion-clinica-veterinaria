package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private String id;
    private String nombre;
    private Especie especie;
    private String raza;
    private int edad;
    private List <String> alergiasConocidas; 
    private Dueno dueno;
    private Expediente expediente;

    public Paciente() {
    }

    public Paciente(String id, String nombre, Especie especie, String raza, int edad, String idDueno) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.alergiasConocidas = new ArrayList<>();
        this.dueno = new Dueno();
        this.expediente = new Expediente();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Especie getEspecie() { return especie; }
    public void setEspecie(Especie especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

      public List<String> getAlergiasConocidas() {
        return alergiasConocidas;
    }

    public void setAlergiasConocidas(List<String> alergiasConocidas) {
        this.alergiasConocidas = alergiasConocidas;
    }

    public Dueno getDueno() {
        return dueno;
    }

    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }

    public Expediente getExpediente() {
        return expediente;
    }

    public void setExpediente(Expediente expediente) {
        this.expediente = expediente;
    }

    @Override
    public String toString() {
        return "Paciente [id=" + id + ", nombre=" + nombre + ", especie=" + especie + ", raza=" + raza + ", edad="
                + edad + ", alergiasConocidas=" + alergiasConocidas + ", dueno=" + dueno + ", expediente=" + expediente
                + "]";
    }
}
