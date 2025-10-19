package com.example.shoppingapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int = 1
) : Parcelable {
    fun getPrecioTotal(): Double {
        return producto.precio * cantidad
    }
}