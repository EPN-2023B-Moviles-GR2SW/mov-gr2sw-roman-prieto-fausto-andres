package com.example.examen_con_sqlite.Controller

import android.content.ContentValues
import android.content.Context
import com.example.examen_con_sqlite.Database.BaseDeDatosHelper
import com.example.examen_con_sqlite.Model.Producto

class ProductoCRUD(context: Context) {

    private val dbHelper: BaseDeDatosHelper = BaseDeDatosHelper(context)

    // Operaciones CRUD para trabajar con Cancion en la Base de Datos

    // Funcion para Crear una Cancion con la Base de Datos

    fun crearProducto(producto: Producto){

        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("empresaId", producto.empresaId)
            put("nombre", producto.nombre)
            put("tipo", producto.tipo)
            put("cantidad", producto.cantidad)
            put("precio", producto.precio)
        }

        // Insertar la nueva canción y obtener su ID
        val nuevoId = db.insert(BaseDeDatosHelper.TABLA_PRODUCTO, null, values)

        // Asignar el nuevo ID a la canción
        producto.id = nuevoId.toInt()

        // Cerrar la conexión
        db.close()
    }

    // Funcion para obtener todas las canciones de un Album asociadas por su ID

    fun obtenerProductosPorEmpresaId(empresaId: Int): List<Producto> {
        val listaProductos = mutableListOf<Producto>()
        val db = dbHelper.readableDatabase

        val query = "SELECT * FROM ${BaseDeDatosHelper.TABLA_PRODUCTO} WHERE empresaId = ?"
        val selectionArgs = arrayOf(empresaId.toString())

        val cursor = db.rawQuery(query, selectionArgs)

        // Verificar si el cursor tiene columnas antes de intentar acceder a ellas
        if (cursor.columnCount > 0) {
            // Iterar sobre el cursor y construir la lista de canciones
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                    val tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"))
                    val cantidad = cursor.getInt(cursor.getColumnIndexOrThrow("cantidad"))
                    val precio = cursor.getInt(cursor.getColumnIndexOrThrow("precio"))

                    // Crear una instancia de Cancion y agregarla a la lista
                    val producto = Producto(id, empresaId, nombre, tipo, cantidad, precio)
                    listaProductos.add(producto)
                } while (cursor.moveToNext())
            }
        }

        // Cerrar el cursor y la base de datos
        cursor.close()
        db.close()

        return listaProductos
    }


    // Funcion para obtener todas las canciones almacenadas

    fun obtenerTodosLosProductos(): List<Producto> {
        val listaProductos = mutableListOf<Producto>()

        dbHelper.readableDatabase.use { db ->
            val query = "SELECT * FROM ${BaseDeDatosHelper.TABLA_EMPRESA}"
            val cursor = db.rawQuery(query, null)

            cursor.use {
                // Iterar sobre el cursor y construir la lista de canciones
                if (it.moveToFirst()) {
                    do {
                        val id = it.getInt(it.getColumnIndexOrThrow("id"))
                        val empresaId = it.getInt(it.getColumnIndexOrThrow("empresaId"))
                        val nombre = it.getString(it.getColumnIndexOrThrow("nombre"))
                        val tipo = it.getString(it.getColumnIndexOrThrow("tipo"))
                        val cantidad = it.getInt(it.getColumnIndexOrThrow("cantidad"))
                        val precio = it.getInt(it.getColumnIndexOrThrow("precio"))

                        // Crear una instancia de Cancion y agregarla a la lista
                        val producto = Producto(id, empresaId, nombre, tipo, cantidad, precio)
                        listaProductos.add(producto)
                    } while (it.moveToNext())
                }
            }
        }

        return listaProductos
    }

    // Funcion para Obtener una Cancion por su ID

    fun obtenerProductoPorId(productoId: Int): Producto? {
        var producto: Producto? = null

        dbHelper.readableDatabase.use { db ->
            val query = "SELECT * FROM ${BaseDeDatosHelper.TABLA_PRODUCTO} WHERE id = ?"
            val selectionArgs = arrayOf(productoId.toString())
            val cursor = db.rawQuery(query, selectionArgs)

            cursor.use {
                if (it.moveToFirst()) {
                    val id = it.getInt(it.getColumnIndexOrThrow("id"))
                    val empresaId = it.getInt(it.getColumnIndexOrThrow("empresaId"))
                    val nombre = it.getString(it.getColumnIndexOrThrow("nombre"))
                    val tipo = it.getString(it.getColumnIndexOrThrow("tipo"))
                    val cantidad = it.getInt(it.getColumnIndexOrThrow("cantidad"))
                    val precio = it.getInt(it.getColumnIndexOrThrow("precio"))

                    // Crear una instancia de Cancion
                    producto = Producto(id, empresaId, nombre, tipo, cantidad, precio)
                }
            }
        }

        return producto
    }

    // Funcion para Actualizar una Cancion

    fun actualizarProducto(productoActualizada: Producto) {
        dbHelper.writableDatabase.use { db ->
            val values = ContentValues().apply {
                put("empresaId", productoActualizada.empresaId)
                put("nombre", productoActualizada.nombre)
                put("tipo", productoActualizada.tipo)
                put("cantidad", productoActualizada.cantidad)
                put("precio", productoActualizada.precio)
            }

            // Especificar la condición para la actualización (basada en el ID de la canción)
            val whereClause = "id = ?"
            val whereArgs = arrayOf(productoActualizada.id.toString())

            // Actualizar la canción en la base de datos
            db.update(BaseDeDatosHelper.TABLA_PRODUCTO, values, whereClause, whereArgs)
        }
    }

    // Funcion para Eliminar una cancion por su ID

    fun eliminarProductoPorId(idProducto: Int) {
        dbHelper.writableDatabase.use { db ->
            // Especificar la condición para la eliminación (basada en el ID de la canción)
            val whereClause = "id = ?"
            val whereArgs = arrayOf(idProducto.toString())

            // Eliminar la canción de la base de datos
            db.delete(BaseDeDatosHelper.TABLA_PRODUCTO, whereClause, whereArgs)
        }
    }

}