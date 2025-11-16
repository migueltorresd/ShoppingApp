# Guion de Presentación - Proyecto Final
## ShoppingApp - Aplicación de Compras Android

---

## 📋 INTRODUCCIÓN (1-2 minutos)

### Saludo y Contexto
"Buenos días/tardes. Mi nombre es [Tu Nombre] y hoy les voy a presentar mi proyecto final para el curso de Desarrollo de Aplicaciones Nativas Android: **ShoppingApp**, una aplicación móvil de comercio electrónico completa desarrollada en Kotlin."

### Visión General
"ShoppingApp es una aplicación que permite a los usuarios:
- Registrarse y autenticarse de forma segura
- Navegar por un catálogo de productos tecnológicos
- Gestionar un carrito de compras con persistencia de datos
- Administrar productos mediante operaciones CRUD
- Gestionar su perfil de usuario
- Obtener su ubicación GPS

Todo esto con un diseño moderno que incluye modo oscuro automático y Material Design 3."

---

## 🎯 OBJETIVOS DEL PROYECTO (1 minuto)

"Los objetivos principales que cumple este proyecto son:

1. **Implementar un sistema CRUD completo** tanto para productos como para perfil de usuario
2. **Desarrollar una arquitectura móvil robusta** utilizando SQLite para persistencia de datos
3. **Aplicar las mejores prácticas** de desarrollo Android nativo
4. **Crear una interfaz moderna y funcional** siguiendo los lineamientos de Material Design"

---

## 🛠️ TECNOLOGÍAS UTILIZADAS (1 minuto)

"Para el desarrollo de esta aplicación utilicé:

### Lenguaje y Plataforma
- **Kotlin** como lenguaje principal
- **Android SDK** nivel mínimo 24 (Android 7.0)
- **Android Studio** como IDE

### Componentes y Librerías
- **SQLite** con SQLiteOpenHelper para base de datos local
- **Material Design 3** para componentes UI modernos
- **RecyclerView** para listas optimizadas
- **Google Play Services** para geolocalización
- **SharedPreferences** para sesión de usuario

### Arquitectura
- Patrón **Singleton** para el CarritoManager
- **Activities** como base arquitectónica
- **Adapters personalizados** para listas dinámicas"

---

## 💾 BASE DE DATOS (2 minutos)

"El corazón de la persistencia de datos es una base de datos SQLite con 3 tablas principales:"

### Tabla: Usuarios
"La tabla de usuarios almacena:
- ID autoincrementable
- Nombre completo
- Email único (usado para login)
- Teléfono
- Dirección
- Contraseña

Incluye un usuario demo precargado: demo@shopping.com / demo123"

### Tabla: Productos
"La tabla de productos contiene:
- ID autoincrementable
- Nombre del producto
- Precio (tipo REAL)
- Descripción
- ID del recurso de imagen

Viene con 5 productos tecnológicos precargados: smartphone, laptop, auriculares, tablet y TV."

### Tabla: Carrito
"La nueva tabla de carrito para persistencia:
- ID autoincrementable
- ID del producto (llave foránea)
- Cantidad

Esto permite que el carrito persista entre sesiones de la app."

---

## 📱 DEMOSTRACIÓN DE FUNCIONALIDADES (5-7 minutos)

### 1. Sistema de Autenticación (1 min)

**[MOSTRAR PANTALLA DE LOGIN]**

"Comenzamos en la pantalla de login con:
- Validación de formato de email
- Contraseña mínima de 6 caracteres
- Diseño moderno con gradientes

Voy a iniciar sesión con el usuario demo..."

**[HACER LOGIN]**

### 2. Pantalla Principal - Catálogo de Productos (1 min)

**[MOSTRAR LISTADO DE PRODUCTOS]**

"Ahora estamos en la pantalla principal donde vemos:
- Catálogo de productos con imágenes reales
- Diseño con cards modernas y glassmorphism
- Barra de navegación superior con múltiples opciones
- Contador de items en el carrito en el footer

Cada producto muestra:
- Imagen del producto
- Nombre
- Precio en dólares
- Descripción
- Botón para agregar al carrito"

### 3. CRUD de Productos (2 min)

**[DEMOSTRAR CREAR]**

