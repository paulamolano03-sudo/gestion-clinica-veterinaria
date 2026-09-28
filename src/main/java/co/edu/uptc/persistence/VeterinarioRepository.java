package co.edu.uptc.persistence;

import co.edu.uptc.model.Veterinario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioRepository {

    private Gson gson;

    public VeterinarioRepository() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();

        builder.registerTypeAdapter(LocalTime.class, new TypeAdapter<LocalTime>() {
            @Override
            public void write(JsonWriter out, LocalTime value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value.format(DateTimeFormatter.ISO_LOCAL_TIME));
                }
            }

            @Override
            public LocalTime read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String timeString = in.nextString();
                return LocalTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_TIME);
            }
        });

        this.gson = builder.create();
    }

    public List<Veterinario> leerVeterinarios() {
        File file = new File("gestion-clinica-veterinaria\\data\\Veterinarios.json");
        
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                Type tipoLista = new TypeToken<List<Veterinario>>(){}.getType();
                List<Veterinario> veterinarios = gson.fromJson(br, tipoLista);
                if (veterinarios == null) {
                    return new ArrayList<>();
                }
                return veterinarios;
            } catch (Exception e) {
                System.out.println("Hubo un error al leer el archivo " + e.getMessage());
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public void guardarVeterinarios(List<Veterinario> veterinarios) {
        File file = new File("gestion-clinica-veterinaria\\\\data\\\\Veterinarios.json");
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (FileWriter fw = new FileWriter(file)) {
            gson.toJson(veterinarios, fw);
        } catch (Exception e) {
            System.out.println("Ocurrio un error al guardar el archivo " + e.getMessage());
        }
    }
}