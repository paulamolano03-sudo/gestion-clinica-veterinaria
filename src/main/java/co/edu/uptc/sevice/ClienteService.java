package co.edu.uptc.sevice;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.model.Dueno;
import co.edu.uptc.model.Expediente;
import co.edu.uptc.model.I18n;
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
  
  //Metodos de los dueños :p

   public void registrarDueno(Dueno dueno){
        validarDueno(dueno);
        if (validarDuenoExiste(dueno.getId())){
            throw new IllegalArgumentException(I18n.get("error.duenoExiste", dueno.getId()));
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
            throw new IllegalArgumentException(I18n.get("error.duenoNoExiste", id));
        }
        return dueno;
    }

    public boolean validarDuenoExiste(String id) {
        return buscarDueno(id) != null;
    }

    //editar datos

    public void editarNombreDueno(String id, String nuevoNombre) {
        Dueno dueno = consultarDueno(id);
        validarCampoObligatorio(id, nuevoNombre);
        dueno.setNombre(nuevoNombre.trim());
        duenosService.actualizar(dueno);
    }

    public void editarTelefonoDueno(String id, String nuevoTelefono) {
        Dueno dueno = buscarDueno(id);
        validarCampoObligatorio(id, nuevoTelefono);
        dueno.setTelefono(nuevoTelefono.trim());
        duenosService.actualizar(dueno);
    }

    
    public void editarDireccionDueno(String id, String nuevaDireccion) {
            Dueno dueno = buscarDueno(id);
            validarCampoObligatorio(id, nuevaDireccion);
            dueno.setDireccion(nuevaDireccion.trim());
        }

    public void editarEmailDueno(String id, String nuevoEmail) {    
        Dueno dueno = buscarDueno(id);
        validarCampoObligatorio(id, nuevoEmail);
        dueno.setEmail(nuevoEmail.trim());
        }


    public List<Dueno> listaDuenos(){
        return duenosService.listarDuenos();
    }

    //Metodos de los pacientes/mascotas :p

    public void registrarPaciente(Paciente paciente, String duenoId){
        validarPaciente(paciente);
        if (validarPacienteExiste(paciente.getId())){
            throw new IllegalArgumentException(I18n.get("error.datosNulos"));
        }

        validarEdad(paciente.getEdad());
        validarPeso(paciente.getPeso());

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
            throw new IllegalArgumentException(I18n.get("error.pacienteNoExiste", id));
        }
        return paciente;
    }

    //Editar datos paciente 

    public void editarNombrePaciente(String id, String nuevoNombre) {
        Paciente paciente = consultarPaciente(id);
        validarCampoObligatorio(id, nuevoNombre);
        paciente.setNombre(nuevoNombre.trim());
        mascotasService.actualizar(paciente);
    }

    public void editarRazaPaciente(String id, String nuevaRaza) {
        Paciente paciente = consultarPaciente(id);
        validarCampoObligatorio(nuevaRaza, "campo.raza");
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

    private void validarDueno(Dueno dueno) {
        if (dueno == null) {
            throw new IllegalArgumentException(I18n.get("error.datosNulos"));
        }
        validarCampoObligatorio(dueno.getId(), "campo.id");
        validarCampoObligatorio(dueno.getNombre(), "campo.nombre");
    }

     private void validarPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException(I18n.get("error.datosNulos"));
        }
        validarCampoObligatorio(paciente.getId(), "campo.id");
        validarCampoObligatorio(paciente.getNombre(), "campo.nombre");
        if (paciente.getEspecie() == null) {
            throw new IllegalArgumentException(I18n.get("error.campoRequerido", I18n.get("campo.especie")));
        }
        validarCampoObligatorio(paciente.getRaza(), "campo.raza");
        validarEdad(paciente.getEdad());
        validarPeso(paciente.getPeso());
    }


     private void validarCampoObligatorio(String valor, String claveCampo) {
        if (estaVacio(valor)) {
            throw new IllegalArgumentException(I18n.get("error.campoRequerido", I18n.get(claveCampo)));
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }
     private void validarEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException(I18n.get("error.edadInvalida"));
        }
    }

    private void validarPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException(I18n.get("error.pesoInvalido"));
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
            paciente.setDueno(buscarDueno(paciente.getDuenoId()));
            if (paciente.getExpediente() == null) {
                paciente.setExpediente(new Expediente());
            }
        }
    }

        public boolean validarPacienteExiste(String id) {
        return buscarPaciente(id) != null;
    }
    
}




