package co.edu.uptc.sevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.edu.uptc.model.Consulta;
import co.edu.uptc.model.Expediente;
import co.edu.uptc.model.Factura;
import co.edu.uptc.persistence.ExpedienteRepository;
import co.edu.uptc.persistence.FacturaRepository;

public class ReportesService {

    private final FacturaRepository facturaRepository;
    private final ExpedienteRepository expedienteRepository;

    public ReportesService(FacturaRepository facturaRepository, ExpedienteRepository expedienteRepository) {
        this.facturaRepository = facturaRepository;
        this.expedienteRepository = expedienteRepository;
    }
    //metodo para el reporte de ingresos de una fecha especifica puede ser dia o mes 
    public double calcularIngresosPorPeriodo(String periodoBuscado) {
        double totalIngresos = 0.0;
        List<Factura> facturas = facturaRepository.listar();
        
        for (Factura f : facturas) {
            // buscamos si el texto de la fecha contiene el periodo que el usuario digit
            //(ej: "25-10-2023 14:30") para ese dia de octubre
            //(ej: "10-2023") para el mes de octubre
            if (f.getFechaEmision() != null && f.getFechaEmision().contains(periodoBuscado)) {
                totalIngresos += f.getTotal();
            }
        }
        return totalIngresos;
    }

    // 2. TOP 5 PROCEDIMIENTOS
    public List<String> obtenerTop5Procedimientos() {
        // mapa para almacenar el nombre del procedimiento como llave y su frecuencia como valor
        HashMap<String, Integer> contadorProcedimientos = new HashMap<>();
        List<Expediente> expedientes = expedienteRepository.listar();

        // recorrido de los historiales medicos para realizar el conteo
        for (Expediente exp : expedientes) {
            if (exp.getConsultas() != null) {
                for (Consulta consulta : exp.getConsultas()) {
                    if (consulta.getProcedimiento() != null && consulta.getProcedimiento().getNombreProcedimiento() != null) {
                        
                        String nombreProcedimiento = consulta.getProcedimiento().getNombreProcedimiento();
                        
                        // verificacion de existencia en el mapa para incrementar o inicializar el registro
                        if (contadorProcedimientos.containsKey(nombreProcedimiento)) {
                            int cantidadActual = contadorProcedimientos.get(nombreProcedimiento);
                            contadorProcedimientos.put(nombreProcedimiento, cantidadActual + 1);
                        } else {
                            contadorProcedimientos.put(nombreProcedimiento, 1);
                        }
                    }
                }
            }
        }

        List<String> top5Final = new ArrayList<>();
        
        // validacion para definir el numero de iteraciones en caso de tener menos de 5 procedimientos registrados
        int limiteDeResultados = 5;
        if (contadorProcedimientos.size() < 5) {
            limiteDeResultados = contadorProcedimientos.size();
        }

        // ciclo externo para extraer secuencialmente los elementos con mayor valor
        for (int i = 0; i < limiteDeResultados; i++) {
            String procedimientoMasFrecuente = "";
            
            // se inicializa en -1 para garantizar que el primer registro asuma el valor maximo temporal
            int cantidadMasFrecuente = -1;

            // ciclo interno para buscar la llave con el valor maximo dentro del estado actual del mapa
            for (String nombreActual : contadorProcedimientos.keySet()) {
                int cantidadActual = contadorProcedimientos.get(nombreActual);
                
                // se evalua si el registro actual supera al maximo encontrado en la iteracion en curso
                if (cantidadActual > cantidadMasFrecuente) {
                    cantidadMasFrecuente = cantidadActual;
                    procedimientoMasFrecuente = nombreActual;
                }
            }

            // adicion del registro dominante a la lista de salida
            top5Final.add((i + 1) + ". " + procedimientoMasFrecuente + " - " + cantidadMasFrecuente + " veces");
            
            // eliminacion del registro dominante del mapa para permitir la evaluacion del siguiente mayor valor
            contadorProcedimientos.remove(procedimientoMasFrecuente);
        }

        return top5Final;
    }
}
