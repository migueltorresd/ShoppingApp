# Cómo Agregar Imágenes Reales a los Productos

## 📁 Ubicación de las Imágenes

Las imágenes deben ir en:
```
app/src/main/res/drawable/
```

## 📋 Nombres de Archivo Requeridos

Descarga imágenes y guárdalas con estos nombres EXACTOS:

1. **img_smartphone.png** o **img_smartphone.jpg**
   - Producto: Smartphone Samsung Galaxy
   - Busca: imagen de smartphone/celular Samsung

2. **img_laptop.png** o **img_laptop.jpg**
   - Producto: Laptop HP Pavilion
   - Busca: imagen de laptop HP

3. **img_auriculares.png** o **img_auriculares.jpg**
   - Producto: Auriculares Sony WH-1000XM4
   - Busca: imagen de auriculares Sony

4. **img_tablet.png** o **img_tablet.jpg**
   - Producto: Tablet iPad Air
   - Busca: imagen de tablet iPad

5. **img_tv.png** o **img_tv.jpg**
   - Producto: Smart TV LG 55"
   - Busca: imagen de Smart TV LG

## 🎨 Recomendaciones para las Imágenes

- **Formato**: PNG o JPG
- **Tamaño**: Máximo 500KB por imagen
- **Dimensiones**: 400x400 píxeles o 500x500 píxeles
- **Fondo**: Preferiblemente fondo blanco o transparente
- **Calidad**: Imagen clara del producto

## 🌐 Dónde Descargar Imágenes

### Opción 1: Unsplash (Gratuito, sin registro)
1. Ve a https://unsplash.com
2. Busca el producto (ej: "smartphone samsung")
3. Descarga la imagen
4. Renombra según la lista de arriba

### Opción 2: Pexels (Gratuito, sin registro)
1. Ve a https://pexels.com
2. Busca el producto
3. Descarga
4. Renombra

### Opción 3: Imágenes de productos oficiales
- Samsung: https://www.samsung.com
- HP: https://www.hp.com
- Sony: https://www.sony.com
- Apple: https://www.apple.com
- LG: https://www.lg.com

## 📝 Pasos para Agregar las Imágenes

1. Descarga las 5 imágenes
2. Renómbralas con los nombres exactos de arriba
3. Cópialas a: `app/src/main/res/drawable/`
4. **IMPORTANTE**: Si son PNG o JPG, ELIMINA los archivos XML correspondientes
   - Borra `img_smartphone.xml`
   - Borra `img_laptop.xml`
   - Borra `img_auriculares.xml`
   - Borra `img_tablet.xml`
   - Borra `img_tv.xml`
5. Reconstruye el proyecto en Android Studio (Build > Rebuild Project)
6. Ejecuta la app

## 🔄 Si Quieres Cambiar un Producto

Por ejemplo, cambiar el smartphone Samsung por un iPhone:

1. Edita `DatabaseHelper.kt` línea ~83
2. Cambia el nombre y descripción del producto
3. Reemplaza la imagen `img_smartphone.png` con la nueva
4. Reconstruye

## ⚠️ Notas Importantes

- **NO** cambies los nombres de archivo en el código sin cambiar los archivos físicos
- Los nombres deben estar en **minúsculas** y sin espacios
- Si usas imágenes muy grandes, la app puede ir lenta
- Los archivos XML actuales son placeholders temporales con iconos vectoriales

## 🎨 Actualmente

La app tiene iconos vectoriales de placeholder que se ven bien pero no son fotos reales.
Una vez agregues las imágenes PNG/JPG, se verán mucho más profesionales.

---

✅ Una vez hagas esto, tus productos tendrán imágenes reales y la app se verá increíble!
