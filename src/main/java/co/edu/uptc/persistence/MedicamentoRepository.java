package co.edu.uptc.persistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.model.Medicamento;

public class MedicamentoRepository {
    private static final String RUTA_ARCHIVO = "data/medicamentos_disponibles.json";
    private List<Medicamento> medicamentos;
    private final Gson gson;

    public MedicamentoRepository(){
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting();
        this.gson = gb.create();

        // Llama al método que lee el archivo y carga el catálogo en memoria al instanciar
        this.medicamentos = cargarDatos();
    }

    public void guardar(Medicamento medicamento){
        this.medicamentos.add(medicamento);
        guardarDatosEnArchivo(); // Actualiza el JSON del catálogo
    }

    public List<Medicamento> listar(){
        return this.medicamentos;
    }

    public Medicamento buscarPorId(String id){
        for(Medicamento medicamento : medicamentos){
            if(medicamento.getId() != null && medicamento.getId().equalsIgnoreCase(id)){
                return medicamento;
            }
        }
        return null;
    }

    public void actualizar(Medicamento medicamentoActualizado){
        for (int i = 0; i < medicamentos.size(); i++){
            if(medicamentos.get(i).getId() != null && medicamentos.get(i).getId().equalsIgnoreCase(medicamentoActualizado.getId())){
                medicamentos.set(i, medicamentoActualizado);
                guardarDatosEnArchivo();
                return;
            }
        }
    }

    public void eliminar(String id){
        medicamentos.removeIf(medicamento -> medicamento.getId() != null && medicamento.getId().equalsIgnoreCase(id));
        guardarDatosEnArchivo(); // Actualiza el JSON sin el medicamento eliminado
    }

    // Cargar medicamentos (Deserialización: JSON a objeto Java)
    private List<Medicamento> cargarDatos(){
        try(BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))){

            StringBuilder sb = new StringBuilder();
            String linea = br.readLine();

            while (linea != null) {
                sb.append(linea);
                linea = br.readLine();
            }
            
            Type listType = new TypeToken<List<Medicamento>>(){}.getType();
            List<Medicamento> datosCargados = gson.fromJson(sb.toString(), listType);

            if(datosCargados != null){
                return datosCargados;
            } else {
                return new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            // Si el archivo no existe aún en data/, retorna una lista vacía para iniciar
            return new ArrayList<>(); 
        } catch (IOException ex) {
            System.out.println("Error al leer medicamentos_disponibles.json: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    // Guardar medicamentos: objetos Java a JSON
    private void guardarDatosEnArchivo(){
        try(FileWriter writer = new FileWriter(RUTA_ARCHIVO)){
            gson.toJson(this.medicamentos, writer);
        } catch(IOException ex){
            System.out.println("Error al guardar el archivo JSON: " + ex.getMessage());
        }
    }
}
