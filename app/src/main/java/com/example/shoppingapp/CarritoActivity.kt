package com.example.shoppingapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

class CarritoActivity : AppCompatActivity() {

    private lateinit var rvCarrito: RecyclerView
    private lateinit var layoutCarritoVacio: LinearLayout
    private lateinit var tvTotal: TextView
    private lateinit var btnVolver: Button
    private lateinit var btnVaciarCarrito: Button
    private lateinit var btnFinalizarCompra: Button
    private lateinit var carritoAdapter: CarritoAdapter

    private val formatoPrecio = DecimalFormat("$#,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        inicializarVistas()
        configurarRecyclerView()
        configurarEventos()
        actualizarVistaCarrito()
    }

    private fun inicializarVistas() {
        rvCarrito = findViewById(R.id.rvCarrito)
        layoutCarritoVacio = findViewById(R.id.layoutCarritoVacio)
        tvTotal = findViewById(R.id.tvTotal)
        btnVolver = findViewById(R.id.btnVolver)
        btnVaciarCarrito = findViewById(R.id.btnVaciarCarrito)
        btnFinalizarCompra = findViewById(R.id.btnFinalizarCompra)
    }

    private fun configurarRecyclerView() {
        carritoAdapter = CarritoAdapter(
            CarritoManager.obtenerItems().toMutableList()
        ) {
            actualizarVistaCarrito()
        }

        rvCarrito.apply {
            adapter = carritoAdapter
            layoutManager = LinearLayoutManager(this@CarritoActivity)
        }
    }

    private fun configurarEventos() {
        btnVolver.setOnClickListener {
            finish()
        }

        btnVaciarCarrito.setOnClickListener {
            mostrarDialogoVaciarCarrito()
        }

        btnFinalizarCompra.setOnClickListener {
            finalizarCompra()
        }
    }

    private fun actualizarVistaCarrito() {
        val itemsCarrito = CarritoManager.obtenerItems()
        
        if (itemsCarrito.isEmpty()) {
            // Mostrar mensaje de carrito vacío
            rvCarrito.visibility = View.GONE
            layoutCarritoVacio.visibility = View.VISIBLE
            btnFinalizarCompra.isEnabled = false
            btnVaciarCarrito.isEnabled = false
        } else {
            // Mostrar lista de productos
            rvCarrito.visibility = View.VISIBLE
            layoutCarritoVacio.visibility = View.GONE
            btnFinalizarCompra.isEnabled = true
            btnVaciarCarrito.isEnabled = true
            
            carritoAdapter.actualizarItems(itemsCarrito)
        }

        // Actualizar el total
        val total = CarritoManager.obtenerTotal()
        tvTotal.text = formatoPrecio.format(total)
    }

    private fun mostrarDialogoVaciarCarrito() {
        AlertDialog.Builder(this)
            .setTitle("Vaciar Carrito")
            .setMessage("¿Estás seguro de que quieres vaciar todo el carrito?")
            .setPositiveButton("Sí") { _, _ ->
                vaciarCarrito()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun vaciarCarrito() {
        CarritoManager.vaciarCarrito()
        actualizarVistaCarrito()
        Toast.makeText(this, "Carrito vaciado", Toast.LENGTH_SHORT).show()
    }

    private fun finalizarCompra() {
        val total = CarritoManager.obtenerTotal()
        val cantidadItems = CarritoManager.obtenerCantidadTotal()

        AlertDialog.Builder(this)
            .setTitle("Finalizar Compra")
            .setMessage("¿Confirmas la compra de $cantidadItems productos por ${formatoPrecio.format(total)}?")
            .setPositiveButton("Confirmar") { _, _ ->
                procesarCompra(total, cantidadItems)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun procesarCompra(total: Double, cantidadItems: Int) {
        // Simulación del proceso de compra
        CarritoManager.vaciarCarrito()
        actualizarVistaCarrito()

        AlertDialog.Builder(this)
            .setTitle("¡Compra Exitosa!")
            .setMessage("Tu pedido de $cantidadItems productos por ${formatoPrecio.format(total)} ha sido procesado exitosamente.\n\nGracias por tu compra.")
            .setPositiveButton("Continuar Comprando") { _, _ ->
                finish() // Volver a la lista de productos
            }
            .setCancelable(false)
            .show()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}