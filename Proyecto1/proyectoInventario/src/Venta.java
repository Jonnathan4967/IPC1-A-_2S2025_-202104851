import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Venta {
    public String codigo, nombre, fecha;
    public int cantidad;
    public double total;

    public Venta(String codigo, String nombre, int cantidad, double precioUnitario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.total = cantidad * precioUnitario;
        this.fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public void mostrar() {
        System.out.println("Producto: " + nombre + " | Cantidad: " + cantidad +
                " | Total: Q" + total + " | Fecha: " + fecha);
    }
}