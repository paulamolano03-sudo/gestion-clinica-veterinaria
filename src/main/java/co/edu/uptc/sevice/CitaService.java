package co.edu.uptc.sevice;

import co.edu.uptc.model.Cita;
import co.edu.uptc.model.Veterinario;
import co.edu.uptc.persistence.CitaRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CitaService {
    private List<Cita> citas;
    private CitaRepository citaRepository;
    public CitaService(){
        this.citaRepository = new CitaRepository();
        this.citas = citaRepository.leerCitas();

        if (this.citas == null) {
            this.citas = new ArrayList<>();
        }
    }

    private boolean horarioDisponible(Veterinario veterinario, LocalDateTime fechaHora){
        for (Cita aux : citas){
            if (aux.getVeterinario().getNombre().equalsIgnoreCase(veterinario.getNombre())) {
                
                LocalDateTime fechaConocida = aux.getFechaHora();
                long diferenciaMinutos = Math.abs(ChronoUnit.MINUTES.between(fechaConocida, fechaHora));

                if (diferenciaMinutos < 30) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean estaEnHorario(Veterinario veterinario, LocalDateTime fechaHora){
        LocalTime horaEntrada = veterinario.getHorariosAtencion();
        if (horaEntrada == null) {
            return false;
        }

        LocalTime horaSalida = horaEntrada.plusHours(8);
        LocalTime horaPropuesta = fechaHora.toLocalTime();

        if (horaPropuesta.isBefore(horaEntrada) || horaPropuesta.isAfter(horaSalida)) {
            return false;
        }
        return true;

    }

    public boolean agendarCita(Cita cita){
        if (cita == null || cita.getVeterinario() == null || cita.getFechaHora() == null) {
            return false;
        }
        Veterinario vet = cita.getVeterinario();
        LocalDateTime fechaHora = cita.getFechaHora();

        if (!estaEnHorario(vet, fechaHora)) {
            return false;
        }

        if (!horarioDisponible(vet, fechaHora)) {
            return false;
        }

        this.citas.add(cita);
        this.citaRepository.guardarCitas(citas);

        return true;
    }

    public List<Cita> obtenerCitasPorVeterinario(String nombre){
        List <Cita> citasVet = new ArrayList<>();
        for (Cita aux : this.citas){
            if (aux.getVeterinario().getNombre().equalsIgnoreCase(nombre)) {
                citasVet.add(aux);
            }
        }
        return citasVet;
    }

    
}
