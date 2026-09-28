package co.edu.uptc.sevice;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.model.Dueno;
import co.edu.uptc.model.Expediente;
import co.edu.uptc.model.Paciente;
import co.edu.uptc.persistence.DuenosService;
import co.edu.uptc.persistence.MascotasService;

public class ClienteService {

    private final DuenosService duenosService;
    private final MascotasService mascotasService;

    public ClienteService(DuenosService duenosService, MascotasService mascotasService) {
        this.duenosService = duenosService;
        this.mascotasService = mascotasService;
        enlazarDuenos();
    }

    // Metodos de los dueños :p

    public void registrarDueno(Dueno dueno) {
        validarDueno(dueno);
        if (validarDuenoExiste(dueno.getId())) {
            throw new IllegalArgumentException("Ya existe un dueño con ID " + dueno.getId());
        }
        dueno.setId(dueno.getId().trim());
        dueno.setNombre(dueno.getNombre().trim());
        dueno.setTelefono(dueno.getTelefono().trim());

        duenosService.guardar(dueno);
    }

    public Dueno buscarDueno(String id) {
        if (estaVacio(id)) {
            return null;
        }
        return duenosService.buscarPorId(id.trim());
    }

    public Dueno consultarDueno(String id) {
        Dueno dueno = buscarDueno(id);
        if (dueno == null) {
            throw new IllegalArgumentException("No existe un dueño con ID " + id);
        }
        return dueno;
    }

    public boolean validarDuenoExiste(String id) {
        return buscarDueno(id) != null;
    }

    // editar datos

    public void editarNombreDueno(String id, String nuevoNombre) {
        Dueno dueno = consultarDueno(id);
        validarCampoObligatorio(nuevoNombre, "nombre");
        dueno.setNombre(nuevoNombre.trim());
        duenosService.actualizar(dueno);
    }

    public void editarTelefonoDueno(String id, String nuevoTelefono) {
        Dueno dueno = consultarDueno(id);
        dueno.setTelefono(nuevoTelefono.trim());
        duenosService.actualizar(dueno);
    }

    /** La dirección es opcional: si llega vacía, se borra. */
    public void editarDireccionDueno(String id, String nuevaDireccion) {
        Dueno dueno = consultarDueno(id);
        if (estaVacio(nuevaDireccion)) {
            dueno.setDireccion(null);
        } else {
            dueno.setDireccion(nuevaDireccion.trim());
        }
        duenosService.actualizar(dueno);
    }

    /** El email es opcional: si llega vacío, se borra. */
    public void editarEmailDueno(String id, String nuevoEmail) {
        Dueno dueno = consultarDueno(id);
        if (estaVacio(nuevoEmail)) {
            dueno.setEmail(null);
        } else {
            dueno.setEmail(nuevoEmail.trim());
        }
        duenosService.actualizar(dueno);
    }

    public List<Dueno> listaDuenos() {
        return duenosService.listarDuenos();
    }

    // Metodos de los pacientes/mascotas :p

    public void registrarPaciente(Paciente paciente, String duenoId) {
        validarPaciente(paciente);
        if (validarPacienteExiste(paciente.getId())) {
            throw new IllegalArgumentException("Ya existe una mascota con ID " + paciente.getId());
        }
        Dueno dueno = consultarDueno(duenoId); // el dueño debe existir

        paciente.setId(paciente.getId().trim());
        paciente.setNombre(paciente.getNombre().trim());
        paciente.setRaza(paciente.getRaza().trim());
        paciente.setAlergiasConocidas(limpiarAlergias(paciente.getAlergiasConocidas()));
        paciente.setDueno(dueno);
        paciente.setExpediente(new Expediente());

        mascotasService.guardar(paciente);
    }

    public Paciente buscarPaciente(String id) {
        if (estaVacio(id)) {
            return null;
        }
        return mascotasService.buscarPorId(id.trim());
    }

