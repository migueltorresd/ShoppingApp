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
    private lateinit var etContrasena: TextInputEditText
    private lateinit var etConfirmarContrasena: TextInputEditText
    private lateinit var btnRegistrarse: Button
    private lateinit var btnVolverLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        inicializarVistas()
        configurarEventos()
    }

    private fun inicializarVistas() {
        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
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
        val contrasena = etContrasena.text.toString().trim()
        val confirmarContrasena = etConfirmarContrasena.text.toString().trim()

        if (validarCampos(nombre, correo, contrasena, confirmarContrasena)) {
            // Simulación de registro exitoso
            Toast.makeText(this, "¡Registro exitoso! Ahora puedes iniciar sesión", Toast.LENGTH_LONG).show()
            
            // Pasar datos del usuario de vuelta al login (opcional)
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("usuario_registrado", correo)
            startActivity(intent)
            finish()
        }
    }

    private fun validarCampos(nombre: String, correo: String, contrasena: String, confirmarContrasena: String): Boolean {
        // Limpiar errores previos
        limpiarErrores()

        var isValid = true

        if (nombre.isEmpty()) {
            etNombre.error = "El nombre es requerido"
            isValid = false
        }

        if (correo.isEmpty()) {
            etCorreo.error = "El correo es requerido"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            etCorreo.error = "El formato del correo no es válido"
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