package com.example.examen_con_sqlite.Controller

import android.content.ContentValues
import android.content.Context
import com.example.examen_con_sqlite.Database.BaseDeDatosHelper
import com.example.examen_con_sqlite.Model.Empresa
import com.example.examen_con_sqlite.Model.Producto

class EmpresaCRUD(context: Context) {

    private val dbHelper: BaseDeDatosHelper = BaseDeDatosHelper(context)

    // Operaciones CRUD para trabajar con Album en la Base de Datos

    // Funcion para Crear un Album con la Base de Datos

    fun crearAlbum(empresa: Empresa){

        // Obtener la base de datos en modo escritura
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put("nombre", empresa.nombre)
            put("ceo", empresa.ceo)
            put("anioDeRegistro", empresa.anioDeRegistro)
            put ("sector", empresa.sector)
        }

        // Inserta el nuevo álbum y obtiene su ID
        val nuevoId = db.insert(BaseDeDatosHelper.TABLA_EMPRESA, null, values)

        // Asigna el nuevo ID al álbum
        empresa.id = nuevoId.toInt()

        // Cerrar la conexion
        db.close()
    }

    // Funcion para Obtener Todos los Albumes

    fun obtenerTodos(): List<Empresa> {
        val listaEmpresas = mutableListOf<Empresa>()

        dbHelper.readableDatabase.use { db ->
            val query = "SELECT * FROM ${BaseDeDatosHelper.TABLA_EMPRESA}"
            val cursor = db.rawQuery(query, null)

            cursor.use {
                // Iterar sobre el cursor y construir la lista de álbumes
                if (it.moveToFirst()) {
                    do {
                        val id = it.getInt(it.getColumnIndexOrThrow("id"))
                        val nombre = it.getString(it.getColumnIndexOrThrow("nombre"))
                        val ceo = it.getString(it.getColumnIndexOrThrow("ceo"))
                        val anioDeRegistro = it.getInt(it.getColumnIndexOrThrow("anioDeRegistro"))
                        val sector = it.getString(it.getColumnIndexOrThrow("sector"))


                        // Crear una instancia de Album y agregarla a la lista
                        val empresa = Empresa(id, nombre, ceo, anioDeRegistro, sector)
                        listaEmpresas.add(empresa)
                    } while (it.moveToNext())
                }
            }
        }

        return listaEmpresas
    }

    // Funcion para obtener un Album por su ID

    fun obtenerEmpresaPorId(empresaId: Int): Empresa? {
        var empresa: Empresa? = null

        dbHelper.readableDatabase.use { db ->
            val query = "SELECT * FROM ${BaseDeDatosHelper.TABLA_EMPRESA} WHERE id = ?"
            val cursor = db.rawQuery(query, arrayOf(empresaId.toString()))

            cursor.use {
                if (it.moveToFirst()) {
                    val id = it.getInt(it.getColumnIndexOrThrow("id"))
                    val nombre = it.getString(it.getColumnIndexOrThrow("nombre"))
                    val ceo = it.getString(it.getColumnIndexOrThrow("ceo"))
                    val anioDeRegistro = it.getInt(it.getColumnIndexOrThrow("anioDeRegistro"))
                    val sector = it.getString(it.getColumnIndexOrThrow("sector"))

                    empresa = Empresa(id, nombre, ceo, anioDeRegistro, sector)
                }
            }
        }

        return empresa
    }

    // Funcion para Actualizar un Album

    fun updateEmpresa(empresaActualizado: Empresa) {
        dbHelper.writableDatabase.use { db ->
            val values = ContentValues().apply {
                put("nombre", empresaActualizado.nombre)
                put("ceo", empresaActualizado.ceo)
                put("anioDeRegistro", empresaActualizado.anioDeRegistro)
                put("sector", empresaActualizado.sector)

            }

            // Especificar la condición para la actualización (basada en el ID del álbum)
            val whereClause = "id = ?"
            val whereArgs = arrayOf(empresaActualizado.id.toString())

            // Actualizar el álbum en la base de datos
            val filasActualizadas = db.update(BaseDeDatosHelper.TABLA_EMPRESA, values, whereClause, whereArgs)

        }
    }

    // Funcion para Borrar un Album por su ID

    fun borrarEmpresaPorId(empresaId: Int) {
        val db = dbHelper.writableDatabase


        val whereClause = "id = ?"
        val whereArgs = arrayOf(empresaId.toString())

        // Borrar el álbum de la base de datos
        db.delete(BaseDeDatosHelper.TABLA_EMPRESA, whereClause, whereArgs)

        // Cerrar la conexión a la base de datos
        db.close()
    }

    // Funcion Auxiliar para obtener todas las Canciones del Album

    fun obtenerProductosPorEmpresaId(empresaId: Int): List<Producto> {
        val listaProductos = mutableListOf<Producto>()

        dbHelper.readableDatabase.use { db ->
            val query = "SELECT * FROM ${BaseDeDatosHelper.TABLA_PRODUCTO} WHERE empresaId = ?"
            val cursor = db.rawQuery(query, arrayOf(empresaId.toString()))

            cursor.use {
                // Iterar sobre el cursor y construir la lista de canciones
                if (it.moveToFirst()) {
                    do {
                        val id = it.getInt(it.getColumnIndexOrThrow("id"))
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

}