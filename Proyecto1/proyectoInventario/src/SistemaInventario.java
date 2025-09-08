import java.util.Scanner;

public class SistemaInventario {
    private static GestorProductos gestorProductos;

    public static void main(String[] args) {
        // CORRECCION CRITICA: Inicializar gestorProductos
        gestorProductos = new GestorProductos();

        var salir = false;
        var consola = new Scanner(System.in);

        System.out.println("Bienvenido al sistema de inventario - tienda de ropa JON´S");

        while (!salir) {
            System.out.printf("""       
                     1. 📦 Agregar Producto          *
                    * 2.📋 Mostrar Todos los Productos*
                    * 3. 🚪 Salir                     *
                    ***********************************
                    Seleccione una opcion:\s""");

            try {
                var opciones = consola.nextInt();
                consola.nextLine(); // CORRECCION: Limpiar buffer

                switch (opciones) {
                    case 1 -> {
                        gestorProductos.agregarProducto();
                        System.out.println("\nPresione Enter para continuar...");
                        consola.nextLine();
                    }
                    case 2 -> {
                        gestorProductos.mostrarTodosLosProductos();
                        System.out.println("\nPresione Enter para continuar...");
                        consola.nextLine();
                    }
                    case 3 -> {
                        System.out.println("Gracias por usar el sistema de inventario");
                        System.out.println("Hasta pronto!");
                        salir = true;
                    }
                    default -> {
                        System.out.println("Error: opcion invalida. Por favor seleccione 1, 2 o 3.");
                        System.out.println("Presione Enter para continuar...");
                        consola.nextLine();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: Por favor ingrese solo numeros.");
                System.out.println("Presione Enter para continuar...");
                consola.nextLine();
                consola.nextLine();
            }
        }
        consola.close();

        consola.close();
    }
}