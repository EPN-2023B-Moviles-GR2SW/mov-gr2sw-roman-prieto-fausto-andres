package com.example.examenb1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<String>;
    var idItemSeleccionado = 0
    var idBorrar =0;


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonCrearEmpresa = findViewById<Button>(R.id.id_btn_agregar_empresa)
        botonCrearEmpresa.setOnClickListener{
            irActividad(FormEmpresa::class.java)
        }
        //adaptador
        actualizarLitViewEmpresas();
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater.inflate(R.menu.menu_empresa, menu);
        val infoEmpresa = menuInfo as AdapterView.AdapterContextMenuInfo;
        val empresaId = infoEmpresa.position
        if(empresaId != null){
            idItemSeleccionado = empresaId
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_ver_empresa -> {
                try {
                    val idEmpresa = idItemSeleccionado;
                    irActividad(VerEmpresa::class.java,idEmpresa)
                }catch (e:Throwable){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
                return true;
            }
            R.id.menu_editar_empresa-> {
                try {
                    val idEmpresa = idItemSeleccionado;
                    irActividad(FormEmpresa::class.java,idEmpresa)
                }catch (e:Throwable){
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
                return true;
            }
            R.id.menu_eliminar_empresa -> {
                    abrirDialogEliminarEmpresa()
                return true;
            }

            else -> super.onContextItemSelected(item)
        }
    }

    fun irActividad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }

    fun irActividad(clase: Class<*>, id:Int?){
        val intent = Intent(this,clase)
        intent.putExtra("idEmpresa",id)
        startActivity(intent)
    }


    // --- dialogo de eliminar libro ---

    fun abrirDialogEliminarEmpresa(){
        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setTitle("Desea eliminar?")
        builderDialog.setNegativeButton("No",null);
        builderDialog.setPositiveButton("Si"){
            dialog,_ ->
            if(idItemSeleccionado.let { BaseDatosMemoria.eliminarEmpresa(it) }){
                actualizarLitViewEmpresas()
            }
        }
        val dialog = builderDialog.create();
        dialog.show();
    }


    // ---- Actualizacion de la lista ---

    fun actualizarLitViewEmpresas(){
        val listViewEmpresas = findViewById<ListView>(R.id.id_list_view_empresas)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            BaseDatosMemoria.empresas.mapIndexed { index, empresa ->
                "${index}: ${empresa.nombre}"
            }
        )
        listViewEmpresas.adapter = adaptador;
        adaptador.notifyDataSetChanged();
        registerForContextMenu(listViewEmpresas)
    }


    override fun onRestart() {
        super.onRestart()
        actualizarLitViewEmpresas()
    }

    override fun onResume() {
        super.onResume()
        actualizarLitViewEmpresas()
    }

}