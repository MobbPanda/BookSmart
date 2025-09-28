package org.example
import kotlinx.coroutines.delay //// Import necesario para simular espera en corrutinas

// Se define como 'object' porque no se necesita crear múltiples instancias
object GestorPrestamo {

    fun inicializarCatalogo(): List<Libro> = listOf(
        LibroFisico("Estructuras de Datos", "Goodrich", 12990, 7, esReferencia = false),
        LibroFisico("Diccionario Enciclopedico", "Varios", 15990, 0, esReferencia = true),
        LibroDigital("Programacion en Kotlin", "JetBrains", 9990, 10, drm = true),
        LibroDigital("Algoritmos Basicos", "Cormen", 11990, 10, drm = false)
    )

    fun aplicarDescuento(subtotal: Int, tipoUsuario: String): Int {
        val descuento = when (tipoUsuario.lowercase()) {
            "estudiante" -> 0.10
            "docente" -> 0.15
            else -> 0.0
        }
        return (subtotal * (1 - descuento)).toInt()
    }

    // Simula el proceso de préstamo de un libro, con espera artificial utilizando delay
    suspend fun procesarPrestamo(libro: Libro): EstadoPrestamo {
        return if (!libro.esPrestable) {
            EstadoPrestamo.Error("El libro '${libro.titulo}' no se puede prestar.")
        } else {
            delay(3000) // espera 3 segundos
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
        // Filtra los libros que sí se pueden prestar y muestra su descripción
        librosPrestados
            .filter { it.esPrestable }
            .map { it.descripcion() }
            .forEach { println(it) }

        val total = librosPrestados.sumOf { it.costoFinal() }
        println("Total sin descuentos: $total")
    }
}