package com.example.examen_con_sqlite.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.examen_con_sqlite.Controller.EmpresaCRUD
import com.example.examen_con_sqlite.Model.Empresa
import com.example.examen_con_sqlite.R
import com.google.android.material.snackbar.Snackbar

class AgregarEmpresa : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_empresa)

        // Obtener referencias a los elementos de la interfaz

        val inputNombreEmpresa = findViewById<EditText>(R.id.input_Nombre_Empresa)
        val inputCeoEmpresa = findViewById<EditText>(R.id.input_Ceo_Empresa)
        val inputAnioRegistroEmpresa = findViewById<EditText>(R.id.input_Anio_Empresa)
        val inputSectorEmpresa = findViewById<EditText>(R.id.input_Sector_Empresa)


        // Funcionalidad Botones

        val botonRegresarHome = findViewById<Button>(R.id.btn_Regresar_Home)
        botonRegresarHome.setOnClickListener{
            irActividad(MainActivity::class.java)
        }


        val botonCrearEmpresa = findViewById<Button>(R.id.btn_Crear_Empresa)
        botonCrearEmpresa.setOnClickListener {
            // Obtener los valores ingresados por el usuario
            val nombreEmpresa = inputNombreEmpresa.text.toString()
            val ceoEmpresa = inputCeoEmpresa.text.toString()
            val anioEmpresa = inputAnioRegistroEmpresa.text.toString().toIntOrNull() ?: 0
            val sectorEmpresa = inputSectorEmpresa.text.toString()

            // Validar que no haya campos vacíos
            if (nombreEmpresa.isBlank() || ceoEmpresa.isBlank() || sectorEmpresa.isBlank()) {
                // Mostrar mensaje de error en un Snackbar
                mostrarSnackbarError("Completa todos los campos.")
            } else {
                // Crear un nuevo objeto Album
                val nuevoEmpresa = Empresa(
                    nombreEmpresa,
                    ceoEmpresa,
                    anioEmpresa,
                    sectorEmpresa
                )

                // Utilizar la función para agregar el nuevo álbum
                EmpresaCRUD(this).crearAlbum(nuevoEmpresa)

                // Regresa a Main Activity
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("NOMBRE_EMPRESA_AGREGADA", nombreEmpresa)
                startActivity(intent)
            }
        }
    }


    // Funcion
    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    // SnackBar
    private fun mostrarSnackbarError(mensaje: String) {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }
}