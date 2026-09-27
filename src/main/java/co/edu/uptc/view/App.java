package co.edu.uptc.view;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import co.edu.uptc.controller.AtencionMedicaController;
import co.edu.uptc.model.Consulta;
import co.edu.uptc.model.Factura;
import co.edu.uptc.model.Medicamento;

public class App {
    private static final Scanner sc = new Scanner(System.in);
    private static final AtencionMedicaController controllerAtencionMedica = new AtencionMedicaController();

    public static void main(String[] args) {

        int opc = 0;
        do {
            String menuPrincipal = """
                    =======MENU PRINCIPAL=======
                    1. Parte Jesus
                    2. Parte Camilo
                    3. Atencion Medica y Facturacion
                    4. Salir

                    Seleccione una opcion: """;
            System.out.print(menuPrincipal);
            try {
                opc = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opc = -1;
            }

            switch (opc) {
                case 1:
                    // llamar metodo
                    opc = 4;
                    break;
                case 2:
                    opc =4;
                    break;
                case 3:
                    menuAtencionMedicayFacturacion();
                    break;
                case 4:
                    System.out.println("Saliendo de la app...");
                    break;

                default:
                    System.out.println("Opcion invalida");
                    break;
            }

        } while (opc != 4);
        sc.close();
    }

    // submenus y metodos

    // =========================================================
    // SUBMENÚS Y MÉTODOS DE ATENCION MEDICA Y FACTURACION
    // =========================================================

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
                6. Cambiar tarifa base o porcentaje de impuesto
                7. Volver al menu principal

