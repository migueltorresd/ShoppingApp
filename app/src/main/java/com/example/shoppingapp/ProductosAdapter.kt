package com.example.shoppingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

class ProductosAdapter(
    private val productos: List<Producto>,
    private val onAgregarCarrito: (Producto) -> Unit
) : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    private val formatoPrecio = DecimalFormat("$#,##0.00")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.bind(producto)
    }

    override fun getItemCount(): Int = productos.size

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivProducto: ImageView = itemView.findViewById(R.id.ivProducto)
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreProducto)
        private val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecioProducto)
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcionProducto)
        private val btnAgregar: Button = itemView.findViewById(R.id.btnAgregarCarrito)

        fun bind(producto: Producto) {
            tvNombre.text = producto.nombre
            tvPrecio.text = formatoPrecio.format(producto.precio)
            tvDescripcion.text = producto.descripcion
            ivProducto.setImageResource(producto.imagenResId)

            btnAgregar.setOnClickListener {
                onAgregarCarrito(producto)
            }
        }
    }
}