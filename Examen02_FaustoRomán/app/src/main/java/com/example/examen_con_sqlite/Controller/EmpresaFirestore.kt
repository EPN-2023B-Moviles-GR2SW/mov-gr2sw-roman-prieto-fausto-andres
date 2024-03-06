package com.example.examen_con_sqlite.Controller

import com.example.examen_con_sqlite.Model.Empresa
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class EmpresaFirestore {
    private val db = FirebaseFirestore.getInstance()

    companion object {
        fun crearEmpresaFromDocument(document: DocumentSnapshot): Empresa {
            val id = document.id
            val nombre = document.data?.get("nombre") as String?
            val ceo = document.data?.get("ceo") as String?
            val anioDeRegistro = document.data?.get("anioDeRegistro") as Long?
            val sector = document.data?.get("sector") as String?

            if (id == null || nombre == null || ceo == null || anioDeRegistro == null || sector == null) {
                return Empresa()
            }

            return Empresa(id, nombre, ceo, anioDeRegistro.toInt(), sector)
        }
    }

    fun obtenerTodosEmpresas(): Task<QuerySnapshot> {
        return db.collection("empresas").get()
    }

    fun obtenerUnEmpresa(id: String): Task<DocumentSnapshot> {
        return db.collection("empresas").document(id).get()
    }

    fun crearEmpresa(empresa: Empresa) {
        val empresaData = hashMapOf(
            "nombre" to empresa.nombre,
            "ceo" to empresa.ceo,
            "anioDeRegistro" to empresa.anioDeRegistro,
            "sector" to empresa.sector
        )

        db.collection("empresas").add(empresaData)
            .addOnSuccessListener { documentReference ->
                // Asignar el ID asignado por Firestore al álbum
                empresa.id = documentReference.id
            }
    }
    fun updateEmpresa(id: String, empresa: Empresa) {
        val empresaData = hashMapOf(
            "nombre" to empresa.nombre,
            "ceo" to empresa.ceo,
            "anioDeRegistro" to empresa.anioDeRegistro,
            "sector" to empresa.sector
        )

        db.collection("empresas").document(id).set(empresaData)
    }

    fun removeEmpresa(id: String) {
        db.collection("empresas").document(id).delete()
    }

    // Función para obtener todas las canciones de un álbum desde Firestore
    fun obtenerProductosPorEmpresaId(empresaId: String): Task<QuerySnapshot> {
        return db.collection("productos").whereEqualTo("empresaId", empresaId).get()
    }

    fun eliminarProductosAsociadasAlEmpresa(empresaId: String) {
        // Obtener y eliminar las canciones asociadas al álbum
        obtenerProductosPorEmpresaId(empresaId)
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val productoId = document.id
                    // Eliminar la canción
                    ProductoFirestore().removeProducto(productoId)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener la lista de canciones
            }
    }
}