                Seleccione una opcion: """;
        do {
            System.out.print(menuAtencion);
            try {
                opcionMedica = Integer.parseInt(sc.nextLine());// evita problemas de enter con el buffer
            } catch (NumberFormatException e) {
                opcionMedica = -1;// si es -1 en el switch manda al default
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
                    System.out.println("Regresando al menu principal...");
                    break;

                default:
                    System.out.println("Opcion invalida. Intente de nuevo");
                    break;
            }

        } while (opcionMedica != 7);

    }

    // metodos
    private static void capturarDatosConsulta() {
        System.out.println("\n--- REGISTRO DE NUEVA CONSULTA ---");

        System.out.print("Ingrese el ID del Expediente/Mascota: ");
        String idExpediente = sc.nextLine();// pasa luego el id al servicio y verifica que exista

        Consulta consulta = new Consulta();

        double peso = 0;
        boolean pesoValido = false;
        while (!pesoValido) {
            try {
                System.out.print("Ingrese el peso medido de la mascota (kg): ");
                peso = Double.parseDouble(sc.nextLine());
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
        consulta.setSintomas(sc.nextLine());

        System.out.print("Ingrese el diagnostico clinico: ");
        consulta.setDiagnostico(sc.nextLine());

        List<Medicamento> medicamentosRecetados = capturarMedicamentos();

        System.out.println("\nProcesando consulta y generando factura...");
        Factura facturaGenerada = controllerAtencionMedica.procesarAtencionMedica(idExpediente, consulta,
                medicamentosRecetados);

        if (facturaGenerada != null) {
            System.out.println(facturaGenerada.toString());

        } else {
            System.out.println("\nError: No se pudo precesar la atencion.");
        }
    }

    //suministrado al paciente 
    // ------------------------------------------------------------------------------------------
    private static List<Medicamento> capturarMedicamentos() {
        List<Medicamento> listaPrescrita = new ArrayList<>();
        List<Medicamento> catalogo = controllerAtencionMedica.listarCatalogoMedicamentos();

        if (catalogo.isEmpty()) {
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
            String idSel = sc.nextLine();

            Medicamento medCat = controllerAtencionMedica.buscarMedicamentoCatalogo(idSel);

            if (medCat != null) {
                System.out.print("Ingrese la dosis aplicada: ");
                String dosis = sc.nextLine();

                int cantidad = 1;
                boolean cantidadValida = false;
                do {

                    try {
                        System.out.print("Ingrese la cantidad: ");
                        cantidad = Integer.parseInt(sc.nextLine());
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

                listaPrescrita.add(recetado);// agregamos el medicamento creado a la lista de medicamentos del paciente
                System.out.println("El medicamento recetado se agrego con exito ");
            } else {
                System.out.println("Error: el id del medicamento no se encontro en el catalogo ");
            }

            System.out.print("\n¿Desea agregar otro medicamento? (s/n): ");
            continuar = sc.nextLine().trim();// borra espacios sobrantes ingresados sin querer antes o despues del texto

        }
        return listaPrescrita;
    }

    // ------------------------------------------------------------------------------------------
    // mostrar historial de facturas
    private static void mostrarFacturas() {
        System.out.println("\n--- HISTORIAL DE FACTURAS ---");
        List<Factura> historial = controllerAtencionMedica.consultarHistorialFacturas();

        if (historial.isEmpty()) {
            System.out.println("No hay facturas registradas en el sistema.");
        } else {
            for (Factura f : historial) {
                System.out.println(f.toString());
            }
        }
    }
    // ------------------------------------------------------------------------------------------
    // anular o eliminar factura
    private static void anularFactura() {
        System.out.println("\n--- ANULAR FACTURA ---");
        System.out.print("Ingrese el ID de la factura a anular: ");
        String id = sc.nextLine();

        if (controllerAtencionMedica.anularFactura(id)) {
            System.out.println("Factura " + id + " eliminada del sistema con éxito.");
        } else {
            System.out.println("Error: No se encontró la factura con ID " + id);
        }
    }

    // ------------------------------------------------------------------------------------------
    // GESTION DEL CATALOGO DE MEDICAMENTOS

    // ------------------------------------------------------------------------------------------

    // agregarMedicamento
    private static void agregarMedicamentoCatalogo() {
        System.out.println("\n--- AGREGAR MEDICAMENTO AL CATALOGO ---");
        System.out.print("Ingrese ID único del medicamento (ej: MED-01): ");
        String id = sc.nextLine();

        System.out.print("Ingrese el nombre comercial o compuesto: ");
        String nombre = sc.nextLine();

        double precio = 0;
        boolean precioValido = false;
        while (!precioValido) {
            try {
                System.out.print("Ingrese el precio unitario base ($): ");
                precio = Double.parseDouble(sc.nextLine());
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
    // eliminar medicamentos del catalogo
    private static void eliminarMedicamentoCatalogo() {
        System.out.println("\n--- ELIMINAR MEDICAMENTO DEL CATÁLOGO ---");
        List<Medicamento> catalogo = controllerAtencionMedica.listarCatalogoMedicamentos();

        if (catalogo.isEmpty()) {
            System.out.println("No hay medicamentos en el catálogo para eliminar.");
        } else {
            System.out.println("Medicamentos actualmente en el catálogo:");
            for (Medicamento m : catalogo) {
                System.out.println("ID: " + m.getId() + " | Nombre: " + m.getNombreMedicamento() + " | Precio Base: $"
                        + m.getPrecioUnitario());
            }

            System.out.print("\nIngrese el ID del medicamento a eliminar: ");
            String id = sc.nextLine();

            if (controllerAtencionMedica.eliminarMedicamentoCatalogo(id)) {
                System.out.println("¡Medicamento eliminado del catálogo con éxito!");
            } else {
                System.out.println("Error: No se encontró ningún medicamento con el ID '" + id + "'.");
            }
        }
    }

    // ------------------------------------------------------------------------------------------
    // GESTION DE LA TARIFA INICAL Y EL IMPUESTO

    // ------------------------------------------------------------------------------------------
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
                subOpc = Integer.parseInt(sc.nextLine());
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
                            nuevaTarifa = Double.parseDouble(sc.nextLine());
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
                            nuevoPorcentaje = Double.parseDouble(sc.nextLine());
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

    
}
