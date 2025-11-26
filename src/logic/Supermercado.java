package logic;

import models.Cliente;
import models.Producto;
import structures.ArbolAVL;
import structures.ColaPrioridad;

public class Supermercado {
    private final ArbolAVL inventario;
    private final ColaPrioridad colaClientes;

    public Supermercado() {
        this.inventario = new ArbolAVL();
        this.colaClientes = new ColaPrioridad();
        inicializarDatosPrueba();
    }

    private void inicializarDatosPrueba() {
        // Datos iniciales para probar

    }

    // Métodos puente para inventario
    public String agregarProducto(String cod, String nom, String cat, double precio, int stock) {
        if (precio <= 0 || stock < 0) return "Error: Valores inválidos";
        boolean exito = inventario.insertar(new Producto(cod, nom, cat, precio, stock));
        return exito ? "Producto insertado correctamente" : "Error: Código duplicado";
    }

    public Producto buscarProducto(String codigo) {
        return inventario.buscar(codigo);
    }
    
    public String actualizarProducto(String cod, String nom, String cat, double precio, int stock) {
        Producto p = inventario.buscar(cod);
        if (p == null) return "Producto no existe";
        p.setNombre(nom);
        p.setCategoria(cat);
        p.setPrecio(precio);
        p.setStock(stock);
        return "Producto actualizado exitosamente";
    }

    public String eliminarProducto(String codigo) {
        boolean exito = inventario.eliminar(codigo);
        return exito ? "Producto eliminado" : "Producto no encontrado";
    }

    public void mostrarInventario() {
        inventario.imprimirInventario();
    }

    // Métodos para clientes
    public void agregarCliente(Cliente c) {
        colaClientes.agregar(c);
        System.out.println(">> Cliente " + c.getNombre() + " agregado a la cola. Prioridad: " + c.getTipo());
    }

    public void verCola() {
        colaClientes.mostrarCola();
    }

    // Simulación [cite: 81-86]
    public void atenderSiguiente() {
        Cliente c = colaClientes.extraer();
        if (c == null) {
            System.out.println("No hay clientes en espera.");
            return;
        }
        procesarCliente(c);
    }

    public void simularTodo() {
        System.out.println("\n=== INICIANDO SIMULACIÓN MASIVA ===");
        while (!colaClientes.esVacia()) {
            atenderSiguiente();
        }
        System.out.println("=== TODOS LOS CLIENTES HAN SIDO ATENDIDOS ===");
    }

    private void procesarCliente(Cliente c) {
        int tiempoTotal = c.getCantidadProductos() * 2; // 2 seg por producto [cite: 84]
        System.out.println("\n----------------------------------------");
        System.out.printf("Atendiendo a: %s (%s)\n", c.getNombre(), c.getTipo());
        System.out.printf("Productos: %d | Tiempo estimado: %d segundos\n", c.getCantidadProductos(), tiempoTotal);
        
        try {
            // Simulación visual de progreso
            System.out.print("Procesando: [");
            for(int i=0; i<tiempoTotal; i++) {
                Thread.sleep(1000); // Esperamos 1 segundo real por cada paso (acelerado para demo: usa 1000 para realismo o 200 para rapidez)
                System.out.print("=");
            }
            System.out.println("] 100%");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(">> Cliente atendido correctamente.");
    }
}