"Voy a demostrar el CRUD de productos. Primero, **crear un producto nuevo**:
- Presiono el botón 'Nuevo'
- Se abre un diálogo con formulario
- Ingreso nombre, precio y descripción
- Valida campos obligatorios
- Al guardar, se inserta en la base de datos SQLite"

**[CREAR PRODUCTO]**

"Como pueden ver, el producto aparece inmediatamente en la lista."

**[DEMOSTRAR EDITAR]**

"Ahora voy a **editar** un producto:
- Mantengo presionado el producto
- Aparece un menú con opciones 'Editar' y 'Eliminar'
- Selecciono 'Editar'
- Se abre el mismo diálogo pero con los datos precargados
- Modifico algún campo
- Al guardar, se actualiza en la base de datos"

**[EDITAR PRODUCTO]**

**[DEMOSTRAR ELIMINAR]**

"Para **eliminar**:
- Long press en cualquier producto
- Selecciono 'Eliminar'
- Confirmo en el diálogo
- El producto se elimina de la base de datos"

**[ELIMINAR PRODUCTO]**

### 4. Gestión del Carrito (1 min)

**[AGREGAR PRODUCTOS AL CARRITO]**

"Ahora voy a agregar algunos productos al carrito...
Observen cómo el contador en el footer se actualiza automáticamente."

**[IR AL CARRITO]**

"En la pantalla del carrito podemos:
- Ver todos los productos agregados
- Aumentar o disminuir la cantidad con los botones + y -
- Eliminar productos individuales
- Ver el total calculado automáticamente
- Vaciar todo el carrito
- Finalizar la compra

La característica más importante es que **el carrito persiste en SQLite**, si cierro y abro la app, los productos siguen aquí."

### 5. Mi Perfil - CRUD de Usuario (1 min)

**[IR A MI PERFIL]**

"En la sección de Mi Perfil tenemos el segundo CRUD del proyecto:
- Visualización de toda la información personal
- El email no es editable (es el identificador único)
- Puedo editar nombre, teléfono y dirección
- Botón para cambiar contraseña

Voy a editar mi información..."

**[EDITAR PERFIL]**

"Y ahora voy a cambiar la contraseña..."

**[ABRIR DIÁLOGO DE CAMBIAR CONTRASEÑA]**

"El sistema valida:
- Que la contraseña actual sea correcta
- Que la nueva contraseña tenga mínimo 6 caracteres
- Que la confirmación coincida"

### 6. Geolocalización (30 seg)

**[IR A UBICACIÓN]**

"Finalmente, la funcionalidad de geolocalización:
- Solicita permisos en tiempo de ejecución
- Obtiene coordenadas GPS (latitud y longitud)
- Diseño moderno con cards informativas
- Muestra el estado de los permisos

Voy a obtener mi ubicación..."

**[OBTENER UBICACIÓN]**

---

## 🎨 DISEÑO Y UX (1 minuto)

"Quiero destacar algunos aspectos del diseño:

### Material Design 3
- Componentes modernos de Google
- Botones con MaterialButton
- Cards con elevación y bordes redondeados
- TextInputLayout para formularios elegantes

### Modo Oscuro Automático
"**[SI ES POSIBLE, MOSTRAR CAMBIO DE TEMA]**
- Se activa automáticamente según el tema del sistema
- Paleta de colores optimizada para ambos modos
- Todos los componentes se adaptan

### Efectos Visuales
- Glassmorphism en cards
- Gradientes en headers
- Iconos consistentes en toda la app
- Espaciado y tipografía cuidadosamente diseñados"

---

## ✅ CRITERIOS CUMPLIDOS (1 minuto)

"Este proyecto cumple con todos los criterios del proyecto final:

### 1. Sistema CRUD Completo ✓
- CRUD de Productos (Crear, Leer, Actualizar, Eliminar)
- CRUD de Perfil de Usuario
- Persistencia en SQLite con 3 tablas

### 2. Arquitectura Móvil ✓
- Activities bien estructuradas
- Patrón Singleton para CarritoManager
- Adaptadores personalizados
- DatabaseHelper con SQLiteOpenHelper

### 3. Código Fuente y Producto ✓
- Código en Kotlin limpio y documentado
- Validaciones en todos los formularios
- Manejo de permisos en tiempo de ejecución
- Material Design 3

