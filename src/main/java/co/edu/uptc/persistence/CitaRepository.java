package co.edu.uptc.persistence;

import co.edu.uptc.model.Cita;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CitaRepository {

    private Gson gson;

    public CitaRepository() {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();

        builder.registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
            
            @Override
            public void write(JsonWriter out, LocalDateTime value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                }
            }

            @Override
            public LocalDateTime read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String dateString = in.nextString();
                return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        });

        this.gson = builder.create();
    }

    public List<Cita> leerCitas() {
        File file = new File("gestion-clinica-veterinaria\\data\\Citas.json");
        
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                Type tipoLista = new TypeToken<List<Cita>>(){}.getType();
                List<Cita> citas = gson.fromJson(br, tipoLista);
                
                if (citas == null) {
                    return new ArrayList<>();
                }
                return citas;
                
            } catch (Exception e) {
                System.out.println("Hubo un error al leer el archivo de citas: " + e.getMessage());
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }
    }

    public void guardarCitas(List<Cita> citas) {
        File file = new File("gestion-clinica-veterinaria\\\\data\\\\Citas.json");
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        
        try (FileWriter fw = new FileWriter(file)) {
            gson.toJson(citas, fw);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al guardar el archivo de citas: " + e.getMessage());
        }
    }
}