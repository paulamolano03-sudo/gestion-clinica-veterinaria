
package co.edu.uptc.sevice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import co.edu.uptc.model.Consulta;
import co.edu.uptc.model.Expediente;
import co.edu.uptc.model.Factura;
import co.edu.uptc.model.Medicamento;
import co.edu.uptc.persistence.ExpedienteRepository;
import co.edu.uptc.persistence.FacturaRepository;
import co.edu.uptc.persistence.MedicamentoRepository;

public class AtencionMedicaService {
    
    //inyectar repositorios
    private final FacturaRepository facturaRepository;
    private final ExpedienteRepository expedienteRepository;

    private double tarifaBaseConsulta = 50000.0;
    private double porcentajeImpuesto = 0.10;

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

        double totalFactura = this.tarifaBaseConsulta + totalMedicamentos + costoProcedimiento;

        //crear la factura y generar id simple 

        int numeroSiguiente = facturaRepository.listar().size() +1;
        String idGenerado = "FAC-"+numeroSiguiente;

        Factura nuevaFactura = new Factura();
        nuevaFactura.setId(idGenerado);
        DateTimeFormatter formatoElegante = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String fechaFormateada = LocalDateTime.now().format(formatoElegante);
        nuevaFactura.setFechaEmision(fechaFormateada);
        nuevaFactura.setConsulta(nuevaConsulta);
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

    //Gestion del catalogo de medicamentos 

    

}
