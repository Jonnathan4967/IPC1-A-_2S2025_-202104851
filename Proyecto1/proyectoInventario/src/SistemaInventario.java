import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SistemaInventario {
    // Arrays principales
    static Producto[] productos = new Producto[100];
    static Venta[] ventas = new Venta[500];
    static String[] bitacora = new String[200];

    // Contadores
    static int totalProductos = 0, totalVentas = 0, totalBitacora = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE INVENTARIO - TIENDA DE ROPA ===");
        agregarBitacora("Sistema iniciado");

        int opcion;
        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1: agregarProducto(); break;
                case 2: buscarProducto(); break;
                case 3: eliminarProducto(); break;
                case 4: registrarVenta(); break;
                case 5: menuReportes(); break;
                case 6: datosEstudiante(); break;
                case 7: mostrarBitacora(); break;
                case 8:
                    System.out.println("¡Hasta pronto!");
                    agregarBitacora("Sistema cerrado");
                    break;
                default: System.out.println("Opción inválida");
            }
            if (opcion != 8) pausar();
        } while (opcion != 8);
    }

    static void mostrarMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Agregar Producto\n2. Buscar Producto\n3. Eliminar Producto");
        System.out.println("4. Registrar Venta\n5. Generar Reportes\n6. Datos del Estudiante");
        System.out.println("7. Ver Bitácora\n8. Salir");
        System.out.print("Seleccione opción: ");
    }

    static void agregarProducto() {
        System.out.println("\n=== AGREGAR PRODUCTO ===");

        if (totalProductos >= 100) {
            System.out.println("Error: Inventario lleno");
            agregarBitacora("Error: Inventario lleno");
            return;
        }

        // Solicitar datos con validación básica
        String nombre = solicitarTexto("Nombre: ");
        if (nombre == null) return;

        String categoria = solicitarTexto("Categoría: ");
        if (categoria == null) return;

        String codigo = solicitarTexto("Código: ");
        if (codigo == null || buscarPorCodigo(codigo) != null) {
            System.out.println("Error: Código vacío o ya existe");
            agregarBitacora("Error: Código duplicado - " + codigo);
            return;
        }

        System.out.print("Precio: Q");
        double precio = sc.nextDouble();
        if (precio <= 0) {
            System.out.println("Error: Precio debe ser positivo");
            return;
        }

        System.out.print("Cantidad: ");
        int cantidad = sc.nextInt();
        if (cantidad <= 0) {
            System.out.println("Error: Cantidad debe ser positiva");
            return;
        }

        productos[totalProductos++] = new Producto(nombre, categoria, codigo, precio, cantidad);
        System.out.println("✅ Producto agregado exitosamente");
        agregarBitacora("Producto agregado: " + nombre);
    }

    static void buscarProducto() {
        System.out.println("\n=== BUSCAR PRODUCTO ===");

        if (totalProductos == 0) {
            System.out.println("No hay productos registrados");
            return;
        }

        System.out.print("Ingrese código del producto: ");
        String codigo = sc.nextLine().trim();

        Producto producto = buscarPorCodigo(codigo);
        if (producto != null) {
            System.out.println("\n✅ Producto encontrado:");
            producto.mostrar();
            agregarBitacora("Producto encontrado: " + codigo);
        } else {
            System.out.println("❌ Producto no encontrado");
            agregarBitacora("Búsqueda fallida: " + codigo);
        }
    }

    static void eliminarProducto() {
        System.out.println("\n=== ELIMINAR PRODUCTO ===");

        if (totalProductos == 0) {
            System.out.println("No hay productos para eliminar");
            return;
        }

        // Mostrar productos
        System.out.println("Productos disponibles:");
        for (int i = 0; i < totalProductos; i++) {
            System.out.println("- " + productos[i].codigo + " | " + productos[i].nombre);
        }

        System.out.print("\nCódigo del producto a eliminar: ");
        String codigo = sc.nextLine().trim();

        int indice = buscarIndice(codigo);
        if (indice == -1) {
            System.out.println("❌ Producto no encontrado");
            return;
        }

        productos[indice].mostrar();
        System.out.print("¿Confirmar eliminación? (s/n): ");
        if (sc.nextLine().toLowerCase().equals("s")) {
            // Mover elementos hacia la izquierda
            for (int i = indice; i < totalProductos - 1; i++) {
                productos[i] = productos[i + 1];
            }
            productos[--totalProductos] = null;

            System.out.println("✅ Producto eliminado exitosamente");
            agregarBitacora("Producto eliminado: " + codigo);
        } else {
            System.out.println("⚠️ Eliminación cancelada");
        }
    }

    static void registrarVenta() {
        System.out.println("\n=== REGISTRAR VENTA ===");

        if (totalProductos == 0) {
            System.out.println("No hay productos para vender");
            return;
        }

        // Mostrar productos con stock
        System.out.println("Productos disponibles:");
        boolean hayStock = false;
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].cantidad > 0) {
                System.out.println("- " + productos[i].codigo + " | " + productos[i].nombre +
                        " | Stock: " + productos[i].cantidad + " | Q" + productos[i].precio);
                hayStock = true;
            }
        }

        if (!hayStock) {
            System.out.println("No hay productos con stock disponible");
            return;
        }

        System.out.print("\nCódigo del producto: ");
        String codigo = sc.nextLine().trim();

        Producto producto = buscarPorCodigo(codigo);
        if (producto == null || producto.cantidad <= 0) {
            System.out.println("❌ Producto no encontrado o sin stock");
            return;
        }

        System.out.println("Producto: " + producto.nombre + " | Stock disponible: " + producto.cantidad);
        System.out.print("Cantidad a vender: ");
        int cantidadVenta = sc.nextInt();
        sc.nextLine();

        if (cantidadVenta <= 0 || cantidadVenta > producto.cantidad) {
            System.out.println("❌ Cantidad inválida o stock insuficiente");
            return;
        }

        // Mostrar resumen
        double total = cantidadVenta * producto.precio;
        System.out.println("\n--- RESUMEN DE VENTA ---");
        System.out.println("Producto: " + producto.nombre + " | Cantidad: " + cantidadVenta +
                " | Total: Q" + total);

        System.out.print("¿Confirmar venta? (s/n): ");
        if (sc.nextLine().toLowerCase().equals("s")) {
            ventas[totalVentas++] = new Venta(codigo, producto.nombre, cantidadVenta, producto.precio);
            producto.cantidad -= cantidadVenta;

            guardarVentaEnArchivo(ventas[totalVentas - 1]);
            System.out.println("✅ Venta registrada exitosamente | Stock restante: " + producto.cantidad);
            agregarBitacora("Venta registrada: " + producto.nombre);
        } else {
            System.out.println("⚠️ Venta cancelada");
        }
    }

    static void menuReportes() {
        System.out.println("\n=== GENERAR REPORTES ===");
        System.out.println("1. Reporte de Stock\n2. Reporte de Ventas\n3. Ver Historial de Ventas\n4. Ver Todos los Productos");
        System.out.print("Opción: ");

        switch (sc.nextInt()) {
            case 1: generarReporte("Stock"); break;
            case 2: generarReporte("Ventas"); break;
            case 3: mostrarHistorialVentas(); break;
            case 4: mostrarTodosProductos(); break;
            default: System.out.println("Opción inválida");
        }
        sc.nextLine();
    }

    static void generarReporte(String tipo) {
        String archivo = obtenerFechaArchivo() + "_" + tipo + ".txt";

        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("=== REPORTE DE " + tipo.toUpperCase() + " ===\n");
            writer.write("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n\n");

            if (tipo.equals("Stock")) {
                writer.write("Total de productos: " + totalProductos + "\n\n");
                writer.write("DETALLE:\n" + "-".repeat(60) + "\n");
                for (int i = 0; i < totalProductos; i++) {
                    writer.write(productos[i].codigo + " | " + productos[i].nombre + " | " +
                            productos[i].categoria + " | Q" + productos[i].precio + " | Stock: " +
                            productos[i].cantidad + "\n");
                }
            } else {
                writer.write("Total de ventas: " + totalVentas + "\n\n");
                double totalGeneral = 0;
                writer.write("DETALLE:\n" + "-".repeat(60) + "\n");
                for (int i = 0; i < totalVentas; i++) {
                    writer.write(ventas[i].codigo + " | " + ventas[i].nombre + " | Cant: " +
                            ventas[i].cantidad + " | Total: Q" + ventas[i].total + " | " +
                            ventas[i].fecha + "\n");
                    totalGeneral += ventas[i].total;
                }
                writer.write("\nTOTAL GENERAL: Q" + totalGeneral + "\n");
            }

            System.out.println("✅ Reporte generado: " + archivo);
            agregarBitacora("Reporte de " + tipo.toLowerCase() + " generado");

        } catch (IOException e) {
            System.out.println("❌ Error al generar reporte: " + e.getMessage());
        }
    }

    static void mostrarHistorialVentas() {
        System.out.println("\n=== HISTORIAL DE VENTAS ===");

        if (totalVentas == 0) {
            System.out.println("No hay ventas registradas");
            return;
        }

        double totalGeneral = 0;
        for (int i = 0; i < totalVentas; i++) {
            System.out.println((i + 1) + ". ");
            ventas[i].mostrar();
            totalGeneral += ventas[i].total;
        }
        System.out.println("\nTOTAL GENERAL: Q" + totalGeneral);
    }

    static void mostrarTodosProductos() {
        System.out.println("\n=== TODOS LOS PRODUCTOS ===");

        if (totalProductos == 0) {
            System.out.println("No hay productos registrados");
            return;
        }

        for (int i = 0; i < totalProductos; i++) {
            System.out.println((i + 1) + ". ");
            productos[i].mostrar();
        }
    }

    static void datosEstudiante() {
        System.out.println("\n=== DATOS DEL ESTUDIANTE ===");
        System.out.println("Nombre: Jonnathan David Hernandez");
        System.out.println("Carnet: 202104851");
        System.out.println("Curso: Introducción a la Programación y Computación 1");
        System.out.println("Sección: A");
        System.out.println("GitHub: Jonnathan4967");
    }

    static void mostrarBitacora() {
        System.out.println("\n=== BITÁCORA ===");

        if (totalBitacora == 0) {
            System.out.println("No hay acciones registradas");
            return;
        }

        // Mostrar en orden inverso (más reciente primero)
        for (int i = totalBitacora - 1; i >= 0; i--) {
            System.out.println((totalBitacora - i) + ". " + bitacora[i]);
        }
    }

    // ===== FUNCIONES AUXILIARES =====

    static String solicitarTexto(String mensaje) {
        System.out.print(mensaje);
        String texto = sc.nextLine().trim();
        if (texto.isEmpty()) {
            System.out.println("Error: Campo vacío");
            return null;
        }
        return texto;
    }

    static Producto buscarPorCodigo(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].codigo.equals(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

    static int buscarIndice(String codigo) {
        for (int i = 0; i < totalProductos; i++) {
            if (productos[i].codigo.equals(codigo)) {
                return i;
            }
        }
        return -1;
    }

    static void guardarVentaEnArchivo(Venta venta) {
        try (FileWriter writer = new FileWriter("ventas.txt", true)) {
            writer.write(venta.codigo + "," + venta.nombre + "," + venta.cantidad + "," +
                    venta.total + "," + venta.fecha + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar venta: " + e.getMessage());
        }
    }

    static void agregarBitacora(String accion) {
        if (totalBitacora < 200) {
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            bitacora[totalBitacora++] = "[" + fecha + "] " + accion;
        }
    }

    static String obtenerFechaArchivo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"));
    }

    static void pausar() {
        System.out.println("\nPresione Enter para continuar...");
        sc.nextLine();
    }
}