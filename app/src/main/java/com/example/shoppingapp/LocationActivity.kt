package com.example.shoppingapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class LocationActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var tvLatitud: TextView
    private lateinit var tvLongitud: TextView
    private lateinit var tvDireccion: TextView
    private lateinit var tvEstado: TextView
    private lateinit var btnObtenerUbicacion: Button
    private lateinit var btnVolver: Button

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        
        inicializarVistas()
        configurarEventos()
        verificarPermisos()
    }

    private fun inicializarVistas() {
        tvLatitud = findViewById(R.id.tvLatitud)
        tvLongitud = findViewById(R.id.tvLongitud)
        tvDireccion = findViewById(R.id.tvDireccion)
        tvEstado = findViewById(R.id.tvEstado)
        btnObtenerUbicacion = findViewById(R.id.btnObtenerUbicacion)
        btnVolver = findViewById(R.id.btnVolver)
    }

    private fun configurarEventos() {
        btnObtenerUbicacion.setOnClickListener {
            obtenerUbicacion()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun verificarPermisos() {
        when {
            tienePermisosDeUbicacion() -> {
                tvEstado.text = "Permisos de ubicación concedidos"
                tvEstado.setTextColor(getColor(R.color.success_green))
            }
            else -> {
                solicitarPermisosDeUbicacion()
            }
        }
    }

    private fun tienePermisosDeUbicacion(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun solicitarPermisosDeUbicacion() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    tvEstado.text = "Permisos de ubicación concedidos"
                    tvEstado.setTextColor(getColor(R.color.success_green))
                    Toast.makeText(this, "Permisos concedidos. Ahora puedes obtener tu ubicación", Toast.LENGTH_SHORT).show()
                } else {
                    tvEstado.text = "Permisos de ubicación denegados"
                    tvEstado.setTextColor(getColor(R.color.error_red))
                    Toast.makeText(this, "Se necesitan permisos de ubicación para usar esta función", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun obtenerUbicacion() {
        if (!tienePermisosDeUbicacion()) {
            Toast.makeText(this, "Por favor, concede permisos de ubicación primero", Toast.LENGTH_SHORT).show()
            solicitarPermisosDeUbicacion()
            return
        }

        tvEstado.text = "Obteniendo ubicación..."
        tvEstado.setTextColor(getColor(R.color.text_secondary))
        btnObtenerUbicacion.isEnabled = false

        try {
            val cancellationTokenSource = CancellationTokenSource()
            
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).addOnSuccessListener { location: Location? ->
                btnObtenerUbicacion.isEnabled = true
                
                if (location != null) {
                    mostrarUbicacion(location)
                } else {
                    tvEstado.text = "No se pudo obtener la ubicación. Intenta de nuevo"
                    tvEstado.setTextColor(getColor(R.color.error_red))
                    Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                btnObtenerUbicacion.isEnabled = true
                tvEstado.text = "Error al obtener ubicación"
                tvEstado.setTextColor(getColor(R.color.error_red))
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } catch (e: SecurityException) {
            btnObtenerUbicacion.isEnabled = true
            tvEstado.text = "Error de permisos"
            tvEstado.setTextColor(getColor(R.color.error_red))
            Toast.makeText(this, "Error de permisos de ubicación", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarUbicacion(location: Location) {
        val latitud = location.latitude
        val longitud = location.longitude
        
        tvLatitud.text = "Latitud: %.6f".format(latitud)
        tvLongitud.text = "Longitud: %.6f".format(longitud)
        
        // Aproximar dirección basada en coordenadas (simplificado)
        tvDireccion.text = "Coordenadas obtenidas exitosamente"
        
        tvEstado.text = "Ubicación obtenida correctamente"
        tvEstado.setTextColor(getColor(R.color.success_green))
        
        Toast.makeText(this, "Ubicación actualizada", Toast.LENGTH_SHORT).show()
    }
}
