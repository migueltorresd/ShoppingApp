package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class RegistroActivity : AppCompatActivity() {

    private lateinit var etNombre: TextInputEditText
    private lateinit var etCorreo: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etDireccion: TextInputEditText
    private lateinit var etContrasena: TextInputEditText
    private lateinit var etConfirmarContrasena: TextInputEditText
    private lateinit var btnRegistrarse: Button
    private lateinit var btnVolverLogin: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        dbHelper = DatabaseHelper(this)
        inicializarVistas()
        configurarEventos()
    }

    private fun inicializarVistas() {
        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etTelefono = findViewById(R.id.etTelefono)
        etDireccion = findViewById(R.id.etDireccion)
        etContrasena = findViewById(R.id.etContrasena)
        etConfirmarContrasena = findViewById(R.id.etConfirmarContrasena)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        btnVolverLogin = findViewById(R.id.btnVolverLogin)
    }

    private fun configurarEventos() {
        btnRegistrarse.setOnClickListener {
            registrarUsuario()
        }

        btnVolverLogin.setOnClickListener {
            volverAlLogin()
        }
    }

    private fun registrarUsuario() {
        val nombre = etNombre.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()
        val direccion = etDireccion.text.toString().trim()
        val contrasena = etContrasena.text.toString().trim()
        val confirmarContrasena = etConfirmarContrasena.text.toString().trim()

        if (validarCampos(nombre, correo, telefono, direccion, contrasena, confirmarContrasena)) {
            // Verificar si el email ya existe
            if (dbHelper.emailExiste(correo)) {
                Toast.makeText(this, "Este correo ya está registrado", Toast.LENGTH_LONG).show()
                etCorreo.error = "Email ya registrado"
                return
            }

            // Registrar en la base de datos
            val registrado = dbHelper.registrarUsuario(nombre, correo, telefono, direccion, contrasena)
            
            if (registrado) {
                Toast.makeText(this, "¡Registro exitoso! Ahora puedes iniciar sesión", Toast.LENGTH_LONG).show()
                
                // Volver al login
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("usuario_registrado", correo)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al registrar. Intenta nuevamente", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validarCampos(nombre: String, correo: String, telefono: String, direccion: String, contrasena: String, confirmarContrasena: String): Boolean {
        // Limpiar errores previos
        limpiarErrores()

        var isValid = true

        if (nombre.isEmpty()) {
            etNombre.error = "El nombre es requerido"
            isValid = false
        } else if (nombre.length < 3) {
            etNombre.error = "El nombre debe tener al menos 3 caracteres"
            isValid = false
        }

        if (correo.isEmpty()) {
            etCorreo.error = "El correo es requerido"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.error = "El formato del correo no es válido"
            isValid = false
        }

        if (telefono.isEmpty()) {
            etTelefono.error = "El teléfono es requerido"
            isValid = false
        } else if (telefono.length < 10) {
            etTelefono.error = "Ingresa un teléfono válido (mínimo 10 dígitos)"
            isValid = false
        }

        if (direccion.isEmpty()) {
            etDireccion.error = "La dirección es requerida"
            isValid = false
        }

        if (contrasena.isEmpty()) {
            etContrasena.error = "La contraseña es requerida"
            isValid = false
        } else if (contrasena.length < 6) {
            etContrasena.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        }

        if (confirmarContrasena.isEmpty()) {
            etConfirmarContrasena.error = "Debes confirmar la contraseña"
            isValid = false
        } else if (contrasena != confirmarContrasena) {
            etConfirmarContrasena.error = "Las contraseñas no coinciden"
            isValid = false
        }

        return isValid
    }

    private fun limpiarErrores() {
        etNombre.error = null
        etCorreo.error = null
        etTelefono.error = null
        etDireccion.error = null
        etContrasena.error = null
        etConfirmarContrasena.error = null
    }

    private fun volverAlLogin() {
        finish() // Cierra esta actividad y vuelve a la anterior
    }

    override fun onBackPressed() {
        volverAlLogin()
        super.onBackPressed()
    }
}