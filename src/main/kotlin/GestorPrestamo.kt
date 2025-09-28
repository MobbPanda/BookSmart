package org.example

class GestorPrestamo {

    fun inicializarCatalogo(): List<Libro> = listOf(
        LibroFisico("Estructuras de Datos", "Goodrich", 12990, 7, esReferencia = false),
        LibroFisico("Diccionario Enciclopédico", "Varios", 15990, 0, esReferencia = true),
        LibroDigital("Programación en Kotlin", "JetBrains", 9990, 10, drm = true),
        LibroDigital("Algoritmos Básicos", "Cormen", 11990, 10, drm = false)
    )

    fun aplicarDescuento(subtotal: Int, tipoUsuario: String): Int {
        val descuento = when (tipoUsuario.lowercase()) {
            "estudiante" -> 0.10
            "docente" -> 0.15
            else -> 0.0
        }
        return (subtotal * (1 - descuento)).toInt()
    }

    fun procesarPrestamo(libro: Libro): EstadoPrestamo {
        return if (!libro.esPrestable) {
            EstadoPrestamo.Error("El libro '${libro.titulo}' no se puede prestar.")
        } else {
            println("Procesando préstamo...")
            EstadoPrestamo.EnPrestamo
        }
    }

    fun calcularMulta(libro: Libro, diasRetraso: Int): Int {
        return if (diasRetraso > 0) {
            val multaPorDia = 500
            diasRetraso * multaPorDia
        } else 0
    }

    fun generarReporte(librosPrestados: List<Libro>) {
        println("Reporte de libros prestados:")
        librosPrestados
            .filter { it.esPrestable }
            .map { it.descripcion() }
            .forEach { println(it) }

        val total = librosPrestados.sumOf { it.costoFinal() }
        println("Total sin descuentos: $total")
    }
}