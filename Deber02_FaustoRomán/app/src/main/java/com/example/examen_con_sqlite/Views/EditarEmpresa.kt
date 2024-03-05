package com.example.examen_con_sqlite.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.examen_con_sqlite.Controller.EmpresaCRUD
import com.example.examen_con_sqlite.Model.Empresa
import com.example.examen_con_sqlite.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class EditarEmpresa : AppCompatActivity() {

    // Variable para almacenar el ID del álbum que se está editando
    private var empresaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_empresa)

        // Funcionalidad Botones

        val botonRegresarHome = findViewById<Button>(R.id.btn_Regresar_Editar_Empresa)
        botonRegresarHome.setOnClickListener{
            irActividad(MainActivity::class.java)
        }

        val botonActualizar = findViewById<Button>(R.id.btn_Actualizar_Empresa)
        botonActualizar.setOnClickListener {
            actualizarEmpresa()
        }



        // Obtener ID del álbum recibido del Intent
        empresaId = intent.getIntExtra("EMPRESA_ID", -1)

        // Verificar si se recibió un ID válido
        if (empresaId != -1) {
            // Cargar datos del álbum y llenar la interfaz
            cargarDatosDelEmpresa(empresaId)
        } else {
            // Manejar el caso en el que no se proporcionó un ID válido
            Toast.makeText(this, "No se proporcionó un ID de Empresa válido", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    // Funciones

    fun cargarDatosDelEmpresa(albumId: Int) {
        // Obtener el álbum de la base de datos o donde se almacene el Álbum
        val empresaAEditar = EmpresaCRUD(this).obtenerEmpresaPorId(empresaId)

        // Llenar la interfaz con los datos actuales del álbum
        empresaAEditar?.let {
            findViewById<EditText>(R.id.input_Editar_Nombre_Empresa).setText(it.nombre)
            findViewById<EditText>(R.id.input_Editar_Ceo_Empresa).setText(it.ceo)
            findViewById<EditText>(R.id.input_Editar_Anio_Empresa).setText(it.anioDeRegistro.toString())
            findViewById<EditText>(R.id.input_Editar_Sector_Empresa).setText(it.sector)
        }
    }

    private fun actualizarEmpresa() {
        // Obtener nuevos valores de la interfaz de usuario
        val nuevoNombre = findViewById<TextInputEditText>(R.id.input_Editar_Nombre_Empresa).text.toString()
        val nuevoCeo = findViewById<TextInputEditText>(R.id.input_Editar_Ceo_Empresa).text.toString()
        val nuevoAnioDeRegistro = findViewById<TextInputEditText>(R.id.input_Editar_Anio_Empresa).text.toString().toIntOrNull() ?: 0
        val sector = findViewById<TextInputEditText>(R.id.input_Editar_Ceo_Empresa).text.toString()



        // Validar que no haya campos vacíos
        if (nuevoNombre.isBlank() || nuevoCeo.isBlank() || sector.isBlank()) {
            // Mostrar mensaje de error en un Snackbar
            mostrarSnackbarError("Completa todos los campos.")
        } else {
            // Crear un objeto Album con los nuevos valores
            val empresaActualizado = Empresa(
                id = empresaId,
                nombre = nuevoNombre,
                ceo = nuevoCeo,
                anioDeRegistro = nuevoAnioDeRegistro,
                sector = sector
            )

            // Actualizar el álbum utilizando AlbumCRUD
            EmpresaCRUD(this).updateEmpresa(empresaActualizado)

            //Regresar a Main Activity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("EMPRESA_ACTUALIZADO", "Empresa actualizada: $nuevoNombre")
            startActivity(intent)

        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    // SnackBar
    fun mostrarSnackbarError(mensaje: String) {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }
}

