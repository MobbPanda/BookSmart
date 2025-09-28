package org.example

import kotlinx.coroutines.runBlocking
import java.text.NumberFormat
import java.util.Locale

// Funcion auxiliar para formatear CLP
fun Int.toCLP(): String {
    val formato = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    return formato.format(this)
}

fun main() = runBlocking {
    println("=== SISTEMA BOOKSMART ===")

    // 1. Inicializar catalogo desde GestorPrestamo
    val catalogo = GestorPrestamo.inicializarCatalogo()

    println("\nCatalogo disponible:")
    catalogo.forEachIndexed { i, libro ->
        println("${i + 1}. ${libro.descripcion()} (${libro.costoFinal().toCLP()})")
    }

    // 2. Seleccion de libros (ejemplo: usuario elige 1 y 3)
    val seleccionados: List<Libro> = try {
        listOf(catalogo[0], catalogo[2])
    } catch (e: Exception) {
        println("Error al seleccionar libros: ${e.message}")
        emptyList()
    }
    println("\nSeleccion de libros para prestamo: 1,3")

    // 3. Tipo de usuario
    val tipoUsuario = "docente"
    println("Tipo de usuario: $tipoUsuario")

    // 4. Validaciones de prestamo
    println("\nValidando solicitud...")
    seleccionados.forEachIndexed { i, libro ->
        if (!libro.esPrestable) {
            println("- Libro #${i + 1} (${libro.titulo}) no se puede prestar")
        } else {
            println("- Libro #${i + 1} (${libro.titulo}) OK")
        }
    }

    // 5. Procesar prestamo con GestorPrestamo
    println("\nProcesando prestamo...")
    val estado = try {
        if (seleccionados.isNotEmpty()) {
            GestorPrestamo.procesarPrestamo(seleccionados[0])
        } else {
            EstadoPrestamo.Error("No se seleccionaron libros")
        }
    } catch (e: Exception) {
        println("Error al procesar el prestamo: ${e.message}")
        EstadoPrestamo.Error("Fallo en el prestamo")
    }

    // 6. Calcular subtotal y aplicar descuento con GestorPrestamo
    val subtotal = seleccionados.sumOf { it.costoFinal() }
    val totalConDescuento = GestorPrestamo.aplicarDescuento(subtotal, tipoUsuario)

    // 7. Calcular multa con GestorPrestamo
    val diasRetraso = 0
    val multa = try {
        if (seleccionados.isNotEmpty()) {
            GestorPrestamo.calcularMulta(seleccionados[0], diasRetraso)
        } else 0
    } catch (e: Exception) {
        println("Error al calcular multa: ${e.message}")
        0
    }

    // 8. Generar reporte con GestorPrestamo
    println("\n=== RESUMEN DEL PRESTAMO ===")
    GestorPrestamo.generarReporte(seleccionados)
    println("Subtotal: ${subtotal.toCLP()}")
    println("Total con descuento ($tipoUsuario): ${totalConDescuento.toCLP()}")
    println("Multa por retraso ($diasRetraso dias): +${multa.toCLP()}")
    println("Total final: ${(totalConDescuento + multa).toCLP()}")

    println("\nEstado final: $estado")
    println("Tiempo estimado para retiro/activacion digital: 3 s.")
}