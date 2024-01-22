package com.example.examenb1

import Empresa
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class FormEmpresa : AppCompatActivity() {
    var managerEmpresa = BaseDatosMemoria;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_empresa)
        llenarDatos();
        val botonGuardarEmpresa = findViewById<Button>(R.id.id_btn_guardar)
        botonGuardarEmpresa.setOnClickListener {
            try {
                guardarEmpresa();
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
            val nuevoIntent = Intent(this, MainActivity::class.java)
            startActivity(nuevoIntent)
        }

        val botonCancelarEmpresa = findViewById<Button>(R.id.id_btn_empresa_cancelar)
        botonCancelarEmpresa.setOnClickListener { finish() }


    }


    fun guardarEmpresa(){
        val nombreInput =findViewById<EditText>(R.id.id_text_nombre);
        val ceoInput =findViewById<EditText>(R.id.id_text_ceo);
        val anioDeRegistroInput =findViewById<EditText>(R.id.id_text_cantidad);
        val sectorInput =findViewById<EditText>(R.id.id_text_sector);
        val idEmpresa = intent.getIntExtra("idEmpresa",-1);
        var empresa = Empresa(
            nombre= nombreInput.text.toString(),
            ceo= ceoInput.text.toString(),
            anioDeRegistro = anioDeRegistroInput.text.toString().toInt(),
            sector = sectorInput.text.toString()
        )
        if(idEmpresa == -1){
            managerEmpresa.agregarEmpresa(empresa);
        }else{
            managerEmpresa.actualizarempresa(idEmpresa,empresa);
        }



    }
    fun llenarDatos(){
        val formTitulo = findViewById<TextView>(R.id.id_text_view_form_empresa)
        val nombreInput =findViewById<EditText>(R.id.id_text_nombre);
        val ceoInput =findViewById<EditText>(R.id.id_text_ceo);
        val anioDeRegistroInput =findViewById<EditText>(R.id.id_text_cantidad);
        val sectorInput =findViewById<EditText>(R.id.id_text_sector);
        val idEmpresa = intent.getIntExtra("idEmpresa",-1);
        if(idEmpresa != -1){
            val empresaDB = managerEmpresa.buscarEmpresaPorId(idEmpresa);
            if(empresaDB!==null){
                formTitulo.text = "Actualizar Empresa"
                nombreInput.setText(empresaDB.nombre.toString());
                ceoInput.setText(empresaDB.ceo.toString());
                anioDeRegistroInput.setText(empresaDB.anioDeRegistro.toString())
                sectorInput.setText(empresaDB.sector.toString())
            }
        }

    }
}