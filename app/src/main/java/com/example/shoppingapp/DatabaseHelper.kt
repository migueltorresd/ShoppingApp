package com.example.shoppingapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "shopping_app.db"
        private const val DATABASE_VERSION = 2

        // Tabla de usuarios
        private const val TABLE_USERS = "usuarios"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_TELEFONO = "telefono"
        private const val COLUMN_DIRECCION = "direccion"
        private const val COLUMN_PASSWORD = "password"

        // Tabla de productos
        private const val TABLE_PRODUCTS = "productos"
        private const val COLUMN_PRODUCT_ID = "id"
        private const val COLUMN_PRODUCT_NOMBRE = "nombre"
        private const val COLUMN_PRODUCT_PRECIO = "precio"
        private const val COLUMN_PRODUCT_DESCRIPCION = "descripcion"
        private const val COLUMN_PRODUCT_IMAGEN_RES_ID = "imagen_res_id"
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

        val createProductsTable = """
            CREATE TABLE $TABLE_PRODUCTS (
                $COLUMN_PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_PRODUCT_NOMBRE TEXT NOT NULL,
                $COLUMN_PRODUCT_PRECIO REAL NOT NULL,
                $COLUMN_PRODUCT_DESCRIPCION TEXT,
                $COLUMN_PRODUCT_IMAGEN_RES_ID INTEGER
            )
        """.trimIndent()

        db.execSQL(createUsersTable)
        db.execSQL(createProductsTable)

        // Insertar datos de prueba
        insertarUsuarioPrueba(db)
        insertarProductosIniciales(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUCTS")
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

    private fun insertarProductosIniciales(db: SQLiteDatabase) {
        insertarProductoEnDb(
            db,
            nombre = "Smartphone Samsung Galaxy",
            precio = 299.99,
            descripcion = "Teléfono inteligente con pantalla de 6.4 pulgadas y 128GB de almacenamiento",
            imagenResId = android.R.drawable.ic_menu_gallery
        )

        insertarProductoEnDb(
            db,
            nombre = "Laptop HP Pavilion",
            precio = 599.99,
            descripcion = "Laptop con procesador Intel i5, 8GB RAM y 256GB SSD",
            imagenResId = android.R.drawable.ic_menu_gallery
        )

        insertarProductoEnDb(
            db,
            nombre = "Auriculares Sony WH-1000XM4",
            precio = 199.99,
            descripcion = "Auriculares inalámbricos con cancelación de ruido",
            imagenResId = android.R.drawable.ic_menu_gallery
        )

        insertarProductoEnDb(
            db,
            nombre = "Tablet iPad Air",
            precio = 399.99,
            descripcion = "Tablet de 10.9 pulgadas con chip M1 y 64GB",
            imagenResId = android.R.drawable.ic_menu_gallery
        )

        insertarProductoEnDb(
            db,
            nombre = "Smart TV LG 55\"",
            precio = 499.99,
            descripcion = "Smart TV 4K UHD con WebOS y HDR10",
            imagenResId = android.R.drawable.ic_menu_gallery
        )
    }

    private fun insertarProductoEnDb(
        db: SQLiteDatabase,
        nombre: String,
        precio: Double,
        descripcion: String,
        imagenResId: Int
    ) {
        val values = ContentValues().apply {
            put(COLUMN_PRODUCT_NOMBRE, nombre)
            put(COLUMN_PRODUCT_PRECIO, precio)
            put(COLUMN_PRODUCT_DESCRIPCION, descripcion)
            put(COLUMN_PRODUCT_IMAGEN_RES_ID, imagenResId)
        }
        db.insert(TABLE_PRODUCTS, null, values)
    }

    // -------------------- CRUD USUARIOS --------------------

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

    // -------------------- CRUD PRODUCTOS --------------------

    // Crear un nuevo producto
    fun crearProducto(
        nombre: String,
        precio: Double,
        descripcion: String,
        imagenResId: Int = android.R.drawable.ic_menu_gallery
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PRODUCT_NOMBRE, nombre)
            put(COLUMN_PRODUCT_PRECIO, precio)
            put(COLUMN_PRODUCT_DESCRIPCION, descripcion)
            put(COLUMN_PRODUCT_IMAGEN_RES_ID, imagenResId)
        }

        return db.insert(TABLE_PRODUCTS, null, values)
    }

    // Obtener todos los productos
    fun obtenerProductos(): List<Producto> {
        val productos = mutableListOf<Producto>()
        val db = this.readableDatabase

        val cursor = db.query(
            TABLE_PRODUCTS,
            arrayOf(
                COLUMN_PRODUCT_ID,
                COLUMN_PRODUCT_NOMBRE,
                COLUMN_PRODUCT_PRECIO,
                COLUMN_PRODUCT_DESCRIPCION,
                COLUMN_PRODUCT_IMAGEN_RES_ID
            ),
            null,
            null,
            null,
            null,
            "$COLUMN_PRODUCT_NOMBRE ASC"
        )

        cursor.use { c ->
            if (c.moveToFirst()) {
                do {
                    val id = c.getInt(c.getColumnIndexOrThrow(COLUMN_PRODUCT_ID))
                    val nombre = c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_NOMBRE))
                    val precio = c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_PRECIO))
                    val descripcion = c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPCION))
                    val imagenResId = c.getInt(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGEN_RES_ID))

                    productos.add(
                        Producto(
                            id = id,
                            nombre = nombre,
                            precio = precio,
                            descripcion = descripcion,
                            imagenResId = imagenResId
                        )
                    )
                } while (c.moveToNext())
            }
        }

        return productos
    }

    // Obtener un producto por ID
    fun obtenerProductoPorId(id: Int): Producto? {
        val db = this.readableDatabase

        val cursor = db.query(
            TABLE_PRODUCTS,
            arrayOf(
                COLUMN_PRODUCT_ID,
                COLUMN_PRODUCT_NOMBRE,
                COLUMN_PRODUCT_PRECIO,
                COLUMN_PRODUCT_DESCRIPCION,
                COLUMN_PRODUCT_IMAGEN_RES_ID
            ),
            "$COLUMN_PRODUCT_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        cursor.use { c ->
            if (c.moveToFirst()) {
                val nombre = c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_NOMBRE))
                val precio = c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_PRECIO))
                val descripcion = c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPCION))
                val imagenResId = c.getInt(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGEN_RES_ID))

                return Producto(
                    id = id,
                    nombre = nombre,
                    precio = precio,
                    descripcion = descripcion,
                    imagenResId = imagenResId
                )
            }
        }

        return null
    }

    // Actualizar un producto existente
    fun actualizarProducto(producto: Producto): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PRODUCT_NOMBRE, producto.nombre)
            put(COLUMN_PRODUCT_PRECIO, producto.precio)
            put(COLUMN_PRODUCT_DESCRIPCION, producto.descripcion)
            put(COLUMN_PRODUCT_IMAGEN_RES_ID, producto.imagenResId)
        }

        return db.update(
            TABLE_PRODUCTS,
            values,
            "$COLUMN_PRODUCT_ID = ?",
            arrayOf(producto.id.toString())
        )
    }

    // Eliminar un producto por ID
    fun eliminarProducto(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(
            TABLE_PRODUCTS,
            "$COLUMN_PRODUCT_ID = ?",
            arrayOf(id.toString())
        )
    }
}
