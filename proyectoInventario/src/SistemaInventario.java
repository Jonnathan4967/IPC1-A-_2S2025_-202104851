import java.util.Scanner;

public class SistemaInventario {
    private static GestorProductos gestorProductos;

    public static void main(String[] args) {
        // Inicializar gestorProductos
        gestorProductos = new GestorProductos();

        var salir = false;
        var consola = new Scanner(System.in);

        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("    Bienvenido al sistema de inventario - Tienda de ropa JON'S");
        System.out.println("═══════════════════════════════════════════════════════════════");

        while (!salir) {
            mostrarMenu();

            try {
                var opcion = consola.nextInt();
                consola.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1 -> {
                        gestorProductos.agregarProducto();
                        pausar(consola);
                    }
                    case 2 -> {
                        gestorProductos.mostrarTodosLosProductos();
                        pausar(consola);
                    }
                    case 3 -> {
                        gestorProductos.eliminarProducto();
                        pausar(consola);
                    }
                    case 4 -> {
                        System.out.println("Gracias por usar el sistema de inventario");
                        System.out.println("¡Hasta pronto!");
                        salir = true;
                    }
                    default -> {
                        System.out.println("❌ Error: opción inválida. Por favor seleccione 1, 2, 3 o 4.");
                        pausar(consola);
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ Error: Por favor ingrese solo números.");
                consola.nextLine(); // Limpiar buffer
                pausar(consola);
            }
        }
        consola.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n┌─────────────────── MENÚ PRINCIPAL ───────────────────┐");
        System.out.println("│  1. 📦 Agregar Producto                              │");
        System.out.println("│  2. 📋 Mostrar Todos los Productos                   │");
        System.out.println("│  3. 🗑️  Eliminar Producto                            │");
        System.out.println("│  4. 🚪 Salir                                         │");
        System.out.println("└───────────────────────────────────────────────────────┘");
        System.out.print("Seleccione una opción: ");
    }

    private static void pausar(Scanner consola) {
        System.out.println("\n⏸️  Presione Enter para continuar...");
        consola.nextLine();
    }
}