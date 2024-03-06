package com.example.examen_con_sqlite.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.examen_con_sqlite.Controller.ProductoFirestore
import com.example.examen_con_sqlite.Model.Producto
import com.example.examen_con_sqlite.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class EditarProducto : AppCompatActivity() {

    // Variables para almacenar el ID del álbum y canción que se están editando
    private var empresaId: String = ""
    private var productoId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)

        // Botones
        val botonRegresar = findViewById<Button>(R.id.btn_Regresar_Listado_Productos_Editar)
        botonRegresar.setOnClickListener {
            irActividadConID(VerProductos::class.java, empresaId)
        }

        val botonActualizar = findViewById<Button>(R.id.btn_Actualizar_Producto)
        botonActualizar.setOnClickListener {
            actualizarProducto()
        }

        // Obtener el ID del álbum desde el Intent
        empresaId = intent.getStringExtra("EMPRESA_ID") ?: ""
        // Obtener el ID de la canción desde el Intent
        productoId = intent.getStringExtra("PRODUCTO_ID") ?: ""

        // Verificar si se recibió un ID válido
        if (empresaId.isNotEmpty() && productoId.isNotEmpty()) {
            // Cargar datos del álbum y llenar la interfaz
            cargarDatosDeLaProducto(empresaId, productoId)
        }else{
            // Manejar el caso en el que no se proporcionó un ID válido
            Toast.makeText(this, "No se proporcionó un ID de empresa y/o producto válido", Toast.LENGTH_SHORT).show()
            finish() // Cerrar la actividad si no hay ID válido
        }
    }
    // Funciones

    private fun irActividadConIDs(clase: Class<*>, idEmpresa: Int, idProducto: Int) {
        val intent = Intent(this, clase)
        intent.putExtra("EMPRESA_ID", idEmpresa)
        intent.putExtra("PRODUCTO_ID", idProducto)
        startActivity(intent)
    }

    private fun irActividadConID(clase: Class<*>, idProducto: String) {
        val intent = Intent(this, clase)
        intent.putExtra("EMPRESA_ID", empresaId)
        intent.putExtra("PRODUCTO_ID", idProducto)
        startActivity(intent)
    }

    private fun cargarDatosDeLaProducto(empresaId: String, productoId: String) {
        ProductoFirestore().obtenerUnaProducto(productoId)
            .addOnSuccessListener { documentSnapshot ->
                val producto = if (documentSnapshot.exists()) {
                    ProductoFirestore.createProductoFromDocument(documentSnapshot)
                } else {
                    null
                }
                // Obtener la canción de la base de datos o donde se almacena las canciones
                // Llenar la interfaz con los datos actuales de la canción
                producto?.let {
                    findViewById<EditText>(R.id.input_Editar_Nombre_Producto).setText(it.nombre)
                    findViewById<EditText>(R.id.input_Editar_Cantidad_Producto).setText(it.cantidad.toString())
                    findViewById<EditText>(R.id.input_Editar_Precio_Producto).setText(it.precio.toString())
                    findViewById<EditText>(R.id.input_Editar_Tipo_Producto).setText(it.tipo)

                }
            }
            .addOnFailureListener { e ->
                // Manejar el fallo, por ejemplo, mostrar un mensaje al usuario
                Toast.makeText(this, "Error al cargar datos de la producto", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
    }

    private fun actualizarProducto() {
        // Obtener nuevos valores de la interfaz de usuario
        val nuevoNombre = findViewById<TextInputEditText>(R.id.input_Editar_Nombre_Producto).text.toString()
        val nuevaCantidad = findViewById<TextInputEditText>(R.id.input_Editar_Cantidad_Producto).text.toString().toIntOrNull() ?: 0
        val nuevoPrecio = findViewById<TextInputEditText>(R.id.input_Editar_Precio_Producto).text.toString().toIntOrNull() ?: 0
        val nuevoTipo = findViewById<TextInputEditText>(R.id.input_Editar_Tipo_Producto).text.toString()


        // Validar que no haya campos vacíos
        if (nuevoNombre.isBlank() || nuevoTipo.isBlank()) {
            mostrarSnackbarError("Completa todos los campos.")
        }else{
            // Crear un objeto Cancion con los nuevos valores
            val productoActualizada = Producto(
                id = productoId,
                empresaId = empresaId,
                nombre = nuevoNombre,
                tipo = nuevoTipo,
                cantidad = nuevaCantidad,
                precio = nuevoPrecio
            )
            // Actualizar la canción utilizando CancionCRUD
            ProductoFirestore().updateProducto(productoId, productoActualizada)

            // Regresar a VerCanciones
            irActividadConID(VerProductos::class.java, productoId)
        }
    }

    // SnackBar
    private fun mostrarSnackbarError(mensaje: String) {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }

}