package com.example.examen_con_sqlite.Controller

import com.example.examen_con_sqlite.Model.Producto
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ProductoFirestore {
    private val db = FirebaseFirestore.getInstance()

    //Función estática en la clase `Cancion` que crea y devuelve una instancia de `Cancion` a partir de un `DocumentSnapshot`.
    //Se utiliza para convertir datos recuperados de Firestore en objetos `Cancion` en la aplicación Kotlin
    companion object {
        fun createProductoFromDocument(document: DocumentSnapshot): Producto {
            val id = document.id
            val empresaId = document.data?.get("empresaId") as String?
            val nombre = document.data?.get("nombre") as String?
            val tipo = document.data?.get("tipo") as String?
            val cantidad = document.data?.get("cantidad") as Long?
            val precio = document.data?.get("precio") as Long?

            if (id == null || empresaId == null || nombre == null || tipo == null || cantidad == null || precio == null) {
                return Producto()
            }

            return Producto(id, empresaId, nombre, tipo, cantidad.toInt(), precio.toInt())
        }
    }

    fun obtenerTodasProductos(): Task<QuerySnapshot> {
        return db.collection("productos").get()
    }

    fun obtenerUnaProducto(id: String): Task<DocumentSnapshot> {
        return db.collection("productos").document(id).get()
    }

    fun crearProducto(producto: Producto) {
        val productoData = hashMapOf(
            "empresaId" to producto.empresaId,
            "nombre" to producto.nombre,
            "tipo" to producto.tipo,
            "cantidad" to producto.cantidad,
            "precio" to producto.precio
        )

        db.collection("productos").add(productoData)
            .addOnSuccessListener { documentReference ->
                // Asignar el ID asignado por Firestore a la canción
                producto.id = documentReference.id
                // Puedes hacer cualquier otra lógica aquí después de crear la canción en Firestore
            }
    }

    fun updateProducto(id: String, producto: Producto) {
        val productoData = hashMapOf(
            "empresaId" to producto.empresaId,
            "nombre" to producto.nombre,
            "tipo" to producto.tipo,
            "cantidad" to producto.cantidad,
            "precio" to producto.precio
        )

        db.collection("productos").document(id).set(productoData)
    }

    fun removeProducto(id: String) {
        db.collection("productos").document(id).delete()
    }

    fun obtenerProductosPorEmpresaId(empresaId: String): Task<QuerySnapshot> {
        return db.collection("productos")
            .whereEqualTo("empresaId", empresaId)
            .get()
    }
}