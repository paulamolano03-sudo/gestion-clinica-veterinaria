package co.edu.uptc.controller;

import java.time.LocalTime;
import java.util.List;

import co.edu.uptc.model.Especialidad;
import co.edu.uptc.model.Veterinario;
import co.edu.uptc.sevice.VeterinarioService;

public class VeterinarioController {

    private VeterinarioService veterinarioService;

    public VeterinarioController(){
        this.veterinarioService = new VeterinarioService();
    }

    public String registrarVeterinario(String id,String nombre, String especialidadStr, String horaEntradaStr) {
        try {
   
            String enumFormateado = especialidadStr.toUpperCase().replace(" ", "_");
            Especialidad especialidad = Especialidad.valueOf(enumFormateado);
            
            LocalTime horaEntrada = LocalTime.parse(horaEntradaStr); 

            Veterinario nuevoVet = new Veterinario(id, nombre, especialidad, horaEntrada);
            this.veterinarioService.registrarVeterinarios(nuevoVet);
            
            return "Se registro correctametne el veterinario";
            
        } catch (IllegalArgumentException e) {
            return "Error: La especialidad no existe o el formato de hora es inválido (Formato valido: HH:MM";
        }
    }

    public List<Veterinario> obtenerTodosLosVeterinarios(){
        return this.veterinarioService.getVeterinarios();
    }
    
}
