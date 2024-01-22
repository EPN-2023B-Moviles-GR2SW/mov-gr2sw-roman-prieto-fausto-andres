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
import androidx.appcompat.app.AlertDialog

class VerEmpresa : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<String>
    lateinit var productos:MutableList<Producto>;
    var managerEmpresa = BaseDatosMemoria;
    var idEmpresa=-1;
    private var idItemSeleccionado = -1


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_empresa)

        val botonAgregarProducto = findViewById<Button>(R.id.id_btn_agregar_producto)
        botonAgregarProducto.setOnClickListener{
            irActividad(FormProducto1::class.java)
        }
        val botonRegresarEmpresas = findViewById<Button>(R.id.id_btn_empresa_atras)
        botonRegresarEmpresas.setOnClickListener{
            finish()
        }
        idEmpresa = intent.getIntExtra("idEmpresa",-1)
        actualizarlistViewProductos();
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        var inflater = menuInflater
        inflater.inflate(R.menu.menu_producto, menu)
        val infoProducto = menuInfo as AdapterView.AdapterContextMenuInfo;
        val productoId = infoProducto.position;
        if(productoId != null){
            idItemSeleccionado = productoId;
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_editar_producto->{
                try{
                    val idProducto = idItemSeleccionado;
                    irActividad(FormProducto1::class.java,idProducto,idEmpresa)
                }catch (e:Throwable){

                }
                return true;
            }
            R.id.menu_eliminar_producto ->{
                abrirDialogEliminarProducto()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogEliminarProducto(){
        val builderDialog = AlertDialog.Builder(this);
        builderDialog.setTitle("Desea eliminar?");
        builderDialog.setNegativeButton("No",null);
        builderDialog.setPositiveButton("Si"){
            dialog, _ ->
            if(idItemSeleccionado.let { BaseDatosMemoria.eliminarProducto(idEmpresa,it) }){
                actualizarlistViewProductos()
            }
        }
        val dialog = builderDialog.create();
        dialog.show()
    }

    fun actualizarlistViewProductos(){
        var empresa =  managerEmpresa.buscarEmpresaPorId(idEmpresa)!!;
        productos = managerEmpresa.obtenerProductos(empresa);
        val listViewProductos = findViewById<ListView>(R.id.id_list_view_productos)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            productos.mapIndexed { index, producto ->
                "${index}: Producto: ${producto.nombre}"
            }
        )
        listViewProductos.adapter = adaptador;
        adaptador.notifyDataSetChanged();
        registerForContextMenu(listViewProductos);
    }

    fun irActividad(clase:Class<*>){
        val intent = Intent(this,clase);
        intent.putExtra("idEmpresa",idEmpresa);
        actualizarlistViewProductos()
        startActivity(intent)
    }
    fun irActividad(clase:Class<*>,idProducto:Int?,idEmpresa: Int?){
        val intent = Intent(this,clase);
        intent.putExtra("idProducto",idProducto);
        intent.putExtra("idEmpresa",idEmpresa)
        actualizarlistViewProductos()
        startActivity(intent)
    }


    override fun onRestart() {
        super.onRestart()
        actualizarlistViewProductos()
    }

    override fun onResume() {
        super.onResume()
        actualizarlistViewProductos()
    }
}