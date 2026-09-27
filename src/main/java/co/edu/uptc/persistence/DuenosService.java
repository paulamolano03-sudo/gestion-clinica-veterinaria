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

import co.edu.uptc.model.Dueno;

public class DuenosService {

    private static final String RUTA_ARCHIVO = "src/main/resources/data/duenos.json";

    private List<Dueno> duenos; 
    private final Gson gson;


     public DuenosService(){
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting();
        this.gson = gb.create();

        this.duenos = cargarDatos();
    }

    public void guardar (Dueno dueno){
        this.duenos.add(dueno);
    }

    public List<Dueno> listarDuenos(){
        return new ArrayList<>(this.duenos);
    }

    public Dueno buscarPorId(String id){
        for(Dueno dueno : duenos){
            if (dueno.getId().equalsIgnoreCase(id)){
                return dueno;
            }
        }
        return null;

    }

    public void actualizar(Dueno duenoActualizado){
         for (int i = 0; i < duenos.size(); i++) {
            if (duenos.get(i).getId().equalsIgnoreCase(duenoActualizado.getId())) {
                duenos.set(i, duenoActualizado);
                guardarDatosEnElArchivo();
                return;
            }
        }
    }

    public void elminar(String id){
        duenos.removeIf(dueno -> dueno.getId().equalsIgnoreCase(id) );
    }


    private List <Dueno> cargarDatos(){
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String linea = br.readLine();

            while (linea != null) {
                sb.append(linea);
                linea = br.readLine();
            }

            Type listType = new TypeToken<List<Dueno>>() { }.getType();
            List<Dueno> datosCargados = gson.fromJson(sb.toString(), listType);

            if (datosCargados != null) {
                return datosCargados;
            } else {
                return new ArrayList<>();
            }

        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException ex) {
            System.out.println("Error al leer duenos.json: " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    private void guardarDatosEnElArchivo(){
          try (FileWriter writer = new FileWriter(RUTA_ARCHIVO, StandardCharsets.UTF_8)) {
            gson.toJson(this.duenos, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar en duenos.json: " + e.getMessage());
        }
    }



    

}
