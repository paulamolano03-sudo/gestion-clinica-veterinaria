package co.edu.uptc.persistence;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorCSV {

    
   
    public boolean exportarArchivo(String nombreArchivo, List<String> lineasCSV) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            
            for (String linea : lineasCSV) {
                writer.println(linea);
            }
            
            return true;
        } catch (IOException e) {
            System.out.println("Ocurrio un error al escribir el archivo CSV: " + e.getMessage());
            return false;
        }
    }
}