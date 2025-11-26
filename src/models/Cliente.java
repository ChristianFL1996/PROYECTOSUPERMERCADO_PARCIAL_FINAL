package models;

import java.time.Instant;

public class Cliente implements Comparable<Cliente> {
    private final String nombre;
    private final TipoCliente tipo;
    private final int cantidadProductos;
    private final long horaLlegada; // Timestamp para FIFO en empates

    public Cliente(String nombre, TipoCliente tipo, int cantidadProductos) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.cantidadProductos = cantidadProductos;
        this.horaLlegada = Instant.now().toEpochMilli();
    }

    // Lógica crítica para la Cola de Prioridad [cite: 72-76]
    @Override
    public int compareTo(Cliente otro) {
        // 1. Comparar por tipo (Menor valor = Mayor prioridad)
        int comparacionPrioridad = Integer.compare(this.tipo.getPrioridad(), otro.tipo.getPrioridad());
        
        if (comparacionPrioridad != 0) {
            return comparacionPrioridad;
        }
        
        // 2. Si hay empate en tipo, usar FIFO (quien llegó primero tiene menor timestamp)
        return Long.compare(this.horaLlegada, otro.horaLlegada);
    }

    public String getNombre() { return nombre; }
    public TipoCliente getTipo() { return tipo; }
    public int getCantidadProductos() { return cantidadProductos; }
    
    @Override
    public String toString() {
        return String.format("| %-20s | %-15s | %-10d |", nombre, tipo, cantidadProductos);
    }
}