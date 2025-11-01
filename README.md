# ShoppingApp 🛒

Aplicación móvil de compras desarrollada en Kotlin para Android con funcionalidades completas de e-commerce.

## 🎯 Características Implementadas

### ✅ Funcionalidades Principales
- **Login de Usuario**: Sistema de autenticación con base de datos SQLite
- **Registro de Clientes**: Formulario completo de registro con validaciones
- **Listado de Productos**: Catálogo de 10 productos con RecyclerView
- **Carrito de Compras**: Gestión completa (agregar/eliminar/modificar cantidades)
- **Geolocalización**: Obtención de ubicación GPS del usuario
- **Base de Datos SQLite**: Almacenamiento persistente de usuarios

### ✨ Características Técnicas
- 🔒 **Validación de Datos**: Email, teléfono, contraseñas seguras
- 📍 **Permisos en Tiempo Real**: Solicitud dinámica de permisos de ubicación
- 🎨 **Material Design**: Interfaz moderna y consistente
- 💾 **Persistencia**: Base de datos SQLite para usuarios
- 🔄 **Navegación Fluida**: Transiciones suaves entre pantallas

## 🛠️ Tecnologías

- **Lenguaje**: Kotlin
- **UI**: Android XML Layouts + Material Design Components
- **Base de Datos**: SQLite (SQLiteOpenHelper)
- **Geolocalización**: Google Play Services Location API
- **Arquitectura**: Activity-based con Singleton Pattern (CarritoManager)
- **Componentes**: RecyclerView, CardView, TextInputLayout

## 📋 Requisitos

- **Android Studio**: Arctic Fox o superior
- **Kotlin**: 1.8+
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Google Play Services**: Para geolocalización

## 📱 Pantallas de la App

### 1. Login (Pantalla de Inicio)
- Autenticación con email y contraseña
- Validación de formato de email
- Usuario demo: `demo@shopping.com` / `demo123`
- Navegación a registro

### 2. Registro de Usuario
- Campos: Nombre, Email, Teléfono, Dirección, Contraseña
- Validaciones completas en todos los campos
- Verificación de email duplicado
- Confirmación de contraseña

### 3. Listado de Productos
- Catálogo de 10 productos tecnológicos
- Añadir productos al carrito
- Contador de items en carrito
- Acceso rápido a carrito y ubicación
- Botón de cerrar sesión

### 4. Carrito de Compras
- Ver todos los productos agregados
- Modificar cantidades (+/-)
- Eliminar productos
- Total calculado automáticamente
- Confirmación de pedido

### 5. Geolocalización
- Solicitud de permisos en tiempo real
- Obtención de coordenadas GPS (latitud/longitud)
- Feedback visual del estado de permisos
- Manejo de errores

## 💾 Base de Datos

### Tabla: usuarios
```sql
CREATE TABLE usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    telefono TEXT,
    direccion TEXT,
    password TEXT NOT NULL
)
```

### Usuario Demo Precargado
- **Email**: demo@shopping.com
- **Contraseña**: demo123
- **Nombre**: Usuario Demo

## 📦 Estructura del Proyecto

```
ShoppingApp/
│
├── app/
│   ├── src/main/
│   │   ├── java/com/example/shoppingapp/
│   │   │   ├── LoginActivity.kt           # Pantalla de login
│   │   │   ├── RegistroActivity.kt        # Registro de usuarios
│   │   │   ├── ListadoProductosActivity.kt # Catálogo
│   │   │   ├── CarritoActivity.kt         # Carrito de compras
│   │   │   ├── LocationActivity.kt        # Geolocalización
│   │   │   ├── DatabaseHelper.kt          # SQLite helper
│   │   │   ├── CarritoManager.kt          # Singleton del carrito
│   │   │   ├── Producto.kt                # Modelo de producto
│   │   │   ├── ItemCarrito.kt             # Modelo item carrito
│   │   │   ├── ProductosAdapter.kt        # Adapter productos
│   │   │   └── CarritoAdapter.kt          # Adapter carrito
│   │   │
│   │   ├── res/
│   │   │   ├── layout/                   # XML layouts
│   │   │   ├── values/                   # Colores, strings, temas
│   │   │   └── xml/                      # Configuraciones
│   │   │
│   │   └── AndroidManifest.xml
│   │
│   └── build.gradle
│
├── build.gradle
├── settings.gradle
└── README.md
```

## 🚀 Instalación y Ejecución

### Paso 1: Clonar el repositorio
```bash
git clone <repository-url>
cd ShoppingApp
```

### Paso 2: Abrir en Android Studio
1. Abrir Android Studio
2. Seleccionar "Open an Existing Project"
3. Navegar a la carpeta del proyecto

### Paso 3: Sincronizar dependencias
```bash
# Android Studio sincronizará automáticamente
# O ejecutar manualmente:
./gradlew build
```

### Paso 4: Ejecutar la app
1. Conectar dispositivo Android o iniciar emulador
2. Click en "Run" (Shift + F10)
3. Seleccionar dispositivo de destino

## 📝 Uso de la Aplicación

### Primera Vez
1. **Inicia la app** - Verás la pantalla de Login
2. **Usa el usuario demo**: 
   - Email: `demo@shopping.com`
   - Contraseña: `demo123`
3. **O crea una cuenta nueva** - Click en "Regístrate"

### Flujo Normal
1. **Login** → Inicia sesión con tus credenciales
2. **Explora Productos** → Navega el catálogo
3. **Agregar al Carrito** → Click en "Agregar" en productos deseados
4. **Ver Carrito** → Revisa y modifica tu pedido
5. **Ubicación** → 📍 Obtén tu ubicación GPS (opcional)
6. **Confirmar Pedido** → Finaliza tu compra
7. **Cerrar Sesión** → Sal de la app de forma segura

## 🔑 Permisos Requeridos

```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
```

Los permisos de ubicación se solicitan en tiempo de ejecución al usar la funcionalidad de geolocalización.

## 🎨 Diseño y Usabilidad

### Paleta de Colores
- **Primary Blue**: #2196F3
- **Accent Orange**: #FF9800
- **Success Green**: #4CAF50
- **Error Red**: #F44336
- **Background Light**: #F5F5F5

### Validaciones Implementadas
- ✅ Email válido (formato correcto)
- ✅ Contraseña mínimo 6 caracteres
- ✅ Nombre mínimo 3 caracteres
- ✅ Teléfono mínimo 10 dígitos
- ✅ Confirmación de contraseña
- ✅ Verificación de email duplicado

## 📊 Productos Disponibles

1. Smartphone Samsung Galaxy - $299.99
2. Laptop HP Pavilion - $599.99
3. Auriculares Sony WH-1000XM4 - $199.99
4. Tablet iPad Air - $399.99
5. Smart TV LG 55" - $499.99
6. Cámara Canon EOS M50 - $449.99
7. Nintendo Switch - $279.99
8. Smartwatch Apple Watch SE - $249.99
9. Altavoz Bluetooth JBL - $79.99
10. Teclado Mecánico Razer - $89.99

## 👥 Autor

**Proyecto Universitario**
- Curso: Desarrollo de Aplicaciones Nativas
- Fecha: Noviembre 2025

## 📄 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.
