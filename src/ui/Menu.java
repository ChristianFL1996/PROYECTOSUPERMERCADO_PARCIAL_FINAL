package ui;

import java.util.InputMismatchException;
import java.util.Scanner;
import logic.Supermercado;
import models.Cliente;
import models.Producto;
import models.TipoCliente;

public class Menu {
    private final Supermercado sistema;
    private final Scanner scanner;

    // --- CONSTANTES DE COLOR ANSI ---
    //  Cumpliendo con la recomendación de usar colores.
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_BOLD = "\u001B[1m";
    // ---------------------------------

    public Menu() {
        sistema = new Supermercado();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = 0;
        do {
            limpiarConsola();
            mostrarEncabezado();
            System.out.println(ANSI_YELLOW + "\n Por favor, ingrese la opción opción deseada ingresando el número correspondiente." + ANSI_RESET);
            System.out.println(ANSI_GREEN + "\n__________ OPCIONES DE GESTIÓN DE INVENTARIO ___________" + ANSI_RESET);
            System.out.println("\n 1) Insertar nuevo producto, " + ANSI_BOLD + ANSI_BLUE + "[Opción 1]" + ANSI_RESET);
            System.out.println(" 2) Buscar producto por código, " + ANSI_BOLD + ANSI_BLUE + "[Opción 2]" + ANSI_RESET);
            System.out.println(" 3) Actualizar producto, " + ANSI_BOLD + ANSI_BLUE + "[Opción 3]" + ANSI_RESET);
            System.out.println(" 4) Eliminar producto, " + ANSI_BOLD + ANSI_BLUE + "[Opción 4]" + ANSI_RESET);
            System.out.println(" 5) Mostrar inventario completo, " + ANSI_BOLD + ANSI_BLUE + "[Opción 5]" + ANSI_RESET);
            System.out.println("\n" + ANSI_GREEN + "___________ OPCIONES DE GESTIÓN DE CLIENTES ____________" + ANSI_RESET);
            System.out.println("\n 6) Agregar cliente a la cola, " + ANSI_BOLD + ANSI_BLUE + "[Opción 6]" + ANSI_RESET);
            System.out.println(" 7) Atender siguiente cliente, " + ANSI_BOLD + ANSI_BLUE + "[Opción 7]" + ANSI_RESET);
            System.out.println(" 8) Ver clientes en espera, " + ANSI_BOLD + ANSI_BLUE + "[Opción 8]" + ANSI_RESET);
            System.out.println(" 9) Simular atención masiva, " + ANSI_BOLD + ANSI_BLUE + "[Opción 9]" + ANSI_RESET);
            System.out.println("\n" + ANSI_GREEN + "\n___________ OPCIÓN PARA SALIR DEL SISTEMA _____________" + ANSI_RESET);
            System.out.println("\n 10) Salir, " + ANSI_BOLD + ANSI_BLUE + "[Opción 10]" + ANSI_RESET);
            System.out.print("\n" + ANSI_YELLOW + "Seleccione una opción: " + ANSI_RESET);

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                // [cite: 125] Validación de entrada (manejo de excepciones)
                System.out.println(ANSI_RED + "¡Error! Ingrese un número válido." + ANSI_RESET);
                pausa();
            } catch (InputMismatchException e) {
                System.out.println(ANSI_RED + "¡Error! Tipo de dato incorrecto." + ANSI_RESET);
                scanner.nextLine(); // Limpiar buffer
                pausa();
            }
        } while (opcion != 10);
        System.out.println("Saliendo del sistema...");
    }

    private void procesarOpcion(int op) {
        // [cite: 127] Mostrar confirmaciones de operaciones
        switch (op) {
            case 1 -> {
                // Insertar
                System.out.println(ANSI_CYAN + "\n== Insertar Producto ==" + ANSI_RESET);
                System.out.print("Código (ej: PROD001): "); String cod = scanner.nextLine();
                System.out.print("Nombre: "); String nom = scanner.nextLine();
                System.out.print("Categoría: "); String cat = scanner.nextLine();
                System.out.print("Precio: "); double pre = Double.parseDouble(scanner.nextLine());
                System.out.print("Stock: "); int stock = Integer.parseInt(scanner.nextLine());
                String resIns = sistema.agregarProducto(cod, nom, cat, pre, stock);
                if (resIns.startsWith("Error")) {
                    System.out.println(ANSI_RED + resIns + ANSI_RESET);
                } else {
                    System.out.println(ANSI_GREEN + resIns + ANSI_RESET);
                }
            }
            case 2 -> {
                // Buscar
                System.out.println(ANSI_CYAN + "\n Buscar Producto " + ANSI_RESET);
                System.out.print("Ingrese Código a buscar: ");
                Producto p = sistema.buscarProducto(scanner.nextLine());
                if (p != null) {
                    System.out.println(ANSI_GREEN + "Producto encontrado:" + ANSI_RESET);
                    System.out.println(p);
                } else {
                    System.out.println(ANSI_RED + "Producto no encontrado." + ANSI_RESET);
                }
            }
            case 3 -> {
                // Actualizar
                System.out.println(ANSI_CYAN + "\n Actualizar Producto " + ANSI_RESET);
                System.out.print("Código del producto a editar: "); String cUpd = scanner.nextLine();
                if (sistema.buscarProducto(cUpd) == null) {
                    System.out.println(ANSI_RED + "Producto no encontrado." + ANSI_RESET);
                } else {
                    System.out.print("Nuevo Nombre: "); String nNom = scanner.nextLine();
                    System.out.print("Nueva Categoría: "); String nCat = scanner.nextLine();
                    System.out.print("Nuevo Precio: "); double nPre = Double.parseDouble(scanner.nextLine());
                    System.out.print("Nuevo Stock: "); int nStk = Integer.parseInt(scanner.nextLine());
                    System.out.println(ANSI_GREEN + sistema.actualizarProducto(cUpd, nNom, nCat, nPre, nStk) + ANSI_RESET);
                }
            }
            case 4 -> {
                // Eliminar
                System.out.println(ANSI_CYAN + "\n Eliminar Producto " + ANSI_RESET);
                System.out.print("Código a eliminar: ");
                String resDel = sistema.eliminarProducto(scanner.nextLine());
                if(resDel.startsWith("Error")) {
                    System.out.println(ANSI_RED + resDel + ANSI_RESET);
                } else {
                    System.out.println(ANSI_GREEN + resDel + ANSI_RESET);
                }
            }
            case 5 -> {
                // Listar
                System.out.println(ANSI_CYAN + "\n Inventario Completo" + ANSI_RESET);
                sistema.mostrarInventario();
            }
            case 6 -> {
                // Agregar Cliente
                System.out.println(ANSI_CYAN + "\n Agregar Cliente a la Cola " + ANSI_RESET);
                System.out.print("Nombre del Cliente: "); String cNom = scanner.nextLine();
                System.out.println("Tipo: VIP (1), ADULTO MAYOR (2), REGULAR (3)");
                System.out.print("Seleccione tipo de cliente, si es VIP coloque \"1\", Sí es ADULTO MAYOR coloque \"2\", si es REGULAR coloque \"3\": ");
                int tipoInt = Integer.parseInt(scanner.nextLine());
                TipoCliente tipo = TipoCliente.REGULAR;
                if (tipoInt == 1) tipo = TipoCliente.VIP;
                if (tipoInt == 2) tipo = TipoCliente.ADULTO_MAYOR;
                System.out.print("Cantidad de productos: "); int cant = Integer.parseInt(scanner.nextLine());
                sistema.agregarCliente(new Cliente(cNom, tipo, cant));
            }
            case 7 -> {
                // Atender uno
                System.out.println(ANSI_CYAN + "\n Atender Siguiente Cliente" + ANSI_RESET);
                sistema.atenderSiguiente();
            }
            case 8 -> {
                // Ver cola
                System.out.println(ANSI_CYAN + "\n Cola de Clientes en Espera" + ANSI_RESET);
                sistema.verCola();
            }
            case 9 -> // Simular todo
                sistema.simularTodo();
            case 10 -> {
            }
            default -> System.out.println(ANSI_RED + "Opción no válida." + ANSI_RESET);
        }
        // No necesita acción aquí, el bucle se encarga
        // [cite: 128] Permitir volver al menú principal
        if (op != 10) pausa();
    }

    private void mostrarEncabezado() {
        System.out.println(ANSI_BOLD + ANSI_BLUE + "\n===================================================================================");
        System.out.println("                         SISTEMA DE GESTIÓN DE SUPERMERCADO                          ");
        System.out.println("===================================================================================" + ANSI_RESET);
    }

    private void pausa() {
        System.out.print("\n" + ANSI_YELLOW + "Presione ENTER para continuar..." + ANSI_RESET);
        scanner.nextLine();
    }

    private void limpiarConsola() {
        // Esto limpia la pantalla en terminales reales ANSI
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void main(String[] args) {
        new Menu().iniciar();
    }
}