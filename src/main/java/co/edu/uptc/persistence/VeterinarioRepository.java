package co.edu.uptc.persistence;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;

import com.google.gson.Gson;

public class VeterinarioDao {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    BufferedReader br;

    public static void leerVeterinarios(){
        File file = new File("gestion-clinica-veterinaria\\data\\Veterinarios.json");
    }

}
