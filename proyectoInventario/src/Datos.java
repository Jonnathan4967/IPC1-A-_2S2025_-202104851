public class Datos {
    private Producto[] productos;
    private int totalProductos;
    private final int CAPACIDAD_MAXIMA = 100;

    public Datos() {
        productos = new Producto[CAPACIDAD_MAXIMA];
        totalProductos = 0;
    }

    // Agregamos un producto nuevo al sistema
    public boolean agregarProducto(Producto producto) {
        if (totalProductos >= CAPACIDAD_MAXIMA) {
            return false;
        }
        productos[totalProductos] = producto;
        totalProductos++;
        return true;
    }

    // Verificamos el código
    public boolean codigoExistente(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].getCodigo().equalsIgnoreCase(codigo.trim())) {
                return true;
            }
        }
        return false;
    }

    // NUEVO: Buscar producto por código
    public Producto buscarProductoPorCodigo(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].getCodigo().equals(codigo)) {
                return productos[i];
            }
        }
        return null; // No encontrado
    }

    // NUEVO: Eliminar producto por código
    public boolean eliminarProducto(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].getCodigo().equals(codigo)) {
                // Mover todos los elementos hacia la izquierda para eliminar el producto
                for (int j = i; j < totalProductos - 1; j++) {
                    productos[j] = productos[j + 1];
                }
                // Limpiar la última posición
                productos[totalProductos - 1] = null;
                totalProductos--;
                return true; // Producto eliminado exitosamente
            }
        }
        return false; // Producto no encontrado
    }

    // Obtenemos todos los productos en el sistema
    public Producto[] obtenerProductos() {
        Producto[] productosActivos = new Producto[totalProductos];
        for (int i = 0; i < totalProductos; i++) {
            productosActivos[i] = productos[i];
        }
        return productosActivos;
    }

    // Obtener el total de los productos
    public int getTotalProductos() {
        return totalProductos;
    }

    // Verificamos si el inventario está lleno
    public boolean estaLleno() {
        return totalProductos >= CAPACIDAD_MAXIMA;
    }

    // Verificamos si está vacío
    public boolean estaVacio() {
        return totalProductos == 0;
    }

    // La capacidad máxima
    public int getCAPACIDAD_MAXIMA() {
        return CAPACIDAD_MAXIMA;
    }

    public String getCapacidadMaxima() {
        return String.valueOf(CAPACIDAD_MAXIMA);
    }
}