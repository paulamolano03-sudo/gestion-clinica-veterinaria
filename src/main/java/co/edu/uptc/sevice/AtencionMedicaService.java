
package co.edu.uptc.sevice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.model.Consulta;
import co.edu.uptc.model.Expediente;
import co.edu.uptc.model.Factura;
import co.edu.uptc.model.Medicamento;
import co.edu.uptc.persistence.ExpedienteRepository;
import co.edu.uptc.persistence.ExportadorCSV;
import co.edu.uptc.persistence.FacturaRepository;
import co.edu.uptc.persistence.MedicamentoRepository;

public class AtencionMedicaService {
    
    //inyectar repositorios
    private final FacturaRepository facturaRepository;
    private final ExpedienteRepository expedienteRepository;
    // Instancia de la capa de persistencia para CSV
    private final ExportadorCSV exportadorCSV = new ExportadorCSV();

    private double tarifaBaseConsulta = 50000.0;
    private double porcentajeImpuesto = 0.10;

    // NUEVAS CONFIGURACIONES DE CAMPAÑA
    private int mesCampanaVacunacion = 10; // Mes configurado (ej: 9 = Septiembre)
    private double porcentajeDescuentoCampana = 0.15; // 15% de descuento

    public AtencionMedicaService(FacturaRepository facturaRepository, ExpedienteRepository expedienteRepository) {
        this.facturaRepository = facturaRepository;
        this.expedienteRepository = expedienteRepository;
    }

    // OPERACIONES DE ATENCION Y FACTURACION 

    public Factura registrarConsultaYFacturar(String idExpediente, Consulta nuevaConsulta){
        
        // buscar el expediente del paciente 
        Expediente expediente = expedienteRepository.buscarPorId(idExpediente);
        if(expediente == null){
            System.out.println("NO se encontro el expediente con ID: "+idExpediente);
            return null;
        }
        //agregar la consulta al histroial medico y guardar en experdientes.json
        expediente.getConsultas().add(nuevaConsulta);
        expedienteRepository.actualizar(expediente);
        // ACTUALIZACION AUTOMATICA DEL CSV
        this.exportarExpedientesCSV();

        //iniciar calculos matematicos de la factura 
        double totalMedicamentos = 0.0;
        double totalImpuestosMedicamentos = 0.0;

        if(nuevaConsulta.getMedicamentos()!=null){
            for(Medicamento med : nuevaConsulta.getMedicamentos()){
                double subtotalMed = med.getPrecioUnitario()*med.getCantidad();
                double impuesto = subtotalMed*this.porcentajeImpuesto;

                totalImpuestosMedicamentos += impuesto;
                totalMedicamentos += (subtotalMed+impuesto);
            }
        }

        double costoProcedimiento = 0.0;
        if(nuevaConsulta.getProcedimiento() != null){
            costoProcedimiento = nuevaConsulta.getProcedimiento().getCosto();
        }

        // Obtener el mes actual del sistema (1 a 12)
        int mesActual = java.time.LocalDate.now().getMonthValue();
        double tarifaAplicada = this.tarifaBaseConsulta;
        double montoDescuento = 0.0; // Variable para registrar el monto descontado

        // Si el mes actual coincide con el mes de campaña, aplicamos el 15% de descuento
        if (mesActual == this.mesCampanaVacunacion) {
            montoDescuento = this.tarifaBaseConsulta * this.porcentajeDescuentoCampana;
            tarifaAplicada = this.tarifaBaseConsulta - montoDescuento;
        }

        double totalFactura = tarifaAplicada + totalMedicamentos + costoProcedimiento;

        //crear la factura y generar id simple 

        int numeroSiguiente = facturaRepository.listar().size() +1;
        String idGenerado = "FAC-"+numeroSiguiente;

        Factura nuevaFactura = new Factura();
        nuevaFactura.setId(idGenerado);
        DateTimeFormatter formatoElegante = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String fechaFormateada = LocalDateTime.now().format(formatoElegante);
        nuevaFactura.setFechaEmision(fechaFormateada);
        nuevaFactura.setConsulta(nuevaConsulta);
        nuevaFactura.setDescuento(montoDescuento);
        nuevaFactura.setImpuesto(totalImpuestosMedicamentos);
        nuevaFactura.setTotal(totalFactura);
        
        //enviar factura nueva para guardar en factura.json
        facturaRepository.guardar(nuevaFactura);

        return nuevaFactura;
    }

