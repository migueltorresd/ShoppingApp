package com.example.shoppingapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.TextView
import android.widget.EditText

class PerfilActivity : AppCompatActivity() {

    private lateinit var tvPerfilEmail: TextView
    private lateinit var etNombre: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etDireccion: TextInputEditText
    private lateinit var btnGuardarCambios: MaterialButton
    private lateinit var btnCambiarPassword: MaterialButton
    private lateinit var btnVolver: MaterialButton
    private lateinit var dbHelper: DatabaseHelper
    
    private var emailUsuario: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        dbHelper = DatabaseHelper(this)
        
        // Obtener el email del usuario desde SharedPreferences o Intent
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        emailUsuario = sharedPref.getString("user_email", "") ?: ""

        if (emailUsuario.isEmpty()) {
            Toast.makeText(this, "Error: No se encontró la sesión del usuario", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        inicializarVistas()
        cargarDatosUsuario()
        configurarEventos()
    }

    private fun inicializarVistas() {
        tvPerfilEmail = findViewById(R.id.tvPerfilEmail)
        etNombre = findViewById(R.id.etNombre)
        etTelefono = findViewById(R.id.etTelefono)
        etDireccion = findViewById(R.id.etDireccion)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)
        btnCambiarPassword = findViewById(R.id.btnCambiarPassword)
        btnVolver = findViewById(R.id.btnVolver)
    }

    private fun cargarDatosUsuario() {
        val usuario = dbHelper.obtenerUsuario(emailUsuario)
        
        if (usuario != null) {
            tvPerfilEmail.text = usuario.email
            etNombre.setText(usuario.nombre)
            etTelefono.setText(usuario.telefono)
            etDireccion.setText(usuario.direccion)
        } else {
            Toast.makeText(this, "Error al cargar datos del usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configurarEventos() {
        btnGuardarCambios.setOnClickListener {
            guardarCambios()
        }

        btnCambiarPassword.setOnClickListener {
            mostrarDialogoCambiarPassword()
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun guardarCambios() {
        val nombre = etNombre.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()
        val direccion = etDireccion.text.toString().trim()

        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
            return
        }

        val actualizado = dbHelper.actualizarUsuario(emailUsuario, nombre, telefono, direccion)

        if (actualizado) {
            Toast.makeText(this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al actualizar el perfil", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDialogoCambiarPassword() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_cambiar_password, null)
        val etPasswordActual = dialogView.findViewById<EditText>(R.id.etPasswordActual)
        val etPasswordNuevo = dialogView.findViewById<EditText>(R.id.etPasswordNuevo)
        val etPasswordConfirmar = dialogView.findViewById<EditText>(R.id.etPasswordConfirmar)

        AlertDialog.Builder(this)
            .setTitle("Cambiar Contraseña")
            .setView(dialogView)
            .setPositiveButton("Cambiar") { _, _ ->
                val passwordActual = etPasswordActual.text.toString()
                val passwordNuevo = etPasswordNuevo.text.toString()
                val passwordConfirmar = etPasswordConfirmar.text.toString()

                if (passwordActual.isEmpty() || passwordNuevo.isEmpty() || passwordConfirmar.isEmpty()) {
                    Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (passwordNuevo != passwordConfirmar) {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (passwordNuevo.length < 6) {
                    Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val cambiado = dbHelper.cambiarPassword(emailUsuario, passwordActual, passwordNuevo)

                if (cambiado) {
                    Toast.makeText(this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error: Contraseña actual incorrecta", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
