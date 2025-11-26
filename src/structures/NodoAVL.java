package structures;
import models.Producto;

public class NodoAVL {
    Producto producto;
    NodoAVL izquierdo;
    NodoAVL derecho;
    int altura;

    public NodoAVL(Producto producto) {
        this.producto = producto;
        this.altura = 1;
    }
}