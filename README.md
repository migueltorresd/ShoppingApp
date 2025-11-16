# ShoppingApp 🛍️

**Proyecto Final - Desarrollo de Aplicaciones Nativas Android**

Aplicación móvil de compras desarrollada en Kotlin para Android con arquitectura completa, sistema CRUD, gestión de usuarios, carrito de compras y geolocalización.

## 🎯 Características Implementadas

### ✅ Funcionalidades Principales
- **🔐 Sistema de Autenticación**: Login y registro con validaciones
- **📝 CRUD de Productos**: Crear, Leer, Actualizar y Eliminar productos (SQLite)
- **👤 CRUD de Perfil de Usuario**: Ver y editar información personal, cambiar contraseña
- **🛍️ Carrito de Compras**: Gestión completa (agregar/eliminar/modificar cantidades)
- **📍 Geolocalización**: Obtención de ubicación GPS en tiempo real
- **💾 Base de Datos SQLite**: Persistencia de usuarios y productos

### ✨ Características Técnicas
- 🔒 **Validación de Datos**: Email, teléfono, contraseñas seguras (mínimo 6 caracteres)
- 📍 **Permisos en Tiempo Real**: Solicitud dinámica de permisos de ubicación
- 🎨 **Material Design 3**: Interfaz moderna con modo oscuro automático
- 🕶️ **Glassmorphism Effects**: Diseño moderno con efectos visuales
- 💾 **Persistencia Completa**: Base de datos SQLite para usuarios y productos
- 🔄 **Sesión de Usuario**: SharedPreferences para mantener la sesión activa
- 🖼️ **Imágenes Reales**: Productos con imágenes reales almacenadas en drawable

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

## 🐱 Pantallas de la App

### 1. 🔐 Login (Pantalla de Inicio)
- Autenticación con email y contraseña
- Validación de formato de email
- Usuario demo precargado: `demo@shopping.com` / `demo123`
- Navegación a registro
- Diseño moderno con gradientes

### 2. 📝 Registro de Usuario
- Campos: Nombre, Email, Teléfono, Dirección, Contraseña
- Validaciones completas en todos los campos
- Verificación de email duplicado en base de datos
- Confirmación de contraseña
- Material Design con TextInputLayout

### 3. 🛍️ Listado de Productos (Pantalla Principal)
- Catálogo de productos con imágenes reales
- **CRUD Completo de Productos**:
  - ➕ **Crear**: Botón "Nuevo" para agregar productos
  - 👁️ **Leer**: Visualización en RecyclerView con cards modernos
  - ✏️ **Actualizar**: Long press en producto → "Editar"
  - 🗑️ **Eliminar**: Long press en producto → "Eliminar"
- Añadir productos al carrito
- Contador de items en carrito
- Barra de navegación con iconos:
  - 👤 Mi Perfil
  - 📍 Ubicación
  - 🛍️ Carrito
- Botón de cerrar sesión

### 4. 👤 Mi Perfil (CRUD de Usuario)
- **Ver información personal**:
  - Nombre completo
  - Email (no editable)
  - Teléfono
  - Dirección
- **Editar datos personales**: Actualizar nombre, teléfono y dirección
- **Cambiar contraseña**:
  - Validación de contraseña actual
  - Nueva contraseña con confirmación
  - Mínimo 6 caracteres
- Diseño moderno con avatar y cards

### 5. 🛍️ Carrito de Compras
- Ver todos los productos agregados
- Modificar cantidades (botones + / -)
- Eliminar productos individualmente
- Total calculado automáticamente
- Confirmación de pedido
- Diseó vertical de botones de cantidad

### 6. 📍 Geolocalización
- Solicitud de permisos en tiempo real
- Obtención de coordenadas GPS (latitud/longitud)
- Visualización clara de coordenadas
- Feedback visual del estado de permisos
- Manejo de errores y estados
- Diseño moderno con cards informativas

## 💾 Base de Datos (SQLite)

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

### Tabla: productos
```sql
CREATE TABLE productos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    precio REAL NOT NULL,
    descripcion TEXT,
    imagen_res_id INTEGER
)
```

### Datos Precargados

**Usuario Demo:**
- **Email**: demo@shopping.com
- **Contraseña**: demo123
- **Nombre**: Usuario Demo
- **Teléfono**: 1234567890
- **Dirección**: Calle Principal 123

**Productos Iniciales:** 5 productos tecnológicos con imágenes reales
- Smartphone Samsung Galaxy - $299.99
- Laptop HP Pavilion - $599.99
- Auriculares Sony WH-1000XM4 - $199.99
- Tablet iPad Air - $399.99
- Smart TV LG 55" - $499.99

## 📦 Estructura del Proyecto

