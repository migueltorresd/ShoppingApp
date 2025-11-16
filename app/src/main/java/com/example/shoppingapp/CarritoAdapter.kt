package com.example.shoppingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

class CarritoAdapter(
    private var itemsCarrito: MutableList<ItemCarrito>,
    private val onCantidadChanged: () -> Unit
) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    private val formatoPrecio = DecimalFormat("$#,##0.00")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val itemCarrito = itemsCarrito[position]
        holder.bind(itemCarrito)
    }

    override fun getItemCount(): Int = itemsCarrito.size

    fun actualizarItems(nuevosItems: List<ItemCarrito>) {
        itemsCarrito.clear()
        itemsCarrito.addAll(nuevosItems)
        notifyDataSetChanged()
    }

    inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivProducto: ImageView = itemView.findViewById(R.id.ivProductoCarrito)
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreProductoCarrito)
        private val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioProductoCarrito)
        private val tvCantidad: TextView = itemView.findViewById(R.id.tvCantidad)
        private val btnReducir: Button = itemView.findViewById(R.id.btnReducirCantidad)
        private val btnAumentar: Button = itemView.findViewById(R.id.btnAumentarCantidad)

        fun bind(itemCarrito: ItemCarrito) {
            val producto = itemCarrito.producto

            tvNombre.text = producto.nombre
            tvPrecio.text = formatoPrecio.format(itemCarrito.getPrecioTotal())
            tvCantidad.text = itemCarrito.cantidad.toString()
            ivProducto.setImageResource(producto.imagenResId)

            btnReducir.setOnClickListener {
                CarritoManager.reducirCantidad(producto.id)
                
                // Actualizar la lista completa desde el Manager para evitar desincronización
                actualizarItems(CarritoManager.obtenerItems())
                onCantidadChanged()
            }

            btnAumentar.setOnClickListener {
                CarritoManager.aumentarCantidad(producto.id)
                
                // Actualizar la lista completa desde el Manager para evitar desincronización
                actualizarItems(CarritoManager.obtenerItems())
                onCantidadChanged()
            }
        }
    }
}