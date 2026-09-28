package co.edu.uptc.persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.model.Paciente;

public class MascotasService {

    private static final String RUTA_ARCHIVO = "src/main/resources/data/mascotas.json";
    private List <Paciente> pacientes;
    private final Gson gson;

    public MascotasService() {
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting();
        this.gson = gb.create();

        this.pacientes = cargarDatos();
    }

    public void guardar(Paciente paciente) {
        this.pacientes.add(paciente);
        guardarDatosEnArchivo();
    }

    public List<Paciente> listar() {
        return new ArrayList<>(this.pacientes); // copia: la lista del DAO solo cambia con guardar/actualizar/eliminar
    }

    public Paciente buscarPorId(String id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId().equalsIgnoreCase(id)) {
                return paciente;
            }
        }
        return null;
    }

    public void actualizar(Paciente mascotaActualizado) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId().equalsIgnoreCase(mascotaActualizado.getId())) {
                pacientes.set(i, mascotaActualizado);
                guardarDatosEnArchivo();
                return;
            }
        }
    }

    public void eliminar(String id) {
        pacientes.removeIf(mascota -> mascota.getId().equalsIgnoreCase(id));
        guardarDatosEnArchivo();
    }

        private List<Paciente> cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String linea = br.readLine();

            while (linea != null) {
                sb.append(linea);
                linea = br.readLine();
            }

            Type listType = new TypeToken<List<Paciente>>() { }.getType();
            List<Paciente> datosCargados = gson.fromJson(sb.toString(), listType);

            if (datosCargados != null) {
                return datosCargados;
            } else {
                return new ArrayList<>();
            }

        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException ex) {
            System.out.println("Error al leer mascotas.json: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    // GUARDAR DATOS

    private void guardarDatosEnArchivo() {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO, StandardCharsets.UTF_8)) {
            gson.toJson(this.pacientes, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar en mascotas.json: " + e.getMessage());
        }
    }


}
