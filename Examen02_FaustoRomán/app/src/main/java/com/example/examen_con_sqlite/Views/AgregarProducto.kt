package com.example.examen_con_sqlite.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import com.example.examen_con_sqlite.Controller.ProductoFirestore
import com.example.examen_con_sqlite.Model.Producto
import com.example.examen_con_sqlite.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class AgregarProducto : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_producto)

        // Funcionalidad Botones
        val botonRegresarListaProductos = findViewById<Button>(R.id.btn_Regresar_Listado_Productos)
        botonRegresarListaProductos.setOnClickListener {
            val empresaId = intent.getStringExtra("EMPRESA_ID") ?: ""
            irActividad(VerProductos::class.java, empresaId)
        }

        val botonCrearProducto = findViewById<Button>(R.id.btn_Crear_Producto)
        botonCrearProducto.setOnClickListener {
            crearNuevaProducto()
        }

    }


    // Funciones
    fun irActividad(clase: Class<*>, empresaId: String) {
        val intent = Intent(this, clase)
        intent.putExtra("EMPRESA_ID", empresaId)
        startActivity(intent)
    }

    fun crearNuevaProducto() {
        // Obtener datos ingresados por el Usuario
        val nombre = findViewById<TextInputEditText>(R.id.input_Nombre_Producto).text.toString()
        val tipo = findViewById<TextInputEditText>(R.id.input_Tipo_Producto).text.toString()
        val cantidad = findViewById<TextInputEditText>(R.id.input_Cantidad_Producto).text.toString().toIntOrNull() ?: 0
        val precio = findViewById<TextInputEditText>(R.id.input_Precio_Producto).text.toString().toIntOrNull() ?: 0

        // Obtener el ID del álbum desde el Intent
        val empresaId = intent.getStringExtra("EMPRESA_ID") ?: ""

        // Validar que no haya campos vacíos
        if (nombre.isBlank() || tipo.isBlank()) {
            // Mostrar mensaje de error en un Snackbar
            mostrarSnackbarError("Completa todos los campos.")
            return
        }

        // Crear una nueva instancia de la Cancion
        val nuevaProducto = Producto(
            id = "",
            empresaId = empresaId,
            nombre = nombre,
            tipo = tipo,
            cantidad = cantidad,
            precio = precio
        )

        // Agregar la nueva canción a la base de datos
        ProductoFirestore().crearProducto(nuevaProducto)

        // Regresar a la actividad de ver canciones del álbum
        val intent = Intent(this, VerProductos::class.java)
        intent.putExtra("EMPRESA_ID", empresaId)
        intent.putExtra("NOMBRE_PRODUCTO_AGREGADA", nombre) // Agregar el nombre como extra
        startActivity(intent)
    }


    // SnackBar
    private fun mostrarSnackbar(mensaje: String) {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }

    private fun mostrarSnackbarError(mensaje: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_LONG).show()
    }
}