    public List<Factura> obtenerHistorialFacturas(){
        return facturaRepository.listar();
    }

    public Factura buscarFacturaPorId(String idFactura){
        return facturaRepository.buscarPorId(idFactura);
    }

    public boolean actualizarFactura(Factura facturaActualizada) {
        Factura existente = facturaRepository.buscarPorId(facturaActualizada.getId());
        if (existente != null) {
            facturaRepository.actualizar(facturaActualizada);
            return true;
        }
        return false;
    }
    public boolean anularFactura(String id) {
    Factura existente = facturaRepository.buscarPorId(id);
    if (existente != null) {
        facturaRepository.eliminar(id);
        return true;
    }
    return false;
    }

// CONFIGURACION DE TARIFA E IMPUESTOS

    public double getTarifaBaseConsulta() {
        return tarifaBaseConsulta;
    }

    public void actualizarTarifaBaseConsulta(double nuevaTarifa) {
        if (nuevaTarifa >= 0) {
            this.tarifaBaseConsulta = nuevaTarifa;
        }
    }
    public double getPorcentajeImpuesto() {
        return porcentajeImpuesto;
    }

    public void actualizarPorcentajeImpuesto(double nuevoPorcentaje) {
        if (nuevoPorcentaje >= 0 && nuevoPorcentaje <= 1.0) {
            this.porcentajeImpuesto = nuevoPorcentaje;
        }
    }

    //GESTION DE VACUNAS Y CIRUJIAS
    public boolean registrarVacuna(String idExpediente, String vacuna) {
        Expediente expediente = expedienteRepository.buscarPorId(idExpediente);
        
        if (expediente != null) {
            if (expediente.getHistorialVacunas() == null) {
                expediente.setHistorialVacunas(new java.util.ArrayList<>());
            }
            expediente.getHistorialVacunas().add(vacuna);
            expedienteRepository.actualizar(expediente);
            // ACTUALIZACION AUTOMATICA DEL CSV
            this.exportarExpedientesCSV();
            return true;
        }
        return false;
    }

    public boolean registrarCirugia(String idExpediente, String cirugia) {
        Expediente expediente = expedienteRepository.buscarPorId(idExpediente);
        
        if (expediente != null) {
            if (expediente.getHistorialCirugias() == null) {
                expediente.setHistorialCirugias(new java.util.ArrayList<>());
            }
            expediente.getHistorialCirugias().add(cirugia);
            expedienteRepository.actualizar(expediente);

            // ACTUALIZACION AUTOMATICA DEL CSV
            this.exportarExpedientesCSV();

            return true;
        }
        return false;
    }

    // EXPORTACION A CSV (EXPEDIENTES)

    public boolean exportarExpedientesCSV() {
        List<Expediente> expedientes = expedienteRepository.listar();
        List<String> lineas = new ArrayList<>();

        // encabezado del archivo
        lineas.add("ID Expediente,Cantidad Vacunas,Cantidad Cirugias,Cantidad Consultas");

        // extracción de totales 
        for (Expediente exp : expedientes) {
            
            int totalVacunas = 0;
            if (exp.getHistorialVacunas() != null) {
                totalVacunas = exp.getHistorialVacunas().size();
            }

            int totalCirugias = 0;
            if (exp.getHistorialCirugias() != null) {
                totalCirugias = exp.getHistorialCirugias().size();
            }

            int totalConsultas = 0;
            if (exp.getConsultas() != null) {
                totalConsultas = exp.getConsultas().size();
            }

            lineas.add(exp.getId() + "," + totalVacunas + "," + totalCirugias + "," + totalConsultas);
        }

        // guardado directo en la carpeta 'data'
        return exportadorCSV.exportarArchivo("data/reporte_expedientes.csv", lineas);
    }

    

}
