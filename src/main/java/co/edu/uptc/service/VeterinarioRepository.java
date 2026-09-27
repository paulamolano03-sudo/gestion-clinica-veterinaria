package co.edu.uptc.service;
import co.edu.uptc.model.Veterinario;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;



public class VeterinarioRepository {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Veterinario> leerVeterinarios(){
        File file = new File("gestion-clinica-veterinaria\\data\\Veterinarios.json");
        
        if (file.exists()) {
            try{

            BufferedReader br = new BufferedReader(new FileReader(file));
            Type tipoLista = new TypeToken<List<Veterinario>>(){}.getType();
            List<Veterinario> veterinarios = gson.fromJson(br, tipoLista);
            if (veterinarios == null){
                return new ArrayList<>();
            }
            return veterinarios;
            
            } catch (Exception e){
                System.out.println("Hubo un error al leer el archivo " + e.getMessage());
                return new ArrayList<>();

            }
        } else {
            return new ArrayList<>();
        }
    }

    public void guardarVeterinarios(List<Veterinario> veterinarios){
        File file = new File("gestion-clinica-veterinaria\\data\\Veterinarios.json");
        try (FileWriter fw = new FileWriter(file)){

            gson.toJson(fw);
            
            
        } catch (Exception e) {
            System.out.println("Ocurrio un error al guardar el archivo " +e.getMessage());
        }
    }

}
