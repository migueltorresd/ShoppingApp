package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListadoProductosActivity : AppCompatActivity() {

    private lateinit var rvProductos: RecyclerView
    private lateinit var btnVerCarrito: Button
    private lateinit var btnUbicacion: Button
    private lateinit var btnCerrarSesion: Button
    private lateinit var tvItemsCarrito: TextView
    private lateinit var productosAdapter: ProductosAdapter

    private val productos = listOf(
        Producto(1, "Smartphone Samsung Galaxy", 299.99, "Teléfono inteligente con pantalla de 6.4 pulgadas y 128GB de almacenamiento"),
        Producto(2, "Laptop HP Pavilion", 599.99, "Laptop con procesador Intel i5, 8GB RAM y 256GB SSD"),
        Producto(3, "Auriculares Sony WH-1000XM4", 199.99, "Auriculares inalámbricos con cancelación de ruido"),
        Producto(4, "Tablet iPad Air", 399.99, "Tablet de 10.9 pulgadas con chip M1 y 64GB"),
        Producto(5, "Smart TV LG 55\"", 499.99, "Smart TV 4K UHD con WebOS y HDR10"),
        Producto(6, "Cámara Canon EOS M50", 449.99, "Cámara mirrorless de 24.1MP con grabación 4K"),
        Producto(7, "Nintendo Switch", 279.99, "Consola de videojuegos híbrida portátil"),
        Producto(8, "Smartwatch Apple Watch SE", 249.99, "Reloj inteligente con GPS y monitoreo de salud"),
        Producto(9, "Altavoz Bluetooth JBL", 79.99, "Altavoz portátil resistente al agua"),
        Producto(10, "Teclado Mecánico Razer", 89.99, "Teclado gaming mecánico con retroiluminación RGB")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_productos)

        inicializarVistas()
        configurarRecyclerView()
        configurarEventos()
        actualizarContadorCarrito()
    }

    override fun onResume() {
        super.onResume()
        actualizarContadorCarrito()
    }

    private fun inicializarVistas() {
        rvProductos = findViewById(R.id.rvProductos)
        btnVerCarrito = findViewById(R.id.btnVerCarrito)
        btnUbicacion = findViewById(R.id.btnUbicacion)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)
        tvItemsCarrito = findViewById(R.id.tvItemsCarrito)
    }

    private fun configurarRecyclerView() {
        productosAdapter = ProductosAdapter(productos) { producto ->
            agregarAlCarrito(producto)
        }
        
        rvProductos.apply {
            adapter = productosAdapter
            layoutManager = LinearLayoutManager(this@ListadoProductosActivity)
        }
    }

    private fun configurarEventos() {
        btnVerCarrito.setOnClickListener {
            irAlCarrito()
        }
        
        btnUbicacion.setOnClickListener {
            irAUbicacion()
        }
        
        btnCerrarSesion.setOnClickListener {
            mostrarDialogoCerrarSesion()
        }
    }

    private fun agregarAlCarrito(producto: Producto) {
        CarritoManager.agregarProducto(producto)
        Toast.makeText(this, "${producto.nombre} agregado al carrito", Toast.LENGTH_SHORT).show()
        actualizarContadorCarrito()
    }

    private fun actualizarContadorCarrito() {
        val cantidadItems = CarritoManager.obtenerCantidadTotal()
        tvItemsCarrito.text = "Items en carrito: $cantidadItems"
    }

    private fun irAlCarrito() {
        val intent = Intent(this, CarritoActivity::class.java)
        startActivity(intent)
    }
    
    private fun irAUbicacion() {
        val intent = Intent(this, LocationActivity::class.java)
        startActivity(intent)
    }
    
    private fun mostrarDialogoCerrarSesion() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                cerrarSesion()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    private fun cerrarSesion() {
        // Limpiar el carrito al cerrar sesión
        CarritoManager.vaciarCarrito()
        
        // Mostrar mensaje de despedida
        Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
        
        // Regresar al login y limpiar el stack de actividades
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        // Opcional: mostrar diálogo de confirmación para salir
        finish()
        super.onBackPressed()
    }
}