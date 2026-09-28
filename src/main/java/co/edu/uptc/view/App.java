package co.edu.uptc.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import co.edu.uptc.controller.AtencionMedicaController;
import co.edu.uptc.model.Consulta;
import co.edu.uptc.model.Factura;
import co.edu.uptc.model.Medicamento;
import co.edu.uptc.controller.CitaController;
import co.edu.uptc.controller.VeterinarioController;
import co.edu.uptc.sevice.VeterinarioService;
import co.edu.uptc.model.Veterinario;
import co.edu.uptc.model.Cita;
import co.edu.uptc.model.Paciente;

public class App {
    private static final Scanner sc = new Scanner(System.in);
    private static final AtencionMedicaController controllerAtencionMedica = new AtencionMedicaController();
    private static final VeterinarioService veterinarioServiceCompartido = new VeterinarioService();
    private static final VeterinarioController controllerVeterinario = new VeterinarioController();
    private static final CitaController controllerCita = new CitaController(veterinarioServiceCompartido);

    public static void main(String[] args) {

        int opc = 0;
        do {
            String menuPrincipal = """
                    =======MENU PRINCIPAL=======
                    1. Parte Jesus
                    2. Gestion de citas y veterinarios
                    3. Atencion Medica y Facturacion
                    4. Reportes y estadicticas
                    5. Salir

                    Seleccione una opcion: """;
            System.out.print(menuPrincipal);
            try {
                opc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opc = -1;
            }

            switch (opc) {
                case 1:
                    System.out.println("Opcion 1 seleccionada");
                    break;
                case 2:
                    menuPersonalYCitas();
                    break;
                case 3:
                    menuAtencionMedicayFacturacion();
                    break;
                case 4:
                    menuReportes();
                    break;
                case 5:
                    System.out.println("Saliendo de la app...");
                    break;

                default:
                    System.out.println("Opcion invalida");
                    break;
            }

        } while (opc != 5);
        sc.close();
    }

    // ------------------------------------------------------------------------------------------
    //                          SUBMENU (GESTION DE CITAS Y VETERINARIOS)
    // ------------------------------------------------------------------------------------------
    

    public static void menuPersonalYCitas() {
        int opcionCitas = 0;
        String menuCitas = """
                MENU PERSONAL Y CITAS
                1. Registrar veterinario
                2. Ver veterinarios
                3. Agendar cita
                4. Consultar agenda
                5. Volver
                Opcion: """;
        do {
            System.out.print(menuCitas);
            try {
                opcionCitas = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcionCitas = -1;
            }

            switch (opcionCitas) {
                case 1:
                    capturarDatosVeterinario();
                    break;
                case 2:
                    mostrarVeterinarios();
                    break;
                case 3:
                    capturarDatosCita();
                    break;
                case 4:
                    consultarAgendaMedico();
                    break;
                case 5:
                    System.out.println("Volviendo al menu principal");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        } while (opcionCitas != 5);
    }
    // ------------------------------------------------------------------------------------------
   

    private static void capturarDatosVeterinario() {
        System.out.print("ID de veterinario: ");
        String id = sc.nextLine().trim();
        System.out.print("Nombre de veterinario: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Especialidad: ");
        String especialidad = sc.nextLine().trim();
        System.out.print("Hora de entrada(HH:MM): ");
        String horaEntrada = sc.nextLine().trim();

        String respuesta = controllerVeterinario.registrarVeterinario(id, nombre, especialidad, horaEntrada);
        System.out.println(respuesta);
    }
    // ------------------------------------------------------------------------------------------
   

    private static void mostrarVeterinarios() {
        List<Veterinario> lista = controllerVeterinario.obtenerTodosLosVeterinarios();
        
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay veterinarios registrados");
        } else {
            for (Veterinario v : lista) {
                System.out.println(v.toString());
            }
        }
    }
 // ------------------------------------------------------------------------------------------
   
