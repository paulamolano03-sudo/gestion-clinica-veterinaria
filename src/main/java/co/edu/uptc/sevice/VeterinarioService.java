package co.edu.uptc.sevice;

import co.edu.uptc.model.Veterinario;
import co.edu.uptc.persistence.VeterinarioRepository;

import java.util.ArrayList;
import java.util.List;

public class VeterinarioService {

    private VeterinarioRepository veterinarioRepository;
    private List<Veterinario> veterionarios;

    public VeterinarioService() {
        this.veterinarioRepository = new VeterinarioRepository();
        this.veterionarios = this.veterinarioRepository.leerVeterinarios();

        if (this.veterionarios == null) {
            this.veterionarios = new ArrayList<>();
        }
    }

    public void registrarVeterinarios(Veterinario veterinario) {
        if (veterinario == null) {
            return;
        }

        veterionarios.add(veterinario);
        this.veterinarioRepository.guardarVeterinarios(veterionarios);
        System.out.println("Se ha registrado el veterinario");
    }

    public List<Veterinario> getVeterinarios() {
        return this.veterionarios;
    }

    public Veterinario buscarVeterinario(String nombre) {
        if (nombre == null) {
            return null;
        }

        for (Veterinario aux : this.veterionarios) {
            if (aux.getNombre() != null && aux.getNombre().trim().equalsIgnoreCase(nombre.trim())) {
                return aux;
            }
        }
        return null;
    }
}