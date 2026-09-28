package co.edu.uptc.controller;

import java.util.List;

import co.edu.uptc.model.Dueno;
import co.edu.uptc.model.Paciente;
import co.edu.uptc.persistence.DuenosService;
import co.edu.uptc.persistence.MascotasService;
import co.edu.uptc.sevice.ClienteService;

public class ClienteController {
    
    private final ClienteService clienteService;

	public ClienteController() {
	DuenosService duenosService = new DuenosService();
    MascotasService mascotasService = new MascotasService();

    this.clienteService = new ClienteService(duenosService, mascotasService);
    }

        public ClienteService getClienteService() {
        return clienteService;
    }

    // dueños

    public void registrarDueno(Dueno dueno) {
        clienteService.registrarDueno(dueno);
    }

    public Dueno consultarDueno(String id) {
        return clienteService.consultarDueno(id);
    }

    public boolean existeDueno(String id) {
        return clienteService.validarDuenoExiste(id);
    }

    public List<Dueno> listarDuenos() {
        return clienteService.listaDuenos();
    }

    public void editarNombreDueno(String id, String nuevoNombre) {
        clienteService.editarNombreDueno(id, nuevoNombre);
    }

    public void editarTelefonoDueno(String id, String nuevoTelefono) {
        clienteService.editarTelefonoDueno(id, nuevoTelefono);
    }

    public void editarDireccionDueno(String id, String nuevaDireccion) {
        clienteService.editarDireccionDueno(id, nuevaDireccion);
    }

    public void editarEmailDueno(String id, String nuevoEmail) {
        clienteService.editarEmailDueno(id, nuevoEmail);
    }

    // mascotas

    public void registrarMascota(Paciente paciente, String duenoId) {
        clienteService.registrarPaciente(paciente, duenoId);
    }

    public Paciente consultarMascota(String id) {
        return clienteService.consultarPaciente(id);
    }

    public boolean existeMascota(String id) {
        return clienteService.validarPacienteExiste(id);
    }

    public List<Paciente> listarMascotas() {
        return clienteService.listarPacientes();
    }

    public void editarNombreMascota(String id, String nuevoNombre) {
        clienteService.editarNombrePaciente(id, nuevoNombre);
    }

    public void editarRazaMascota(String id, String nuevaRaza) {
        clienteService.editarRazaPaciente(id, nuevaRaza);
    }

    public void editarEdadMascota(String id, int nuevaEdad) {
        clienteService.editarEdadPaciente(id, nuevaEdad);
    }

    public void editarPesoMascota(String id, double nuevoPeso) {
        clienteService.editarPesoPaciente(id, nuevoPeso);
    }

    public void editarAlergiasMascota(String id, List<String> nuevasAlergias) {
        clienteService.editarAlergiasPaciente(id, nuevasAlergias);
    }




}
