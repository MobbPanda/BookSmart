package org.example

abstract class Libro(
    val titulo: String,
    val autor: String,
    val precioBase: Int,      // Se deja en entero ya que el CLP no trabaja con decimales
    val diasPrestamo: Int // Dias de prestamo
) {
    // Evita que los libros se creen con precios imposibles, y también evita prestamos negativos
    init {
        require(precioBase >= 0) { "El precio del libro no puede ser negativo" }
        require(diasPrestamo >= 0) { "Los dias de prestamo no pueden ser negativos" }
    }

    // Devuelve true si el libro tiene días de préstamo > 0. Con require() ya evitamos dias de prestamo negativos, significa “no se presta” si esta en 0
    open val esPrestable: Boolean
        get() = diasPrestamo > 0

    // Metodo base que retorna el precio por defecto del libro
    open fun costoFinal(): Int = precioBase

    // Metodo que arma texto para la UI, con titulo, autor, dias de prestamo y el precio base del libro
    open fun descripcion(): String = "$titulo - $autor | $diasPrestamo dias | precio base: $precioBase"

  }

class LibroDigital(
    titulo: String,
    autor: String,
    precioBase: Int,
    diasPrestamo: Int,
    val drm: Boolean
) : Libro(titulo, autor, precioBase, diasPrestamo) {

    // Si no tiene DRM, 5% menos sobre el precioBase del libro
    override fun costoFinal(): Int =
        if (drm) precioBase else (precioBase * 95) / 100

    override fun descripcion(): String {
        val etiqueta = if (drm) "Digital (con DRM)" else "Digital (sin DRM)"
        return "[$etiqueta] " + super.descripcion()
    }
}

class LibroFisico(
    titulo: String,
    autor: String,
    precioBase: Int,
    diasPrestamo: Int,
    val esReferencia: Boolean
) : Libro(titulo, autor, precioBase, diasPrestamo) {

    // Si es de referencia, no es prestable
    override val esPrestable: Boolean
        get() = !esReferencia && super.esPrestable

    // A prueba de errores
    override fun costoFinal(): Int =
        if (esReferencia) 0 else precioBase

    override fun descripcion(): String {
        val tipo = if (esReferencia) "Referencia (NO se presta)" else "Fisico"
        return "[$tipo] " + super.descripcion()
    }
}