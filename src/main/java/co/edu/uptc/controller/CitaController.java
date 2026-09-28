package co.edu.uptc.controller;

import co.edu.uptc.model.Cita;
import co.edu.uptc.model.Paciente;
import co.edu.uptc.model.Veterinario;
import co.edu.uptc.sevice.CitaService;
import co.edu.uptc.sevice.VeterinarioService;

import java.time.LocalDateTime;
import java.util.List;

public class CitaController {

    private CitaService citaService;
    private VeterinarioService veterinarioService;

    public CitaController(VeterinarioService veterinarioService){
        this.citaService = new CitaService();
        this.veterinarioService = veterinarioService;
    }

    public String agendarCita (String id, Paciente paciente, String nombreVet, String motivo, String fechaHoraStr, double costo){
        Veterinario veterinarioEncontrado = this.veterinarioService.buscarVeterinario(nombreVet);

        if (veterinarioEncontrado == null) {
            return "El veterinario con el nombre: " + nombreVet + " no existe en el sistema";
        }
        try {
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr);
            Cita nuevaCita = new Cita(id, paciente, veterinarioEncontrado, motivo, fechaHora, costo);
            boolean creado = this.citaService.agendarCita(nuevaCita);
            if (creado) {
                return "La cita fue creada correctamente";
            } else {
                return "No se pudo crear la cita con exito, probablemente esta fuera del turno o coincide con otra";
            }

        } catch (Exception e) {
            return "Error: El formato de la fecha es incorrecto. Debe ser YYYY-MM-DDTHH:MM";
        }
    }

    public List<Cita> ConsultarAgendaVeterinario(String nombreVet){
        return this.citaService.obtenerCitasPorVeterinario(nombreVet);
    }
}
