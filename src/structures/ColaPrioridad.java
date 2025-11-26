package structures;
import java.util.ArrayList;
import models.Cliente;

/**
 * Cola de prioridad implementada con un Min-Heap binario.
 * Ordena clientes según VIP > Adulto Mayor > Regular.
 */
public class ColaPrioridad {
    private final ArrayList<Cliente> heap;

    public ColaPrioridad() {
        this.heap = new ArrayList<>();
    }

    public boolean esVacia() {
        return heap.isEmpty();
    }

    // Agregar (Bubble Up) [cite: 78, 80]
    public void agregar(Cliente cliente) {
        heap.add(cliente);
        subir(heap.size() - 1);
    }

    // Extraer el de mayor prioridad (Bubble Down) [cite: 78, 80]
    public Cliente extraer() {
        if (esVacia()) return null;
        Cliente maxPrioridad = heap.get(0);
        Cliente ultimo = heap.remove(heap.size() - 1);
        
        if (!esVacia()) {
            heap.set(0, ultimo);
            bajar(0);
        }
        return maxPrioridad;
    }

    private void subir(int indice) {
        int padre = (indice - 1) / 2;
        while (indice > 0 && heap.get(indice).compareTo(heap.get(padre)) < 0) {
            intercambiar(indice, padre);
            indice = padre;
            padre = (indice - 1) / 2;
        }
    }

    private void bajar(int indice) {
        int menor = indice;
        int izq = 2 * indice + 1;
        int der = 2 * indice + 2;

        if (izq < heap.size() && heap.get(izq).compareTo(heap.get(menor)) < 0) {
            menor = izq;
        }
        if (der < heap.size() && heap.get(der).compareTo(heap.get(menor)) < 0) {
            menor = der;
        }
        if (menor != indice) {
            intercambiar(indice, menor);
            bajar(menor);
        }
    }

    private void intercambiar(int i, int j) {
        Cliente temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Mostrar estado [cite: 91]
    public void mostrarCola() {
        System.out.println("=== CLIENTES EN ESPERA ===");
        System.out.printf("| %-5s | %-20s | %-15s | %-10s |\n", "Pos", "Nombre", "Tipo", "Productos");
        System.out.println("-------------------------------------------------------------");
        
        // Nota: Mostramos una copia ordenada sin destruir el heap original
        ArrayList<Cliente> copia = new ArrayList<>(heap);
        copia.sort(Cliente::compareTo);
        
        for (int i = 0; i < copia.size(); i++) {
            Cliente c = copia.get(i);
            System.out.printf("| %-5d %s\n", (i + 1), c.toString());
        }
        System.out.println("-------------------------------------------------------------");
    }
    
    public int tamano() { return heap.size(); }
}