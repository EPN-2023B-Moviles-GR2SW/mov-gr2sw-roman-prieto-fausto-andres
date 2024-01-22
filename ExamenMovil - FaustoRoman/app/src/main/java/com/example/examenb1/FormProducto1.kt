package com.example.examenb1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class FormProducto1 : AppCompatActivity() {
    var managerEmpresa = BaseDatosMemoria;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_producto)
        llenarDatos();
        val botonGuardarProducto = findViewById<Button>(R.id.id_btn_guardar_producto)
        botonGuardarProducto.setOnClickListener{
            guardarProducto()
            finish()
        }

        val botonCancelarGuardado = findViewById<Button>(R.id.id_btn_cancelar_producto)
        botonCancelarGuardado.setOnClickListener{
            finish()
        }

    }

    fun guardarProducto(){
        val nombreInput = findViewById<EditText>(R.id.id_text_nombre)
        val tipoInput = findViewById<EditText>(R.id.id_text_tipo)
        val cantidadInput = findViewById<EditText>(R.id.id_text_cantidad)
        val precioInput = findViewById<EditText>(R.id.id_text_precio)
        var producto = Producto(
            nombre = nombreInput.text.toString(),
            tipo = tipoInput.text.toString(),
            cantidad = cantidadInput.text.toString().toInt(),
            precio = precioInput.text.toString().toInt()
        )
        val idEmpresa = intent.getIntExtra("idEmpresa",-1);
        val idProducto = intent.getIntExtra("idProducto",-1);
        if(idEmpresa != -1 && idProducto !=-1){

            managerEmpresa.actualizarProducto(idEmpresa,idProducto, producto);
            setResult(RESULT_OK);
        }else{
            managerEmpresa.agregarProducto(idEmpresa,producto)
            setResult(RESULT_OK);
        }
    }

    fun llenarDatos(){

        val formTitulo = findViewById<TextView>(R.id.id_text_view_form_producto)
        val nombreInput = findViewById<EditText>(R.id.id_text_nombre)
        val tipoInput = findViewById<EditText>(R.id.id_text_tipo)
        val cantidadInput = findViewById<EditText>(R.id.id_text_cantidad)
        val precioInput = findViewById<EditText>(R.id.id_text_precio)
        val idEmpresa = intent.getIntExtra("idEmpresa",-1);
        val idProducto = intent.getIntExtra("idProducto",-1);

        if(idEmpresa != -1 && idProducto !=-1){
            formTitulo.text = "Actualizar Producto"
            val empresaDB = managerEmpresa.buscarEmpresaPorId(idEmpresa);
            val producto = empresaDB!!.productos.get(idProducto);
            nombreInput.setText(producto.nombre.toString())
            tipoInput.setText(producto.tipo.toString())
            cantidadInput.setText(producto.cantidad.toString())
            precioInput.setText(producto.precio.toString())
        }

    }
}