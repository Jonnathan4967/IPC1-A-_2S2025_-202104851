class Producto {
    public String nombre, categoria, codigo;
    public double precio;
    public int cantidad;

    public Producto(String nombre, String categoria, String codigo, double precio, int cantidad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.codigo = codigo;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void mostrar() {
        System.out.println("Código: " + codigo + " | Nombre: " + nombre +
                " | Categoría: " + categoria + " | Precio: Q" + precio +
                " | Stock: " + cantidad);
    }
}
