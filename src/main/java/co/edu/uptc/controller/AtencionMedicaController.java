package co.edu.uptc.controller;

import java.util.List;

import co.edu.uptc.model.Consulta;
import co.edu.uptc.model.Factura;
import co.edu.uptc.model.Medicamento;
import co.edu.uptc.persistence.ExpedienteRepository;
import co.edu.uptc.persistence.FacturaRepository;
import co.edu.uptc.persistence.MedicamentoRepository;
import co.edu.uptc.sevice.AtencionMedicaService;
import co.edu.uptc.sevice.MedicamentoService;

public class AtencionMedicaController {

    private final AtencionMedicaService atencionMedicaService;
    private final MedicamentoService medicamentoService;

    public AtencionMedicaController(){
        // instaciar repositorios
        FacturaRepository facturaRepo = new FacturaRepository();
        ExpedienteRepository expedienteRepo = new ExpedienteRepository();
        MedicamentoRepository medicamentoRepo = new MedicamentoRepository();

        //iny repositorios
        this.atencionMedicaService = new AtencionMedicaService(facturaRepo, expedienteRepo);
        this.medicamentoService = new MedicamentoService(medicamentoRepo);

    }
    
    //el controlador recibe la peticion del menu y llama al servicio
    //atencion medica y facturacion
    public Factura procesarAtencionMedica(String idExpediente, Consulta consulta, List<Medicamento> medicamentos){
        return atencionMedicaService.registrarConsultaYFacturar(idExpediente, consulta);
    }

    public List<Factura> consultarHistorialFacturas(){
        return atencionMedicaService.obtenerHistorialFacturas(); 
    }

    public Factura buscarFacturaPorId(String id) {
        return atencionMedicaService.buscarFacturaPorId(id);
    }

    public boolean anularFactura(String id) {
        return atencionMedicaService.anularFactura(id);
    }

    //configuracion de las tarifas
    public double obtenerTarifaBase() {
        return atencionMedicaService.getTarifaBaseConsulta();
    }

    public void cambiarTarifaBase(double nuevaTarifa) {
        atencionMedicaService.actualizarTarifaBaseConsulta(nuevaTarifa);
    }

    public double obtenerPorcentajeImpuesto() {
        return atencionMedicaService.getPorcentajeImpuesto();
    }

    public void cambiarPorcentajeImpuesto(double nuevoPorcentaje) {
        atencionMedicaService.actualizarPorcentajeImpuesto(nuevoPorcentaje);
    }
    
    //catalogo de medicamentos 

    public List<Medicamento> listarCatalogoMedicamentos() {
        return medicamentoService.obtenerCatalogo();
    }

    public Medicamento buscarMedicamentoCatalogo(String id) {
        return medicamentoService.buscarPorId(id);
    }

    public boolean agregarMedicamentoCatalogo(Medicamento m) {
        return medicamentoService.agregarAlCatalogo(m);
    }

    public boolean eliminarMedicamentoCatalogo(String id) {
        return medicamentoService.eliminarDelCatalogo(id);
    }

}
