package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
    private lateinit var btnAgregarProducto: Button
    private lateinit var tvItemsCarrito: TextView
    private lateinit var productosAdapter: ProductosAdapter
    private lateinit var dbHelper: DatabaseHelper

    private var productos: List<Producto> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_productos)

        dbHelper = DatabaseHelper(this)

        inicializarVistas()
        cargarProductosDesdeDb()
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
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto)
        tvItemsCarrito = findViewById(R.id.tvItemsCarrito)
    }

    private fun configurarRecyclerView() {
        productosAdapter = ProductosAdapter(productos,
            onAgregarCarrito = { producto ->
                agregarAlCarrito(producto)
            },
            onProductoLongClick = { producto ->
                mostrarOpcionesProducto(producto)
            }
        )

        rvProductos.apply {
            adapter = productosAdapter
            layoutManager = LinearLayoutManager(this@ListadoProductosActivity)
        }
    }

    private fun cargarProductosDesdeDb() {
        productos = dbHelper.obtenerProductos()
        if (productos.isEmpty()) {
            Toast.makeText(this, "No hay productos registrados", Toast.LENGTH_SHORT).show()
        }

        if (this::productosAdapter.isInitialized) {
            productosAdapter.actualizarProductos(productos)
        }
    }

    private fun configurarEventos() {
        btnVerCarrito.setOnClickListener {
            irAlCarrito()
        }
        
        btnUbicacion.setOnClickListener {
            irAUbicacion()
        }

        btnAgregarProducto.setOnClickListener {
            mostrarDialogoProducto(null)
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
        tvItemsCarrito.text = "$cantidadItems items"
    }

    private fun mostrarOpcionesProducto(producto: Producto) {
        val opciones = arrayOf("Editar", "Eliminar")

        AlertDialog.Builder(this)
            .setTitle(producto.nombre)
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> mostrarDialogoProducto(producto)
                    1 -> confirmarEliminarProducto(producto)
                }
            }
            .show()
    }

    private fun mostrarDialogoProducto(producto: Producto?) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_producto, null)
        val etNombre = dialogView.findViewById<EditText>(R.id.etNombreProducto)
        val etPrecio = dialogView.findViewById<EditText>(R.id.etPrecioProducto)
        val etDescripcion = dialogView.findViewById<EditText>(R.id.etDescripcionProducto)

        if (producto != null) {
            etNombre.setText(producto.nombre)
            etPrecio.setText(producto.precio.toString())
            etDescripcion.setText(producto.descripcion)
        }

        AlertDialog.Builder(this)
            .setTitle(if (producto == null) "Agregar producto" else "Editar producto")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre = etNombre.text.toString().trim()
                val precioTexto = etPrecio.text.toString().trim()
                val descripcion = etDescripcion.text.toString().trim()

                if (nombre.isEmpty() || precioTexto.isEmpty()) {
                    Toast.makeText(this, "Nombre y precio son obligatorios", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val precio = precioTexto.toDoubleOrNull()
                if (precio == null) {
                    Toast.makeText(this, "El precio debe ser numérico", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (producto == null) {
                    dbHelper.crearProducto(nombre, precio, descripcion)
                    Toast.makeText(this, "Producto creado", Toast.LENGTH_SHORT).show()
                } else {
                    val actualizado = Producto(
                        id = producto.id,
                        nombre = nombre,
                        precio = precio,
                        descripcion = descripcion,
                        imagenResId = producto.imagenResId
                    )
                    dbHelper.actualizarProducto(actualizado)
                    Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show()
                }

                cargarProductosDesdeDb()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun confirmarEliminarProducto(producto: Producto) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar producto")
            .setMessage("¿Deseas eliminar '${producto.nombre}'?")
            .setPositiveButton("Eliminar") { _, _ ->
                dbHelper.eliminarProducto(producto.id)
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
                cargarProductosDesdeDb()
            }
            .setNegativeButton("Cancelar", null)
            .show()
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