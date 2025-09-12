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
            System.out.println("El inventario está lleno. No se pueden agregar más productos. Capacidad máxima: "
                    + datos.getCapacidadMaxima());
            return;
        }

        try {
            // Solicitar los datos
            System.out.print("Ingrese el nombre del producto: ");
            String nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("Error: el nombre no puede estar vacío");
                return;
            }

            System.out.print("Ingrese la categoría (camisas, pantalones, accesorios, etc.): ");
            String categoria = scanner.nextLine().trim();
            if (categoria.isEmpty()) {
                System.out.println("Error: la categoría no puede estar vacía");
                return;
            }

            // Ingresar el código
            System.out.print("Ingrese el código del producto: ");
            String codigo = scanner.nextLine().trim();
            if (codigo.isEmpty()) {
                System.out.println("Error: el código no puede estar vacío");
                return;
            }

            // Verificar si el código existe
            if (datos.codigoExistente(codigo)) {
                System.out.println("Error: el código del producto ya existe");
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
            System.out.println("Error: entrada inválida. Por favor intente de nuevo.");
            scanner.nextLine(); // Limpiar buffer
        }
    }

    // NUEVA FUNCIONALIDAD: Eliminar producto
    public void eliminarProducto() {
        System.out.println("\n ======ELIMINAR PRODUCTO =======");

        // Verificar si hay productos en el inventario
        if (datos.estaVacio()) {
            System.out.println("El inventario está vacío. No hay productos para eliminar.");
            return;
        }

        // Mostrar productos disponibles para referencia
        System.out.println("\nProductos disponibles:");
        Producto[] productos = datos.obtenerProductos();
        for (int i = 0; i < productos.length; i++) {
            System.out.println("- Código: [" + productos[i].getCodigo() + "] - " + productos[i].getNombre());
        }

        try {
            System.out.print("\nIngrese el código del producto a eliminar: ");
            String codigo = scanner.nextLine().trim();

            if (codigo.isEmpty()) {
                System.out.println("Error: el código no puede estar vacío");
                return;
            }

            System.out.println("Buscando producto con código: [" + codigo + "]");

            // Buscar el producto antes de eliminarlo
            Producto productoAEliminar = datos.buscarProductoPorCodigo(codigo);

            if (productoAEliminar == null) {
                System.out.println("Error: no se encontró un producto con el código: [" + codigo + "]");
                System.out.println("Verifique que el código sea exactamente igual al mostrado arriba.");
                return;
            }

            // Mostrar información del producto encontrado
            System.out.println("\nProducto encontrado:");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            productoAEliminar.mostrarInfo();
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // Pedir confirmación
            System.out.print("\n¿Está seguro de que desea eliminar este producto? (s/n): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("s") || confirmacion.equals("si") || confirmacion.equals("sí")) {
                // Intentar eliminar el producto
                if (datos.eliminarProducto(codigo)) {
                    System.out.println("\n✅ Producto eliminado correctamente");
                    System.out.println("Total de productos restantes: " + datos.getTotalProductos());
                } else {
                    System.out.println("\n❌ Error: no se pudo eliminar el producto");
                }
            } else {
                System.out.println("\n⚠️  Eliminación cancelada por el usuario");
            }

        } catch (Exception e) {
            System.out.println("Error: entrada inválida. Por favor intente de nuevo.");
            scanner.nextLine(); // Limpiar buffer
        }
    }

    public void mostrarTodosLosProductos() {
        System.out.println("---- LISTA DE PRODUCTOS EN INVENTARIO ----");

        if (datos.estaVacio()) {
            System.out.println("El inventario está vacío");
            return;
        }

        Producto[] productos = datos.obtenerProductos();
        System.out.println("Total de productos en inventario: " + datos.getTotalProductos());
        System.out.println("_________________________________________________________________");

        for (int i = 0; i < productos.length; i++) {
            System.out.println("Producto #" + (i + 1));
            productos[i].mostrarInfo();
            System.out.println("-----------------------------------------------------------------");
        }
    }

    public Datos getDatos() {
        return datos;
    }
}