package com.example.shoppingapp

import android.content.Context

object CarritoManager {
    private var dbHelper: DatabaseHelper? = null
    
    fun inicializar(context: Context) {
        if (dbHelper == null) {
            dbHelper = DatabaseHelper(context.applicationContext)
        }
    }

    fun agregarProducto(producto: Producto) {
        dbHelper?.agregarAlCarrito(producto.id, 1)
    }

    fun obtenerItems(): List<ItemCarrito> {
        return dbHelper?.obtenerItemsCarrito() ?: emptyList()
    }

    fun obtenerCantidadTotal(): Int {
        return obtenerItems().sumOf { it.cantidad }
    }

    fun obtenerTotal(): Double {
        return obtenerItems().sumOf { it.getPrecioTotal() }
    }

    fun aumentarCantidad(productoId: Int) {
        val items = obtenerItems()
        val item = items.find { it.producto.id == productoId }
        item?.let {
            dbHelper?.actualizarCantidadCarrito(productoId, it.cantidad + 1)
        }
    }

    fun reducirCantidad(productoId: Int) {
        val items = obtenerItems()
        val item = items.find { it.producto.id == productoId }
        item?.let {
            val nuevaCantidad = it.cantidad - 1
            if (nuevaCantidad <= 0) {
                dbHelper?.eliminarDelCarrito(productoId)
            } else {
                dbHelper?.actualizarCantidadCarrito(productoId, nuevaCantidad)
            }
        }
    }

    fun vaciarCarrito() {
        dbHelper?.vaciarCarrito()
    }

    fun removerProducto(productoId: Int) {
        dbHelper?.eliminarDelCarrito(productoId)
    }
}
