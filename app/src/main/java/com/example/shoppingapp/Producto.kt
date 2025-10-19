package com.example.shoppingapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val descripcion: String,
    val imagenResId: Int = android.R.drawable.ic_menu_gallery
) : Parcelable