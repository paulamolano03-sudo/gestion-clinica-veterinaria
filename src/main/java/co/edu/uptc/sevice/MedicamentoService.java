package co.edu.uptc.sevice;

import java.util.List;
import co.edu.uptc.model.Medicamento;
import co.edu.uptc.persistence.MedicamentoRepository;

public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Medicamento> obtenerCatalogo() {
        return medicamentoRepository.listar();
    }

    public Medicamento buscarPorId(String id) {
        return medicamentoRepository.buscarPorId(id);
    }

    public boolean agregarAlCatalogo(Medicamento nuevo) {
        if (nuevo != null && nuevo.getId() != null && !nuevo.getId().isBlank()) {
            medicamentoRepository.guardar(nuevo);
            return true;
        }
        return false;
    }

    public boolean eliminarDelCatalogo(String id) {
        Medicamento existente = medicamentoRepository.buscarPorId(id);
        if (existente != null) {
            medicamentoRepository.eliminar(id);
            return true;
        }
        return false;
    }
}