package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.app.Activity
import android.widget.EditText

class LoginActivity : Activity() {

    private lateinit var etUsuario: EditText
    private lateinit var etContrasena: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnIrRegistro: Button
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHelper(this)
        inicializarVistas()
        configurarEventos()
    }

    private fun inicializarVistas() {
        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnIrRegistro = findViewById(R.id.btnIrRegistro)
    }

    private fun configurarEventos() {
        btnIniciarSesion.setOnClickListener {
            iniciarSesion()
        }

        btnIrRegistro.setOnClickListener {
            irARegistro()
        }
    }

    private fun iniciarSesion() {
        val usuario = etUsuario.text.toString().trim()
        val contrasena = etContrasena.text.toString().trim()

        if (validarCampos(usuario, contrasena)) {
            // Validar con base de datos
            if (dbHelper.validarLogin(usuario, contrasena)) {
                val nombre = dbHelper.obtenerNombreUsuario(usuario)
                
                // Guardar el email en SharedPreferences
                val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
                sharedPref.edit().apply {
                    putString("user_email", usuario)
                    apply()
                }
                
                Toast.makeText(this, "¡Bienvenido $nombre!", Toast.LENGTH_SHORT).show()
                irAListadoProductos()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validarCampos(usuario: String, contrasena: String): Boolean {
        if (usuario.isEmpty()) {
            etUsuario.error = "El email es requerido"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(usuario).matches()) {
            etUsuario.error = "Ingresa un email válido"
            return false
        }

        if (contrasena.isEmpty()) {
            etContrasena.error = "La contraseña es requerida"
            return false
        }

        if (contrasena.length < 6) {
            etContrasena.error = "La contraseña debe tener al menos 6 caracteres"
            return false
        }

        return true
    }

    private fun irAListadoProductos() {
        val intent = Intent(this, ListadoProductosActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irARegistro() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}