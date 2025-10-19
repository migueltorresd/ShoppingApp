package com.example.shoppingapp

object CarritoManager {
    private val itemsCarrito = mutableListOf<ItemCarrito>()

    fun agregarProducto(producto: Producto) {
        val itemExistente = itemsCarrito.find { it.producto.id == producto.id }
        if (itemExistente != null) {
            itemExistente.cantidad++
        } else {
            itemsCarrito.add(ItemCarrito(producto, 1))
        }
    }

    fun obtenerItems(): List<ItemCarrito> {
        return itemsCarrito.toList()
    }

    fun obtenerCantidadTotal(): Int {
        return itemsCarrito.sumOf { it.cantidad }
    }

    fun obtenerTotal(): Double {
        return itemsCarrito.sumOf { it.getPrecioTotal() }
    }

    fun aumentarCantidad(productoId: Int) {
        val item = itemsCarrito.find { it.producto.id == productoId }
        item?.let { it.cantidad++ }
    }

    fun reducirCantidad(productoId: Int) {
        val item = itemsCarrito.find { it.producto.id == productoId }
        item?.let { 
            it.cantidad--
            if (it.cantidad <= 0) {
                itemsCarrito.remove(it)
            }
        }
    }

    fun vaciarCarrito() {
        itemsCarrito.clear()
    }

    fun removerProducto(productoId: Int) {
        itemsCarrito.removeAll { it.producto.id == productoId }
    }
}