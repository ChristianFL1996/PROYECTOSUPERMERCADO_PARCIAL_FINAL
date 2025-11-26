=========================================================
PROYECTO FINAL: SISTEMA DE SUPERMERCADO (AVL + HEAP)
Curso: Algoritmos y Procesos II
=========================================================

DESCRIPCIÓN
-----------
Sistema de gestión para supermercado que implementa dos estructuras de datos avanzadas
desde cero (sin librerías externas):
1. Árbol AVL: Para la gestión eficiente y balanceada del inventario de productos.
2. Cola de Prioridad (Min-Heap): Para la atención de clientes basada en reglas de negocio.

REGLAS DE NEGOCIO
-----------------
1. Prioridad de Clientes:
   - Nivel 1: VIP (Máxima prioridad)
   - Nivel 2: ADULTO MAYOR
   - Nivel 3: REGULAR
   * En caso de empate de prioridad, se atiende por orden de llegada (FIFO).

2. Inventario:
   - Los productos se ordenan alfabéticamente por CÓDIGO único.
   - El árbol se autobalancea tras cada inserción o eliminación.

COMPILACIÓN Y EJECUCIÓN
-----------------------
Requisitos: Java JDK 8 o superior.

1. Navegar a la carpeta 'src' del proyecto desde la terminal:
   cd ProyectoSupermercado/src

2. Compilar todos los archivos:
   javac ui/Menu.java logic/*.java models/*.java structures/*.java

3. Ejecutar el sistema:
   java ui.Menu

ESTRUCTURA DE ARCHIVOS
----------------------
/models      -> Clases base (Producto, Cliente, Enum TipoCliente)
/structures  -> Lógica algorítmica (ArbolAVL, NodoAVL, ColaPrioridad)
/logic       -> Controlador del sistema (Supermercado)
/ui          -> Interfaz de usuario en consola (Menu)

AUTOR
-----
Christian Fernando Flores Luna
Carnet 2427035