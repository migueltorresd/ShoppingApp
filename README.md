# Shopping App - Aplicación de Compras Android

Una aplicación de comercio electrónico desarrollada en Kotlin para Android que permite a los usuarios navegar productos, agregarlos a un carrito y simular el proceso de compra.

## Características

### Autenticación
- **Pantalla de Login**: Ingreso con usuario y contraseña
- **Pantalla de Registro**: Registro de nuevos usuarios con validación de campos

### Tienda
- **Listado de Productos**: Catálogo con 10 productos predefinidos
- **Agregar al Carrito**: Funcionalidad para añadir productos al carrito
- **Contador de Items**: Visualización en tiempo real de productos en carrito

### 🛒 Carrito de Compras
- **Gestión de Cantidad**: Aumentar/reducir cantidad de productos
- **Cálculo de Total**: Total automático del costo de compra
- **Vaciar Carrito**: Opción para eliminar todos los productos
- **Finalizar Compra**: Simulación del proceso de compra

## Estructura del Proyecto

```
ShoppingApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/shoppingapp/
│   │   │   ├── LoginActivity.kt
│   │   │   ├── RegistroActivity.kt
│   │   │   ├── ListadoProductosActivity.kt
│   │   │   ├── CarritoActivity.kt
│   │   │   ├── Producto.kt
│   │   │   ├── ItemCarrito.kt
│   │   │   ├── CarritoManager.kt
│   │   │   ├── ProductosAdapter.kt
│   │   │   └── CarritoAdapter.kt
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_login.xml
│   │   │   │   ├── activity_registro.xml
│   │   │   │   ├── activity_listado_productos.xml
│   │   │   │   ├── activity_carrito.xml
│   │   │   │   ├── item_producto.xml
│   │   │   │   └── item_carrito.xml
│   │   │   ├── values/
│   │   │   │   ├── colors.xml
│   │   │   │   └── strings.xml
│   │   │   └── AndroidManifest.xml
│   │   └── build.gradle
│   ├── build.gradle
│   ├── gradle.properties
│   └── settings.gradle
```

## Tecnologías Utilizadas

- **Lenguaje**: Kotlin
- **UI Framework**: Android Views (XML layouts)
- **Architecture Components**: 
  - RecyclerView para listas
  - CardView para elementos de producto
  - Material Design Components
- **Navegación**: Intents explícitos entre Activities

## Diseño y UI

### Paleta de Colores Personalizada
- **Azul Principal**: #2196F3
- **Azul Oscuro**: #1976D2
- **Naranja Acento**: #FF9800
- **Verde Éxito**: #4CAF50
- **Rojo Error**: #F44336

### Características de Diseño
- Interfaz moderna con Material Design
- Layouts responsivos y adaptativos
- Iconografía consistente
- Feedback visual para interacciones

## Instrucciones para Compilar

### Prerrequisitos
- Android Studio Arctic Fox o superior
- JDK 8 o superior
- Android SDK API 24 o superior

### Pasos para Compilar

1. **Abrir el proyecto**:
   ```bash
   # Abre Android Studio y selecciona "Open an Existing Project"
   # Navega hasta la carpeta ShoppingApp y ábrela
   ```

2. **Sincronizar dependencias**:
   - Android Studio automáticamente sincronizará las dependencias
   - Si no, haz clic en "Sync Now" en la barra superior

3. **Compilar la aplicación**:
   - Menú: Build → Make Project (Ctrl+F9)
   - O usar el botón de compilar en la toolbar

4. **Generar APK**:
   - Menú: Build → Build Bundle(s) / APK(s) → Build APK(s)
   - El APK se generará en: `app/build/outputs/apk/debug/`

5. **Ejecutar en dispositivo/emulador**:
   - Conecta un dispositivo Android o inicia un emulador
   - Haz clic en Run (▶️) o presiona Shift+F10

## Flujo de Navegación

1. **Login** → Ingreso de credenciales → **Listado de Productos**
2. **Login** → "¿No tienes cuenta?" → **Registro** → **Login**
3. **Listado de Productos** → "Ver Carrito" → **Carrito**
4. **Carrito** → "←" → **Listado de Productos**

## Funcionalidades Implementadas

### ✅ Requisitos Cumplidos

- [x] 4 pantallas (Activities): Login, Registro, Listado, Carrito
- [x] Diseño visual personalizado con colors.xml
- [x] Login con campos usuario/contraseña
- [x] Registro con campos básicos y validaciones
- [x] Listado de productos con imágenes y botón agregar
- [x] Carrito con productos agregados y botón finalizar
- [x] Navegación entre pantallas con Intents
- [x] AndroidManifest.xml configurado
- [x] Paleta de colores personalizada
- [x] Generación de APK funcional

### 🚀 Funcionalidades Adicionales

- [x] Validación de formularios
- [x] Gestión de estado del carrito (Singleton)
- [x] Cálculo automático de totales
- [x] Interfaz adaptativa (carrito vacío/con productos)
- [x] Confirmaciones para acciones importantes
- [x] RecyclerView para listas eficientes
- [x] Datos de muestra realistas (10 productos)

## Uso de la Aplicación

1. **Iniciar**: Ingresa cualquier usuario y contraseña
2. **Explorar**: Navega por el catálogo de productos
3. **Agregar**: Añade productos al carrito con el botón "Agregar"
4. **Gestionar**: Ve al carrito para modificar cantidades
5. **Comprar**: Finaliza la compra con el botón correspondiente

## Notas de Desarrollo

- La aplicación utiliza datos estáticos para demostración
- No se conecta a servicios backend reales
- El login acepta cualquier combinación de usuario/contraseña
- Las compras son simuladas (no procesamiento real)
- Optimizada para dispositivos Android API 24+

---
