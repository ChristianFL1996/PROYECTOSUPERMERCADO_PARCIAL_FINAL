package structures;
import models.Producto;

/**
 * Implementación de Árbol AVL para gestión de inventario.
 * Soporta inserción, eliminación, búsqueda y actualización con auto-balanceo.
 */
public class ArbolAVL {
    private NodoAVL raiz;

    // --- Utilidades de Altura y Balance ---
    private int altura(NodoAVL N) {
        if (N == null) return 0;
        return N.altura;
    }

    private int getBalance(NodoAVL N) {
        if (N == null) return 0;
        return altura(N.izquierdo) - altura(N.derecho);
    }

    // --- Rotaciones [cite: 26] ---
    private NodoAVL rotacionDerecha(NodoAVL y) {
        NodoAVL x = y.izquierdo;
        NodoAVL T2 = x.derecho;
        x.derecho = y;
        y.izquierdo = T2;
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;
        return x;
    }

    private NodoAVL rotacionIzquierda(NodoAVL x) {
        NodoAVL y = x.derecho;
        NodoAVL T2 = y.izquierdo;
        y.izquierdo = x;
        x.derecho = T2;
        x.altura = Math.max(altura(x.izquierdo), altura(x.derecho)) + 1;
        y.altura = Math.max(altura(y.izquierdo), altura(y.derecho)) + 1;
        return y;
    }

    // --- Operaciones CRUD ---
    
    // Insertar [cite: 33]
    public boolean insertar(Producto producto) {
        if (buscar(producto.getCodigo()) != null) return false; // Duplicado
        raiz = insertarRec(raiz, producto);
        return true;
    }

    private NodoAVL insertarRec(NodoAVL nodo, Producto producto) {
        if (nodo == null) return new NodoAVL(producto);

        if (producto.getCodigo().compareTo(nodo.producto.getCodigo()) < 0)
            nodo.izquierdo = insertarRec(nodo.izquierdo, producto);
        else if (producto.getCodigo().compareTo(nodo.producto.getCodigo()) > 0)
            nodo.derecho = insertarRec(nodo.derecho, producto);
        else
            return nodo; // No duplicados

        // Actualizar altura y balancear
        nodo.altura = 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
        return balancear(nodo, producto.getCodigo());
    }

    // Balanceo genérico
    private NodoAVL balancear(NodoAVL nodo, String codigoInsertado) {
        int balance = getBalance(nodo);

        // Caso Izquierda-Izquierda
        if (balance > 1 && codigoInsertado.compareTo(nodo.izquierdo.producto.getCodigo()) < 0)
            return rotacionDerecha(nodo);

        // Caso Derecha-Derecha
        if (balance < -1 && codigoInsertado.compareTo(nodo.derecho.producto.getCodigo()) > 0)
            return rotacionIzquierda(nodo);

        // Caso Izquierda-Derecha
        if (balance > 1 && codigoInsertado.compareTo(nodo.izquierdo.producto.getCodigo()) > 0) {
            nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
            return rotacionDerecha(nodo);
        }

        // Caso Derecha-Izquierda
        if (balance < -1 && codigoInsertado.compareTo(nodo.derecho.producto.getCodigo()) < 0) {
            nodo.derecho = rotacionDerecha(nodo.derecho);
            return rotacionIzquierda(nodo);
        }
        return nodo;
    }
    
    // Eliminar [cite: 48]
    public boolean eliminar(String codigo) {
        if (buscar(codigo) == null) return false;
        raiz = eliminarRec(raiz, codigo);
        return true;
    }

    private NodoAVL eliminarRec(NodoAVL root, String codigo) {
        if (root == null) return root;

        if (codigo.compareTo(root.producto.getCodigo()) < 0)
            root.izquierdo = eliminarRec(root.izquierdo, codigo);
        else if (codigo.compareTo(root.producto.getCodigo()) > 0)
            root.derecho = eliminarRec(root.derecho, codigo);
        else {
            // Nodo encontrado
            if ((root.izquierdo == null) || (root.derecho == null)) {
                NodoAVL temp = (root.izquierdo != null) ? root.izquierdo : root.derecho;
                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {
                NodoAVL temp = nodoValorMinimo(root.derecho);
                root.producto = temp.producto; // Copiar datos
                root.derecho = eliminarRec(root.derecho, temp.producto.getCodigo());
            }
        }

        if (root == null) return root;

        root.altura = Math.max(altura(root.izquierdo), altura(root.derecho)) + 1;
        int balance = getBalance(root);

        // Rebalanceo en eliminación
        if (balance > 1 && getBalance(root.izquierdo) >= 0)
            return rotacionDerecha(root);
        if (balance > 1 && getBalance(root.izquierdo) < 0) {
            root.izquierdo = rotacionIzquierda(root.izquierdo);
            return rotacionDerecha(root);
        }
        if (balance < -1 && getBalance(root.derecho) <= 0)
            return rotacionIzquierda(root);
        if (balance < -1 && getBalance(root.derecho) > 0) {
            root.derecho = rotacionDerecha(root.derecho);
            return rotacionIzquierda(root);
        }
        return root;
    }

    private NodoAVL nodoValorMinimo(NodoAVL nodo) {
        NodoAVL current = nodo;
        while (current.izquierdo != null) current = current.izquierdo;
        return current;
    }

    // Buscar [cite: 39]
    public Producto buscar(String codigo) {
        NodoAVL res = buscarRec(raiz, codigo);
        return res == null ? null : res.producto;
    }

    private NodoAVL buscarRec(NodoAVL nodo, String codigo) {
        if (nodo == null || nodo.producto.getCodigo().equals(codigo)) return nodo;
        if (codigo.compareTo(nodo.producto.getCodigo()) < 0) return buscarRec(nodo.izquierdo, codigo);
        return buscarRec(nodo.derecho, codigo);
    }

    // Recorrido In-Orden [cite: 53]
    public void imprimirInventario() {
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-15s | %-9s | %-5s |\n", "CÓDIGO", "NOMBRE", "CATEGORÍA", "PRECIO", "STOCK");
        System.out.println("---------------------------------------------------------------------");
        inOrden(raiz);
        System.out.println("---------------------------------------------------------------------");
    }

    private void inOrden(NodoAVL nodo) {
        if (nodo != null) {
            inOrden(nodo.izquierdo);
            System.out.println(nodo.producto);
            inOrden(nodo.derecho);
        }
    }
}