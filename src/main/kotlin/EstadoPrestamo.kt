package org.example

// Una sealed class permite tener un conjunto cerrado de estados
sealed class EstadoPrestamo {
    object Pendiente : EstadoPrestamo()       // Cuando la solicitud recién se crea
    object EnPrestamo : EstadoPrestamo()      // Cuando ya está activo el préstamo
    object Devuelto : EstadoPrestamo()        // Cuando el libro fue devuelto
    data class Error(val msg: String) : EstadoPrestamo() // Para manejar errores
}
