package org.example

open class Libro(
    val titulo: String,
    val autor: String,
    val precio: Int,      // Se deja en entero ya que el CLP no trabaja con decimales
    val diasPrestamo: Int // Dias de prestamo
) {
    init {
        require(precio >= 0) { "El precio del libro no puede ser cero o negativo" }
        require(diasPrestamo >= 0) { "Los dias de prestamo no pueden ser cero o negativos" }
    }
}