package com.example.shoppingapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "shopping_app.db"
        private const val DATABASE_VERSION = 3

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
        
        // Tabla de carrito
        private const val TABLE_CART = "carrito"
        private const val COLUMN_CART_ID = "id"
        private const val COLUMN_CART_PRODUCT_ID = "producto_id"
        private const val COLUMN_CART_CANTIDAD = "cantidad"
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
        
        val createCartTable = """
            CREATE TABLE $TABLE_CART (
                $COLUMN_CART_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_CART_PRODUCT_ID INTEGER NOT NULL,
                $COLUMN_CART_CANTIDAD INTEGER NOT NULL,
                FOREIGN KEY($COLUMN_CART_PRODUCT_ID) REFERENCES $TABLE_PRODUCTS($COLUMN_PRODUCT_ID)
            )
        """.trimIndent()

        db.execSQL(createUsersTable)
        db.execSQL(createProductsTable)
        db.execSQL(createCartTable)

        // Insertar datos de prueba
        insertarUsuarioPrueba(db)
        insertarProductosIniciales(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        when {
            oldVersion < 3 -> {
                // Agregar tabla de carrito si actualizamos de versión 2 a 3
                try {
                    val createCartTable = """
                        CREATE TABLE IF NOT EXISTS $TABLE_CART (
                            $COLUMN_CART_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                            $COLUMN_CART_PRODUCT_ID INTEGER NOT NULL,
                            $COLUMN_CART_CANTIDAD INTEGER NOT NULL,
                            FOREIGN KEY($COLUMN_CART_PRODUCT_ID) REFERENCES $TABLE_PRODUCTS($COLUMN_PRODUCT_ID)
                        )
                    """.trimIndent()
                    db.execSQL(createCartTable)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
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
        // Nota: Las imágenes deben estar en app/src/main/res/drawable/
        // Si no existen, se usará el icono por defecto
        
        insertarProductoEnDb(
            db,
            nombre = "Smartphone Samsung Galaxy",
            precio = 299.99,
            descripcion = "Teléfono inteligente con pantalla de 6.4 pulgadas y 128GB de almacenamiento",
            imagenResId = R.drawable.img_smartphone
        )

        insertarProductoEnDb(
            db,
            nombre = "Laptop HP Pavilion",
            precio = 599.99,
            descripcion = "Laptop con procesador Intel i5, 8GB RAM y 256GB SSD",
            imagenResId = R.drawable.img_laptop
        )

        insertarProductoEnDb(
            db,
            nombre = "Auriculares Sony WH-1000XM4",
            precio = 199.99,
            descripcion = "Auriculares inalámbricos con cancelación de ruido",
            imagenResId = R.drawable.img_auriculares
        )

        insertarProductoEnDb(
            db,
            nombre = "Tablet iPad Air",
            precio = 399.99,
            descripcion = "Tablet de 10.9 pulgadas con chip M1 y 64GB",
            imagenResId = R.drawable.img_tablet
        )

        insertarProductoEnDb(
            db,
            nombre = "Smart TV LG 55\"",
            precio = 499.99,
            descripcion = "Smart TV 4K UHD con WebOS y HDR10",
            imagenResId = R.drawable.img_tv
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

    // Obtener datos completos del usuario por email
    fun obtenerUsuario(email: String): Usuario? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID, COLUMN_NOMBRE, COLUMN_EMAIL, COLUMN_TELEFONO, COLUMN_DIRECCION),
            "$COLUMN_EMAIL = ?",
            arrayOf(email),
            null, null, null
        )

        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            usuario = Usuario(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                telefono = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO)) ?: "",
                direccion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIRECCION)) ?: ""
            )
        }
        cursor.close()
        return usuario
    }

    // Actualizar datos del usuario
    fun actualizarUsuario(email: String, nombre: String, telefono: String, direccion: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_TELEFONO, telefono)
            put(COLUMN_DIRECCION, direccion)
        }

        val filasActualizadas = db.update(
            TABLE_USERS,
            values,
            "$COLUMN_EMAIL = ?",
            arrayOf(email)
        )

        return filasActualizadas > 0
    }

    // Cambiar contraseña del usuario
    fun cambiarPassword(email: String, passwordActual: String, passwordNuevo: String): Boolean {
        // Primero validar que la contraseña actual es correcta
        if (!validarLogin(email, passwordActual)) {
            return false
        }

        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PASSWORD, passwordNuevo)
        }

        val filasActualizadas = db.update(
            TABLE_USERS,
            values,
            "$COLUMN_EMAIL = ?",
            arrayOf(email)
        )

        return filasActualizadas > 0
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
    
    // -------------------- CRUD CARRITO --------------------
    
    // Agregar producto al carrito o incrementar cantidad
    fun agregarAlCarrito(productoId: Int, cantidad: Int = 1): Long {
        val db = this.writableDatabase
        
        // Verificar si el producto ya está en el carrito
        val cursor = db.query(
            TABLE_CART,
            arrayOf(COLUMN_CART_ID, COLUMN_CART_CANTIDAD),
            "$COLUMN_CART_PRODUCT_ID = ?",
            arrayOf(productoId.toString()),
            null, null, null
        )
        
        if (cursor.moveToFirst()) {
            // Ya existe, incrementar cantidad
            val cartId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_ID))
            val cantidadActual = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CART_CANTIDAD))
            cursor.close()
            
            val values = ContentValues().apply {
                put(COLUMN_CART_CANTIDAD, cantidadActual + cantidad)
            }
            
            return db.update(
                TABLE_CART,
                values,
                "$COLUMN_CART_ID = ?",
                arrayOf(cartId.toString())
            ).toLong()
        } else {
            cursor.close()
            // No existe, insertar nuevo
            val values = ContentValues().apply {
                put(COLUMN_CART_PRODUCT_ID, productoId)
                put(COLUMN_CART_CANTIDAD, cantidad)
            }
            return db.insert(TABLE_CART, null, values)
        }
    }
    
    // Obtener items del carrito
    fun obtenerItemsCarrito(): List<ItemCarrito> {
        val items = mutableListOf<ItemCarrito>()
        val db = this.readableDatabase
        
        val query = """
            SELECT c.$COLUMN_CART_ID, c.$COLUMN_CART_CANTIDAD, 
                   p.$COLUMN_PRODUCT_ID, p.$COLUMN_PRODUCT_NOMBRE, 
                   p.$COLUMN_PRODUCT_PRECIO, p.$COLUMN_PRODUCT_DESCRIPCION, 
                   p.$COLUMN_PRODUCT_IMAGEN_RES_ID
            FROM $TABLE_CART c
            INNER JOIN $TABLE_PRODUCTS p ON c.$COLUMN_CART_PRODUCT_ID = p.$COLUMN_PRODUCT_ID
        """.trimIndent()
        
        val cursor = db.rawQuery(query, null)
        
        cursor.use { c ->
            if (c.moveToFirst()) {
                do {
                    val productoId = c.getInt(c.getColumnIndexOrThrow(COLUMN_PRODUCT_ID))
                    val nombre = c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_NOMBRE))
                    val precio = c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_PRECIO))
                    val descripcion = c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPCION))
                    val imagenResId = c.getInt(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGEN_RES_ID))
                    val cantidad = c.getInt(c.getColumnIndexOrThrow(COLUMN_CART_CANTIDAD))
                    
                    val producto = Producto(
                        id = productoId,
                        nombre = nombre,
                        precio = precio,
                        descripcion = descripcion,
                        imagenResId = imagenResId
                    )
                    
                    items.add(ItemCarrito(producto, cantidad))
                } while (c.moveToNext())
            }
        }
        
        return items
    }
    
    // Actualizar cantidad de un producto en el carrito
    fun actualizarCantidadCarrito(productoId: Int, cantidad: Int): Int {
        val db = this.writableDatabase
        
        if (cantidad <= 0) {
            // Si la cantidad es 0 o negativa, eliminar del carrito
            return db.delete(
                TABLE_CART,
                "$COLUMN_CART_PRODUCT_ID = ?",
                arrayOf(productoId.toString())
            )
        }
        
        val values = ContentValues().apply {
            put(COLUMN_CART_CANTIDAD, cantidad)
        }
        
        return db.update(
            TABLE_CART,
            values,
            "$COLUMN_CART_PRODUCT_ID = ?",
            arrayOf(productoId.toString())
        )
    }
    
    // Eliminar un producto del carrito
    fun eliminarDelCarrito(productoId: Int): Int {
        val db = this.writableDatabase
        return db.delete(
            TABLE_CART,
            "$COLUMN_CART_PRODUCT_ID = ?",
            arrayOf(productoId.toString())
        )
    }
    
    // Vaciar carrito completo
    fun vaciarCarrito(): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_CART, null, null)
    }
}
