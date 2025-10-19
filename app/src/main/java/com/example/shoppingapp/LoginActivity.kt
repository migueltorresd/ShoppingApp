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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
            // Validación simple para demo - en producción usar autenticación real
            if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {
                Toast.makeText(this, "¡Bienvenido $usuario!", Toast.LENGTH_SHORT).show()
                irAListadoProductos()
            }
        }
    }

    private fun validarCampos(usuario: String, contrasena: String): Boolean {
        if (usuario.isEmpty()) {
            etUsuario.error = "El usuario es requerido"
            return false
        }

        if (contrasena.isEmpty()) {
            etContrasena.error = "La contraseña es requerida"
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