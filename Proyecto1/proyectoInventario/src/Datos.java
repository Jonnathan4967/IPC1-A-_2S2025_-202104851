public class Datos {
    private Producto [] productos;
    private int totalProductos;
    private final int CAPACIDAD_MAXIMA = 100;

    public Datos() {
        productos = new Producto[CAPACIDAD_MAXIMA];
        totalProductos = 0;
    }

    //agregamos un producto nuevo al sistema

    public boolean agregarProducto(Producto producto) {
        if (totalProductos >= CAPACIDAD_MAXIMA) {
            return false;
        }
        productos[totalProductos] = producto;
        totalProductos++;
        return true;
    }

    //verificamos el codigo

    public boolean codigoExistente(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (codigo.equals(productos[i].getCodigo())) {
                return true;
            }
        }
        return false;
    }

    // obtenemos todos los productos en el sistema
    public Producto [] obtenerProductos() {
        Producto [] productosActivos = new Producto[totalProductos];
        for (int i = 0; i < totalProductos; i++) {
            productosActivos[i] = productos[i];
        }
        return productosActivos;
    }

    //obtener el total de los productos

    public int getTotalProductos() {
        return totalProductos;
    }

    //verificamos si el inventario esta lleno

    public boolean estaLleno(){
        return totalProductos >= CAPACIDAD_MAXIMA;
    }

    //verificamos si esta vacio

    public boolean estaVacio(){
        return totalProductos == 0;
    }

    //la capacidad maxima

    public int getCAPACIDAD_MAXIMA() {
        return CAPACIDAD_MAXIMA;
    }

    public String getCapacidadMaxima() {

        return "";
    }
}
