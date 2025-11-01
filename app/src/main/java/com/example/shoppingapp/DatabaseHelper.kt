package com.example.shoppingapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "shopping_app.db"
        private const val DATABASE_VERSION = 1

        // Tabla de usuarios
        private const val TABLE_USERS = "usuarios"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_TELEFONO = "telefono"
        private const val COLUMN_DIRECCION = "direccion"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_TELEFONO TEXT,
                $COLUMN_DIRECCION TEXT,
                $COLUMN_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createUsersTable)

        // Insertar usuario de prueba
        insertarUsuarioPrueba(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    private fun insertarUsuarioPrueba(db: SQLiteDatabase) {
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, "Usuario Demo")
            put(COLUMN_EMAIL, "demo@shopping.com")
            put(COLUMN_TELEFONO, "1234567890")
            put(COLUMN_DIRECCION, "Calle Principal 123")
            put(COLUMN_PASSWORD, "demo123")
        }
        db.insert(TABLE_USERS, null, values)
    }

    // Registrar un nuevo usuario
    fun registrarUsuario(nombre: String, email: String, telefono: String, direccion: String, password: String): Boolean {
        val db = this.writableDatabase
        
        // Verificar si el email ya existe
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_EMAIL),
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null, null, null
        )

        if (cursor.count > 0) {
            cursor.close()
            return false // Usuario ya existe
        }
        cursor.close()

        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_EMAIL, email)
            put(COLUMN_TELEFONO, telefono)
            put(COLUMN_DIRECCION, direccion)
            put(COLUMN_PASSWORD, password)
        }

        val resultado = db.insert(TABLE_USERS, null, values)
        return resultado != -1L
    }

    // Validar login de usuario
    fun validarLogin(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_EMAIL),
            "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(email, password),
            null, null, null
        )

        val resultado = cursor.count > 0
        cursor.close()
        return resultado
    }

    // Obtener nombre del usuario por email
    fun obtenerNombreUsuario(email: String): String? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_NOMBRE),
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null, null, null
        )

        var nombre: String? = null
        if (cursor.moveToFirst()) {
            nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
        }
        cursor.close()
        return nombre
    }

    // Verificar si un email ya está registrado
    fun emailExiste(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_EMAIL),
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null, null, null
        )

        val existe = cursor.count > 0
        cursor.close()
        return existe
    }
}
