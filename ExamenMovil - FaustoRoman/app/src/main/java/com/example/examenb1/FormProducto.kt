package com.joxxx69.examenb1

import Receta
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class FormReceta : AppCompatActivity() {
    var managerLibro = BaseDatosMemoria;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_producto)
        llenarDatos();
        val botonGuardarReceta = findViewById<Button>(R.id.id_btn_guardar_receta)
        botonGuardarReceta.setOnClickListener{
            guardarReceta()
            finish()
//            val nuevoIntent = Intent(this,VerLibro::class.java);
//            startActivity(nuevoIntent);
        }

        val botonCancelarGuardado = findViewById<Button>(R.id.id_btn_cancelar_receta)
        botonCancelarGuardado.setOnClickListener{
            finish()
        }

    }

    fun guardarReceta(){
        val nombreInput = findViewById<EditText>(R.id.id_text_nombre)
        val nacionalidadInput = findViewById<EditText>(R.id.id_text_nacionalidad)
        val tiempoInput = findViewById<EditText>(R.id.id_text_tiempo)
        val ingredientesInput = findViewById<EditText>(R.id.id_text_ingredientes)
        val pasosInput = findViewById<EditText>(R.id.id_text_pasos)
        var receta = Receta(
            nombre = nombreInput.text.toString(),
            nacionalidad = nacionalidadInput.text.toString(),
            tiempoPreparacion = tiempoInput.text.toString().toInt(),
            ingredientes = ingredientesInput.text.toString().split(",").map { it.trim() }.toMutableList(),
            pasos = pasosInput.text.toString().split(",").map { it.trim() }.toMutableList()
        )
        val idLibro = intent.getIntExtra("idLibro",-1);
        val idReceta = intent.getIntExtra("idReceta",-1);
        if(idLibro != -1 && idReceta !=-1){

            managerLibro.actualizarReceta(idLibro,idReceta, receta);
            setResult(RESULT_OK);
        }else{
            managerLibro.agregarReceta(idLibro,receta)
            setResult(RESULT_OK);
        }
    }

    fun llenarDatos(){

        val formTitulo = findViewById<TextView>(R.id.id_text_view_form_receta)
        val nombreInput = findViewById<EditText>(R.id.id_text_nombre)
        val nacionalidadInput = findViewById<EditText>(R.id.id_text_nacionalidad)
        val tiempoInput = findViewById<EditText>(R.id.id_text_tiempo)
        val ingredientesInput = findViewById<EditText>(R.id.id_text_ingredientes)
        val pasosInput = findViewById<EditText>(R.id.id_text_pasos)
        val idLibro = intent.getIntExtra("idLibro",-1);
        val idReceta = intent.getIntExtra("idReceta",-1);

        if(idLibro != -1 && idReceta !=-1){
            formTitulo.text = "Actualizar Receta"
            val libroDB = managerLibro.buscarLibroPorId(idLibro);
            val receta = libroDB!!.recetas.get(idReceta);
            nombreInput.setText(receta.nombre.toString())
            nacionalidadInput.setText(receta.nacionalidad.toString())
            tiempoInput.setText(receta.tiempoPreparacion.toString())
            ingredientesInput.setText(receta.ingredientes.joinToString(separator = ",").toString())
            pasosInput.setText(receta.pasos.joinToString(separator = ",").toString())
        }

    }
}