### 4. Despliegue en Git ✓
- Repositorio en GitHub
- Branch feature/proyecto-final
- Commits organizados por funcionalidad
- README completo

### 5. Funcionalidades Adicionales ✓
- Carrito de compras con persistencia
- Sistema de autenticación
- Geolocalización GPS
- Sesión persistente con SharedPreferences
- Imágenes reales de productos"

---

## 🚀 CARACTERÍSTICAS DESTACADAS (1 minuto)

"Algunas características que hacen especial a esta app:

1. **Persistencia Completa**: No solo los usuarios y productos persisten, también el carrito de compras entre sesiones

2. **Validaciones Robustas**: Todos los formularios tienen validaciones del lado del cliente

3. **UX Optimizada**: Feedback inmediato con Toast messages, diálogos de confirmación, y actualizaciones automáticas de UI

4. **Diseño Moderno**: Implementación real de Material Design 3 con modo oscuro automático

5. **Funcionalidad Real**: No es solo una demo, es una app funcional con flujo completo de compra"

---

## 📝 CONCLUSIÓN (1 minuto)

"En conclusión, ShoppingApp es una aplicación completa de comercio electrónico que:

✅ Cumple con todos los requisitos del proyecto final
✅ Implementa dos sistemas CRUD funcionales
✅ Utiliza persistencia de datos con SQLite
✅ Ofrece una experiencia de usuario moderna y fluida
✅ Aplica las mejores prácticas de desarrollo Android

El código está disponible en GitHub en el repositorio:
`https://github.com/migueltorresd/ShoppingApp.git`
Branch: `feature/proyecto-final`

Toda la documentación técnica está en el README del proyecto."

---

## ❓ PREGUNTAS FRECUENTES

### Técnicas:
**"¿Por qué elegiste SQLite en lugar de Room?"**
- "Elegí SQLite con SQLiteOpenHelper para demostrar el conocimiento fundamental de bases de datos en Android. Room es una abstracción sobre SQLite, pero quería mostrar que entiendo cómo funciona la persistencia a bajo nivel."

**"¿Cómo manejas la seguridad de las contraseñas?"**
- "Actualmente las contraseñas se almacenan en texto plano en SQLite. En una app de producción, usaría hashing con bcrypt o similar antes de almacenarlas."

**"¿Por qué usaste un Singleton para el CarritoManager?"**
- "El patrón Singleton garantiza una única instancia del carrito en toda la app, evitando duplicación de datos y asegurando consistencia. Además, facilita el acceso desde cualquier Activity."

### Funcionales:
**"¿El carrito persiste si cierro la app?"**
- "Sí, completamente. Implementé una tabla dedicada en SQLite que guarda todos los items del carrito con sus cantidades. Al reabrir la app, el carrito se carga automáticamente."

**"¿El modo oscuro es automático?"**
- "Sí, se activa automáticamente según la configuración del sistema del usuario. Tengo archivos colors.xml separados para light y dark mode."

---

## 💡 TIPS PARA LA PRESENTACIÓN

1. **Practica antes**: Ensaya varias veces para que fluya naturalmente
2. **Ten la app lista**: Asegúrate de que todo funciona antes de presentar
3. **Prepara el demo**: Ten usuarios y productos de ejemplo listos
4. **Controla el tiempo**: Ajusta según el tiempo que te den (5-10 min usualmente)
5. **Sé entusiasta**: Muestra pasión por tu proyecto
6. **Mantén contacto visual**: No leas, habla con naturalidad
7. **Ten backup**: Screenshots por si algo falla en el demo
8. **Destaca lo importante**: Enfócate en el CRUD y la persistencia

---

## ⏱️ DISTRIBUCIÓN DEL TIEMPO (10 minutos total)

- Introducción y objetivos: **2 min**
- Tecnologías y arquitectura: **1 min**
- Base de datos: **1 min**
- Demo de funcionalidades: **5 min**
  - Login: 30 seg
  - CRUD Productos: 2 min
  - Carrito: 1 min
  - Mi Perfil: 1 min
  - Geolocalización: 30 seg
- Conclusión: **1 min**

**Total: 10 minutos + preguntas**

---

## 🎬 FRASE FINAL

"Gracias por su atención. Estoy disponible para responder cualquier pregunta sobre el proyecto."

---

**¡Mucho éxito en tu presentación! 🚀**