    private static void capturarDatosCita() {
        System.out.print("ID de cita: ");
        String idCita = sc.nextLine().trim();
        System.out.print("Nombre de veterinario: ");
        String nombreVet = sc.nextLine().trim();
        System.out.print("ID de paciente: ");
        String idPaciente = sc.nextLine().trim();
        
        Paciente pacienteTemp = new Paciente();
        
        System.out.print("Motivo: ");
        String motivo = sc.nextLine().trim();
        System.out.print("Fecha y hora: ");
        String fechaHora = sc.nextLine().trim();

        double costo = 0;
        boolean costoValido = false;
        while (!costoValido) {
            try {
                System.out.print("Costo: ");
                costo = Double.parseDouble(sc.nextLine().trim());
                if (costo >= 0) {
                    costoValido = true;
                } else {
                    System.out.println("Costo invalido");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de formato");
            }
        }

        String resultado = controllerCita.agendarCita(idCita, pacienteTemp, nombreVet, motivo, fechaHora, costo);
        System.out.println(resultado);
    }
 // ------------------------------------------------------------------------------------------
   
    private static void consultarAgendaMedico() {
        System.out.print("Nombre de veterinario: ");
        String nombre = sc.nextLine().trim();
        List<Cita> agenda = controllerCita.ConsultarAgendaVeterinario(nombre);

        if (agenda == null || agenda.isEmpty()) {
            System.out.println("No hay citas registradas");
        } else {
            System.out.println("Se consulto agenda de veterinario");
            for (Cita c : agenda) {
                System.out.println(c.toString());
            }
        }
    }
    // ------------------------------------------------------------------------------------------
    //                          SUBMENU (MODICLO DE ATENCION MEDICA Y FACTURACION)
    // ------------------------------------------------------------------------------------------
    

    public static void menuAtencionMedicayFacturacion() {
        int opcionMedica = 0;
        String menuAtencion = """
                ===========================================
                  MODULO DE ATENCION MEDICA Y FACTURACION
                ===========================================
                1. Registrar nueva consulta y facturar
                2. Ver historial de facturas
                3. Anular Factura
                4. Agreagar medicamento al catalogo
                5. Eliminar medicamento del catalogo
                6. Cambiar tarifa base o poracentaje de impuesto
                7. Registrar vacuna o cirugia
                8. Volver al menu principal

                Seleccione una opcion: """;
        do {
            System.out.print(menuAtencion);
            try {
                opcionMedica = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcionMedica = -1;
            }

            switch (opcionMedica) {
                case 1:
                    capturarDatosConsulta();
                    break;
                case 2:
                    mostrarFacturas();
                    break;
                case 3:
                    anularFactura();
                    break;
                case 4:
                    agregarMedicamentoCatalogo();
                    break;
                case 5:
                    eliminarMedicamentoCatalogo();
                    break;
                case 6:
                    configurarTarifasEImpuestos();
                    break;
                case 7:
                    capturarDatosVacunaOCirugia();
                    break;
                case 8:
                    System.out.println("Regresando al menu principal...");
                    break;

                default:
                    System.out.println("Opcion invalida. Intente de nuevo");
                    break;
            }

        } while (opcionMedica != 8);

    }
     // ------------------------------------------------------------------------------------------
   

    private static void capturarDatosConsulta() {
        System.out.println("\n--- REGISTRO DE NUEVA CONSULTA ---");

        System.out.print("Ingrese el ID del Expediente/Mascota: ");
        String idExpediente = sc.nextLine().trim();

        Consulta consulta = new Consulta();

        double peso = 0;
        boolean pesoValido = false;
        while (!pesoValido) {
            try {
                System.out.print("Ingrese el peso medido de la mascota (kg): ");
                peso = Double.parseDouble(sc.nextLine().trim());
                if (peso > 0) {
                    pesoValido = true;
                } else {
                    System.out.println("El peso debe ser mayor a cero ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error. Ingrese un valor numerico valido");
            }
        }
        consulta.setPesoMedido(peso);

        System.out.print("Ingrese los sintomas: ");
        consulta.setSintomas(sc.nextLine().trim());

        System.out.print("Ingrese el diagnostico clinico: ");
        consulta.setDiagnostico(sc.nextLine().trim());

        List<Medicamento> medicamentosRecetados = capturarMedicamentos();

        consulta.setMedicamentos(medicamentosRecetados);

        System.out.println("\nProcesando consulta y generando factura...");
        Factura facturaGenerada = controllerAtencionMedica.procesarAtencionMedica(idExpediente, consulta,
                medicamentosRecetados);

        if (facturaGenerada != null) {
            System.out.println(facturaGenerada.toString());
        } else {
            System.out.println("\nError: No se pudo precesar la atencion.");
        }
    }

     // ------------------------------------------------------------------------------------------
   
    private static List<Medicamento> capturarMedicamentos() {
        List<Medicamento> listaPrescrita = new ArrayList<>();
        List<Medicamento> catalogo = controllerAtencionMedica.listarCatalogoMedicamentos();

        if (catalogo == null || catalogo.isEmpty()) {
            System.out.println("\n No hay medicamentos en el catalogo oficial");
            System.out.println("Agregue produtos desde el submenu de administracion");
            return listaPrescrita;
        }

        String continuar = "s";

        while (continuar.equalsIgnoreCase("s")) {
            System.out.println("------CATALOGO DE MEDICAMENTOS DISPONIBLES------");
            for (Medicamento m : catalogo) {
                System.out.println("ID: " + m.getId() + " | Nombre: " + m.getNombreMedicamento() + " | Precio Base: $"
                        + m.getPrecioUnitario());
            }
            System.out.print("Ingrese el id del medicamento a recetar: ");
            String idSel = sc.nextLine().trim();

            Medicamento medCat = controllerAtencionMedica.buscarMedicamentoCatalogo(idSel);

            if (medCat != null) {
                System.out.print("Ingrese la dosis aplicada: ");
                String dosis = sc.nextLine().trim();

                int cantidad = 1;
                boolean cantidadValida = false;
                do {
                    try {
                        System.out.print("Ingrese la cantidad: ");
                        cantidad = Integer.parseInt(sc.nextLine().trim());
                        if (cantidad > 0) {
                            cantidadValida = true;
                        } else {
                            System.out.println("La cantidad debe ser mayor a cero.");
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: Ingrese un número entero válido.");
                    }
                } while (!cantidadValida);

                Medicamento recetado = new Medicamento();
                recetado.setId(medCat.getId());
                recetado.setNombreMedicamento(medCat.getNombreMedicamento());
                recetado.setPrecioUnitario(medCat.getPrecioUnitario());
                recetado.setDosisAplicada(dosis);
                recetado.setCantidad(cantidad);

                listaPrescrita.add(recetado);
                System.out.println("El medicamento recetado se agrego con exito ");
            } else {
                System.out.println("Error: el id del medicamento no se encontro en el catalogo ");
            }

            System.out.print("\n¿Desea agregar otro medicamento? (s/n): ");
            continuar = sc.nextLine().trim();
        }
        return listaPrescrita;
    }

    private static void mostrarFacturas() {
        System.out.println("\n--- HISTORIAL DE FACTURAS ---");
        List<Factura> historial = controllerAtencionMedica.consultarHistorialFacturas();

        if (historial == null || historial.isEmpty()) {
            System.out.println("No hay facturas registradas en el sistema.");
        } else {
            for (Factura f : historial) {
                System.out.println(f.toString());
            }
        }
    }
     // ------------------------------------------------------------------------------------------
   

    private static void anularFactura() {
        System.out.println("\n--- ANULAR FACTURA ---");
        System.out.print("Ingrese el ID de la factura a anular: ");
        String id = sc.nextLine().trim();

        if (controllerAtencionMedica.anularFactura(id)) {
            System.out.println("Factura " + id + " eliminada del sistema con éxito.");
        } else {
            System.out.println("Error: No se encontró la factura con ID " + id);
        }
    }
    // ------------------------------------------------------------------------------------------
    // ELIMINAR Y AGREGAR MEDICAMENTOS 
    // ------------------------------------------------------------------------------------------
    

    private static void agregarMedicamentoCatalogo() {
        System.out.println("\n--- AGREGAR MEDICAMENTO AL CATALOGO ---");
        System.out.print("Ingrese ID único del medicamento (ej: MED-01): ");
        String id = sc.nextLine().trim();

        System.out.print("Ingrese el nombre comercial o compuesto: ");
        String nombre = sc.nextLine().trim();

        double precio = 0;
        boolean precioValido = false;
        while (!precioValido) {
            try {
                System.out.print("Ingrese el precio unitario base ($): ");
                precio = Double.parseDouble(sc.nextLine().trim());
                if (precio >= 0) {
                    precioValido = true;
                } else {
                    System.out.println("El precio no puede ser negativo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un valor numérico válido.");
            }
        }

        Medicamento m = new Medicamento();
        m.setId(id);
        m.setNombreMedicamento(nombre);
        m.setPrecioUnitario(precio);

        if (controllerAtencionMedica.agregarMedicamentoCatalogo(m)) {
            System.out.println("¡Medicamento guardado en el catálogo con éxito!");
        } else {
            System.out.println("Error al guardar el medicamento.");
        }
    }
 // ------------------------------------------------------------------------------------------
   
    private static void eliminarMedicamentoCatalogo() {
        System.out.println("\n--- ELIMINAR MEDICAMENTO DEL CATOLOGO ---");
        List<Medicamento> catalogo = controllerAtencionMedica.listarCatalogoMedicamentos();

        if (catalogo == null || catalogo.isEmpty()) {
            System.out.println("No hay medicamentos en el catálogo para eliminar.");
        } else {
            System.out.println("Medicamentos actualmente en el catálogo:");
            for (Medicamento m : catalogo) {
                System.out.println("ID: " + m.getId() + " | Nombre: " + m.getNombreMedicamento() + " | Precio Base: $"
                        + m.getPrecioUnitario());
            }

            System.out.print("\nIngrese el ID del medicamento a eliminar: ");
            String id = sc.nextLine().trim();

            if (controllerAtencionMedica.eliminarMedicamentoCatalogo(id)) {
                System.out.println("¡Medicamento eliminado del catálogo con éxito!");
            } else {
                System.out.println("Error: No se encontró ningún medicamento con el ID '" + id + "'.");
            }
        }
    }
 // ------------------------------------------------------------------------------------------
   //=======TARIFAS E IMPUESTOS===========
    private static void configurarTarifasEImpuestos() {
        int subOpc = 0;

        do {
            System.out.println("\n--- CONFIGURACIÓN DE TARIFAS E IMPUESTOS ---");
            System.out.println("1. Cambiar tarifa base de consulta (Actual: $"
                    + controllerAtencionMedica.obtenerTarifaBase() + ")");
            System.out.println("2. Cambiar porcentaje de impuesto (Actual: "
                    + (controllerAtencionMedica.obtenerPorcentajeImpuesto() * 100) + "%)");
            System.out.println("3. Volver");
            System.out.print("Seleccione una opción: ");

            try {
                subOpc = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                subOpc = -1;
            }

            switch (subOpc) {
                case 1:
                    double nuevaTarifa = 0;
                    boolean tarifaValida = false;
                    do {
                        try {
                            System.out.print("Ingrese la nueva tarifa base ($): ");
                            nuevaTarifa = Double.parseDouble(sc.nextLine().trim());
                            if (nuevaTarifa >= 0) {
                                tarifaValida = true;
                            } else {
                                System.out.println("La tarifa base no puede ser negativa.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Ingrese un valor numérico válido.");
                        }
                    } while (!tarifaValida);

                    controllerAtencionMedica.cambiarTarifaBase(nuevaTarifa);
                    System.out.println("Tarifa base actualizada correctamente");
                    break;

                case 2:
                    double nuevoPorcentaje = 0;
                    boolean porcentajeValido = false;
                    do {
                        try {
                            System.out.print("Ingrese el nuevo porcentaje de impuesto (0.10 para 10%): ");
                            nuevoPorcentaje = Double.parseDouble(sc.nextLine().trim());
                            if (nuevoPorcentaje >= 0 && nuevoPorcentaje <= 1.0) {
                                porcentajeValido = true;
                            } else {
                                System.out.println("El porcentaje debe estar entre 0.0 (0%) y 1.0 (100%).");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Ingrese un valor numérico válido.");
                        }
                    } while (!porcentajeValido);

                    controllerAtencionMedica.cambiarPorcentajeImpuesto(nuevoPorcentaje);
                    System.out.println("¡Porcentaje de impuesto actualizado correctamente!");
                    break;

                case 3:
                    System.out.println("Regresando al menú anterior...");
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }

        } while (subOpc != 3);
    }

    // ------------------------------------------------------------------------------------------
    // REGISTRAR VACUNA O CIRUGIA
    // ------------------------------------------------------------------------------------------
    private static void capturarDatosVacunaOCirugia() {
        System.out.println("\n--- REGISTRO DE VACUNA O CIRUGIA ---");
        System.out.print("Ingrese el ID del expediente: ");
        String idExp = sc.nextLine();
        
        System.out.println("¿Que desea registrar?");
        System.out.println("1. Vacuna");
        System.out.println("2. Cirugía");
        System.out.print("Seleccione una opcion: ");
        
        int opc = 0;
        try {
            opc = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            opc = -1;
        }
        
        switch (opc) {
            case 1:
                System.out.print("Ingrese el nombre de la vacuna y fecha (ej: Rabia - 10/10/2023): ");
                String vacuna = sc.nextLine();
                controllerAtencionMedica.registrarVacuna(idExp, vacuna);
                break;
            case 2:
                System.out.print("Ingrese el nombre/descripción de la cirugía y fecha: ");
                String cirugia = sc.nextLine();
                controllerAtencionMedica.registrarCirugia(idExp, cirugia);
                break;
            default:
                System.out.println("Opción inválida. Registro cancelado.");
                break;
        }
    }

    // =========================================================
    // SUBMENU Y METODOS DE REPORTES Y ESTADISTICAS
    // =========================================================

    public static void menuReportes() {
        int opcReporte = 0;
        String menu = """
                ===========================================
                            MODULO DE REPORTES
                ===========================================
                1. Ver Reporte de Ingresos por Periodo
                2. Ver Top 5 de Procedimientos mas realizados
                3. Volver al menu principal

                Seleccione una opcion: """;
        do {
            System.out.print(menu);
            try {
                opcReporte = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcReporte = -1;
            }

            switch (opcReporte) {
                case 1:
                    generarReporteIngresos();
                    break;
                case 2:
                    generarTop5Procedimientos();
                    break;
                case 3:
                    System.out.println("Regresando al menu principal...");
                    break;
                default:
                    System.out.println("Opcion invalida. Intente de nuevo");
                    break;
            }
        } while (opcReporte != 3);
    }
    //------------------------------------------------------------------------------------------------
    private static void generarReporteIngresos() {
        System.out.println("\n--- REPORTE DE INGRESOS ---");
        System.out.println("Ingrese el periodo que desea consultar.");
        System.out.print("Ejemplo: '2023' para el ano, '10-2023' para un mes, o '25-10-2023' para un dia exacto: ");
        String periodo = sc.nextLine();
        
        // Se solicita el calculo al controlador
        double total = controllerAtencionMedica.calcularIngresosController(periodo);
        
        System.out.println("\nTotal de ingresos recaudados para el periodo '" + periodo + "': $" + total + "\n");
    }
    //-------------------------------------------------------------------------------------------------
    private static void generarTop5Procedimientos() {
        System.out.println("\n--- TOP 5 PROCEDIMIENTOS MAS REALIZADOS ---");
        
        // Se solicita la lista ya procesada y ordenada al controlador
        List<String> top5 = controllerAtencionMedica.obtenerTop5ProcedimientosController();
        
        if (top5.isEmpty()) {
            System.out.println("No hay procedimientos medicos registrados en el sistema aun.\n");
        } else {
            for (String procedimiento : top5) {
                System.out.println(procedimiento);
            }

            System.out.println("-------------------------------------------\n");
        }
    }
}
