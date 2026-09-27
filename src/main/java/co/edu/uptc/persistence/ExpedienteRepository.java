package co.edu.uptc.persistence;

import co.edu.uptc.model.Expediente; 
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExpedienteRepository {

    private static final String RUTA_ARCHIVO = "data/expedientes.json";
    private List<Expediente> expedientes;
    private final Gson gson;

    public ExpedienteRepository() {
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting(); 
        this.gson = gb.create(); 
        
        this.expedientes = cargarDatos(); 
    }

    
    
    public void guardar(Expediente expediente) {
        this.expedientes.add(expediente);
        guardarDatosEnArchivo(); 
    }

    public List<Expediente> listar() {
        return this.expedientes;
    }

    public Expediente buscarPorId(String id) {
        for (Expediente exp : expedientes) {
            if (exp.getId().equals(id)) { // Dependerá de cómo llamaste a tu ID en el modelo
                return exp;
            }
        }
        return null;
    }

    public void actualizar(Expediente expedienteActualizado) {
        for (int i = 0; i < expedientes.size(); i++) {
            if (expedientes.get(i).getId().equals(expedienteActualizado.getId())) {
                expedientes.set(i, expedienteActualizado); 
                guardarDatosEnArchivo(); 
                return;
            }
        }
    }

    public void eliminar(String id) {
        expedientes.removeIf(exp -> exp.getId().equals(id)); 
        guardarDatosEnArchivo(); 
    }

    //CARGAR DATOS
    
    private List<Expediente> cargarDatos() {
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            StringBuilder sb = new StringBuilder(); 
            String linea = br.readLine(); 

            while (linea != null) {
                sb.append(linea);
                linea = br.readLine(); 
            }

            
            Type listType = new TypeToken<List<Expediente>>(){}.getType();
            List<Expediente> datosCargados = gson.fromJson(sb.toString(), listType);

            if (datosCargados != null) {
                return datosCargados;
            } else {
                return new ArrayList<>(); 
            }
            
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); 
        } catch (IOException ex) {
            System.out.println("Error al leer expedientes.json: " + ex.getMessage());
            return new ArrayList<>(); 
        }
    }
    //GUARDAR DATOS 
    private void guardarDatosEnArchivo() {
        try (FileWriter writer = new FileWriter(RUTA_ARCHIVO)) {
            gson.toJson(this.expedientes, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo JSON: " + e.getMessage());
        }
    }
}