    public Paciente consultarPaciente(String id) {
        Paciente paciente = buscarPaciente(id);
        if (paciente == null) {
            throw new IllegalArgumentException("No existe una mascota con ID " + id);
        }
        return paciente;
    }

    public boolean validarPacienteExiste(String id) {
        return buscarPaciente(id) != null;
    }

    // Editar datos paciente

    public void editarNombrePaciente(String id, String nuevoNombre) {
        Paciente paciente = consultarPaciente(id);
        validarCampoObligatorio(nuevoNombre, "nombre");
        paciente.setNombre(nuevoNombre.trim());
        mascotasService.actualizar(paciente);
    }

    public void editarRazaPaciente(String id, String nuevaRaza) {
        Paciente paciente = consultarPaciente(id);
        validarCampoObligatorio(nuevaRaza, "raza");
        paciente.setRaza(nuevaRaza.trim());
        mascotasService.actualizar(paciente);
    }

    public void editarEdadPaciente(String id, int nuevaEdad) {
        Paciente paciente = consultarPaciente(id);
        validarEdad(nuevaEdad);
        paciente.setEdad(nuevaEdad);
        mascotasService.actualizar(paciente);
    }

    public void editarPesoPaciente(String id, double nuevoPeso) {
        Paciente paciente = consultarPaciente(id);
        validarPeso(nuevoPeso);
        paciente.setPeso(nuevoPeso);
        mascotasService.actualizar(paciente);
    }

    public void editarAlergiasPaciente(String id, List<String> nuevasAlergias) {
        Paciente paciente = consultarPaciente(id);
        paciente.setAlergiasConocidas(limpiarAlergias(nuevasAlergias));
        mascotasService.actualizar(paciente);
    }

    public List<Paciente> listarPacientes() {
        return mascotasService.listar();
    }

    public List<Paciente> pacientesDeDueno(String duenoId) {
        Dueno dueno = consultarDueno(duenoId);
        List<Paciente> resultado = new ArrayList<>();
        for (Paciente paciente : mascotasService.listar()) {
            if (dueno.getId().equalsIgnoreCase(paciente.getDuenoId())) {
                resultado.add(paciente);
            }
        }
        return resultado;
    }

    // Validaciones

    private void validarDueno(Dueno dueno) {
        if (dueno == null) {
            throw new IllegalArgumentException("Los datos no pueden estar vacíos");
        }
        validarCampoObligatorio(dueno.getId(), "ID");
        validarCampoObligatorio(dueno.getNombre(), "nombre");
    }

    private void validarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("Los datos no pueden estar vacíos");
        }
        validarCampoObligatorio(paciente.getId(), "ID");
        validarCampoObligatorio(paciente.getNombre(), "nombre");
        if (paciente.getEspecie() == null) {
            throw new IllegalArgumentException("El campo especie es obligatorio");
        }
        validarCampoObligatorio(paciente.getRaza(), "raza");
        validarEdad(paciente.getEdad());
        validarPeso(paciente.getPeso());
    }


    private void validarCampoObligatorio(String valor, String nombreCampo) {
        if (estaVacio(valor)) {
            throw new IllegalArgumentException("El campo " + nombreCampo + " es obligatorio");
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }

    private void validarEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
    }

    private void validarPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero");
        }
    }

    private List<String> limpiarAlergias(List<String> alergias) {
        List<String> resultado = new ArrayList<>();
        if (alergias == null) {
            return resultado;
        }
        for (String alergia : alergias) {
            if (!estaVacio(alergia) && !resultado.contains(alergia.trim())) {
                resultado.add(alergia.trim());
            }
        }
        return resultado;
    }

    private void enlazarDuenos() {
        for (Paciente paciente : mascotasService.listar()) {
            Dueno dueno = buscarDueno(paciente.getDuenoId());
            if (dueno != null) { // si no existe, no se borra el duenoId
                paciente.setDueno(dueno);
            }
            if (paciente.getExpediente() == null) {
                paciente.setExpediente(new Expediente());
            }
        }
    }
}