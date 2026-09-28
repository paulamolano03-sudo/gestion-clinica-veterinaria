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

import co.edu.uptc.model.Factura;

public class FacturaRepository {
    private static final String RUTA_ARCHIVO = "data/facturas.json";
    private List<Factura> facturas;
    private final Gson gson;

    public FacturaRepository(){
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting();
        this.gson = gb.create();

        //Llama al metodo que lee el archivo y guarda el resultado en la lista global de la clase
        //cuando el repositorio nace, va al json, lee el historial anterior, y llena la lista en memoria
        //evita que al ingresar una vueva factura se eliminen las anteriores porque la lista no estara vacia
    
        this.facturas = cargarDatos();
    }
    public void guardar(Factura factura){
        this.facturas.add(factura);
        guardarDatosEnArchivo();// actualiza el JSON cada vez que se guarda una factura
    }
    public List<Factura> listar(){
        return this.facturas;
    }
    public Factura buscarPorId(String id){
        for(Factura factura : facturas ){
            if(factura.getId().equals(id)){
                return factura;
            }
        }
        return null;
    }
    public void actualizar(Factura facturaActualizada){
        for (int i =0; i < facturas.size(); i++){
            if(facturas.get(i).getId().equals(facturaActualizada.getId())){
                facturas.set(i, facturaActualizada);
                guardarDatosEnArchivo();
            }
        }
    }
    public void eliminar(String id){
        facturas.removeIf(factura -> factura.getId().equals(id));//funcion lambda
        guardarDatosEnArchivo();//actualiza el JSON sin esa factura
    }

    //Cargar facturas (Deserializacion: JSON a objeto java)

    private List<Factura> cargarDatos(){
        try(BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))){

            StringBuilder sb = new StringBuilder();
            String linea = br.readLine();

            while (linea != null) {
                sb.append(linea);
                linea = br.readLine(); // Lee la siguiente línea
            }
            
            Type listType = new TypeToken<List<Factura>>(){}.getType();
            List<Factura> datosCargados = gson.fromJson(sb.toString(), listType);

            if(datosCargados!=null){
                return datosCargados;
            }else{
                return new ArrayList<>();//evita que servicios tenga futuros inconvenientes 
            }
        }catch (FileNotFoundException e) {
            // Si es la primera vez que se ejecutael programa, el archivo no existirá.
            // En vez de lanzar un error que rompa todo, simplemente se devuelve la lista vacía para empezar desde cero.
            return new ArrayList<>(); 
        } catch (IOException ex) {
            System.out.println("Error al leer facturas.json: " + ex.getMessage());
            return new ArrayList<>(); // Retorna una lista vacía para no romper la app en caso de error de lectura
        }
    }

    // Guardar facturas: objetos java a JSON
    private void guardarDatosEnArchivo(){
        try(FileWriter writer = new FileWriter(RUTA_ARCHIVO)){

            gson.toJson(this.facturas, writer);
        }catch(IOException ex){
            System.out.println("Error al guardar el archivo JSON "+ex.getMessage());
        }
    }
}