```
ShoppingApp/
│
├── app/
│   ├── src/main/
│   │   ├── java/com/example/shoppingapp/
│   │   │   ├── LoginActivity.kt           # Pantalla de login
│   │   │   ├── RegistroActivity.kt        # Registro de usuarios
│   │   │   ├── ListadoProductosActivity.kt # Catálogo + CRUD productos
│   │   │   ├── PerfilActivity.kt          # Perfil de usuario (CRUD)
│   │   │   ├── CarritoActivity.kt         # Carrito de compras
│   │   │   ├── LocationActivity.kt        # Geolocalización GPS
│   │   │   ├── DatabaseHelper.kt          # SQLite helper (CRUD)
│   │   │   ├── CarritoManager.kt          # Singleton del carrito
│   │   │   ├── Producto.kt                # Modelo de producto
│   │   │   ├── Usuario.kt                 # Modelo de usuario
│   │   │   ├── ItemCarrito.kt             # Modelo item carrito
│   │   │   ├── ProductosAdapter.kt        # Adapter productos
│   │   │   └── CarritoAdapter.kt          # Adapter carrito
│   │   │
│   │   ├── res/
│   │   │   ├── drawable/                 # Imágenes de productos
│   │   │   │   ├── img_smartphone.jpg
│   │   │   │   ├── img_laptop.jpg
│   │   │   │   ├── img_auriculares.jpg
│   │   │   │   ├── img_tablet.jpg
│   │   │   │   ├── img_tv.jpg
│   │   │   │   └── *.xml (drawables)
│   │   │   ├── layout/                   # XML layouts (10 archivos)
│   │   │   ├── values/                   # Colores, strings, temas
│   │   │   │   ├── colors.xml            # Paleta light mode
│   │   │   │   └── themes.xml            # Material Design 3
│   │   │   └── values-night/             # Dark mode automático
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

### Paleta de Colores Moderna

**Light Mode:**
- **Primary Blue**: #6366F1 (Indigo moderno)
- **Primary Dark**: #4F46E5
- **Accent Gold**: #F59E0B (Dorado vibrante)
- **Background Light**: #F8FAFC
- **Text Primary**: #1E293B

**Dark Mode (Automático):**
- **Primary Blue**: #818CF8
- **Accent Gold**: #FCD34D
- **Background Dark**: #0F172A (Deep blue-black)
- **Background Card**: #1E293B (Slate)
- **Text Primary**: #F1F5F9

### Efectos Visuales
- 🕶️ **Glassmorphism**: Efectos de vidrio esmerilado en cards
- 🌌 **Gradientes**: Fondos degradados en headers
- ✨ **Material Design 3**: Componentes modernos de Google
- 🌙 **Modo Oscuro**: Activación automática según sistema

### Validaciones Implementadas
- ✅ Email válido (formato correcto)
- ✅ Contraseña mínimo 6 caracteres
- ✅ Nombre mínimo 3 caracteres
- ✅ Teléfono mínimo 10 dígitos
- ✅ Confirmación de contraseña
- ✅ Verificación de email duplicado

## 📋 Sistema CRUD - Productos

### Productos Iniciales (Precargados)
1. 📱 Smartphone Samsung Galaxy - $299.99
2. 💻 Laptop HP Pavilion - $599.99
3. 🎧 Auriculares Sony WH-1000XM4 - $199.99
4. 💎 Tablet iPad Air - $399.99
5. 📺 Smart TV LG 55" - $499.99

### Operaciones CRUD Disponibles

**Crear Producto:**
- Botón "Nuevo" en pantalla principal
- Formulario con: Nombre, Precio, Descripción
- Validación de campos obligatorios
- Imagen predeterminada asignada

**Leer Productos:**
- Visualización en lista con RecyclerView
- Cards con diseño moderno
- Imágenes, nombre, precio y descripción

**Actualizar Producto:**
- Long press en cualquier producto
- Selección "Editar" del menú
- Modificar nombre, precio o descripción

**Eliminar Producto:**
- Long press en cualquier producto
- Selección "Eliminar" del menú
- Confirmación antes de eliminar

## 🎯 Proyecto Final - Criterios Cumplidos

### ✅ Requisitos Implementados

**1. Sistema CRUD Completo**
- ✅ CRUD de Productos (Crear, Leer, Actualizar, Eliminar)
- ✅ CRUD de Perfil de Usuario (Ver, Actualizar, Cambiar contraseña)
- ✅ Base de datos SQLite con 2 tablas relacionadas

**2. Arquitectura Móvil**
- ✅ Arquitectura basada en Activities
- ✅ Patrón Singleton (CarritoManager)
- ✅ Adaptadores personalizados (RecyclerView)
- ✅ DatabaseHelper con SQLiteOpenHelper

**3. Código Fuente y Producto**
- ✅ Código en Kotlin organizado y documentado
- ✅ Material Design 3 con modo oscuro
- ✅ Validaciones completas en todos los formularios
- ✅ Manejo de permisos en tiempo de ejecución

**4. Despliegue en Git**
- ✅ Repositorio en GitHub
- ✅ Commits organizados por funcionalidad
- ✅ README completo con documentación
- ✅ Branch `feature/proyecto-final`

**5. Funcionalidades Adicionales**
- ✅ Carrito de compras funcional
- ✅ Geolocalización GPS
- ✅ Sistema de autenticación
- ✅ Sesión persistente
- ✅ Imágenes reales de productos

## 📦 Entregables

1. **Código Fuente**: Repositorio completo en GitHub
2. **APK**: Archivo de instalación para Android
3. **Documentación**: README.md con manual de uso
4. **Base de Datos**: Esquema SQLite con datos de prueba

## 👥 Autor

**Proyecto Universitario**
- Curso: Desarrollo de Aplicaciones Nativas Android
- Actividad: Proyecto Final - Sistema CRUD
- Fecha: Noviembre 2024

## 📄 Licencia

Este proyecto es de código abierto y está disponible para fines educativos.
