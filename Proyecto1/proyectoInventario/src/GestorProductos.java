import java.util.Scanner;

public class GestorProductos {
    private Datos datos;
    private Scanner scanner;

    public GestorProductos() {
        this.datos = new Datos();
        this.scanner = new Scanner(System.in);
    }

    public void agregarProducto() {
        System.out.println("\n ======AGREGAR PRODUCTO =======");

        // Verificamos la capacidad
        if (datos.estaLleno()) {
            System.out.println("El inventario esta lleno. No se pueden agregar mas productos. Capacidad maxima: "
                    + datos.getCapacidadMaxima());
            return;
        }

        try {
            // Solicitar los datos
            System.out.print("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("Error: el nombre no puede estar vacio");
                return;
            }

            System.out.print("Ingrese la categoría (camisas, pantalones, accesorios, etc.): ");
            String categoria = scanner.nextLine().trim();
            if (categoria.isEmpty()) {
                System.out.println("Error: la categoria no puede estar vacia");
                return;
            }

            // Ingresar el codigo
            System.out.print("Ingrese el codigo del producto: ");
            String codigo = scanner.nextLine().trim();
            if (codigo.isEmpty()) {
                System.out.println("Error: el codigo no puede estar vacio");
                return;
            }

            // Verificar si el codigo existe
            if (datos.codigoExistente(codigo)) {
                System.out.println("Error: el codigo del producto ya existe");
                return;
            }

            System.out.print("Ingrese el precio del producto: Q");
            double precio = scanner.nextDouble();
            scanner.nextLine(); // Limpiar buffer

            if (precio <= 0) {
                System.out.println("Error: el precio debe ser mayor que 0");
                return;
            }

            // Ingresar la cantidad
            System.out.print("Ingrese la cantidad del producto: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            if (cantidad <= 0) {
                System.out.println("Error: la cantidad debe ser mayor que 0");
                return;
            }

            // Crear y agregar los productos
            Producto nuevoProducto = new Producto(nombre, categoria, codigo, precio, cantidad);

            if (datos.agregarProducto(nuevoProducto)) {
                System.out.println("Producto agregado correctamente");
                System.out.println("Total de productos actualmente: " + datos.getTotalProductos());
            } else {
                System.out.println("Error: no se pudo agregar el producto");
            }

        } catch (Exception e) {
            System.out.println("Error: entrada invalida. Por favor intente de nuevo.");
            scanner.nextLine(); // Limpiar buffer
        }
    }

    public void mostrarTodosLosProductos() {
        System.out.println("---- LISTA DE PRODUCTOS EN INVENTARIO ----");

        if (datos.estaVacio()) {
            System.out.println("El inventario esta vacio");
            return;
        }

        Producto[] productos = datos.obtenerProductos();
        System.out.println("Total de productos en inventario: " + datos.getTotalProductos());
        System.out.println("_________________________________________________________________");

        for (int i = 0; i < productos.length; i++) {
            System.out.println("Producto #" + (i + 1));
            productos[i].mostrarInfo();
            System.out.println("--------------------------------------------------------------");
        }
    }

    public Datos getDatos() {
        return datos;
    }
}
